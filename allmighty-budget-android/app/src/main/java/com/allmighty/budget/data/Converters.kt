package com.allmighty.budget.data

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter fun fromLocalDate(d: LocalDate?): String? = d?.toString()
    @TypeConverter fun toLocalDate(s: String?): LocalDate? = s?.let { LocalDate.parse(it) }
    @TypeConverter fun fromTxType(t: TxType?): String? = t?.name
    @TypeConverter fun toTxType(s: String?): TxType? = s?.let { TxType.valueOf(it) }
    @TypeConverter fun fromGoalPeriod(g: GoalPeriod?): String? = g?.name
    @TypeConverter fun toGoalPeriod(s: String?): GoalPeriod? = s?.let { GoalPeriod.valueOf(it) }
}