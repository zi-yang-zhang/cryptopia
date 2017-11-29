package com.cryptopia.android.model.local

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robertzzy on 28/11/17.
 */
const val TIME_STAMP_FORMAT: String = "yyyy-MM-dd HH:mm:ss"


class RoomConverters {
    companion object {
        val SQL_DATE_FORMAT: DateFormat = SimpleDateFormat(TIME_STAMP_FORMAT, Locale.ENGLISH)
    }

    @TypeConverter
    fun stringToPricePair(text: String?): List<PricePair>? = Gson().fromJson(text ?: "")

    @TypeConverter
    fun pricePairToString(pricePairs: List<PricePair>?): String? = Gson().toJson(pricePairs)


}

class TimeConverters {

    @TypeConverter
    fun stringToDate(date: String?): Date? = RoomConverters.SQL_DATE_FORMAT.parse(date)

    @TypeConverter
    fun dateToString(date: Date?): String? = RoomConverters.SQL_DATE_FORMAT.format(date)
}


inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)


