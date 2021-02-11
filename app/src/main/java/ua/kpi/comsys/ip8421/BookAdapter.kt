package ua.kpi.comsys.ip8421

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.InputStream


class BookAdapter(private val context: Context, private val dataSource: MutableList<Book>) : BaseAdapter () {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "CutPasteId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.book_item, parent, false)

        val titleTxt = view.findViewById(R.id.id_title) as TextView
        val subtitleTxt = view.findViewById(R.id.id_subtitle) as TextView
        val priceTxt = view.findViewById(R.id.id_price) as TextView
        val bookImg = view.findViewById(R.id.id_image) as ImageView

        val book = getItem(position) as Book
        titleTxt.text = book.title
        subtitleTxt.text = book.subtitle
        priceTxt.text = book.price

        if(book.image == ""){
            bookImg.setImageResource(R.drawable.no_image)
        } else {
            val ims: InputStream = context.assets.open(book.image.decapitalize())
            val d = Drawable.createFromStream(ims, null)
            bookImg.setImageDrawable(d)
        }
        return view
    }
}