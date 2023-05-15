package com.digimon.example.reactiversocket.server

import com.digimon.example.reactiversocket.model.MarketData
import com.digimon.example.reactiversocket.model.MarketDataRequest
import kotlinx.coroutines.flow.Flow
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono


@Controller
class MarketDataRSocketController(private val marketDataRepository: MarketDataRepository) {

    // request response
    @MessageMapping("currentMarketData")
    fun currentMarketData(marketDataRequest: MarketDataRequest): MarketData {
        return marketDataRepository.getOne(marketDataRequest.stock)
    }

    // fire and forget
    @MessageMapping("collectMarketData")
    fun collectMarketData(marketData: MarketData): Mono<Void?>? {
        marketDataRepository.add(marketData)
        return Mono.empty()
    }

    // request stream
    @MessageMapping("feedMarketData")
    fun feedMarketData(marketDataRequest: MarketDataRequest): Flow<MarketData> {
        return marketDataRepository.getAll(marketDataRequest.stock)
    }
}