package com.yourcompany.loanledger.data

import androidx.room.TypeConverter
import com.yourcompany.loanledger.data.entity.LineType

class Converters {
    @TypeConverter
    fun fromLineType(type: LineType): String = type.name

    @TypeConverter
    fun toLineType(value: String): LineType = LineType.valueOf(value)
}
