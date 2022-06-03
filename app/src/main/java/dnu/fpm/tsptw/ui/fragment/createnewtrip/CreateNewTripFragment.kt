package dnu.fpm.tsptw.ui.fragment.createnewtrip

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.databinding.FragmentCreateNewTripBinding
import dnu.fpm.tsptw.databinding.ItemCreatePointBinding
import dnu.fpm.tsptw.ui.base.BaseFragment
import java.util.*

class CreateNewTripFragment : BaseFragment() {
    private val calendar: Calendar = Calendar.getInstance()
    private val itemCreatePointBindings: ArrayList<ItemCreatePointBinding> = ArrayList()

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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        showPoints()
        binding.addNewPointButton.setOnClickListener {
            addPointView()
        }
        binding.dateLinerLayout.setOnClickListener {
            showDatePicker()
        }
        binding.saveTextView.setOnClickListener {
            if (validateData()) {
                viewModel.dataSet.value?.tripName = binding.nameEditText.text.toString()
                saveDataSet()
                findNavController().navigate(R.id.action_createNewTripFragment_to_homeFragment)
            }
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun saveDataSet() {
        viewModel.dataSet.value?.points?.clear()
        for (itemCreatePointBinding in itemCreatePointBindings) {
            viewModel.dataSet.value?.points?.add(
                Point(
                    itemCreatePointBinding.latitudeEditText.text.toString().toDouble(),
                    itemCreatePointBinding.longitudeEditText.text.toString().toDouble(),
                    0
                )
            )
        }
        viewModel.saveDataSet()
    }

    private fun showPoints() {
        if (viewModel.dataSet.value != null) {
            for (point in viewModel.dataSet.value!!.points) {
                addPointView()
            }
        }
    }

    private fun addPointView() {
        val itemCreatePointBinding = ItemCreatePointBinding.inflate(layoutInflater)
        itemCreatePointBinding.removeImageView.setOnClickListener {
            binding.pointsLinerLayout.removeView(itemCreatePointBinding.root)
            itemCreatePointBindings.remove(itemCreatePointBinding)
        }
        itemCreatePointBindings.add(itemCreatePointBinding)
        binding.pointsLinerLayout.addView(itemCreatePointBinding.root)
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                showTimePicker()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                if (viewModel.dataSet.value != null) {
                    viewModel.dataSet.value!!.date = calendar.timeInMillis
                }
                showDate()
            },
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    private fun showDate() {
        binding.dateTextView.text = viewModel.dataSet.value?.getStringDate()
    }

    private fun validateData(): Boolean {
        var isDataValid = true
        if (binding.nameEditText.text.toString().isEmpty()) {
            isDataValid = false
            showEditTextError(binding.nameEditText, R.string.trip_name_must_be_not_empty)
        }
        for (itemCreatePointBinding in itemCreatePointBindings) {
            try {
                val lat = itemCreatePointBinding.latitudeEditText.text.toString().toDouble()
                if (lat > 90 || lat < -90) {
                    isDataValid = false
                    showEditTextError(
                        itemCreatePointBinding.latitudeEditText,
                        R.string.latitude_must_be_a_number_between_90_and_90
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showEditTextError(
                    itemCreatePointBinding.latitudeEditText,
                    R.string.invalid_number_format
                )
                isDataValid = false
            }
            try {
                val lon = itemCreatePointBinding.longitudeEditText.text.toString().toDouble()
                if (lon > 180 || lon < -180) {
                    isDataValid = false
                    showEditTextError(
                        itemCreatePointBinding.longitudeEditText,
                        R.string.longitude_must_be_a_number_between_180_and_180
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showEditTextError(
                    itemCreatePointBinding.longitudeEditText,
                    R.string.invalid_number_format
                )
                isDataValid = false
            }
        }
        if (itemCreatePointBindings.isEmpty() || itemCreatePointBindings.size < 3) {
            Toast.makeText(
                requireContext(),
                getString(R.string.your_trip_is_too_short),
                Toast.LENGTH_SHORT
            ).show()
            isDataValid = false
        }
        return isDataValid
    }

    private fun showEditTextError(editText: TextInputEditText, @StringRes error: Int) {
        editText.error = getString(error)
    }
}