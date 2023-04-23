package com.zinc.berrybucket.ui.presentation.detail.model

data class MentionSearchInfo(
    val searchText: String = "",
    val startIndex: Int = 0,
    val endIndex: Int = 0,
    val taggedList: List<TaggedTextInfo>
)

data class TaggedTextInfo(
    val id: String,
    val text: String,
    val startIndex: Int,
    val endIndex: Int
)