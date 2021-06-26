'use strict'

let stompClient
let username

const okColor = '#56ff08'
const errorColor = '#fe0000'

const connect = (event) => {
    username = document.querySelector('#username').value.trim()

    if (username) {
        const login = document.querySelector('#login')
        //login.classList.add('hide')

/*
        const chatPage = document.querySelector('#chat-page')
        chatPage.classList.remove('hide')
*/

        const socket = new SockJS('http://localhost:8080/endpoint')
        stompClient = Stomp.over(socket)
        stompClient.connect({}, onConnected, onError)
    }
    event.preventDefault()
}

const onConnected = () => {
    const status = document.querySelector('#status')
    status.innerHTML = 'Success!'
    status.style.color = okColor

    stompClient.subscribe('/topic/quote', onQuote)
    stompClient.send("/app/subscribe",
        {},
        JSON.stringify({ symbol: 'AMZN'})
    )
}

const onError = (error) => {
    const status = document.querySelector('#status')
    status.innerHTML = 'Unable to connect.'
    status.style.color = errorColor
}

const onQuote = (payload) => {
    const quote = JSON.parse(payload.body);

    const text = document.querySelector('#quote')
    text.innerHTML = quote.price

    //alert(quote.symbol)
}

const loginForm = document.querySelector('#login-form')
loginForm.addEventListener('submit', connect, true)
