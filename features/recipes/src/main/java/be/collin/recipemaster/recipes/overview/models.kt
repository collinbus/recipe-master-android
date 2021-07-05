package be.collin.recipemaster.recipes.overview

import android.util.Base64

data class Recipe(val name: String, val durationInMinutes: Int, val image: Base64Image)

data class Recipes(val recipes: List<Recipe>) {
    inline fun forEach(action: (Recipe) -> Unit): Unit = recipes.forEach(action)
}

data class Base64Image(private val data: String) {
    val length: Int = data.toByteArray().size
    val content = data.toByteArray()
}
