package com.toy.LLM.external.gemini

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.genai.Models
import com.google.genai.errors.ClientException
import com.google.genai.types.Content
import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.Part
import com.toy.LLM.application.port.out.gemini.GeminiPort
import com.toy.LLM.domain.NewsData
import com.toy.LLM.domain.StockData
import com.toy.LLM.domain.StockInfo
import com.toy.LLM.external.gemini.dto.StockDataResponse
import com.toy.LLM.external.gemini.dto.StockDescriptionResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.rmi.ServerException

@Component
class GeminiAdaptor(

    @Value("\${gemini.api.config.stock}")
    private val stockConfig: String,

    @Value("\${gemini.api.config.analyst}")
    private val analystConfig: String,

    @Value("\${gemini.api.models}")
    private val models: String,

    private val domainMapper: StockInfoMapper,

    private val gemini: Models
) : GeminiPort {

    private val mapper = jacksonObjectMapper()


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
        models,
        text,
        config
    )

    override fun getStockName(text: String): List<StockData> {
        val q = toAiWithRetry(text, getStockInfoContent.config())

        return mapper.readValue<List<StockDataResponse>>(q).map {
            domainMapper.toDomain(it)

        }
    }


    override fun getStockDescription(data: List<NewsData>): StockInfo {

        val str = toAiWithRetry(mapper.writeValueAsString(data), systemInstructionContent.config())

        val res = mapper.readValue<StockDescriptionResponse>(str)
        return domainMapper.toDomain(res)
    }

    private fun toAi(text: String, config: GenerateContentConfig): String =
        gemini.toAi(text, config).text() ?: throw IllegalStateException("AI 응답 실패")


    private fun toAiWithRetry(input: String, config: GenerateContentConfig): String {
        var lastException: Exception? = null
        for (attempt in 1..3) {
            try {
                return toAi(input, config) // 기존 호출 로직
            } catch (e: ServerException) { // 503 에러 발생 시
                lastException = e
            } catch (e: ClientException) {
                if (e.code() == 429) lastException = e
                else throw e
            }
            Thread.sleep(2000L)
        }
        throw lastException ?: RuntimeException("AI 호출 실패")
    }


}
