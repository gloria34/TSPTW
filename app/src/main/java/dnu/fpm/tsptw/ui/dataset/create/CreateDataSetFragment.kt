package dnu.fpm.tsptw.ui.dataset.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dnu.fpm.tsptw.databinding.FragmentCreateDataSetBinding

class CreateDataSetFragment : Fragment() {

    private lateinit var createDataSetViewModel: CreateDataSetViewModel
    private var _binding: FragmentCreateDataSetBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createDataSetViewModel =
            ViewModelProvider(this).get(CreateDataSetViewModel::class.java)

        _binding = FragmentCreateDataSetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        createDataSetViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}