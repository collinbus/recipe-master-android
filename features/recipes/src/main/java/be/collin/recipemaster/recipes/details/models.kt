package be.collin.recipemaster.recipes.details

import be.collin.recipemaster.recipes.overview.Recipe

data class RecipeDetailsUIModel(
    val recipe: Recipe
) {
    val title = recipe.name
    val imageContent = recipe.image.content
    val duration = "Duration: ${recipe.durationInMinutes} mins"
}