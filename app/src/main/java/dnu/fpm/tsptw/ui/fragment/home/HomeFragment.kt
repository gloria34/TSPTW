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
        binding.createNewTripButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createNewTripFragment)
        }
        val trips = ArrayList<DataSet>()
        trips.add(
            DataSet(
                points = arrayListOf(
                    Point(30.233417, -92.101965, false),
                    Point(30.233155, -92.014507, false),
                    Point(30.201409, -91.999563, false),
                    Point(30.167296, -92.036249, false),
                    Point(30.109821, -92.240990, false)
                )
            )
        )
        binding.tripsRecyclerView.adapter = TripsAdapter(trips, object : OnTripClickListener {
            override fun onTripClick(position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("trip", trips[position])
                findNavController().navigate(R.id.action_homeFragment_to_tripFragment, bundle)
            }
        })
    }
}