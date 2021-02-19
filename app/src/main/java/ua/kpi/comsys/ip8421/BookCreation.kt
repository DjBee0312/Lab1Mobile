package ua.kpi.comsys.ip8421

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class BookCreation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.book_creation)

        findViewById<Button>(R.id.id_add_book_button).setOnClickListener{
            val title = findViewById<TextInputEditText>(R.id.input_title).text.toString()
            val subtitle = findViewById<TextInputEditText>(R.id.input_subtitle).text.toString()
            val price = findViewById<TextInputEditText>(R.id.input_price).text.toString()

            val returnIntent = Intent()
            returnIntent.putExtra("title", title)
            returnIntent.putExtra("subtitle", subtitle)
            returnIntent.putExtra("price", price)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}