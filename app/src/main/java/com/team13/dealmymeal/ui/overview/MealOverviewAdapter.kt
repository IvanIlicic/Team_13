package com.team13.dealmymeal.ui.overview

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
import kotlin.math.roundToInt


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
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

        view.setOnClickListener {
            //TODO edit
            //TODO check if we are in selection -> abort
            if(tracker?.hasSelection() == false) {
                Toast.makeText(parent.context, "Edit", Toast.LENGTH_SHORT).show()
                view.findViewById<TextView>(R.id.item_name).setTextColor(Color.RED)
            }
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = values[position]
        //holder.idView.text = item.name


        tracker?.let {
            holder.setItemSelected(it.isSelected(getItem(position)))
        }

        val current = getItem(position)
        holder.bind(current.title, position, current.categories , current.rating)
        Log.d("Adapter", current.title)

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val itemName: TextView = view.findViewById(R.id.item_name)

        val background: ConstraintLayout = view.findViewById(R.id.item_frame)
        val cardBackground: MaterialCardView = view.findViewById(R.id.card_background)
        val chips: ChipGroup = view.findViewById(R.id.chip_group)
        val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
        val context: Context = view.context


        init {
            itemView.setOnClickListener(this)
        }


        override fun toString(): String {
            return super.toString()
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition //adapterposition
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
            cardBackground.isSelected = isSelected
        }

        fun bind(text: String?, position: Int, categories: List<Int>?, rating: Float?) {
            itemName.text = text

            if((context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES)
                when (position % 2) {
                    0 -> background.setBackgroundResource(R.drawable.ic_backgroundorangedark)
                    1 -> background.setBackgroundResource(R.drawable.ic_backgroundgreendark)
                }
            else
                when (position % 2) {
                    0 -> background.setBackgroundResource(R.drawable.ic_backgroundorangelight)
                    1 -> background.setBackgroundResource(R.drawable.ic_backgroundgreenlight)
                }

            chips.removeAllViews()
            if (categories != null) {
                for (category in categories){
                    val chip = Chip(context)
                    chip.text = context.resources.getStringArray(R.array.categories)[category]
                    chip.setChipBackgroundColorResource(R.color.green)
                    chip.setTextColor(Color.WHITE)
                    chips.addView(chip)
                }
            }

            if (rating != null) {
                ratingBar.rating = rating
            }

        }
    }

    class MyItemKeyProvider(private val adapter: MealOverviewAdapter) :
        ItemKeyProvider<Meal>(SCOPE_CACHED) {
        override fun getKey(position: Int): Meal? {
            return adapter.getItem(position)
        }

        override fun getPosition(key: Meal): Int {
            return adapter.currentList.indexOf(key)//adapter.getI.indexOfFirst { it.name == key.title }
        }
    }

    class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
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
                submitList(results.values as List<Meal>)
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                if(currentList.size >= valuesOriginal.size)
                    valuesOriginal = currentList
                var filteredResults = if (constraint.isEmpty()) {
                    valuesOriginal
                } else {
                    getFilteredResults(constraint.toString().toLowerCase())
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
            if (constraint?.let { item.title?.toLowerCase()?.contains(it) } == true) {
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
                return oldItem.title == newItem.title
            }
        }
    }
}