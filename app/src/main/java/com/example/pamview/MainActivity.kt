package com.example.pamview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

data class DevItem(val code: String, val device: String)

class MainActivity : AppCompatActivity() {
    private lateinit var deviceAdapter: DAdapter
    private val deviceList = mutableListOf(
        DevItem("1001", "Android"),
        DevItem("1002", "iOS"),
        DevItem("1003", "Linux"),
        DevItem("1004", "Windows")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView: RecyclerView = findViewById(R.id.deviceRecyclerView)
        deviceAdapter = DAdapter(deviceList)
        recyclerView.adapter = deviceAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dateEditText: TextInputEditText = findViewById(R.id.codeEditText)
        val taskEditText: TextInputEditText = findViewById(R.id.deviceEditText)
        val addButton: Button = findViewById(R.id.addButton)

        addButton.setOnClickListener {
            val date = dateEditText.text.toString()
            val task = taskEditText.text.toString()

            if (date.isNotEmpty() && task.isNotEmpty()) {
                val newDevItem = DevItem(date, task)
                deviceAdapter.addItem(newDevItem)
                recyclerView.scrollToPosition(deviceList.size - 1)

                // Clear input fields
                dateEditText.text?.clear()
                taskEditText.text?.clear()
            } else {
                Toast.makeText(this, "Please fill both date and task", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class DAdapter(private val deviceList: MutableList<DevItem>) :
        RecyclerView.Adapter<DAdapter.DViewHolder>() {

        inner class DViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val codeTextView: TextView = itemView.findViewById(R.id.codeTextView)
            val deviceTextView: TextView = itemView.findViewById(R.id.deviceTextView)
            val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.dev_item, parent, false)
            return DViewHolder(itemView)
        }


        override fun onBindViewHolder(holder: DViewHolder, position: Int) {
            val currentItem = deviceList[position]
            holder.codeTextView.text = currentItem.code
            holder.deviceTextView.text = currentItem.device

            holder.deleteButton.setOnClickListener {
                deleteItem(position)
            }
        }

        override fun getItemCount() = deviceList.size

        fun addItem(DItem: DevItem) {
            deviceList.add(DItem)
            notifyItemInserted(deviceList.size - 1)
        }

        private fun deleteItem(position: Int) {
            if (position >= 0 && position < deviceList.size) {
                deviceList.removeAt(position)
                notifyItemRemoved(position)
            } else {
                Toast.makeText(this@MainActivity, "Invalid position", Toast.LENGTH_SHORT).show()
            }
        }

    }
}