package com.digimon.example.reactiversocket.server

import com.digimon.example.reactiversocket.model.MarketData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class MarketDataRepository {

    private val random: Random = Random()

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun getAll(stock: String): Flow<MarketData> {
        return flow {
            while (true) {
                emit(getMarketDataResponse(stock))
                delay(1000)
            }
        }
    }

    fun getOne(stock: String): MarketData {
        return getMarketDataResponse(stock)
    }

    fun add(marketData: MarketData) {
        log.info("New market data: {}", marketData)
    }

    private fun getMarketDataResponse(stock: String): MarketData {
        return MarketData(stock, random.nextInt(BOUND))
    }

    companion object {
        private const val BOUND = 100
    }
}