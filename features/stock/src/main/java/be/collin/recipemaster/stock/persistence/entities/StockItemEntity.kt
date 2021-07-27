package be.collin.recipemaster.stock.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StockItem")
data class StockItemEntity(
    @PrimaryKey @ColumnInfo(name = KEY_STOCK_ITEM_ID) val id: String,
    @ColumnInfo(name = KEY_STOCK_ITEM_NAME) val name: String,
    @ColumnInfo(name = KEY_STOCK_ITEM_QUANTITY) val quantity: Int,
    @ColumnInfo(name= KEY_STOCK_ITEM_TYPE) val stockItemType: Int
) {
    companion object {
        const val KEY_STOCK_ITEM_ID = "stockItemId"
        const val KEY_STOCK_ITEM_NAME = "stockItemName"
        const val KEY_STOCK_ITEM_QUANTITY = "stockItemQuantity"
        const val KEY_STOCK_ITEM_TYPE = "stockItemType"
    }
}