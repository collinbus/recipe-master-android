package be.collin.recipemaster.recipes.overview

data class RecipeUIModel(
    private val recipe: Recipe
) {
    val title = recipe.name
    val duration = durationAsString()

    val image = recipe.image

    private fun durationAsString(): String {
        if (recipe.durationInMinutes == 1)
            return "${recipe.durationInMinutes} min"
        return "${recipe.durationInMinutes} mins"
    }
}

data class RecipeUIModels(
    private val recipeUIModels: List<RecipeUIModel>
) {
    val size: Int = recipeUIModels.size
    operator fun get(position: Int): RecipeUIModel = recipeUIModels[position]
}