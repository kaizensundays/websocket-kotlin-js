package com.kaizensundays.particles.websocket.service.tests

import org.junit.Ignore
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.Transport
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import java.lang.Thread.sleep
import java.util.*

/**
 * Created: Monday 5/31/2021, 11:24 AM Eastern Time
 *
 * @author Sergey Chuykov
 */
@Ignore
class WebSocketClientTest {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    class LocalWebSocketHandler(private val logger: Logger) : TextWebSocketHandler() {
        override fun afterConnectionEstablished(session: WebSocketSession) {
            logger.info("* {}", session)
        }

        override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
            logger.info("* {} {}", session, status)
        }

        override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
            logger.info("* {}", message)
        }
    }

    @Test
    fun test() {

        val transports: MutableList<Transport> = ArrayList<Transport>()
        transports.add(WebSocketTransport(StandardWebSocketClient()))

        val sockJsClient = SockJsClient(transports)
        sockJsClient.doHandshake(LocalWebSocketHandler(logger), "ws://localhost:8080/endpoint")

        sleep(10000)

        sockJsClient.stop()

        sleep(3000)
    }

}