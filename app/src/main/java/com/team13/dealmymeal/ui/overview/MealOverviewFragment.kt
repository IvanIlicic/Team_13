package com.team13.dealmymeal.ui.overview

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team13.dealmymeal.*
import com.team13.dealmymeal.data.Meal
import com.team13.dealmymeal.data.MealViewModel
import com.team13.dealmymeal.data.MealViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class MealOverviewFragment : Fragment(), ActionMode.Callback, SearchView.OnQueryTextListener, MealOverviewAdapter.OnItemClickListener {
    private val mealViewModel: MealViewModel by viewModels {
        MealViewModelFactory((activity?.application as MealApplication).repository)
    }

    private var columnCount = 1
    private var tracker: SelectionTracker<Meal>? = null
    private var selectedPostItems: MutableList<Meal> = mutableListOf()
    private var actionMode: ActionMode? = null
    private var overviewAdapter: MealOverviewAdapter? = null
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_overview_list, container, false)

        setHasOptionsMenu(true)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                adapter =
                    MealOverviewAdapter(
                        ArrayList(), this@MealOverviewFragment
                    )

                tracker = SelectionTracker.Builder(
                    "mySelection",
                    view,
                    MealOverviewAdapter.MealItemKeyProvider(
                        adapter as MealOverviewAdapter
                    ),
                    MealOverviewAdapter.MealItemDetailsLookup(
                        view
                    ),
                    StorageStrategy.createParcelableStorage(Meal::class.java)
                ).withSelectionPredicate(
                    SelectionPredicates.createSelectAnything()
                ).build()

                tracker?.addObserver(
                    object : SelectionTracker.SelectionObserver<Meal>() {
                        override fun onSelectionChanged() {
                            super.onSelectionChanged()
                            tracker?.let {
                                selectedPostItems = it.selection.toMutableList()

                                if (selectedPostItems.isEmpty()) {
                                    actionMode?.finish()
                                } else {
                                    if (actionMode == null) actionMode = parent.startActionModeForChild(view, this@MealOverviewFragment)
                                    actionMode?.title =
                                        "${selectedPostItems.size}"
                                }
                            }
                        }
                    })

                overviewAdapter = adapter as MealOverviewAdapter
                overviewAdapter!!.tracker = tracker
                // Add an observer on the LiveData returned by getAll.
                // The onChanged() method fires when the observed data changes and the activity is
                // in the foreground.
                mealViewModel.allMeals.observe(viewLifecycleOwner) { meals ->
                    // Update the cached copy of the words in the adapter.
                    meals.let { overviewAdapter!!.submitList(it) }
                }
                //Log.d("MOF", "calling coroutine")
                //main(context, overviewAdapter!!).invoke()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_view_delete -> {

                val builder = AlertDialog.Builder(context)
                builder.setMessage(R.string.delete_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes) { _, _ ->
                            // Delete selected note from database
                            for (meal in selectedPostItems)
                                mealViewModel.delete(meal)
                        }
                        .setNegativeButton(R.string.no) { dialog, _ ->
                            // Dismiss the dialog
                            dialog.dismiss()
                        }
                val alert = builder.create()
                alert.show()

            }
        }
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.let {
            val inflater: MenuInflater = it.menuInflater
            inflater.inflate(R.menu.overview_selection_menu, menu)
            return true
        }
        return false
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        overviewAdapter?.tracker?.clearSelection()
        overviewAdapter?.notifyDataSetChanged()
        actionMode = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overview_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            R.id.action_filter -> {
                Log.d("MealOverview", "Filter")

                if (!item.isChecked)
                {
                    val categories = resources.getStringArray(R.array.categories)
                    val selectCategoryAlert = AlertDialog.Builder(context)
                    selectCategoryAlert.setTitle(R.string.chooseCategory)
                    selectCategoryAlert.setSingleChoiceItems(categories, -1) { dialog, selection ->

                        overviewAdapter?.filterCategory(selection)
                        item.isChecked  = true
                        item.setIcon(R.drawable.ic_close)
                        dialog.dismiss()
                    }
                    selectCategoryAlert.create().show()

                }else{

                    overviewAdapter?.resetFilter()
                    item.isChecked = false
                    item.setIcon(R.drawable.ic_filter)
                }
                // TODO add filter for rating & type (AlertDialog)
                true
            }
            R.id.action_filter_star -> {
                Log.d("MealOverview", "Filter")
                // TODO add filter for rating & type (AlertDialog)
                if (!item.isChecked)
                {
                    val stars = arrayOf("1 star", "2 stars", "3 stars", "4 stars")
                    val stars2 = arrayOf(1.0f,2.0f,3.0f,4.0f)
                    val selectStarAlert = AlertDialog.Builder(context)
                    selectStarAlert.setTitle(R.string.chooseRating)
                    selectStarAlert.setSingleChoiceItems(stars, -1) { dialog, selection ->

                        overviewAdapter?.filterRating(stars2[selection])
                        item.isChecked  = true
                        item.setIcon(R.drawable.ic_filter_star_filled)
                        dialog.dismiss()
                    }
                    selectStarAlert.create().show()

                }else{

                    overviewAdapter?.resetFilter()
                    item.isChecked = false
                    item.setIcon(R.drawable.ic_filter_star)
                }
                true
            }
            else -> false
        }
    }

    override fun onQueryTextChange(query: String?): Boolean {
        // Here is where we are going to implement the filter logic
        overviewAdapter?.filter?.filter(query)
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }




    override fun onItemClick(position: Int) {
        val clickedMeal = overviewAdapter!!.currentList[position]

        val bundle = bundleOf("Meal" to clickedMeal)
        navController.navigate(R.id.action_navigation_overview_to_editMealFragment, bundle)

        overviewAdapter?.notifyItemChanged(position)
    }

}
