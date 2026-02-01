package com.toy.LLM.external.news

import com.toy.LLM.application.port.out.news.NewsPort
import com.toy.LLM.domain.NewsData
import com.toy.LLM.domain.StockData
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class News(
    private val mapper: NewsMapper
) : NewsPort {

    private val webClient: WebClient = WebClient.builder()
        .baseUrl("https://finance.yahoo.com/rss")
        .build()

    override fun getNews(data: List<StockData>): List<NewsData> = data.map { stock ->
        try {
            val res = webClient.get()
                .uri("/headline?s=${stock.code}")
                .retrieve()
                .bodyToMono(String::class.java)
                .block()!!

            mapper.toDomain(stock.code, stock.market, res)

        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }
}