package com.vcount.app.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vcount.app.R
import com.vcount.app.adapters.StreakAdapter
import com.vcount.app.models.StreakModel
import org.json.JSONArray
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var adapter: StreakAdapter

    private val streakList = ArrayList<StreakModel>()

    private val ADD_REQUEST = 1
    private val EDIT_REQUEST = 2
    private var editPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupRecycler()
        setupListeners()
        loadData()
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

        // 🔥 Layout animation
        val animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
        recyclerView.layoutAnimation = animation
    }

    private fun setupListeners() {
        fabAdd.setOnClickListener {

            val intent = Intent(this, AddEditActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST)

            // 🔥 Smooth rotation animation
            fabAdd.animate()
                .rotationBy(360f)
                .setDuration(400)
                .start()
        }
    }

    // 🔥 SAVE DATA
    private fun saveData() {
        val sharedPref = getSharedPreferences("streak_prefs", MODE_PRIVATE)
        val editor = sharedPref.edit()

        val jsonList = JSONArray()

        for (item in streakList) {
            val obj = JSONObject()
            obj.put("title", item.title)
            obj.put("count", item.count)
            jsonList.put(obj)
        }

        editor.putString("streak_data", jsonList.toString())
        editor.apply()
    }

    // 🔥 LOAD DATA
    private fun loadData() {
        val sharedPref = getSharedPreferences("streak_prefs", MODE_PRIVATE)
        val jsonString = sharedPref.getString("streak_data", null)

        streakList.clear()

        if (jsonString != null) {
            val jsonList = JSONArray(jsonString)

            for (i in 0 until jsonList.length()) {
                val obj = jsonList.getJSONObject(i)
                val title = obj.getString("title")
                val count = obj.getInt("count")

                streakList.add(StreakModel(title, count))
            }
        }

        adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK || data == null) return

        val title = data.getStringExtra("title") ?: return
        val count = data.getIntExtra("count", 0)

        if (requestCode == ADD_REQUEST) {

            streakList.add(StreakModel(title, count))
            adapter.notifyItemInserted(streakList.size - 1)

            recyclerView.smoothScrollToPosition(streakList.size - 1)

            // 🔥 Item animation
            recyclerView.scheduleLayoutAnimation()

            saveData()

        } else if (requestCode == EDIT_REQUEST && editPosition != -1) {

            streakList[editPosition].title = title
            streakList[editPosition].count = count
            adapter.notifyItemChanged(editPosition)

            saveData()
        }
    }
}