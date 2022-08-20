package com.falcon.news.entity

class NewsItemEntity(
    sectionTitle: String,
    val thumb: String,
    val subscript: String,
    val created: String,
    val title: String,
    val subTitle: String,
    val ref: String
) : BaseItemEntity(sectionTitle)