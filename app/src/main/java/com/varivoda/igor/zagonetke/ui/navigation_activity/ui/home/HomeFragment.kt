package com.varivoda.igor.zagonetke.ui.navigation_activity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.varivoda.igor.zagonetke.R
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Exception

const val image_url = "https://i.snipboard.io/6Lxcrt.jpg"
class HomeFragment : Fragment() {

    private var navController: NavController? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        Picasso.get().load(image_url).error(R.drawable.ic_no_connection).into(titlePicture,object : Callback{
            override fun onSuccess() {
                progress.visibility = View.GONE
            }
            override fun onError(e: Exception?) {
                //rjesenje: error(R.drawable.ic_no_connection)
            }
        })
        startButton.setOnClickListener {
            navController!!.navigate(R.id.action_nav_home_to_nav_levels)
        }
    }
}
