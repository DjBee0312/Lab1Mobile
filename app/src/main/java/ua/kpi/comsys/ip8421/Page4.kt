package ua.kpi.comsys.ip8421

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Page4: Fragment() {

    private var imageAdapter : ImageAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.page4, container, false)

        val recyclerview = view.findViewById<RecyclerView>(R.id.id_recycler_pictures)

        val spannedGridLayoutManager = SpannedGridLayoutManager(
            orientation = SpannedGridLayoutManager.Orientation.VERTICAL,
            spans = 3
        )

        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position ->
            when (position % 9){
                4 -> SpanSize(2, 2)
                else ->
                    SpanSize(1, 1)
            }
        }

        val dataSource = mutableListOf<Uri>()
        spannedGridLayoutManager.itemOrderIsStable = true
        recyclerview.layoutManager = spannedGridLayoutManager
        imageAdapter = context?.let { ImageAdapter(it, dataSource) }
        recyclerview.adapter = imageAdapter

        view.findViewById<Button>(R.id.id_add_picture_button).setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 2)
        }

        val req = "night+city"
        val count = 27.toString()
        GlobalScope.launch {
            val imageList = readImages(req, count)
            withContext(Dispatchers.Main){
                addPictureFromWeb(imageList)
            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2 && data != null){
            imageAdapter!!.dataSource.add(data.data!!)
            Log.d("111", data.data!!.toString())
            imageAdapter!!.notifyDataSetChanged()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

    fun addPictureFromWeb(imageList: MutableList<Image>){
        imageList.forEach {
        val imageUri = Uri.parse(it.largeImageURL)
            imageAdapter!!.dataSource.add(imageUri)
            imageAdapter!!.notifyDataSetChanged()
        }
    }
}
