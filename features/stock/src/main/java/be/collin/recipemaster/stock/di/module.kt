package be.collin.recipemaster.stock.di

import be.collin.recipemaster.stock.refrigerator.RefrigeratorViewModel
import be.collin.recipemaster.stock.refrigerator.RefrigeratorViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stockModule = module {
    viewModel<RefrigeratorViewModel> { RefrigeratorViewModelImpl() }
}