package com.toy.LLM.external.gemini

import com.toy.LLM.domain.StockData
import com.toy.LLM.domain.StockDataImpl
import com.toy.LLM.domain.StockInfoImpl
import com.toy.LLM.domain.StockInfoImpl.SummaryImpl
import com.toy.LLM.external.gemini.dto.StockDataResponse
import com.toy.LLM.external.gemini.dto.StockDescriptionResponse
import com.toy.LLM.external.gemini.dto.StockDescriptionResponse.StockDescriptionResImpl
import com.toy.LLM.external.gemini.dto.StockDescriptionResponse.SummaryResImpl
import org.springframework.stereotype.Component

@Component
object StockInfoMapper {

    fun toDomain(
        res: StockDataResponse
    ): StockData = StockDataImpl(
        res.code,
        res.market
    )

    fun toDomain(
        res: StockDescriptionResponse
    ): StockInfoImpl = StockInfoImpl(
        res.stocks.map {
            toDomain(it)
        },
        toDomain(res.summary)
    )

    fun toDomain(
        res: StockDescriptionResImpl
    ): StockInfoImpl.StockDescriptionImpl {
        return StockInfoImpl.StockDescriptionImpl(
            res.recentIssue,
            res.goodNews,
            res.badNews,
            res.decision
        )

    }

    fun toDomain(
        res: SummaryResImpl
    ) = SummaryImpl(
        res.summation,
        res.total,
        res.warning
    )
}