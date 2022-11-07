package com.appspiriment.kashproxy.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MappingItem(
    val httpCode: String?,
    val successResponse: String?,
    val errorResponse: String?
): Parcelable