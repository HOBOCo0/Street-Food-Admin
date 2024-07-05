package com.example.streetfoodadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.streetfoodadmin.adapter.DeliveryAdapter
import com.example.streetfoodadmin.adapter.PendingOrderAdapter
import com.example.streetfoodadmin.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {

    private val binding:ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val customerNames =  arrayListOf("John Abc", "Alex Xyz", "Tom Uvw")
        val quantities = arrayListOf("2", "4","6")
        val foodImages = arrayListOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3)

        val adapter = PendingOrderAdapter(customerNames,quantities,foodImages)
        binding.pendingRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pendingRecyclerView.adapter = adapter
    }
}