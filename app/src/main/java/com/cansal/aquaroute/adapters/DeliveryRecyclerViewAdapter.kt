package com.cansal.aquaroute.adapters

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cansal.aquaroute.R
import com.cansal.aquaroute.databinding.DialogOrderDetailsBinding
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
            binding.acceptButton.setImageResource(R.drawable.orders)

            binding.acceptButton.setOnClickListener {
                showConfirmDialog("Confirm pickup completion?", order, confirmPickUp)
            }

            binding.declineButton.setOnClickListener {
                showConfirmDialog("Confirm cancellation of pickup?", order, cancelPickUp)
            }

            itemView.setOnClickListener {
                showOrderDetailsDialog(order)

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
