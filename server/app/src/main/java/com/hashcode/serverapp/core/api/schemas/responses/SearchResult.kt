package com.hashcode.serverapp.core.api.schemas.responses

data class SearchResult(
    val results:List<UserWithLastMessage>
)