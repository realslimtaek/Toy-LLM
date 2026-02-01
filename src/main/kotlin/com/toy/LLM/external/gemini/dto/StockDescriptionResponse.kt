package com.toy.LLM.external.gemini.dto


interface StockDescriptionRes {
    val recentIssue: String
    val goodNews: String
    val badNews: String
    val decision: String
}

interface SummaryRes {

    val summation: String
    val total: List<String>
    val warning: String
}

interface TotalStockDescriptionRes {
    val stocks: List<StockDescriptionRes>
    val summary: SummaryRes
}

data class StockDescriptionResponse (
    override val stocks: List<StockDescriptionResImpl>,
    override val summary: SummaryResImpl
): TotalStockDescriptionRes {
    data class StockDescriptionResImpl(
        override val recentIssue: String,
        override val goodNews: String,
        override val badNews: String,
        override val decision: String
    ) : StockDescriptionRes

    data class SummaryResImpl(
        override val summation: String,
        override val total: List<String>,
        override val warning: String
    ) : SummaryRes
}
