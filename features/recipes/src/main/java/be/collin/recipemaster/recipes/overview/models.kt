package be.collin.recipemaster.recipes.overview

data class Recipe(val name: String, val durationInMinutes: Int, val image: Base64Image)

data class Base64Image(private val data: String) {
    val length: Int = data.toByteArray().size
    val content = data.toByteArray()
}
