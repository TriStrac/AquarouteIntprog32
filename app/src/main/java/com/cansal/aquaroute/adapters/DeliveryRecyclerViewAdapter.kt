package com.cansal.aquaroute.adapters

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cansal.aquaroute.databinding.PickupDeliveryRecycleLayoutBinding
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer

class DeliveryRecyclerViewAdapter(
    private val activity: Activity,
    private var ordersList: List<OrdersForOwnerToCustomer>,
    private val confirmPickUp: (OrdersForOwnerToCustomer) -> Unit,
    private val cancelPickUp: (OrdersForOwnerToCustomer) -> Unit
) : RecyclerView.Adapter<DeliveryRecyclerViewAdapter.DeliveryViewHolder>() {
    class DeliveryViewHolder(
        private val activity: Activity,
        private val binding: PickupDeliveryRecycleLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrdersForOwnerToCustomer, confirmPickUp: (OrdersForOwnerToCustomer) -> Unit, cancelPickUp: (OrdersForOwnerToCustomer) -> Unit) {
            binding.customerNameText.text = order.customerName
            binding.customerOrderText.text = "${order.orderAmount} Jugs"
            binding.customerAddressText.text = order.customerAddress.take(20)
            binding.deliveryCostText.text = "${order.orderCost} PHP"
            binding.deliveryTimeText.text = "Delivery time: ${order.deliveryTime}"

            binding.acceptButton.setOnClickListener {
                showConfirmDialog("Confirm pickup completion?", order, confirmPickUp)
            }

            binding.declineButton.setOnClickListener {
                showConfirmDialog("Confirm cancellation of pickup?", order, cancelPickUp)
            }
        }

        private fun showConfirmDialog(message: String, order: OrdersForOwnerToCustomer, action: (OrdersForOwnerToCustomer) -> Unit) {
            AlertDialog.Builder(activity).apply {
                setMessage(message)
                setPositiveButton("Okay") { _, _ ->
                    action(order)
                    Toast.makeText(activity, "Action confirmed for ${order.customerName}", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PickupDeliveryRecycleLayoutBinding.inflate(
            inflater,
            parent,
            false
        )
        return DeliveryViewHolder(activity, binding)
    }

    override fun getItemCount() = ordersList.size

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(ordersList[position], confirmPickUp, cancelPickUp)
    }
}
