package com.digimon.example.reactiversocket.client

import com.digimon.example.reactiversocket.model.MarketData
import com.digimon.example.reactiversocket.model.MarketDataRequest
import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveAndAwaitOrNull
import org.springframework.messaging.rsocket.retrieveFlow
import org.springframework.messaging.rsocket.sendAndAwait
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class MarketDataRestController(
    private val rSocketRequester: RSocketRequester
) {

    private val random = Random()

    // request response
    @GetMapping("/current/{stock}")
    suspend fun current(@PathVariable("stock") stock: String): MarketData? {
        return rSocketRequester
            .route("currentMarketData")
            .data(MarketDataRequest(stock))
            .retrieveAndAwaitOrNull<MarketData>()
    }

    // fire and forget
    @GetMapping("/collect")
    suspend fun collect() {
        rSocketRequester
            .route("collectMarketData")
            .data(getMarketData())
            .sendAndAwait()
    }

    // request stream
    @GetMapping(value = ["/feed/{stock}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    private fun feed(@PathVariable("stock") stock: String): Flow<MarketData> {
        return rSocketRequester
            .route("feedMarketData")
            .data(MarketDataRequest(stock))
            .retrieveFlow<MarketData>()
    }

    private fun getMarketData(): MarketData {
        return MarketData("X", random.nextInt(10))
    }
}