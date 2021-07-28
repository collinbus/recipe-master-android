package be.collin.recipemaster.stock.di

import android.app.Application
import be.collin.recipemaster.stock.persistence.AppDatabase
import be.collin.recipemaster.stock.persistence.dao.RefrigeratorDao
import be.collin.recipemaster.stock.persistence.dao.ShoppingListDao
import be.collin.recipemaster.stock.persistence.dao.StockItemDao
import be.collin.recipemaster.stock.persistence.repository.StockItemRepository
import be.collin.recipemaster.stock.refrigerator.RefrigeratorFragment.Companion.REFRIGERATOR_PARAM
import be.collin.recipemaster.stock.shopping.ShoppingListFragment.Companion.SHOPPING_LIST_PARAM
import be.collin.recipemaster.stock.stockitem.StockItemViewModel
import be.collin.recipemaster.stock.stockitem.StockItemViewModelImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val stockModule = module {
    viewModel<StockItemViewModel> { parameters -> StockItemViewModelImpl(get(qualifier = named(parameters.get())) { parameters }) }
    single(named(REFRIGERATOR_PARAM)) { parameters -> StockItemRepository<RefrigeratorDao>(get(qualifier = named(parameters.get()))) }
    single(named(SHOPPING_LIST_PARAM)) { parameters -> StockItemRepository<ShoppingListDao>(get(qualifier = named(parameters.get()))) }
    single<StockItemDao>(named(REFRIGERATOR_PARAM)) { refrigeratorDao(get()) }.bind(RefrigeratorDao::class)
    single<StockItemDao>(named(SHOPPING_LIST_PARAM)) { shoppingListDao(get()) }.bind(ShoppingListDao::class)
    single { appDatabase(androidApplication()) }
}

private fun refrigeratorDao(database: AppDatabase) = database.refrigeratorDao()
private fun shoppingListDao(database: AppDatabase) = database.shoppingListDao()

private fun appDatabase(application: Application) =
    AppDatabase.getInstance(application.applicationContext)
