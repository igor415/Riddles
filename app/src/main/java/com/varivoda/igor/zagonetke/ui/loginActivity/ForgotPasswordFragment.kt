package com.varivoda.igor.zagonetke.ui.loginActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.ui.shared.Utils
import com.varivoda.igor.zagonetke.ui.shared.bounceAnimation
import com.varivoda.igor.zagonetke.ui.shared.toast
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ForgotPasswordFragment : Fragment() {
    private val loginViewModel by viewModel<LoginViewModel>()
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        buttonLogInForgot.setOnClickListener {
            buttonLogInForgotFunction()
        }
        loginViewModel.changePasswordResponse.observe(viewLifecycleOwner, Observer {
            if(it){
                Utils.showSelectedToast(requireContext(),getString(R.string.changed_password_success))
                navController!!.popBackStack()
            }else{
                Utils.showSelectedToast(requireContext(),getString(R.string.inserted_info_not_correct))
            }
        })
    }

    private fun buttonLogInForgotFunction() {
        if(userNameForgot.text.isNotEmpty() && petNameForgot.text.isNotEmpty() && passwordInputForgot.text.toString().isNotEmpty()){
            val (digits, notDigits) = passwordInputForgot.text.toString().partition { it.isDigit() }
            if(passwordInputForgot.text.toString().length < 8 || digits.isEmpty() || notDigits.isEmpty()){
                buttonLogInForgot.bounceAnimation()
                Utils.showSelectedToast(requireContext(),getString(R.string.password_demands))
            }else {
                loginViewModel.changePassword(
                    userNameForgot.text.toString(),
                    petNameForgot.text.toString(),
                    passwordInputForgot.text.toString()
                )
            }
        }else{
            buttonLogInForgot.bounceAnimation()
            Utils.showSelectedToast(requireContext(),getString(R.string.did_not_entered_login_info))
        }
    }

}
