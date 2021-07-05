package be.collin.recipemaster

import be.collin.recipemaster.recipes.RecipeApi
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RecipesInitializer {
    fun init() {
        startKoin {
            modules(recipesModule)
        }
    }
}