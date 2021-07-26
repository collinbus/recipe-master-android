package be.collin.recipemaster.recipes.di

import be.collin.recipemaster.recipes.RecipeApi
import be.collin.recipemaster.recipes.RecipeRepository
import be.collin.recipemaster.recipes.details.RecipeDetailsViewModel
import be.collin.recipemaster.recipes.details.RecipeDetailsViewModelImpl
import be.collin.recipemaster.recipes.overview.RecipeOverviewViewModel
import be.collin.recipemaster.recipes.overview.RecipeOverviewViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val recipesModule = module {
    viewModel<RecipeOverviewViewModel> {
        RecipeOverviewViewModelImpl(get())
    }
    viewModel<RecipeDetailsViewModel> { params ->
        RecipeDetailsViewModelImpl(params.get())
    }
    single { RecipeRepository(get()) }
    single<RecipeApi> { provideRetrofit().create(RecipeApi::class.java) }
}

fun provideRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:8080/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()