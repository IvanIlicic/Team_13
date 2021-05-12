package com.team13.dealmymeal

import android.os.Parcel
import android.os.Parcelable

data class MealItem(
    var name: String?,
    var category: Short?,
    var rating: Short?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Short::class.java.classLoader) as? Short,
        parcel.readValue(Short::class.java.classLoader) as? Short
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(category)
        parcel.writeValue(rating)
    }

    companion object CREATOR : Parcelable.Creator<MealItem> {
        override fun createFromParcel(parcel: Parcel): MealItem {
            return MealItem(parcel)
        }

        override fun newArray(size: Int): Array<MealItem?> {
            return arrayOfNulls(size)
        }
    }

}