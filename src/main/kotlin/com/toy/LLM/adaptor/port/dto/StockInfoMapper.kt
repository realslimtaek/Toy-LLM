package com.toy.LLM.adaptor.port.dto

import com.toy.LLM.domain.StockDescription
import com.toy.LLM.domain.StockInfo
import com.toy.LLM.domain.Summary
import org.springframework.stereotype.Component

@Component
object ResponseMapper {

    fun toResponse(domain: StockInfo): StockInfoResponse {
        return StockInfoResponse(
            stocks = domain.stocks.map { it.toDto() },
            summary = domain.summary.toDto()
        )
    }

    private fun StockDescription.toDto(): StockDescriptionDto {
        return StockDescriptionDto(
            recentIssue = this.recentIssue,
            goodNews = this.goodNews,
            badNews = this.badNews,
            decision = this.decision
        )
    }

    private fun Summary.toDto(): SummaryDto {
        return SummaryDto(
            summation = this.summation,
            total = this.total,
            warning = this.warning
        )
    }
}
