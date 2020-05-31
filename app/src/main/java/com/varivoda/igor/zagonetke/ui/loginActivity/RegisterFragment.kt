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
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {
    private val loginViewModel by viewModel<LoginViewModel>()
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        buttonLogInNew.setOnClickListener {
            buttonLogInFunction()
        }

        loginViewModel.createUserResponse.observe(viewLifecycleOwner, Observer {
            if(it){
                Utils.showSelectedToast(requireContext(),getString(R.string.registration_success))
                navController!!.popBackStack()
            }else{
                buttonLogInNew.bounceAnimation()
                Utils.showSelectedToast(requireContext(),getString(R.string.username_taken))
            }
        })
    }

    private fun buttonLogInFunction() {
        if(usernameInputNew.text.isNotEmpty() && firstNameInputNew.text.isNotEmpty() && lastNameInputNew.text.isNotEmpty()
            && petNameInputNew.text.isNotEmpty() && emailInputNew.text.isNotEmpty() && passwordInputNew.text.toString().isNotEmpty()){
            val (digits, notDigits) = passwordInputNew.text.toString().partition { it.isDigit() }
            if(passwordInputNew.text.toString().length < 8 || digits.isEmpty() || notDigits.isEmpty()){
                buttonLogInNew.bounceAnimation()
                Utils.showSelectedToast(requireContext(),getString(R.string.password_demands))
            }else{
                if(!emailInputNew.text.toString().contains('@')){
                    buttonLogInNew.bounceAnimation()
                    Utils.showSelectedToast(requireContext(),getString(R.string.valid_email))
                    return
                }
                loginViewModel.createUser(usernameInputNew.text.toString(),firstNameInputNew.text.toString(),lastNameInputNew.text.toString(),
                    petNameInputNew.text.toString(),emailInputNew.text.toString(),passwordInputNew.text.toString())
            }
        }else{
            buttonLogInNew.bounceAnimation()
            Utils.showSelectedToast(requireContext(),getString(R.string.did_not_entered_login_info))
        }
    }

}
