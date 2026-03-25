package com.vcount.app.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vcount.app.R
import com.vcount.app.adapters.StreakAdapter
import com.vcount.app.models.StreakModel

class HomeActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var fabAdd: FloatingActionButton? = null

    private val streakList = ArrayList<StreakModel>()
    private var adapter: StreakAdapter? = null

    private val ADD_REQUEST = 1
    private val EDIT_REQUEST = 2
    private var editPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_home)
        } catch (e: Exception) {
            showFallback("Layout error")
            return
        }

        initViews()
        setupRecycler()
        setupListeners()
        streakList.add(StreakModel("GYM", 5))
        streakList.add(StreakModel("CODE", 3))
        streakList.add(StreakModel("GUITER", 7))
        streakList.add(StreakModel("SCRIPT", 2))
        adapter?.notifyDataSetChanged()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        fabAdd = findViewById(R.id.fabAdd)

        if (recyclerView == null || fabAdd == null) {
            showFallback("View ID error")
        }
    }

    private fun setupRecycler() {
        recyclerView ?: return

        adapter = StreakAdapter(streakList) { position ->
            if (position !in streakList.indices) return@StreakAdapter

            val item = streakList[position]

            val intent = Intent(this, AddEditActivity::class.java)
            intent.putExtra("title", item.title)
            intent.putExtra("count", item.count)

            editPosition = position
            startActivityForResult(intent, EDIT_REQUEST)
        }

        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
    }

    private fun setupListeners() {
        fabAdd?.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK || data == null) return

        val title = data.getStringExtra("title") ?: return
        val count = data.getIntExtra("count", 0)

        if (requestCode == ADD_REQUEST) {
            streakList.add(StreakModel(title, count))
            adapter?.notifyItemInserted(streakList.size - 1)

        } else if (requestCode == EDIT_REQUEST && editPosition != -1) {
            if (editPosition < streakList.size) {
                streakList[editPosition].title = title
                streakList[editPosition].count = count
                adapter?.notifyItemChanged(editPosition)
            }
        }
    }

    private fun showFallback(msg: String) {
        val tv = android.widget.TextView(this)
        tv.text = msg
        tv.textSize = 22f
        tv.setPadding(40, 40, 40, 40)
        setContentView(tv)
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}