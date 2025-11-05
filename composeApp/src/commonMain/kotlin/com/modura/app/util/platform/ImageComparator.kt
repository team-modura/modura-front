package com.modura.app.util.platform

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toPixelMap
import kotlin.math.min

object ImageComparator {
    private const val HISTOGRAM_BINS = 32

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
}