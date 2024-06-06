package com.cansal.aquaroute

import android.app.Activity
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import com.cansal.aquaroute.databinding.FragmentHomeOwnerBinding
import com.cansal.aquaroute.storage.LocalStorage
import com.google.firebase.database.*

class OwnerHomeDashboardFragment : Fragment() {

    private var _binding: FragmentHomeOwnerBinding? = null
    private val binding get() = _binding!!
    private lateinit var localStorage: LocalStorage
    private val PROFILE_UPDATE_REQUEST_CODE = 100
    private lateinit var email: String
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeOwnerBinding.inflate(inflater, container, false)
        val view = binding.root

        localStorage = LocalStorage(requireContext())
        database = FirebaseDatabase.getInstance().reference
        email = encodeEmail(localStorage.loggedInEmail)

        binding.stationNameText.text = localStorage.loggedInStationName
        binding.ordersPendingCount.text = localStorage.ownerOrdersPending
        binding.ordersCompletedCount.text = localStorage.ownerOrdersCompleted
        binding.jugsStockCount.text = localStorage.ownerJugsInStock

        updateCounts()

        binding.stationProfileButton.setOnClickListener {
            val intent = Intent(requireContext(), OwnerHomeStationProfile::class.java)
            val options = ActivityOptions.makeCustomAnimation(requireContext(), R.anim.zoom_in_intro, R.anim.zoom_in_outro)
            startActivityForResult(intent, PROFILE_UPDATE_REQUEST_CODE, options.toBundle())
        }

        binding.contactsQuickActions.setOnClickListener {
            val intent = Intent(requireContext(), OwnerContacts::class.java)
            val options = ActivityOptions.makeCustomAnimation(requireContext(), R.anim.zoom_in_intro, R.anim.zoom_in_outro)
            startActivity(intent, options.toBundle())
        }

        binding.analyticsQuickActions.setOnClickListener {
            showSorryDialog()
        }

        binding.customerMapQuickActions.setOnClickListener {
            showSorryDialog()
        }

        return view
    }

    private fun updateCounts() {
        lifecycleScope.launchWhenStarted {
            val userRef = database.child("stationStats/$email") // Use the encoded email path

            val valueEventListener = userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val newOrdersPending = dataSnapshot.child("ordersPending").value?.toString() ?: "0"
                        val newOrdersCompleted = dataSnapshot.child("ordersCompleted").value?.toString() ?: "0"
                        val newJugsInStock = dataSnapshot.child("ownerJugsInStock").value?.toString() ?: "0"

                        // Update local storage first
                        localStorage.ownerOrdersPending = newOrdersPending
                        localStorage.ownerOrdersCompleted = newOrdersCompleted
                        localStorage.ownerJugsInStock = newJugsInStock

                        // Then update the text views
                        binding.ordersPendingCount.text = newOrdersPending
                        binding.ordersCompletedCount.text = newOrdersCompleted
                        binding.jugsStockCount.text = newJugsInStock
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

            // Detach the listener when the fragment is destroyed to avoid memory leaks
            viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    userRef.removeEventListener(valueEventListener)
                }
            })
        }
    }

    private fun encodeEmail(email: String): String {
        return email.replace(".", ",") // Or use a URL-safe encoding library
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROFILE_UPDATE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Update the stationNameText after returning from OwnerHomeStationProfile
            binding.stationNameText.text = localStorage.loggedInStationName
        }
    }

    private fun showSorryDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Unavailable for now, Sorry :(")
            .setPositiveButton("Okay") { dialog, _ ->
                // Do something when "Okay" button is clicked
                dialog.dismiss() // Dismiss the dialog
            }
            .setCancelable(false) // Prevent dialog from being canceled by clicking outside
        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
