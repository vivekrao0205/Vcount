package com.vcount.app.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vcount.app.R
import com.vcount.app.adapters.StreakAdapter
import com.vcount.app.models.StreakModel

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    private val streakList = ArrayList<StreakModel>()
    private lateinit var adapter: StreakAdapter

    private val ADD_REQUEST = 1
    private val EDIT_REQUEST = 2
    private var editPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupRecycler()
        setupListeners()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        fabAdd = findViewById(R.id.fabAdd)
    }

    private fun setupRecycler() {
        adapter = StreakAdapter(streakList) { position ->
            val item = streakList[position]

            val intent = Intent(this, AddEditActivity::class.java)
            intent.putExtra("title", item.title)
            intent.putExtra("count", item.count)

            editPosition = position
            startActivityForResult(intent, EDIT_REQUEST)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {

            val title = data.getStringExtra("title") ?: return
            val count = data.getIntExtra("count", 0)

            if (requestCode == ADD_REQUEST) {
                // Add new
                streakList.add(StreakModel(title, count))
                adapter.notifyItemInserted(streakList.size - 1)

            } else if (requestCode == EDIT_REQUEST && editPosition != -1) {
                // Update
                streakList[editPosition].title = title
                streakList[editPosition].count = count
                adapter.notifyItemChanged(editPosition)
            }
        }
    }
}