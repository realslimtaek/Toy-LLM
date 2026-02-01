package com.toy.LLM.application.port.`in`.news

interface NewsUseCase {
    fun getNews(query: String)
}