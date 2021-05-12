package com.team13.dealmymeal

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

@Entity(tableName = "meal")
data class Meal(
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "categories") var categories: List<String>?,
    @ColumnInfo(name = "rating") var rating: Float?

) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    constructor(parcel: Parcel) : this(parcel.readString(), arrayListOf<String>().apply {
        parcel.readArrayList(String::class.java.classLoader)
    }, parcel.readFloat()) {
        id = parcel.readLong()
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeValue(categories)
        parcel.writeValue(rating)
    }

    companion object CREATOR : Parcelable.Creator<Meal> {
        override fun createFromParcel(parcel: Parcel): Meal {
            return Meal(parcel)
        }

        override fun newArray(size: Int): Array<Meal?> {
            return arrayOfNulls(size)
        }
    }

}