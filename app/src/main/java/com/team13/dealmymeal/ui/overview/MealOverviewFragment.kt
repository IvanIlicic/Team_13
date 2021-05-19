package com.team13.dealmymeal.ui.overview

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Filterable
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team13.dealmymeal.R
import com.team13.dealmymeal.Meal
import com.team13.dealmymeal.MealViewModel
import com.team13.dealmymeal.MealViewModelFactory
import com.team13.dealmymeal.MealApplication

/**
 * A fragment representing a list of Items.
 */
class MealOverviewFragment : Fragment(), ActionMode.Callback, SearchView.OnQueryTextListener {
    private val mealViewModel: MealViewModel by viewModels {
        MealViewModelFactory((activity?.application as MealApplication).repository)
    }

    private var columnCount = 1

    private var tracker: SelectionTracker<Meal>? = null

    private var selectedPostItems: MutableList<Meal> = mutableListOf()
    private var actionMode: ActionMode? = null
    private var overviewAdapter: MealOverviewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

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
                        ArrayList()
                    )

                tracker = SelectionTracker.Builder<Meal>(
                    "mySelection",
                    view,
                    MealOverviewAdapter.MyItemKeyProvider(
                        adapter as MealOverviewAdapter
                    ),
                    MealOverviewAdapter.MyItemDetailsLookup(
                        view
                    ),
                    StorageStrategy.createParcelableStorage(Meal::class.java)//createParcelableStorage(Meal::class.java)
                ).withSelectionPredicate(
                    SelectionPredicates.createSelectAnything()
                ).build()


                tracker?.addObserver(
                    object : SelectionTracker.SelectionObserver<Meal>() {
                        override fun onSelectionChanged() {
                            super.onSelectionChanged()
                            tracker?.let {
                                selectedPostItems = it.selection.toMutableList()


                                // TODO enable this when implementing delete

                                if (selectedPostItems.isEmpty()) {
                                    actionMode?.finish()
                                } else {
                                    if (actionMode == null) actionMode = parent.startActionModeForChild(view, this@MealOverviewFragment)
                                    actionMode?.title =
                                        "${selectedPostItems.size}"
                                    for (item in selectedPostItems) {
                                       // (view as RecyclerView).findViewHolderForItemId(item).
                                      //  ((view as RecyclerView).findViewHolderForItemId(item) as MealOverviewAdapter.ViewHolder?)?.setItemSelected(item, true)

                                    }
                                   // (adapter as MealOverviewAdapter).notifyDataSetChanged()

                                }

                                // TODO delete


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

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_view_delete -> {
                val builder = AlertDialog.Builder(context)
                builder.setMessage(R.string.delete_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes) { dialog, id ->
                            // Delete selected note from database
                            for (meal in selectedPostItems)
                                mealViewModel.delete(meal)
                        }
                        .setNegativeButton(R.string.no) { dialog, id ->
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
            inflater.inflate(R.menu.delete, menu)
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
        inflater.inflate(R.menu.meal_overview_search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        /*
        val filterItem = menu.findItem(R.id.action_filter)
        filterItem.setOnMenuItemClickListener{
            return@setOnMenuItemClickListener true
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            R.id.action_filter -> {
                Log.d("MealOverview", "Filter")
                // TODO add filter for rating & type (AlertDialog)
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


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MealOverviewFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
