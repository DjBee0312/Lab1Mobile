package ua.kpi.comsys.ip8421

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class BookActivity : Fragment() {

    var recyclerViewAdapter : BookAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.page3, container, false)

        val arrBook = mutableListOf<Book>()
        recyclerViewAdapter = BookAdapter(context!!, arrBook)

        context?.let { addBooks(view, it, recyclerViewAdapter!!) }

        val search = view.findViewById<SearchView>(R.id.id_search)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return query != null
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recyclerViewAdapter!!.filter.filter(newText)
                return newText != null
            }

        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerViewAdapter
                adapter?.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        view.findViewById<Button>(R.id.id_add_button).setOnClickListener{
            val intent = Intent(context, BookCreation::class.java)
            startActivityForResult(intent, 1)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                val dataTitle = data?.getStringExtra("title")
                val dataSubtitle = data?.getStringExtra("subtitle")
                val dataPrice = data?.getStringExtra("price")

                recyclerViewAdapter?.dataSource?.add(Book(title = dataTitle!!, subtitle = dataSubtitle!!,price = "$" + dataPrice!! + ".00"))
                recyclerViewAdapter?.notifyItemInserted(recyclerViewAdapter!!.dataSource.size)
            }
            else -> {
                print("Unknown request")
            }
        }
    }
}

fun addBooks(view: View, ctx: Context, recyclerViewAdapter : BookAdapter){
    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    recyclerView.adapter = recyclerViewAdapter
    val books : MutableList<Book> = readBooks(ctx, "BooksList.txt")
    recyclerViewAdapter.actualDataSource = books
    recyclerViewAdapter.dataSource = books
    recyclerViewAdapter.notifyDataSetChanged()
    recyclerView.setOnClickListener {}
}

