package ua.kpi.comsys.ip8421

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookList(val books: MutableList<Book>)

const val url = "https://api.itbook.store/1.0/"

suspend fun readBooks( REQUEST: String): MutableList<Book> {

    val retrofitServicesBookList: RetrofitServicesBookList = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build().create(RetrofitServicesBookList::class.java)

    val bookListRequest = retrofitServicesBookList.getBookList(REQUEST)
    return bookListRequest.books
}

suspend fun readBook(isbn: String): Book? {

    val retrofitServicesBook: RetrofitServicesBook = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build().create(RetrofitServicesBook::class.java)

    val bookRequest = retrofitServicesBook.getBook(isbn)
    if(isbn == "noid") return null
    return bookRequest
}

suspend fun readImages(REQUEST : String, COUNT : String) : MutableList<Image> {
    val urlPic = "https://pixabay.com/api/"

    val retrofitImages: RetrofitImages = Retrofit.Builder()
        .baseUrl(urlPic)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build().create(RetrofitImages::class.java)

    val imageRequest = retrofitImages.getImages()// REQUEST COUNT
    return imageRequest.hits
}