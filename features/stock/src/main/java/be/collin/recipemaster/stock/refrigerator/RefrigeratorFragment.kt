package be.collin.recipemaster.stock.refrigerator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import be.collin.recipemaster.stock.R

class RefrigeratorFragment : Fragment(R.layout.fragment_stock_item_list) {

    private val viewModel: RefrigeratorViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val refrigerator: RecyclerView = view.findViewById(R.id.stockItemRecyclerView)

        viewModel.fridgeItems.observe(viewLifecycleOwner) {
            refrigerator.adapter = RefrigeratorAdapter(it)
        }
    }
}