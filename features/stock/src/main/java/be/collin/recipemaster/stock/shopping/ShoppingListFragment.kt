package be.collin.recipemaster.stock.shopping

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import be.collin.recipemaster.stock.R
import be.collin.recipemaster.stock.stockitem.StockItemFragment
import be.collin.recipemaster.stock.stockitem.StockItemFragment.Companion.DAO_PARAM_NAME

class ShoppingListFragment : Fragment(R.layout.fragment_container_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .add(R.id.fragment_container, StockItemFragment::class.java, Bundle().apply {
                putString(DAO_PARAM_NAME, SHOPPING_LIST_PARAM)
            })
            .commit()
    }

    companion object {
        const val SHOPPING_LIST_PARAM = "shoppingList"
    }
}