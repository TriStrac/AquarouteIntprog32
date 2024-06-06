package com.cansal.aquaroute

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cansal.aquaroute.adapters.CustomerDiscoverRecyclerViewAdapter
import com.cansal.aquaroute.databinding.FragmentCustomerDiscoverBinding
import com.cansal.aquaroute.models.OwnerUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CustomerDiscoverFragment : Fragment() {

    private lateinit var binding: FragmentCustomerDiscoverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerDiscoverBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firebase reference for owner data
        val ownerRef = FirebaseDatabase.getInstance().getReference("users")

        val ownerValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val ownerList = mutableListOf<OwnerUser>()
                for (child in dataSnapshot.children) {
                    val ownerData = child.getValue(OwnerUser::class.java)
                    if (ownerData != null) {
                        ownerList.add(ownerData)
                    }
                }

                val adapter = CustomerDiscoverRecyclerViewAdapter(requireActivity(), ownerList)
                binding.discoverRecyclerView2.layoutManager = LinearLayoutManager(context)
                binding.discoverRecyclerView2.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        ownerRef.addValueEventListener(ownerValueEventListener)
    }
}
