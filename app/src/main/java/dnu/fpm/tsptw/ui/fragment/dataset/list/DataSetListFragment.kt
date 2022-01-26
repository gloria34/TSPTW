package dnu.fpm.tsptw.ui.fragment.dataset.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dnu.fpm.tsptw.databinding.FragmentDataSetListBinding

class DataSetListFragment : Fragment() {

    private lateinit var dataSetListViewModel: DataSetListViewModel
    private var _binding: FragmentDataSetListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataSetListViewModel =
            ViewModelProvider(this).get(DataSetListViewModel::class.java)

        _binding = FragmentDataSetListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}