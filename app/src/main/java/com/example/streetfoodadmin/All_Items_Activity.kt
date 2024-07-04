package com.example.streetfoodadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.streetfoodadmin.adapter.AllItemAdapter
import com.example.streetfoodadmin.databinding.ActivityAllItemsBinding

class All_Items_Activity : AppCompatActivity() {


    private val binding: ActivityAllItemsBinding by lazy {
        ActivityAllItemsBinding.inflate(layoutInflater)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        // Create dummy data for recycler view
        val itemFoodName = arrayListOf("Burger", "Sandwich", "momo", "spring roll")

        val itemFoodPrice = arrayListOf("50 Rs", "60 Rs", "120 Rs", "80 Rs")

        val itemFoodImage =
            arrayListOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4)

        val adapter = AllItemAdapter(itemFoodName, itemFoodPrice, itemFoodImage)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = adapter
    }
}