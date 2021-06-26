package com.kaizensundays.particles.websocket.service.stomp

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * Created: Monday 5/31/2021, 1:20 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
@Controller
class QuoteController : ApplicationListener<SessionDisconnectEvent> {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var template: SimpMessagingTemplate

    private var executor = Executors.newScheduledThreadPool(1)

    private var scheduledFuture: ScheduledFuture<*>? = null

    private val random = Random()

    @Suppress("SameParameterValue")
    private fun sendQuote(symbol: String) {
        val price = random.nextDouble() + 100.0
        val quote = Quote(symbol, price)
        logger.info("{}", quote)
        template.convertAndSend("/topic/quote", quote)
    }

    @MessageMapping("/subscribe")
    fun subscribe(@Payload request: Subscribe) {
        logger.info("subscribe: {}", request)

        scheduledFuture = executor.scheduleWithFixedDelay({ sendQuote(request.symbol) }, 3, 3, TimeUnit.SECONDS)
    }

    fun unsubscribe() {

        scheduledFuture?.cancel(false)
    }

    override fun onApplicationEvent(event: SessionDisconnectEvent) {
        logger.info("{}", event)

        unsubscribe()
    }

    @PostConstruct
    fun start() {

        logger.info("Started")
    }

    @PreDestroy
    fun stop() {

        logger.info("Stopped")
    }

}