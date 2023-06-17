package com.vobbla16.mesh.common

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.os.ConfigurationCompat
import kotlinx.datetime.LocalDate
import java.time.format.TextStyle

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toHumanStr(localConfiguration: Configuration): String {
    val locale = ConfigurationCompat.getLocales(localConfiguration).get(0)!!

    return "${this.dayOfMonth} ${this.month.getDisplayName(TextStyle.SHORT, locale)} ${this.year}"

}