package ua.kpi.comsys.ip8421

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment

class BookActivity : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.page3, container, false)
        context?.let { addBooks(view, it) }
        return view
    }

}

fun addBooks(view: View, ctx: Context){
    val listView: ListView = view.findViewById(R.id.id_listView)
    val arrBook: MutableList<Book> = readBooks(ctx)
    listView.adapter = BookAdapter(ctx, arrBook)
}

