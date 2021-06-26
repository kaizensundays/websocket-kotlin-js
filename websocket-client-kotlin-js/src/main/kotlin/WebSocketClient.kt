import kotlinx.browser.document
import org.w3c.dom.Element
import org.w3c.dom.events.Event

/**
 * Created: Saturday 6/12/2021, 2:11 PM Eastern Time
 *
 * @author Sergey Chuykov
 */

var stompClient: StompClient? = null

lateinit var quoteLabel: Element
lateinit var statusLabel: Element

fun onQuote(payload: Payload) {

    val body = payload.body

    val quote = JSON.parse<Quote>(body)

    quoteLabel.innerHTML = quote.price.toString()
}

fun onStompConnected() {
    statusLabel.innerHTML = "Connected"
    stompClient?.subscribe("/topic/quote", ::onQuote)
    stompClient?.send("/app/subscribe", js("{}") as Any, """{ "symbol": "AMZN"}""")
}

fun onStompError(error: Any) {
    statusLabel.innerHTML = "Error: $error"
}

fun connect() {
    val socket = SockJS("http://localhost:8080/endpoint")
    stompClient = Stomp.over(socket)
    stompClient?.connect("", "", ::onStompConnected, ::onStompError)
}

fun getSubscribeButton(id: String, text: String, onClick: ((Event) -> Unit)): Element {
    val button = document.createElement("BUTTON")
    button.id = id
    button.innerHTML = text
    button.addEventListener("click", onClick)
    return button
}

fun getHorizontalRuler() = document.createElement("HR")

fun getQuoteLabel(): Element {
    val label = document.createElement("LABEL")
    label.innerHTML = "0.00"
    return label
}

fun getStatusLabel(): Element {
    val label = document.createElement("LABEL")
    label.innerHTML = "?"
    return label
}

fun main() {

    val divMain = document.getElementById("main")

    if (divMain != null) {

        divMain.appendChild(getSubscribeButton("btn0", "Subscribe") {
            console.log("click")
            connect()
        })

        divMain.appendChild(getHorizontalRuler())

        quoteLabel = getQuoteLabel()
        divMain.appendChild(quoteLabel)

        divMain.appendChild(getHorizontalRuler())

        statusLabel = getStatusLabel()
        divMain.appendChild(statusLabel)

    }

}
