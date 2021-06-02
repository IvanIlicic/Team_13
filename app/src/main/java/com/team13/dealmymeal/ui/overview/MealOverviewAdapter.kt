package com.team13.dealmymeal.ui.overview

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.team13.dealmymeal.data.Meal
import com.team13.dealmymeal.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


/**
 * [ListAdapter] that can display a [Meal].
 *
 */
class MealOverviewAdapter(
        private var valuesOriginal: MutableList<Meal>,
        private val listener: OnItemClickListener
) : ListAdapter<Meal, MealOverviewAdapter.ViewHolder>(MEAL_COMPARATOR), Filterable {

    var tracker: SelectionTracker<Meal>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_meal_overview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tracker?.let {
            holder.setItemSelected(it.isSelected(getItem(position)))
        }

        val current = getItem(position)
        holder.bind(current, position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val txtTitle: TextView = view.findViewById(R.id.item_name)
        private val imgBackgroundMeal: ImageView = view.findViewById(R.id.item_frame)
        private val cardMeal: MaterialCardView = view.findViewById(R.id.card_background)
        private val chips: ChipGroup = view.findViewById(R.id.chip_group)
        private val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
        val context: Context = view.context

        init {
            itemView.setOnClickListener(this)
        }

        override fun toString(): String {
            return super.toString()
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Meal> =
            object : ItemDetailsLookup.ItemDetails<Meal>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Meal? = getItem(bindingAdapterPosition)
            }

        fun setItemSelected(isSelected: Boolean = false) {
            cardMeal.isSelected = isSelected
        }

        fun bind(meal: Meal, position: Int) {
            txtTitle.text = meal.title

            val typedValue = TypedValue()
            when (position % 2) {
                0 -> context.theme.resolveAttribute(R.attr.overview_item_1, typedValue, true)
                1 -> context.theme.resolveAttribute(R.attr.overview_item_2, typedValue, true)
            }
            imgBackgroundMeal.setImageResource(typedValue.resourceId)

            chips.removeAllViews()
            for (category in meal.categories!!){
                val chip = Chip(context)
                chip.text = context.resources.getStringArray(R.array.categories)[category]
                chip.setChipBackgroundColorResource(R.color.green)
                chip.setTextColor(Color.WHITE)
                chips.addView(chip)
            }

            ratingBar.rating = meal.rating!!
        }
    }

    class MealItemKeyProvider(private val adapter: MealOverviewAdapter) :
        ItemKeyProvider<Meal>(SCOPE_CACHED) {
        override fun getKey(position: Int): Meal? {
            return adapter.getItem(position)
        }

        override fun getPosition(key: Meal): Int {
            return adapter.currentList.indexOf(key)//adapter.getI.indexOfFirst { it.name == key.title }
        }
    }

    class MealItemDetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Meal>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<Meal>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as ViewHolder)
                    .getItemDetails()
            }
            return null
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                submitList(results.values as MutableList<Meal>?)
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                if(currentList.size >= valuesOriginal.size)
                    valuesOriginal = currentList
                val filteredResults = if (constraint.isEmpty()) {
                    valuesOriginal
                } else {
                    getFilteredResults(constraint.toString().toLowerCase(Locale.getDefault()))
                }
                val results = FilterResults()
                results.values = filteredResults
                return results
            }
        }
    }

    private fun getFilteredResults(constraint: String?): List<Meal?> {
        val results: MutableList<Meal?> = ArrayList()
        for (item in valuesOriginal) {
            if (constraint?.let { item.title?.toLowerCase(Locale.getDefault())?.contains(it) } == true) {
                results.add(item)
            }
        }
        return results
    }

    fun filterCategory(category: Int) {
        resetFilter()
        valuesOriginal = currentList
        val results: MutableList<Meal?> = ArrayList()
        for (item in valuesOriginal) {
            if (item.categories?.contains(category) == true) {
                results.add(item)
            }
        }
        submitList(results)
    }

    fun filterRating(category: Float) {
        resetFilter()
        valuesOriginal = currentList
        val results: MutableList<Meal?> = ArrayList()
        for (item in valuesOriginal) {

            if (item.rating!!.roundToInt() == category.roundToInt())
                results.add(item)
        }
        submitList(results)
    }

    fun resetFilter() {
        if(currentList.size >= valuesOriginal.size)
            valuesOriginal = currentList

        submitList(valuesOriginal)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    companion object {
        private val MEAL_COMPARATOR = object : DiffUtil.ItemCallback<Meal>() {
            override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
                return oldItem == newItem
            }
        }
    }
}