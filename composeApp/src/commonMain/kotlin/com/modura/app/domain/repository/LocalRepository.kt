package com.modura.app.domain.repository

interface LocalRepository {
    fun getRecentSearches(): List<String>
    fun addSearchTerm(searchTerm: String)
    fun removeSearchTerm(searchTerm: String)
}