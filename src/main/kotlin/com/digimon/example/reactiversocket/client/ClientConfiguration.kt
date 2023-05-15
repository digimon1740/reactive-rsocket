package com.digimon.example.reactiversocket.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.codec.DataBufferDecoder
import org.springframework.core.codec.DataBufferEncoder
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.util.MimeTypeUtils
import reactor.util.retry.Retry
import java.time.Duration


@Configuration
class ClientConfiguration {

    @Bean
    fun rSocketRequester(rSocketStrategies : RSocketStrategies): RSocketRequester {
        val builder: RSocketRequester.Builder = RSocketRequester.builder()
        return builder
            .rsocketStrategies(rSocketStrategies)
            .rsocketConnector { rSocketConnector -> rSocketConnector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))) }
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .tcp("localhost", 6000)

    }

    @Bean
    fun rSocketStrategies(): RSocketStrategies {
        return RSocketStrategies.builder()
            .encoders { encoders ->
                encoders.add(Jackson2JsonEncoder())
                encoders.add(DataBufferEncoder())
            }.decoders { decoders ->
                decoders.add(Jackson2JsonDecoder())
                decoders.add(DataBufferDecoder())
            }
            .build()
    }


}