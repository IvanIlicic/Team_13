package com.team13.dealmymeal.dummy

import com.team13.dealmymeal.MealItem
import java.util.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    fun generateDummyList(count: Int): List<MealItem> {
        val ret = ArrayList<MealItem>()
        for (i in 1..count) {
            ret.add(createDummyItem(i))
        }
        return ret
    }

    private fun createDummyItem(position: Int): MealItem {
        //return position.toString() + " Item"
        val name = "$position Item"
        return MealItem(name, 1, 1)
    }
}