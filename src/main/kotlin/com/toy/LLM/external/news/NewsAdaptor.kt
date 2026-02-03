package com.toy.LLM.external.news

import com.toy.LLM.application.port.out.news.NewsPort
import com.toy.LLM.domain.NewsData
import com.toy.LLM.domain.StockData
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

@Component
class NewsAdaptor(
    private val mapper: NewsMapper
) : NewsPort {

    private val webClient: WebClient = WebClient.builder()
        .baseUrl("https://feeds.finance.yahoo.com/rss/2.0/")
        .build()

    override fun getNews(data: List<StockData>): List<NewsData> =
        data.mapNotNull { stock ->
            try {
                val res = webClient.get()
                    .uri("/headline?s=${stock.code}")
                    .retrieve()
                    .bodyToMono(String::class.java)
                    .onErrorResume(WebClientResponseException::class.java) { Mono.empty() }
                    .block()

                res?.let { mapper.toDomain(stock.code, stock.market, it) }

            } catch (e: Exception) {
                println(e.message)
                null
            }
        }
}