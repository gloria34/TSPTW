package dnu.fpm.tsptw.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.databinding.FragmentHomeBinding
import dnu.fpm.tsptw.ui.adapter.OnTripClickListener
import dnu.fpm.tsptw.ui.adapter.TripsAdapter
import dnu.fpm.tsptw.ui.base.BaseFragment

class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.createNewTripButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createNewTripFragment)
        }
        val trips = viewModel.loadTripsForToday()
        binding.tripsRecyclerView.adapter = TripsAdapter(trips, object : OnTripClickListener {
            override fun onTripClick(position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("trip", trips[position])
                findNavController().navigate(R.id.action_homeFragment_to_tripFragment, bundle)
            }
        })
    }
}