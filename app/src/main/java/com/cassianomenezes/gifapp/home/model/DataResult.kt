package com.cassianomenezes.gifapp.home.model

import com.cassianomenezes.gifapp.internal.RequestStatus

class DataResult<T>(
    val status: RequestStatus,
    val data: T? = null
)