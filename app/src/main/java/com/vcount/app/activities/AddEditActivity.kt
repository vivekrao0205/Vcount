package com.vcount.app.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.vcount.app.R

class AddEditActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var tvCount: TextView
    private lateinit var btnPlus: ImageButton
    private lateinit var btnMinus: ImageButton
    private lateinit var btnSave: Button

    private var count = 0
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        initViews()
        checkEditMode()
        setupListeners()
    }

    private fun initViews() {
        etTitle = findViewById(R.id.etTitle)
        tvCount = findViewById(R.id.tvCount)
        btnPlus = findViewById(R.id.btnPlus)
        btnMinus = findViewById(R.id.btnMinus)
        btnSave = findViewById(R.id.btnSave)

        updateCountUI()
    }

    private fun checkEditMode() {
        if (intent.hasExtra("title")) {
            isEditMode = true

            val title = intent.getStringExtra("title")
            val receivedCount = intent.getIntExtra("count", 0)

            etTitle.setText(title)
            count = receivedCount
            updateCountUI()

            btnSave.text = "Update"
        }
    }

    private fun setupListeners() {

        btnPlus.setOnClickListener {
            count++
            animateCount()
            updateCountUI()
        }

        btnMinus.setOnClickListener {
            if (count > 0) {
                count--
                animateCount()
                updateCountUI()
            }
        }

        btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun updateCountUI() {
        tvCount.text = count.toString()
    }

    private fun animateCount() {
        tvCount.scaleX = 1.2f
        tvCount.scaleY = 1.2f

        tvCount.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(150)
            .start()
    }

    private fun saveData() {
        val title = etTitle.text.toString().trim()

        if (title.isEmpty()) {
            etTitle.error = "Enter activity name"
            return
        }

        val resultIntent = Intent()
        resultIntent.putExtra("title", title)
        resultIntent.putExtra("count", count)

        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}