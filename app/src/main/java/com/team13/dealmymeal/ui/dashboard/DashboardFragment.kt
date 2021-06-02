package com.team13.dealmymeal.ui.dashboard

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.team13.dealmymeal.MainActivity
import com.team13.dealmymeal.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.dashboard_menu, menu)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.language -> {
                    val languages = arrayOf("русский", "English")
                    val selectLanguageAlert = AlertDialog.Builder(context)
                    selectLanguageAlert.setTitle(R.string.chooseLanguage)
                    selectLanguageAlert.setSingleChoiceItems(languages, -1) { dialog, selection ->
                        when(selection) {
                            0 -> {
                                (activity as MainActivity?)!!.setLocale("ru")
                            }
                            1 -> {
                                (activity as MainActivity?)!!.setLocale("en")
                            }
                        }
                        (activity as MainActivity?)!!.recreate()
                        dialog.dismiss()
                    }
                    selectLanguageAlert.create().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}