package be.collin.recipemaster.recipes.overview

import android.os.Parcelable
import arrow.core.Either
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(val name: String, val durationInMinutes: Int, val image: Base64Image) : Parcelable

data class Recipes(private val recipes: List<Recipe>) {
    fun recipeWithName(name: String): Either<Throwable, Recipe> = Either.catch {
        recipes.first {
            it.name == name
        }
    }
}

@Parcelize
data class Base64Image(private val data: String) : Parcelable {
    @IgnoredOnParcel
    val content = data.toByteArray()
}
