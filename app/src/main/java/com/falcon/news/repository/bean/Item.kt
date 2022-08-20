package com.falcon.news.repository.bean

data class Item(
    val _meta: Meta,
    val appearance: Appearance,
    val extra: Extra,
    val items: List<Item>,
    val ref: String,
    val source: String,
    val style: String,
    val title: String,
    val type: String
)