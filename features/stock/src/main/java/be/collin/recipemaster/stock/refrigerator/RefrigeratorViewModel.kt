package be.collin.recipemaster.stock.refrigerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.collin.recipemaster.stock.StockItems

abstract class RefrigeratorViewModel : ViewModel() {
    abstract val fridgeItems: LiveData<StockItems>
}