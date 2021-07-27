package be.collin.recipemaster.stock.refrigerator

import android.text.Editable
import android.text.TextWatcher
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
    private val stockItemChangedListener: StockItemChangedListener
) : RecyclerView.Adapter<StockItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stock_item_cell, parent, false)
        return StockItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockItemViewHolder, position: Int) {
        holder.bind(stockItems[position], stockItemChangedListener)
    }

    override fun getItemCount(): Int = stockItems.size

    fun update(stockItem: StockItem) {
        stockItems.update(stockItem)
        notifyItemChanged(stockItems.indexOf(stockItem))
    }

    fun add(stockItem: StockItem) {
        stockItems.add(stockItem)
        notifyItemChanged(stockItems.indexOf(stockItem))
    }

    fun remove(position: Int) {
        stockItems.removeAt(position)
        notifyItemRemoved(position)
    }

    class StockItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.stockItemText)
        private val quantity = itemView.findViewById<TextView>(R.id.quantityText)
        private val incrementBtn = itemView.findViewById<FloatingActionButton>(R.id.incrementFab)
        private val decrementBtn = itemView.findViewById<FloatingActionButton>(R.id.decrementFab)

        fun bind(stockItem: StockItem, stockItemChangedListener: StockItemChangedListener) {
            updateData(StockItemUIModel(stockItem))
            incrementBtn.setOnClickListener {
                stockItemChangedListener.quantityIncreased(stockItem)
            }
            decrementBtn.setOnClickListener {
                stockItemChangedListener.quantityDecreased(stockItem)
            }
            name.addTextChangedListener(NameChangedTextWatcher { s: Editable ->
                stockItemChangedListener.nameChanged(s.toString(), stockItem)
            })
        }

        private fun updateData(model: StockItemUIModel) {
            name.text = model.name
            quantity.text = model.quantity
        }
    }

    private class NameChangedTextWatcher(private val textChanged: (text: Editable) -> Unit) : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) = textChanged.invoke(s)
    }
}