package ua.kpi.comsys.ip8421

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookActivity : Fragment() {

    var recyclerViewAdapter : BookAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.page3, container, false)

        val arrBook = mutableListOf<Book>()
        recyclerViewAdapter = BookAdapter(context!!, arrBook)

        val search = view.findViewById<SearchView>(R.id.id_search)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return query != null
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.length >= 3) {
                        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                        addBooks(view, recyclerViewAdapter!!, newText)
                        recyclerViewAdapter!!.filter.filter(newText)
                    } else {
                        recyclerViewAdapter!!.filter.filter("********")
                    }
                }
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

fun addBooks(view: View, recyclerViewAdapter : BookAdapter, REQUEST : String){
    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    recyclerView.adapter = recyclerViewAdapter

    GlobalScope.launch {
        val books = readBooks(REQUEST)
        Log.d("books", books.toString())
        withContext(Dispatchers.Main){
            updateList(recyclerViewAdapter, recyclerView, books)
            view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
        }
    }

}

fun updateList(recyclerViewAdapter: BookAdapter, recyclerView: RecyclerView, books: MutableList<Book>) {
    recyclerViewAdapter.actualDataSource = books
    recyclerViewAdapter.dataSource = books
    recyclerViewAdapter.notifyDataSetChanged()
    recyclerView.setOnClickListener {}
}