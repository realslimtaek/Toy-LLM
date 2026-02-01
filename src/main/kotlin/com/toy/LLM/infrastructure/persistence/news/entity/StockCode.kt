package com.toy.LLM.infrastructure.persistence.news.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "STOCK_CODE")
class StockCode (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long? = null,

    @Column(name = "MARKET")
    val market: String,

    @Column(name = "CODE")
    val code: String,

)