package com.cansal.aquaroute

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cansal.aquaroute.adapters.OwnerContactsRecyclerViewAdapter
import com.cansal.aquaroute.models.Subscribers
import com.cansal.aquaroute.databinding.ActivityOwnerContactsBinding
import com.cansal.aquaroute.storage.LocalStorage
import com.google.firebase.database.*

class OwnerContacts : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActivityOwnerContactsBinding
    private lateinit var adapter: OwnerContactsRecyclerViewAdapter
    private lateinit var localStorage: LocalStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOwnerContactsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        localStorage = LocalStorage(this)

        binding.backIcon.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        adapter = OwnerContactsRecyclerViewAdapter(this, mutableListOf())

        binding.contactsRecyclerView.adapter = adapter
        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launchWhenStarted {
            fetchOrderList()
        }
    }

    private suspend fun fetchOrderList() {
        val subscribersList = mutableListOf<Subscribers>()
        val database = FirebaseDatabase.getInstance().reference
        val ownerEmail = encodeEmail(localStorage.loggedInEmail)

        val query = database.child("subscribers")
            .orderByChild("ownerEmail")
            .equalTo(ownerEmail)

        val valueEventListener = query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                subscribersList.clear()
                for (snapshot in dataSnapshot.children) {
                    val subscriber = snapshot.getValue(Subscribers::class.java)
                    if (subscriber != null && subscriber.customerAddress != "D3F4ULT") {
                        subscribersList.add(subscriber)
                    }
                }
                adapter.updateSubscribers(subscribersList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors (optional)
            }
        })

        lifecycleScope.launchWhenStarted {
        }
    }


    private fun encodeEmail(email: String): String {
        return email.replace(".", ",")
    }
}
