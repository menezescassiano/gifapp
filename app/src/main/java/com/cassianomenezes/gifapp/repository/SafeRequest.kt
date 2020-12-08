package com.cassianomenezes.gifapp.repository

import com.cassianomenezes.gifapp.home.model.DataResult
import com.cassianomenezes.gifapp.internal.RequestStatus
import retrofit2.Response

abstract class SafeRequest {

    companion object {
        private const val NULL_ERROR = "null body"
    }

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): DataResult<T> {

        return try {

            val response = call.invoke()

            if (response.isSuccessful) {
                response.body()?.run {
                    DataResult(RequestStatus.SUCCESS, this as T)
                } ?: DataResult(status = RequestStatus.ERROR)
            } else {
                DataResult(status = RequestStatus.ERROR)
            }
        } catch (ex: Exception) {
            DataResult(status = RequestStatus.ERROR)
        }
    }
}