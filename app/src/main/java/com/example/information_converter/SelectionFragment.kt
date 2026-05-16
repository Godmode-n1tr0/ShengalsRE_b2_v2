package com.example.information_converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.information_converter.ResultFragment
import com.example.information_converter.databinding.FragmentSelectionBinding

class SelectionFragment : Fragment() {

    private var _binding: FragmentSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val units = resources.getStringArray(R.array.units_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter

        binding.buttonCalculate.setOnClickListener {
            val numberStr = binding.editTextNumber.text.toString()
            if (numberStr.isNotEmpty()) {
                val inputVal = numberStr.toDouble()
                val fromUnit = binding.spinnerFrom.selectedItem.toString()
                val toUnit = binding.spinnerTo.selectedItem.toString()

                val result = convertUnits(inputVal, fromUnit, toUnit)
                val resultText = "$inputVal $fromUnit = $result $toUnit"
                
                binding.editTextResult.setText(result.toString())

                val resultFragment = ResultFragment.newInstance(numberStr, resultText)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, resultFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun convertUnits(value: Double, from: String, to: String): Double {
        val bytes = when (from) {
            "Байт" -> value
            "Килобайт" -> value * 1024
            "Мегабайт" -> value * 1024 * 1024
            "Гигабайт" -> value * 1024 * 1024 * 1024
            else -> value
        }
        
        return when (to) {
            "Байт" -> bytes
            "Килобайт" -> bytes / 1024
            "Мегабайт" -> bytes / (1024 * 1024)
            "Гигабайт" -> bytes / (1024 * 1024 * 1024)
            else -> bytes
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
