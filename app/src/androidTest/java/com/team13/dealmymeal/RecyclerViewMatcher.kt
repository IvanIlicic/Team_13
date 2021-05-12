package com.team13.dealmymeal

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import com.team13.dealmymeal.dummy.DummyContent
import com.team13.dealmymeal.ui.overview.MealOverviewAdapter
import org.hamcrest.Matcher
import org.hamcrest.Matchers.any


class RecyclerViewMatcher {

    companion object {
        fun populate(count: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return any(View::class.java)
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController, view: View) {
                    val recyclerView = view as RecyclerView
                    val recyclerViewAdapter = recyclerView.adapter as MealOverviewAdapter
                    //recyclerViewAdapter.addItems(DummyContent.generateDummyList(count))
                }
            }
        }
    }
}