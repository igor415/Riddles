package com.varivoda.igor.zagonetke.ui.navigation_activity.ui.highscore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import com.varivoda.igor.zagonetke.ui.shared.Preferences
import kotlinx.android.synthetic.main.high_score_fragment.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HighScoreFragment : Fragment() {

    private val highScoreViewModel by viewModel<HighScoreViewModel>()
    private val preferences by inject<Preferences>()
    private lateinit var highScoreAdapter: HighScoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.high_score_fragment, container, false)
        highScoreViewModel.getHighScores(preferences.getToken())
        highScoreViewModel.highScores.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                highScoreAdapter = HighScoreAdapter(it,preferences.getUser())
                root.recyclerView.adapter = highScoreAdapter
            }else{
                highScoreAdapter = HighScoreAdapter(listOf(),preferences.getUser())
                root.recyclerView.adapter = highScoreAdapter
            }
        })
        root.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as NavigationActivity).supportActionBar?.title =
            getString(R.string.best_players)
    }

}
