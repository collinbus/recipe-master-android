package be.collin.recipemaster.stock.stockitem

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import be.collin.recipemaster.stock.R
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.core.parameter.parametersOf

class StockItemFragment : Fragment(R.layout.fragment_stock_item_list) {

    private val viewModel: StockItemViewModel by viewModel {
        parametersOf(requireArguments().getString(DAO_PARAM_NAME))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addBtn = view.findViewById<FloatingActionButton>(R.id.addBtn)
        val refrigerator: RecyclerView = view.findViewById(R.id.stockItemRecyclerView)

        viewModel.uiState.observe(viewLifecycleOwner) {
            when(it) {
                is StockItemViewModel.UIState.Initialized -> {
                    initializeAdapter(refrigerator, it.stockItems)
                }
                is StockItemViewModel.UIState.Updated -> {
                    (refrigerator.adapter as StockItemAdapter).update(it.stockItem)
                }
                is StockItemViewModel.UIState.Added -> {
                    (refrigerator.adapter as StockItemAdapter).add(it.stockItem)
                }
                is StockItemViewModel.UIState.Removed -> {
                    (refrigerator.adapter as StockItemAdapter).remove(it.position)
                }
            }
        }

        addBtn.setOnClickListener {
            viewModel.addStockItem()
        }

        setupDeleteGesture(refrigerator)
    }

    private fun setupDeleteGesture(refrigerator: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.removeItemAt(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(refrigerator)
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveStockItems()
    }

    private fun initializeAdapter(refrigerator: RecyclerView, stockItems: StockItems) {
        refrigerator.adapter =
            StockItemAdapter(stockItems, object : StockItemChangedListener {
                override fun nameChanged(newName: String, stockItem: StockItem) {
                    viewModel.changeName(newName, stockItem)
                }

                override fun quantityIncreased(stockItem: StockItem) {
                    viewModel.increaseQuantityOf(stockItem)
                }

                override fun quantityDecreased(stockItem: StockItem) {
                    viewModel.decreaseQuantityOf(stockItem)
                }
            })
    }

    companion object {
        const val DAO_PARAM_NAME = "daoName"
    }
}