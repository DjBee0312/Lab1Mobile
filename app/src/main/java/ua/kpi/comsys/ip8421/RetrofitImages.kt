package ua.kpi.comsys.ip8421

import retrofit2.http.GET

interface RetrofitImages {
//    @GET("?key=19193969-87191e5db266905fe8936d565&q={REQUEST}&image_type=photo&per_page={COUNT}")
//    suspend fun getImages(@Query(value = "REQUEST") req: String, @Query(value = "COUNT") count: String): Images
    @GET("?key=19193969-87191e5db266905fe8936d565&q=night+city&image_type=photo&per_page=27")
    suspend fun getImages(): Images
}