package ua.com.app.saienko.yaroslav.cryptoprice.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

// метод перетворення таймстемпа (час в сек, або мілісек який пройшов з 1970р)
fun convertTimestampToTime(timestamp: Long?): String{
    if (timestamp == null) return ""
    // в нашому випадку приходять секунди, а метод працює з мілісек, то множимо на 1000
    val stamp = Timestamp(timestamp * 1000)
    val date = Date(stamp.time)
    // паттерн з годинами, хв і сек.
    val pattern = "HH:mm:ss"
    // час по Грінвічу
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    // час в нашій зоні
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}