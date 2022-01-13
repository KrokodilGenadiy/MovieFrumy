package com.zaus_app.moviefrumy.utils

import androidx.room.TypeConverter

class RoomConverter {
    @TypeConverter
    fun fromListOfIntsToString(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromStringTOListOfInts(str: String): List<Int> {
        return if (str == "")
            emptyList()
        else
            str.split(",").toTypedArray().map { it.toInt() }
    }
}