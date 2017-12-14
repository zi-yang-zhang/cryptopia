package com.cryptopia.android.model.local

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robertzzy on 28/11/17.
 */
const val TIME_STAMP_FORMAT: String = "yyyy-MM-dd HH:mm:ss"


class RoomConverters {

    @TypeConverter
    fun stringToPricePair(text: String?): List<SuggestionPricePair>? = Gson().fromJson(text ?: "")

    @TypeConverter
    fun pricePairToString(suggestionPricePairs: List<SuggestionPricePair>?): String? = Gson().toJson(suggestionPricePairs)


}

class TimeConverters {
    companion object {
        @JvmField
        val SQL_DATE_FORMAT: DateFormat = SimpleDateFormat(TIME_STAMP_FORMAT, Locale.ENGLISH)
    }
    @TypeConverter
    fun stringToDate(date: Long?): LocalDateTime = LocalDateTime(date?.times(1000) ?: System.currentTimeMillis(), DateTimeZone.UTC)

    @TypeConverter
    fun dateToString(date: LocalDateTime?): Long? = date?.millisOfSecond?.toLong()
}


inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)


