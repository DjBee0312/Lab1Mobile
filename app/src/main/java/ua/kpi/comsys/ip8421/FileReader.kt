package ua.kpi.comsys.ip8421

import android.content.Context
import com.beust.klaxon.Klaxon
import java.io.InputStream

class BookList(val books: MutableList<Book>)

fun readBooks(ctx: Context, fileName : String): MutableList<Book> {

    val inputStream: InputStream = ctx.assets.open(fileName)
    val jsonBooks = inputStream.bufferedReader().use { it.readText() }
    val books = Klaxon().parse<BookList>(jsonBooks)
    return books?.books ?: mutableListOf(Book("", "", "", "", ""))

}

fun readBook(ctx: Context, fileName : String): Book? {
    if(fileName == "BooksInfo/noid.txt") return null
    print(fileName)
    val inputStream: InputStream = ctx.assets.open(fileName)
    val jsonBook = inputStream.bufferedReader().use { it.readText() }
    return Klaxon().parse<Book>(jsonBook)
}