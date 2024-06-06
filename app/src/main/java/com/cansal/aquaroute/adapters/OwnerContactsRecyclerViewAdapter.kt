package com.cansal.aquaroute.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cansal.aquaroute.R
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer
import com.cansal.aquaroute.models.Subscribers

class OwnerContactsRecyclerViewAdapter(
    private val context: Context,
    private val dataList: MutableList<Subscribers>
) : RecyclerView.Adapter<OwnerContactsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_recycler_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.customerName.text = currentItem.customerName
        holder.deleteButton.setOnClickListener {
            showDeleteConfirmationDialog(currentItem)
        }
        holder.itemView.setOnClickListener {
            showCustomerDetailsDialog(currentItem)
        }
    }
    fun updateSubscribers(newList: List<Subscribers>) {
        dataList.clear() // Clear the existing list
        dataList.addAll(newList) // Add the new list
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.customerNameText)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }



    private fun showDeleteConfirmationDialog(item: Subscribers) {
        AlertDialog.Builder(context)
            .setMessage("Deleting customer will remove them from your contact list and will automatically unsubscribe from your station. Confirm deletion?")
            .setPositiveButton("Confirm") { dialog, _ ->
                // Delete the customer (e.g., call a function to remove from database)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showCustomerDetailsDialog(item: Subscribers) {
        val message = "Name: ${item.customerName}\n" +
                "Number: ${item.customerPhone}\n" +
                "Address: ${item.customerAddress}"

        AlertDialog.Builder(context)
            .setTitle("Customer Details")
            .setMessage(message)
            .setPositiveButton("Call") { dialog, _ ->
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${item.customerPhone}")
                context.startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}

