package com.example.test02

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookDetailActivity : AppCompatActivity() {
    private lateinit var bookLibrary: BookLibrary

    private lateinit var IdTextView: TextView
    private lateinit var TitleTextView: TextView
    private lateinit var AuthorTextView: TextView
    private lateinit var PageTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Retrieve the user profile from the intent
        bookLibrary = intent.getSerializableExtra("USER_PROFILE") as BookLibrary

        IdTextView = findViewById(R.id.BookIdLabelTextView)
        TitleTextView = findViewById(R.id.BooktitleabelTextView)
        AuthorTextView = findViewById(R.id.BookAuthoreabelTextView)
        PageTextView = findViewById(R.id.BookPageLabelTextView)

        populateFields()
    }

    private fun populateFields() {
        IdTextView.text = bookLibrary.id.toString()
        TitleTextView.text = bookLibrary.title
        AuthorTextView.text = bookLibrary.author
        PageTextView.text = bookLibrary.pages.toString()
    }
}