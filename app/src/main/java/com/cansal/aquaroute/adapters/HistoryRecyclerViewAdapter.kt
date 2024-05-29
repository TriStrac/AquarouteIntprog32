package com.cansal.aquaroute.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
