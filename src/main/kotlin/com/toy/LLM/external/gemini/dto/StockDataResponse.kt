package com.toy.LLM.external.gemini.dto

interface StockDataRes {
    val code: String
    val market: String
}

data class StockDataResponse(
    override val code: String,
    override val market: String
): StockDataRes
