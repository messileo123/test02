package com.example.test02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var listButton: Button

    override    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listButton = findViewById(R.id.profileListBtn)

        listButton.setOnClickListener {

            val intent = Intent(this, BookListActivity::class.java)
            // Start LoadingActivity instead of ProfileListActivity
            intent.putExtra("TARGET_ACTIVITY", "com.example.personallibraryapp.ProfileListActivity")

            startActivity(intent)
            finish() // Finish MainActivity so the user can't return to it
        }
    }
}