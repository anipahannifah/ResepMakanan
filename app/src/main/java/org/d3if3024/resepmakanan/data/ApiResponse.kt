package org.d3if3024.resepmakanan.data

data class ApiResponse(
    val activeFilterOptions: List<Any>,
    val expires: Long,
    val filterMapping: FilterMapping,
    val filterOptions: List<Any>,
    val limit: Int,
    val offset: Int,
    val query: String,
    val searchResults: List<SearchResult>,
    val sorting: String,
    val totalResults: Int
)

class FilterMapping(
)

data class SearchResult(
    val name: String,
    val results: List<Result>,
    val totalResults: Int,
    val totalResultsVariants: Int,
    val type: String
)

data class Result(
    val content: String,
    val dataPoints: List<Any>,
    val id: Int,
    val image: String,
    val link: String,
    val name: String,
    val relevance: Int,
    val type: String
)