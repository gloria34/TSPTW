package dnu.fpm.tsptw.ui.fragment.createnewtrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dnu.fpm.tsptw.databinding.FragmentCreateNewTripBinding
import dnu.fpm.tsptw.ui.base.BaseFragment

class CreateNewTripFragment : BaseFragment() {
    lateinit var binding: FragmentCreateNewTripBinding
    lateinit var viewModel: CreateNewTripViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(CreateNewTripViewModel::class.java)
        binding = FragmentCreateNewTripBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}