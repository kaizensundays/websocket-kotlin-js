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

    fun print() = "Quote('$symbol' $price)"

}