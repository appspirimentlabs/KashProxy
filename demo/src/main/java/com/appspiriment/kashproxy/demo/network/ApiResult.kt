package com.appspiriment.kashproxy.demo.network

import java.lang.Exception

data class ApiResult(
    val status: Int = 0,
    val url: String = "",
    val data: String? = null,
    var error: Exception? = null
)
