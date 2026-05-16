package com.example.information_converter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.information_converter.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enteredValue = arguments?.getString(ARG_ENTERED) ?: ""
        val resultValue = arguments?.getString(ARG_RESULT) ?: ""

        binding.textViewEnteredLabel.text = "${getString(R.string.entered_number_text)}: $enteredValue"
        binding.editTextResult.setText(resultValue)

        binding.buttonRegistration.setOnClickListener {
            parentFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ENTERED = "entered"
        private const val ARG_RESULT = "result"

        fun newInstance(entered: String, result: String) = ResultFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_ENTERED, entered)
                putString(ARG_RESULT, result)
            }
        }
    }
}