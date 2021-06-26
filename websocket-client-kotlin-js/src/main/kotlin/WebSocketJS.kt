/**
 * Created: Saturday 6/26/2021, 1:33 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
external fun alert(message: Any?)

external class SockJS(endpoint: String)

open external class StompClient {
    fun connect(
        login: String, passcode: String,
        connectCallback: () -> Unit, errorCallback: (Any) -> Unit
    )

    fun subscribe(topic: String, callback: (Payload) -> Unit)
    fun send(topic: String, params: Any, wire: String)
}

external object Stomp {
    fun over(socket: SockJS): StompClient
}

external class Payload {
    var body: String
}
