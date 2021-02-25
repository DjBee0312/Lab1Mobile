package ua.kpi.comsys.ip8421

import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitServicesBookList {
    @GET("search/{REQUEST}")
    suspend fun getBookList(@Path(value = "REQUEST") todoId: String): RequestList
}