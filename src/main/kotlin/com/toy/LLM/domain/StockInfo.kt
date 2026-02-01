package com.toy.LLM.domain


interface StockDescription {
    val recentIssue: String
    val goodNews: String
    val badNews: String
    val decision: String
}

interface Summary {
    val summation: String
    val total: List<String>
    val warning: String
}

interface StockInfo {
    val stocks: List<StockDescription>
    val summary: Summary
}

data class StockInfoImpl (
    override val stocks: List<StockDescriptionImpl>,
    override val summary: SummaryImpl
): StockInfo {

    data class StockDescriptionImpl(
        override val recentIssue: String,
        override val goodNews: String,
        override val badNews: String,
        override val decision: String
    ) : StockDescription

    data class SummaryImpl(
        override val summation: String,
        override val total: List<String>,
        override val warning: String
    ) : Summary


}
