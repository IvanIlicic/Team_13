package com.team13.dealmymeal.ui.overview

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team13.dealmymeal.MealItem
import com.team13.dealmymeal.R
import com.team13.dealmymeal.dummy.DummyContent

/**
 * A fragment representing a list of Items.
 */
class MealOverviewFragment : Fragment(), SearchView.OnQueryTextListener, ActionMode.Callback {

    private var columnCount = 1

    private var tracker: SelectionTracker<MealItem>? = null

    private var selectedPostItems: MutableList<MealItem> = mutableListOf()
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
                // TODO hier items von db

                adapter =
                    MealOverviewAdapter(
                        ArrayList()
                        //DummyContent.generateDummyList(15)
                    )

                tracker = SelectionTracker.Builder<MealItem>(
                    "mySelection",
                    view,
                    MealOverviewAdapter.MyItemKeyProvider(
                        adapter as MealOverviewAdapter
                    ),
                    MealOverviewAdapter.MyItemDetailsLookup(
                        view
                    ),
                    StorageStrategy.createParcelableStorage(MealItem::class.java)
                ).withSelectionPredicate(
                    SelectionPredicates.createSelectAnything()
                ).build()

                (adapter as MealOverviewAdapter).tracker = tracker

                tracker?.addObserver(
                    object : SelectionTracker.SelectionObserver<MealItem>() {
                        override fun onSelectionChanged() {
                            super.onSelectionChanged()
                            tracker?.let {
                                selectedPostItems = it.selection.toMutableList()

                                // TODO enable this when implementing delete
                                /*
                                if (selectedPostItems.isEmpty()) {
                                    actionMode?.finish()
                                } else {
                                    if (actionMode == null) actionMode = parent.startActionModeForChild(view, this@MealOverviewFragment)
                                    actionMode?.title =
                                        "${selectedPostItems.size}"
                                }*/

                                // TODO delete
                            }
                        }
                    })
                overviewAdapter = adapter as MealOverviewAdapter;
            }
        }
        return view
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_view_delete -> {
                Toast.makeText(
                    context,
                    selectedPostItems.toString(),
                    Toast.LENGTH_LONG
                ).show()
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

private fun MenuItem.setOnMenuItemClickListener(function: (MenuItem) -> Unit) {

}