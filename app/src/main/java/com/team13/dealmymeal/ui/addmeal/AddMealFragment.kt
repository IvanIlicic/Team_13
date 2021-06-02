package com.team13.dealmymeal.ui.addmeal

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.team13.dealmymeal.*
import com.team13.dealmymeal.data.Meal
import com.team13.dealmymeal.data.MealViewModel
import com.team13.dealmymeal.data.MealViewModelFactory
import kotlinx.coroutines.runBlocking


class AddMealFragment : Fragment() {
    private val mealViewModel: MealViewModel by viewModels {
        MealViewModelFactory((activity?.application as MealApplication).repository)
    }
    private lateinit var addMealViewModel: AddMealViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addMealViewModel =
                ViewModelProvider(this).get(AddMealViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_addmeal, container, false)

        val btnSave = root.findViewById<Button>(R.id.form_save)
        val editTitle = root.findViewById<EditText>(R.id.form_edit)
        val ratingBar = root.findViewById<RatingBar>(R.id.form_ratingBar)
        val switchMeat = root.findViewById<SwitchMaterial>(R.id.check_meat)
        val switchVeggie = root.findViewById<SwitchMaterial>(R.id.check_veggie)
        val switchSpecial = root.findViewById<SwitchMaterial>(R.id.check_special)
        val text = editTitle.text
        btnSave.setOnClickListener {

            val stars = ratingBar.rating
            val type = ArrayList<Int>()
            if (switchMeat.isChecked)
                type.add(0)
            if (switchVeggie.isChecked)
                type.add(1)
            if (switchSpecial.isChecked)
                type.add(2)
            val meal = Meal(text.toString(), type, stars)

            var count: Int
            runBlocking {
                count = mealViewModel.getCount(meal.title!!)
            }

            if(count == 0){
                if (meal.title != "") {
                    mealViewModel.insert(meal)
                    val toast: Toast =
                        Toast.makeText(context, getString(R.string.mealAdded, meal.title), Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.TOP, 0, 250)
                    toast.show()
                } else {
                    val toast: Toast =
                        Toast.makeText(context, getString(R.string.toastEmptyMeal), Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.TOP, 0, 250)
                    toast.show()
                }
            } else {
                val toast: Toast =
                        Toast.makeText(context, getString(R.string.toast_add_duplicate_error), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP, 0, 250)
                toast.show()
            }
        }

        return root
    }

}




