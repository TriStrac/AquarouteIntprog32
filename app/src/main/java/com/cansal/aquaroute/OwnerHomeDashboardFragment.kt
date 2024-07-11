package com.cansal.aquaroute

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cansal.aquaroute.databinding.FragmentHomeOwnerBinding

class OwnerHomeDashboardFragment : Fragment() {

    private var _binding: FragmentHomeOwnerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeOwnerBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.stationProfileButton.setOnClickListener {
            val intent = Intent(requireContext(), OwnerHomeStationProfile::class.java)
            val options = ActivityOptions.makeCustomAnimation(requireContext(), R.anim.zoom_in_intro, R.anim.zoom_in_outro)
            startActivity(intent, options.toBundle())
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
