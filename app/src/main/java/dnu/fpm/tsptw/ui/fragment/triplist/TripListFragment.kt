package dnu.fpm.tsptw.ui.fragment.triplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dnu.fpm.tsptw.databinding.FragmentTripListBinding
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
    }
}