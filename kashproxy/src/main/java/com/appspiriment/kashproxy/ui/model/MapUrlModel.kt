package com.appspiriment.kashproxy.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MapUrlModel(
   val url: String,
   val method: String?,
   val response: String?,
   val isSsl: Boolean
) : Parcelable