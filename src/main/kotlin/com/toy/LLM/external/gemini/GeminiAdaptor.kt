package com.toy.LLM.external.gemini

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import com.google.genai.Client
import com.google.genai.Models
import com.google.genai.types.Content
import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.Part
import com.toy.LLM.application.port.out.gemini.GeminiPort
import com.toy.LLM.domain.NewsData
import com.toy.LLM.domain.StockData
import com.toy.LLM.domain.StockInfo
import com.toy.LLM.external.gemini.dto.StockDescriptionResponse
import com.toy.LLM.external.gemini.dto.StockDataResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.rmi.ServerException
import kotlin.math.pow

@Component
class GeminiAdaptor(
    @Value("\${gemini.api.key}")
    private val apiKey: String,

    @Value("\${gemini.api.config.stock}")
    private val stockConfig: String,

    @Value("\${gemini.api.config.analyst}")
    private val analystConfig: String,

    private val domainMapper: StockInfoMapper
    ) : GeminiPort {

    private val mapper = jacksonObjectMapper()


    private val gemini by lazy {
        Client.builder().apiKey(apiKey).build().models
    }

    private val getStockInfoContent = Content.builder()
        .parts(
            listOf(
                Part.fromText(stockConfig)
            )
        )
        .build()


    private val systemInstructionContent = Content.builder()
        .parts(
            listOf(
                Part.fromText(analystConfig)
            )
        )
        .build()

    private fun Content.config() = GenerateContentConfig.builder().systemInstruction(this@config).build()

    private fun Models.toAi(text: String, config: GenerateContentConfig) = this.generateContent(
        "gemini-2.5-flash-lite",
        text,
        config
    )

    override fun getStockName(text: String): List<StockData> {
        val q = toAiWithRetry(text, getStockInfoContent.config())

        return mapper.readValue<List<StockDataResponse>>(q).map{
            domainMapper.toDomain(it)

        }
    }


    override fun getStockDescription(data: List<NewsData>): StockInfo {

        val str = toAiWithRetry(mapper.writeValueAsString(data), systemInstructionContent.config())

        val res = mapper.readValue<StockDescriptionResponse>(str)
        return domainMapper.toDomain(res)
    }

    private fun toAi(text: String, config: GenerateContentConfig): String =
        gemini.toAi(text, config).text()!!


    private fun toAiWithRetry(input: String, config: GenerateContentConfig): String {
        var lastException: Exception? = null
        val maxRetries = 3;
        for (i in 0 until maxRetries) {
            try {
                return toAi(input, config) // 기존 호출 로직
            } catch (e: Exception) { // 503 에러 발생 시
                if (i == maxRetries - 1) {
                    lastException = e
                    break
                }
                println("failed")
                Thread.sleep(2000)
            }
        }
        throw lastException ?: RuntimeException("AI 호출 실패")
    }


}
