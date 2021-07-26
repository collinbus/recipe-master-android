package be.collin.recipemaster.stock.refrigerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.collin.recipemaster.stock.R
import be.collin.recipemaster.stock.StockItem
import be.collin.recipemaster.stock.StockItems
import be.collin.recipemaster.stock.refrigerator.RefrigeratorAdapter.StockItemViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RefrigeratorAdapter(
    private val stockItems: StockItems,
    private val stockItemQuantityChangedListener: StockItemQuantityChangedListener
) : RecyclerView.Adapter<StockItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stock_item_cell, parent, false)
        return StockItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockItemViewHolder, position: Int) {
        holder.bind(stockItems[position], stockItemQuantityChangedListener)
    }

    override fun getItemCount(): Int = stockItems.size

    fun update(stockItem: StockItem) {
        stockItems.update(stockItem)
        notifyItemChanged(stockItems.indexOf(stockItem))
    }

    class StockItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.stockItemText)
        private val quantity = itemView.findViewById<TextView>(R.id.quantityText)
        private val incrementBtn = itemView.findViewById<FloatingActionButton>(R.id.incrementFab)
        private val decrementBtn = itemView.findViewById<FloatingActionButton>(R.id.decrementFab)

        fun bind(stockItem: StockItem, stockItemQuantityChangedListener: StockItemQuantityChangedListener) {
            updateData(StockItemUIModel(stockItem))
            incrementBtn.setOnClickListener {
                stockItemQuantityChangedListener.quantityIncreased(stockItem)
            }
            decrementBtn.setOnClickListener {
                stockItemQuantityChangedListener.quantityDecreased(stockItem)
            }
        }

        private fun updateData(model: StockItemUIModel) {
            name.text = model.name
            quantity.text = model.quantity
        }
    }
}