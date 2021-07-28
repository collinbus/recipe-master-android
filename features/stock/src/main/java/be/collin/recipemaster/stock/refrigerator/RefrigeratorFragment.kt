package be.collin.recipemaster.stock.refrigerator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import be.collin.recipemaster.stock.R
import be.collin.recipemaster.stock.stockitem.StockItemFragment
import be.collin.recipemaster.stock.stockitem.StockItemFragment.Companion.DAO_PARAM_NAME

class RefrigeratorFragment : Fragment(R.layout.fragment_container_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .add(R.id.fragment_container, StockItemFragment::class.java, Bundle().apply {
                putString(DAO_PARAM_NAME, REFRIGERATOR_PARAM)
            })
            .commit()
    }

    companion object {
        const val REFRIGERATOR_PARAM = "refrigerator"
    }
}