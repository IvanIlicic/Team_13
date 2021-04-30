package com.team13.dealmymeal

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import com.team13.dealmymeal.dummy.DummyContent
import com.team13.dealmymeal.ui.overview.MealOverviewAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


class RecyclerViewMatcher {
    var mRecyclerViewId = 0

    fun RecyclerViewMatcher(recyclerViewId: Int) {
        mRecyclerViewId = recyclerViewId
    }

    fun actionPopulate(id: Int, count: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val recyclerView = view as RecyclerView
                if(recyclerView == null)
                    throw Exception("lol")
                val recyclerViewAdapter = recyclerView.adapter as MealOverviewAdapter
                if(recyclerViewAdapter == null)
                    throw Exception("lol2")
                recyclerViewAdapter.addItems(DummyContent.ITEMS)
            }
        }
    }
}