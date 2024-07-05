package com.example.streetfoodadmin.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streetfoodadmin.databinding.DeliveryItemBinding

class DeliveryAdapter(private val customerNames:ArrayList<String>,private val paymentStatus:ArrayList<String>) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun getItemCount(): Int = customerNames.size

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class DeliveryViewHolder(private val binding:DeliveryItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.customerNameText.text = customerNames[position]
            binding.paymentStatustext.text = paymentStatus[position]

            val colorMap = mapOf(
                "Received" to Color.GREEN,"Not Received" to Color.RED,"Pending" to Color.GRAY
            )
            binding.paymentStatustext.setTextColor(colorMap[paymentStatus[position]]?:Color.BLACK)
            binding.statusIndicator.backgroundTintList = ColorStateList.valueOf(colorMap[paymentStatus[position]]?:Color.YELLOW)
        }

    }
}