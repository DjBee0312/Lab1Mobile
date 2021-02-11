package ua.kpi.comsys.ip8421

import android.content.Context
import com.beust.klaxon.Klaxon
import java.io.InputStream

class BookList(val books: MutableList<Book>)

fun readBooks(ctx: Context): MutableList<Book> {

    val inputStream: InputStream = ctx.assets.open("BooksList.txt")
    val jsonBooks = inputStream.bufferedReader().use { it.readText() }
    val books = Klaxon().parse<BookList>(jsonBooks)
    return books?.books ?: mutableListOf(Book("", "", "", "", ""))

}

