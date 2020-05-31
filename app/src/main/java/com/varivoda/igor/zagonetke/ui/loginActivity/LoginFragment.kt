package com.varivoda.igor.zagonetke.ui.loginActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import com.varivoda.igor.zagonetke.ui.shared.Preferences
import com.varivoda.igor.zagonetke.ui.shared.Utils
import com.varivoda.igor.zagonetke.ui.shared.bounceAnimation
import com.varivoda.igor.zagonetke.ui.shared.toast
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private var navController: NavController? = null
    private val loginViewModel by viewModel<LoginViewModel>()
    private val preferences: Preferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        buttonLogIn.setOnClickListener {
            validateLoginCredentials()
        }
        loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            if (it!=null) {
                preferences.saveToken(it.token)
                preferences.setUser(usernameInput.text.toString())
                startActivity(Intent(activity, NavigationActivity::class.java))
                activity?.finish()
            } else {
                buttonLogIn.bounceAnimation()
                Utils.showSelectedToast(requireContext(),getString(R.string.username_or_password_not_correct))
            }

        })

    }

    override fun onStart() {
        super.onStart()
        val animation = AnimationUtils.loadAnimation(context,R.anim.grow_animation)
        logo.startAnimation(animation)
    }

    private fun validateLoginCredentials() {
        if (usernameInput.text.trim().isEmpty() || passwordInput.text.toString().trim().isEmpty()
        ) {
            buttonLogIn.bounceAnimation()
            Utils.showSelectedToast(requireContext(),getString(R.string.did_not_entered_login_info))
        } else {
            loginViewModel.validateUsernameAndPassword(
                usernameInput.text.toString(),
                passwordInput.text.toString()
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newAccountText.setOnClickListener {
            navController?.navigate(R.id.action_loginFragment_to_registerFragment)
        }
        forgotPasswordText.setOnClickListener {
            navController?.navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }


}
