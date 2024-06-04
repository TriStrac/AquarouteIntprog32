package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cansal.aquaroute.databinding.FragmentCustomerDashboardBinding
import com.cansal.aquaroute.databinding.FragmentCustomerOrdersBinding

class CustomerOrdersFragment : Fragment() {
    private var _binding: FragmentCustomerOrdersBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.historyButton.setOnClickListener{
            val intent = Intent(context, CustomerOrderHistoryActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
        }
    }
    //
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}