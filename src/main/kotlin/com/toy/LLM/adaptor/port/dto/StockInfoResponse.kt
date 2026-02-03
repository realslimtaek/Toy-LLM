package com.toy.LLM.adaptor.port.dto

// 최상위 응답 DTO
data class StockInfoResponse(
    val stocks: List<StockDescriptionDto>,
    val summary: SummaryDto
)

// 주식 설명 DTO
data class StockDescriptionDto(
    val recentIssue: String,
    val goodNews: String,
    val badNews: String,
    val decision: String
)

// 요약 DTO
data class SummaryDto(
    val summation: String,
    val total: List<String>,
    val warning: String
)
