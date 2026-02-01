package com.toy.LLM.application.port.out.gemini

import com.toy.LLM.domain.NewsData
import com.toy.LLM.domain.StockData
import com.toy.LLM.domain.StockInfo

interface GeminiPort {
    fun getStockName(text: String): List<StockData>
    fun getStockDescription(data : List<NewsData>): StockInfo;
}