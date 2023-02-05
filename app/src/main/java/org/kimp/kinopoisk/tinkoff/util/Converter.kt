package org.kimp.kinopoisk.tinkoff.util

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


class Converter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return Moshi.Builder()
            .build()
            .adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java))
            .fromJson(value)!!
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {
        return Moshi.Builder()
            .build()
            .adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java))
            .toJson(list)
    }
}