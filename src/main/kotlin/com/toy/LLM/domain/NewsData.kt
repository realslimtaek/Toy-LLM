package com.toy.LLM.domain

interface NewsData {
    val code: String
    val market: String
    val news: String
}

data class NewsDataImpl (
    override val code: String,
    override val market: String,
    override val news: String
): NewsData