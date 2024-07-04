package com.example.streetfoodadmin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streetfoodadmin.databinding.AllMenuItemBinding

class AllItemAdapter(
    private val itemName: ArrayList<String>, private val itemPrice: ArrayList<String>,
    private val itemImage: ArrayList<Int>
) : RecyclerView.Adapter<AllItemAdapter.AllItemViewHolder>() {

    // by default it's indexing starts from 1
    private val itemQuantities = IntArray(itemName.size) { 1 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemViewHolder {
        val binding = AllMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return itemName.size
    }

    inner class AllItemViewHolder(private val binding: AllMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val quantity = itemQuantities[position]

            binding.apply {

                itemFoodName.text = itemName[position]
                itemFoodPrice.text = itemPrice[position]
                foodImage.setImageResource(itemImage[position])
                textView17.text = quantity.toString()
                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }
                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }
                deleteButton.setOnClickListener {
                    deleteItem(position)
                }
            }


        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.textView17.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.textView17.text = itemQuantities[position].toString()
            }
        }

        private fun deleteItem(position: Int) {
            itemName.removeAt(position)
            itemPrice.removeAt(position)
            itemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemName.size)
        }
    }
}