package com.appspiriment.kashproxy.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal class MappingItem(
    val httpCode: String?,
    val successResponse: String?,
    val errorResponse: String?
): Parcelable