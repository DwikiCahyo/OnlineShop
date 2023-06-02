package jihan.binar.pra_project_final

import java.text.NumberFormat
import java.util.*

object Util {

    fun getPriceIdFormat(price: String): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return formatter.format(price.toInt())
    }

}