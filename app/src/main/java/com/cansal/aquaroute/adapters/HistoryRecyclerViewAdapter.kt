package com.cansal.aquaroute.adapters

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cansal.aquaroute.R
import com.cansal.aquaroute.databinding.DialogOrderDetailsBinding
import com.cansal.aquaroute.databinding.HistoryRecycleLayoutBinding
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer

class HistoryRecyclerViewAdapter(
    private val activity: Activity,
    private var ordersList: List<OrdersForOwnerToCustomer>
) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(
        private val activity: Activity,
        private val binding: HistoryRecycleLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrdersForOwnerToCustomer) {
            binding.customerNameText.text = order.customerName
            binding.customerOrderText.text = order.orderAmount.toString()
            binding.customerAddressText.text = order.customerAddress


            binding.orderStatusText.text = if (order.orderPickupStatus == "Done") {
                order.orderDeliveryStatus
            } else {
                order.orderPickupStatus
            }
            itemView.setOnClickListener {
                showOrderDetailsDialog(order)
            }
        }

        private fun showOrderDetailsDialog(order: OrdersForOwnerToCustomer) {
            val inflater = LayoutInflater.from(activity)
            val dialogBinding = DialogOrderDetailsBinding.inflate(inflater)

            val alertDialogBuilder = AlertDialog.Builder(activity)
            val dialog = alertDialogBuilder.create()

            dialog.setView(dialogBinding.root)

            dialog.window?.setBackgroundDrawableResource(R.drawable.white_back)

            dialogBinding.customerNameText.text = "Customer Name: ${order.customerName}"
            dialogBinding.orderAmountText.text = "Quantity of Jugs: ${order.orderAmount}"
            dialogBinding.paymentText.text = "Payment: ${order.orderCost}"
            dialogBinding.orderStatusText.text = "Order Status: For Delivery"
            dialogBinding.deliveryTimeText.text = "Delivery Time: ${order.deliveryTime}"
            dialogBinding.addressText.text = "Address: ${order.customerAddress}"

            dialogBinding.okButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryRecycleLayoutBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(activity, binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(ordersList[position])
    }

    override fun getItemCount() = ordersList.size
}
