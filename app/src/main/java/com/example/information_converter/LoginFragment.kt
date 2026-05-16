package com.example.information_converter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.information_converter.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: android.content.SharedPreferences
    private val PREFS_NAME = "user_prefs"
    private val KEY_LOGIN = "login"
    private val KEY_PASSWORD = "password"
    private val KEY_IS_FIRST_LOGIN = "is_first_login"

    private val MASTER_LOGIN = "ects"
    private val MASTER_PASSWORD = "ects2026"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        binding.buttonStart.setOnClickListener {
            val loginInput = binding.editTextLogin.text.toString().trim()
            val passwordInput = binding.editTextPassword.text.toString().trim()

            if (loginInput.isEmpty() || passwordInput.isEmpty()) {
                showAlertDialog("Введите логин и пароль")
                return@setOnClickListener
            }

            val isFirstLogin = sharedPreferences.getBoolean(KEY_IS_FIRST_LOGIN, true)
            val isMasterLogin = (loginInput == MASTER_LOGIN && passwordInput == MASTER_PASSWORD)

            if (isMasterLogin) {
                if (isFirstLogin) {
                    saveCredentials(loginInput, passwordInput)
                }
                navigateToSelection()
            } else if (isFirstLogin) {
                saveCredentials(loginInput, passwordInput)
                navigateToSelection()
            } else {
                val savedLogin = sharedPreferences.getString(KEY_LOGIN, "")
                val savedPassword = sharedPreferences.getString(KEY_PASSWORD, "")

                if (loginInput == savedLogin && passwordInput == savedPassword) {
                    navigateToSelection()
                } else {
                    showAlertDialog("Неверный логин или пароль")
                }
            }
        }
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Внимание")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun saveCredentials(login: String, password: String) {
        sharedPreferences.edit().apply {
            putString(KEY_LOGIN, login)
            putString(KEY_PASSWORD, password)
            putBoolean(KEY_IS_FIRST_LOGIN, false)
            apply()
        }
    }

    private fun navigateToSelection() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SelectionFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}