package dnu.fpm.tsptw.ui.fragment.triplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.databinding.FragmentTripListBinding
import dnu.fpm.tsptw.ui.adapter.OnTripClickListener
import dnu.fpm.tsptw.ui.adapter.TripsAdapter
import dnu.fpm.tsptw.ui.base.BaseFragment

class TripListFragment : BaseFragment() {
    lateinit var binding: FragmentTripListBinding
    lateinit var viewModel: TripListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(TripListViewModel::class.java)
        binding = FragmentTripListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val trips = ArrayList<DataSet>()
        trips.add(
            DataSet(
                points = arrayListOf()
            )
        )
        binding.tripsRecyclerView.adapter = TripsAdapter(trips, object : OnTripClickListener {
            override fun onTripClick(position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("trip", trips[position])
                findNavController().navigate(R.id.action_tripListFragment_to_tripFragment, bundle)
            }
        })
    }
}