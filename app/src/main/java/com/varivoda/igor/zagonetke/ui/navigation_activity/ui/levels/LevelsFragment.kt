package com.varivoda.igor.zagonetke.ui.navigation_activity.ui.levels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.models.Riddle
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.riddle.RiddleViewModel
import com.varivoda.igor.zagonetke.ui.shared.Preferences
import kotlinx.android.synthetic.main.fragment_levels.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val RIDDLE_OBJECT = "riddle_object"

class LevelsFragment : Fragment(),LevelAdapter.OnItemClickListener {

    private val riddleViewModel by viewModel<RiddleViewModel>()
    private val levelsViewModel by viewModel<LevelsViewModel>()
    private val preferences by inject<Preferences>()
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_levels, container, false)
        setURecyclerView(root)
        return root
    }

    private fun setURecyclerView(root: View) {
        riddleViewModel.getAllRiddles(preferences.getToken())
        riddleViewModel.allRiddles.observe(viewLifecycleOwner, Observer {
            levelsViewModel.getScoreForUser(preferences.getToken(),preferences.getUser())
            levelsViewModel.scoreForUser.observe(viewLifecycleOwner, Observer {score ->
                if(score!=null) {
                    val adapter: LevelAdapter
                    if (it != null) {
                        adapter = LevelAdapter(it, score)
                        adapter.setOnItemClickListener(this)
                        root.recyclerView.adapter = adapter
                    } else {
                        val list = listOf<Riddle>()
                        adapter = LevelAdapter(list, score)
                        adapter.setOnItemClickListener(this)
                        root.recyclerView.adapter = adapter
                    }
                }
            })

        })
        root.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onClick(riddle: Riddle) {
        val bundle = bundleOf(RIDDLE_OBJECT to riddle)
        navController?.navigate(R.id.action_nav_levels_to_riddleFragment,bundle)
    }
}
