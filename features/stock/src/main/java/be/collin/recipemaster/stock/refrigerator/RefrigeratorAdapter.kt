package be.collin.recipemaster.stock.refrigerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.collin.recipemaster.stock.R
import be.collin.recipemaster.stock.StockItems
import be.collin.recipemaster.stock.refrigerator.RefrigeratorAdapter.StockItemViewHolder

class RefrigeratorAdapter(
    private val stockItems: StockItems
) : RecyclerView.Adapter<StockItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stock_item_cell, parent, false)
        return StockItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockItemViewHolder, position: Int) {
        val model = StockItemUIModel(stockItems[position])
        holder.bind(model)
    }

    override fun getItemCount(): Int = stockItems.size

    class StockItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.stockItemText)
        private val quantity = itemView.findViewById<TextView>(R.id.quantityText)

        fun bind(stockItem: StockItemUIModel) {
            name.text = stockItem.name
            quantity.text = stockItem.quantity
        }
    }
}