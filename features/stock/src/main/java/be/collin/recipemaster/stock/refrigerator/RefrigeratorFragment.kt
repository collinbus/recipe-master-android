package be.collin.recipemaster.stock.refrigerator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import be.collin.recipemaster.stock.R
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems

class RefrigeratorFragment : Fragment(R.layout.fragment_stock_item_list) {

    private val viewModel: RefrigeratorViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val refrigerator: RecyclerView = view.findViewById(R.id.stockItemRecyclerView)

        viewModel.uiState.observe(viewLifecycleOwner) {
            when(it) {
                is RefrigeratorViewModel.UIState.Initialized -> {
                    initializeAdapter(refrigerator, it.stockItems)
                }
                is RefrigeratorViewModel.UIState.Updated -> {
                    (refrigerator.adapter as RefrigeratorAdapter).update(it.stockItem)
                }
            }

        }
    }

    private fun initializeAdapter(refrigerator: RecyclerView, stockItems: StockItems) {
        refrigerator.adapter =
            RefrigeratorAdapter(stockItems, object : StockItemQuantityChangedListener {
                override fun quantityIncreased(stockItem: StockItem) {
                    viewModel.increaseQuantityOf(stockItem)
                }

                override fun quantityDecreased(stockItem: StockItem) {
                    viewModel.decreaseQuantityOf(stockItem)
                }
            })
    }
}