package com.toy.LLM.domain


interface StockData {
    val code: String
    val market: String
}

data class StockDataImpl(
    override val code: String,
    override val market: String
): StockData