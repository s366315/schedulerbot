package com.schedulerbot

class MessageParserImpl : MessageParser {

    override fun parseString(caption: String): RowModel {
        var fullname = ""
        var url = ""
        var phone = ""
        var payment = ""
        var post = ""
        var price = ""
        var city = ""
        var ukrpost = ""
        var dollar = ""

        caption.replace("\n", " ")
            .replace(",", " ")
            .replace("(", "")
            .replace(")", "")
            .replace("-", "")
            .replace("\\s".toRegex(), " ")
            .trim()
            .split(" ").forEach { it ->
                if (it.has("https")) { //url
                    url = it
                }
            }



        val caption = caption
            .replace("\n", " ")
            .replace(",", " ")
            .replace(".", " ")
            .replace("(", "")
            .replace(")", "")
            .replace("-", "")
            .replace("\\s".toRegex(), " ")
            .trim()

        caption.split(" ").iterator()

        caption.split(" ").forEachIndexed { index, it ->
            Regex("\\d{10,12}").find(it.replace("\\s".toRegex(), "").replace(",", ""))?.value?.let {//if phone in middle of line
                phone = it
            }
            Regex("\\b\\d{1,3}\\b").find(it)?.value?.let {
                try {
                    val p = it.toInt()
                    if (p < 200) {
                        post = it
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            Regex("\\b\\d{5}\\b").find(it)?.value?.let {//укрпочта
                post = it
//                ukrpost = "укрпочта"
            }
            Regex("\\b\\d{3,4}\\b").find(it)?.value?.let {
                price = "$it ₴"
            }
            if (it.has("Обмен") || it.has("обмен")) {
                price = "200 ₴"
                dollar = "0"
            }

            if (it.has("карту") || it.has("оплата") || it.has("оплачено")) {//payment
                payment = "на карту банка"
            }
            if (it.has("наложка") || it.has("наложенным") || it.has("наложенній")) {
                payment = "наложка"
            }

            if (!caption.has("✅") && !caption.has("✓")) {
                ukrpost = "нет оплаты"
            }
        }
        return RowModel(ukrpost, fullname, url.ifEmpty { "ТГ" }, phone, payment, post, dollar, price, city)
    }

    private fun String.has(string: String): Boolean {
        return this.contains(string, ignoreCase = true)
    }
}