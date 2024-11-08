package com.example.test02
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BookListActivity : AppCompatActivity() {


    private lateinit var BookViewModel: LibraryBookViewModel
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        BookViewModel = ViewModelProvider(this).get(LibraryBookViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.profileRecyclerView)
        bookAdapter = BookAdapter()

        recyclerView.adapter = bookAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe profiles from ViewModel
        BookViewModel.getUserProfiles().observe(this, Observer { profiles ->
            bookAdapter.submitList(profiles)
        })

        // Set item click listener for details
        bookAdapter.setOnItemClickListener { bookLibrary ->
            val intent = Intent(this@BookListActivity, BookDetailActivity::class.java)
            intent.putExtra("BOOK_LIBRARY", bookLibrary)
            startActivity(intent)
        }

        // Set delete click listener
        bookAdapter.setOnDeleteClickListener { userProfile ->
            showDeleteConfirmationDialog(userProfile)
        }

        // Set update click listener
        bookAdapter.setOnUpdateClickListener { bookLibrary ->
            val intent = Intent(this@BookListActivity, BookUpdateActivity::class.java)
            intent.putExtra("BOOK_LIBRARY", bookLibrary)
            startActivity(intent)
        }

        // Add profile button click listener
        findViewById<FloatingActionButton>(R.id.addProfileBtn).setOnClickListener {
            val intent = Intent(this@BookListActivity, AddBookActivity::class.java)
            startActivity(intent)
        }

        setupSwipeToDelete(recyclerView)

    }

    // Show delete confirmation dialog
    private fun showDeleteConfirmationDialog(bookLibrary: BookLibrary) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Book")
        builder.setMessage("Are you sure you want to delete this Book?")

        builder.setPositiveButton("Yes") { dialog, which ->
            BookViewModel.deleteUserProfile(bookLibrary)  // Delete the profile
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()  // Cancel delete operation
        }


        val dialog = builder.create()
        dialog.show()


    }
    private fun setupSwipeToDelete(recyclerView: RecyclerView) {
        // Attach ItemTouchHelper for swipe actions
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val book = bookAdapter.getBookAtPosition(position)
                BookViewModel.deleteUserProfile(book) // Deleting directly from the ViewModel
            }

        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}