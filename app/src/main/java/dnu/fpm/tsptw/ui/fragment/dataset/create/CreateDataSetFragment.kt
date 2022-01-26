package dnu.fpm.tsptw.ui.fragment.dataset.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.databinding.FragmentCreateDataSetBinding

class CreateDataSetFragment : Fragment() {

    private lateinit var createDataSetViewModel: CreateDataSetViewModel
    private var _binding: FragmentCreateDataSetBinding? = null
    private val _adapter = CreateDataSetAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createDataSetViewModel =
            ViewModelProvider(this).get(CreateDataSetViewModel::class.java)
        _binding = FragmentCreateDataSetBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.pointsRecyclerView?.adapter = _adapter
        if (activity != null) {
            createDataSetViewModel.dataSet.observe(requireActivity(), {
                _adapter.points = it.points
                _binding?.createDataSetViewModel = createDataSetViewModel
                _adapter.notifyItemInserted(0)
            })
        }
        createDataSetViewModel.createEmptyDataSet()
        _binding?.plusButton?.setOnClickListener {
            _adapter.points.add(Point(null, null, null, null))
            _adapter.notifyItemInserted(_adapter.points.size - 1)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}