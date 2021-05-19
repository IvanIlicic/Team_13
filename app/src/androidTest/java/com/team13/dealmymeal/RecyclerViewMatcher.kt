package com.team13.dealmymeal

import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.ViewActions.actionWithAssertions
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
