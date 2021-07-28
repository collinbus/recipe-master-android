package be.collin.recipemaster.stock.di

import android.app.Application
import be.collin.recipemaster.stock.persistence.AppDatabase
import be.collin.recipemaster.stock.persistence.repository.StockItemRepository
import be.collin.recipemaster.stock.stockitem.StockItemViewModel
import be.collin.recipemaster.stock.stockitem.StockItemViewModelImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stockModule = module {
    viewModel<StockItemViewModel> { StockItemViewModelImpl(get()) }
    single { StockItemRepository(get()) }
    single { appDatabase(androidApplication()) }
    single { refrigeratorDao(get()) }
}

private fun refrigeratorDao(database: AppDatabase) = database.refrigeratorDao()

private fun appDatabase(application: Application) =
    AppDatabase.getInstance(application.applicationContext)
