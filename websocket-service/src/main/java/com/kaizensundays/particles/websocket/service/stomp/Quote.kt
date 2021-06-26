package com.kaizensundays.particles.websocket.service.stomp

/**
 * Created: Monday 5/31/2021, 1:18 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class Quote {

    var symbol = "?"
    var price = 0.0

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(symbol: String, price: Double) {
        this.symbol = symbol
        this.price = price
    }


    override fun toString(): String {
        return "Quote('$symbol' $price)"
    }

}