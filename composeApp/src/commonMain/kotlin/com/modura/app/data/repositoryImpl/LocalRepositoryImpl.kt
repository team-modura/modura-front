package com.modura.app.data.repositoryImpl

import com.modura.app.domain.repository.LocalRepository
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

private const val KEY_LOCAL = "local"

class LocalRepositoryImpl(
    private val settings: Settings
): LocalRepository{

    override fun getRecentSearches(): List<String> =
        settings.decodeValue(ListSerializer(String.serializer()),KEY_LOCAL,defaultValue = emptyList())

    override fun addSearchTerm(searchTerm: String) {
        val currentSearches = getRecentSearches().toMutableList()
        currentSearches.remove(searchTerm)
        currentSearches.add(0, searchTerm)
        val updatedSearches = currentSearches.take(10)
        settings.encodeValue(ListSerializer(String.serializer()),KEY_LOCAL,updatedSearches)
    }

    override fun removeSearchTerm(searchTerm: String) {
        val currentSearches = getRecentSearches().toMutableList()
        currentSearches.remove(searchTerm)
        settings.encodeValue(ListSerializer(String.serializer()),KEY_LOCAL,currentSearches)
    }

}