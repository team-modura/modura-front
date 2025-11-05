package com.modura.app.util.platform

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toPixelMap
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

object ImageComparator {
    private const val HISTOGRAM_BINS = 32
    private const val PALETTE_MAX_ITERATIONS = 5
    private const val PALETTE_SAMPLE_RATE = 10

    // --- 1. 색감 유사도 (히스토그램) ---
    fun calculateSimilarity(bmp1: ImageBitmap, bmp2: ImageBitmap): Double {

        val hist1 = createHistogram(bmp1)
        val hist2 = createHistogram(bmp2)

        var intersection = 0.0
        for (i in hist1.indices) {
            intersection += min(hist1[i], hist2[i])
        }

        return intersection * 100.0
    }
    private fun createHistogram(bitmap: ImageBitmap): DoubleArray {
        val histogram = DoubleArray(HISTOGRAM_BINS * 3)
        val pixelMap = bitmap.toPixelMap()
        val totalPixels = pixelMap.width * pixelMap.height

        for (y in 0 until pixelMap.height) {
            for (x in 0 until pixelMap.width) {
                val color = pixelMap[x, y]

                val rIndex = (color.red * (HISTOGRAM_BINS - 1)).toInt()
                val gIndex = (color.green * (HISTOGRAM_BINS - 1)).toInt() + HISTOGRAM_BINS
                val bIndex = (color.blue * (HISTOGRAM_BINS - 1)).toInt() + HISTOGRAM_BINS * 2

                histogram[rIndex]++
                histogram[gIndex]++
                histogram[bIndex]++
            }
        }

        for (i in histogram.indices) {
            histogram[i] = histogram[i] / (totalPixels * 3)
        }

        return histogram
    }

    // --- 2. 색 구성 유사도 (팔레트) ---
    fun extractPalette(bitmap: ImageBitmap, count: Int): List<Color> {
        val pixels = mutableListOf<Color>()
        val pixelMap = bitmap.toPixelMap()
        for (y in 0 until pixelMap.height step PALETTE_SAMPLE_RATE) {
            for (x in 0 until pixelMap.width step PALETTE_SAMPLE_RATE) {
                pixels.add(pixelMap[x, y])
            }
        }
        if (pixels.isEmpty()) return emptyList()

        var centroids = pixels.shuffled().take(count).toMutableList()
        val clusters = Array(count) { mutableListOf<Color>() }

        repeat(PALETTE_MAX_ITERATIONS) {
            clusters.forEach { it.clear() }
            for (pixel in pixels) {
                val nearestCentroidIndex = centroids.indices.minByOrNull { colorDistance(pixel, centroids[it]) }!!
                clusters[nearestCentroidIndex].add(pixel)
            }
            for (i in clusters.indices) {
                if (clusters[i].isNotEmpty()) {
                    centroids[i] = averageColor(clusters[i])
                }
            }
        }
        return centroids
    }

    fun calculatePaletteSimilarity(palette1: List<Color>, palette2: List<Color>): Double {
        if (palette1.isEmpty() || palette2.isEmpty()) return 0.0
        var totalDistance = 0.0
        val numColors = min(palette1.size, palette2.size)
        for (i in 0 until numColors) {
            val dist1 = palette2.minOf { colorDistance(palette1[i], it) }
            val dist2 = palette1.minOf { colorDistance(palette2[i], it) }
            totalDistance += (dist1 + dist2) / 2.0
        }
        val maxPossibleDistance = sqrt(3.0)
        val averageDistance = totalDistance / numColors
        val similarity = (1.0 - (averageDistance / maxPossibleDistance)).coerceIn(0.0, 1.0)
        return similarity * 100.0
    }

    private fun colorDistance(c1: Color, c2: Color): Double {
        val rDiff = (c1.red - c2.red).toDouble()
        val gDiff = (c1.green - c2.green).toDouble()
        val bDiff = (c1.blue - c2.blue).toDouble()
        return sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff)
    }

    private fun averageColor(colors: List<Color>): Color {
        if (colors.isEmpty()) return Color.Black
        var r = 0f
        var g = 0f
        var b = 0f
        colors.forEach {
            r += it.red
            g += it.green
            b += it.blue
        }
        return Color(r / colors.size, g / colors.size, b / colors.size)
    }

    // --- 3. 구도 유사도 (밝기 분포) ---
    fun calculateStructuralSimilarity(bmp1: ImageBitmap, bmp2: ImageBitmap): Double {
        val GRID_SIZE = 3
        val avgLuminance1 = getAverageLuminanceGrid(bmp1, GRID_SIZE)
        val avgLuminance2 = getAverageLuminanceGrid(bmp2, GRID_SIZE)
        var totalDifference = 0.0
        for (i in avgLuminance1.indices) {
            totalDifference += abs(avgLuminance1[i] - avgLuminance2[i])
        }
        val similarity = 1.0 - (totalDifference / avgLuminance1.size)
        return similarity.coerceIn(0.0, 1.0) * 100.0
    }

    private fun getAverageLuminanceGrid(bitmap: ImageBitmap, gridSize: Int): DoubleArray {
        val gridLuminances = DoubleArray(gridSize * gridSize)
        val gridPixelCounts = IntArray(gridSize * gridSize)
        val pixelMap = bitmap.toPixelMap()
        val cellWidth = pixelMap.width / gridSize
        val cellHeight = pixelMap.height / gridSize

        for (y in 0 until pixelMap.height) {
            for (x in 0 until pixelMap.width) {
                val gridX = (x / cellWidth).coerceAtMost(gridSize - 1)
                val gridY = (y / cellHeight).coerceAtMost(gridSize - 1)
                val gridIndex = (gridY * gridSize) + gridX
                gridLuminances[gridIndex] += pixelMap[x, y].luminance().toDouble()
                gridPixelCounts[gridIndex]++
            }
        }
        for (i in gridLuminances.indices) {
            if (gridPixelCounts[i] > 0) {
                gridLuminances[i] /= gridPixelCounts[i]
            }
        }
        return gridLuminances
    }

    // --- 4. 선명도 (라플라시안 분산) ---
    fun calculateClarity(bitmap: ImageBitmap): Double {
        val pixelMap = bitmap.toPixelMap()
        var sumOfVariances = 0.0
        val laplacianKernel = arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(1, -4, 1),
            intArrayOf(0, 1, 0)
        )
        for (y in 1 until pixelMap.height - 1) {
            for (x in 1 until pixelMap.width - 1) {
                var sum = 0.0
                for (i in -1..1) {
                    for (j in -1..1) {
                        val pixel = pixelMap[x + j, y + i]
                        val grayscale = pixel.red * 0.299 + pixel.green * 0.587 + pixel.blue * 0.114
                        sum += grayscale * laplacianKernel[i + 1][j + 1]
                    }
                }
                sumOfVariances += abs(sum)
            }
        }
        val totalPixels = (pixelMap.width * pixelMap.height).toDouble()

        if (totalPixels == 0.0) return 0.0
        val variance = sumOfVariances / totalPixels

        val normalizationFactor = 0.1
        var normalizedScore = (variance / normalizationFactor).coerceIn(0.0, 1.0)

        return normalizedScore * 100.0
    }
}