package com.toy.LLM.config

import com.google.genai.Client
import com.google.genai.Models
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GeminiConfig {

    @Bean
    fun gemini(
        @Value("\${gemini.api.key}")
        apiKey: String
    ): Models = Client.builder().apiKey(apiKey).build().models

}