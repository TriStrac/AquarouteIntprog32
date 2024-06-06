package com.cansal.aquaroute.adapters

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cansal.aquaroute.R
import com.cansal.aquaroute.models.OwnerUser
import com.cansal.aquaroute.models.Subscribers
import com.cansal.aquaroute.storage.LocalStorage
import com.google.firebase.database.FirebaseDatabase

class CustomerDiscoverRecyclerViewAdapter(private val activity: Activity,private val dataSet: List<OwnerUser>) :
    RecyclerView.Adapter<CustomerDiscoverRecyclerViewAdapter.ViewHolder>() {
    // ViewHolder class for the recycler view item
    private var subscribedEmail: String? = null
    private lateinit var flagButton: ImageView
    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val stationNameText: TextView = view.findViewById(R.id.stationNameText)
        private val stationAddressText: TextView = view.findViewById(R.id.stationAddressText)

        fun bind(data: OwnerUser,view: View) {
            stationNameText.text = data.stationName ?: ""  // Handle potential null value

            val address = buildString {
                append(data.street ?: "")
                if (data.unit != null) {
                    append(", #" + data.unit)
                }
                append("\n" + data.region ?: "")
            }
            stationAddressText.text = address
            view.setOnClickListener {
                showStationDetailsDialog(data)
            }

            flagButton.setImageResource(
                if (subscribedEmail == data.email) R.drawable.baseline_home_filled_24 // Unsubscribe icon
                else R.drawable.baseline_outlined_flag_24   // Subscribe icon
            )

            flagButton.setOnClickListener {
                if (subscribedEmail == data.email) {
                    unsubscribeFromStation(data.email!!)  // Unsubscribe if already subscribed
                } else {
                    subscribeToStation(data)  // Subscribe if not already subscribed
                }
            }
        }
    }
    fun encodeEmail(email: String): String {
        return email.replace(".", ",")
    }
    private fun subscribeToStation(data: OwnerUser) {
        val localStorage = LocalStorage(activity)  // Assuming LocalStorage provides methods
        val email = encodeEmail(localStorage.loggedInEmail)
        val subscriber = Subscribers(
            ownerEmail = data.email!!,
            customerEmail = email,
            customerName = localStorage.loggedInName,
            customerPhone = localStorage.loggedInPhoneNumber,
            customerAddress = buildString {
                append(localStorage.loggedInUnit ?: "")
                if (localStorage.loggedInStreet != null) {
                    append(", ${localStorage.loggedInStreet}")
                }
                if (localStorage.loggedInRegion != null) {
                    append(", ${localStorage.loggedInRegion}")
                }
            }
        )

        val firebaseRef = FirebaseDatabase.getInstance().getReference("subscribers")
        firebaseRef.child(email).setValue(subscriber)
            .addOnSuccessListener {
                subscribedEmail = data.email  // Update subscribed email
                flagButton.setImageResource(R.drawable.baseline_home_filled_24)  // Update icon
                Toast.makeText(
                    activity, "Subscribed to station!", Toast.LENGTH_SHORT
                ).show()
                notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun unsubscribeFromStation(ownerEmail: String) {
        val localStorage = LocalStorage(activity)  // Assuming LocalStorage provides methods
        val email = encodeEmail(localStorage.loggedInEmail)

        val firebaseRef = FirebaseDatabase.getInstance().getReference("subscribers")
        firebaseRef.child(email).removeValue()
            .addOnSuccessListener {
                subscribedEmail = null  // Clear subscribed email
                flagButton.setImageResource(R.drawable.baseline_outlined_flag_24)  // Update icon
                Toast.makeText(
                    activity, "Unsubscribed from station!", Toast.LENGTH_SHORT
                ).show()
                notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.customer_discover_recycle_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        flagButton = view.findViewById(R.id.flagButton)
        holder.bind(dataSet[position], view)
    }

    override fun getItemCount() = dataSet.size

    private fun showStationDetailsDialog(data: OwnerUser) {
        AlertDialog.Builder(activity).apply {
            setTitle(data.stationName ?: "")  // Handle potential null value
            setMessage("Address: ${data.region},${data.street}\nEmail: ${data.email}\nPhone Number: ${data.phone}")
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

            create()
            show()
        }
    }
}

