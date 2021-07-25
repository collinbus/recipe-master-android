package be.collin.recipemaster.recipes.overview

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(val name: String, val durationInMinutes: Int, val image: Base64Image) : Parcelable

data class Recipes(val recipes: List<Recipe>) {
    inline fun forEach(action: (Recipe) -> Unit): Unit = recipes.forEach(action)
}

@Parcelize
data class Base64Image(private val data: String) : Parcelable {
    @IgnoredOnParcel
    val content = data.toByteArray()
}
