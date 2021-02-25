package ua.kpi.comsys.ip8421

import retrofit2.http.*

interface RetrofitServicesBook : RetrofitServicesBookList {
    @GET("books/{isbn}")
    suspend fun getBook(@Path(value = "isbn") todoId: String): Book
}