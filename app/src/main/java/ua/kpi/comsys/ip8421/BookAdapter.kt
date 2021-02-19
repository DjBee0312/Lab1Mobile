package ua.kpi.comsys.ip8421

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream
import java.util.*

class BookAdapter(private val context: Context, var dataSource: MutableList<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>(), Filterable {


    class BookViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val relativeLayoutView = view.findViewById(R.id.id_relativeLayout) as View
        val titleTxt = view.findViewById(R.id.id_title) as TextView
        val subtitleTxt = view.findViewById(R.id.id_subtitle) as TextView
        val priceTxt = view.findViewById(R.id.id_price) as TextView
        val bookImg = view.findViewById(R.id.id_image) as ImageView
    }

    var actualDataSource = mutableListOf<Book>()

    init {
        actualDataSource = dataSource
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val bookItemLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_item, parent, false)
        return BookViewHolder(bookItemLayout)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        var book = actualDataSource[position]

        holder.relativeLayoutView.setOnClickListener {
            val intent = Intent(context, BookInfo::class.java)
            intent.putExtra("isbn13", book.isbn13)
            context.startActivity(intent)
        }

        holder.titleTxt.text = book.title
        holder.subtitleTxt.text = book.subtitle
        holder.priceTxt.text = book.price

        if(book.image == ""){
            holder.bookImg.setImageResource(R.drawable.no_image)
        } else {
            val ims: InputStream = context.assets.open(book.image.decapitalize())
            val d = Drawable.createFromStream(ims, null)
            holder.bookImg.setImageDrawable(d)
        }
    }

    fun removeAt(position: Int) {
        dataSource.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                if (charSearch.isEmpty()) {
                    actualDataSource = dataSource
                } else {
                    val resultList = mutableListOf<Book>()
                    for (book in dataSource) {
                        if (book.title.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(book)
                        }
                    }
                    actualDataSource = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = actualDataSource
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                actualDataSource = results?.values as MutableList<Book>
                notifyDataSetChanged()
            }
        }
    }


    override fun getItemCount(): Int {
        return actualDataSource.size
    }
}