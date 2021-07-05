package be.collin.recipemaster.recipes.overview

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.collin.recipemaster.recipes.R
import be.collin.recipemaster.recipes.overview.RecipeOverviewFragment.RecipesAdapter.RecipeViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeOverviewFragment : Fragment(R.layout.fragment_recipe_overview) {

    private val viewModel: RecipeOverviewViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeList = view.findViewById<RecyclerView>(R.id.recipesList)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshRecipes)

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is RecipeOverviewViewModel.UIState.Error -> showErrorState()
                is RecipeOverviewViewModel.UIState.Success -> {
                    recipeList.adapter = RecipesAdapter(uiState.recipeUIModels)
                    swipeRefreshLayout.isRefreshing = false
                }
                RecipeOverviewViewModel.UIState.Loading -> swipeRefreshLayout.isRefreshing = true
            }
        }
    }

    private fun showErrorState() {
        AlertDialog.Builder(requireContext()).setTitle(getString(R.string.error_title))
            .setMessage(getString(R.string.generic_error_description))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    class RecipesAdapter(private val recipes: RecipeUIModels) : Adapter<RecipeViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_cell, parent, false)
            return RecipeViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            holder.bind(recipes[position])
        }

        override fun getItemCount(): Int = recipes.size

        class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val image = itemView.findViewById<ImageView>(R.id.recipeImage)
            private val title = itemView.findViewById<TextView>(R.id.recipeTitle)
            private val duration = itemView.findViewById<TextView>(R.id.recipeDuration)

            fun bind(recipe: RecipeUIModel) {
                title.text = recipe.title
                duration.text = recipe.duration
                val bitmap = BitmapFactory.decodeByteArray(
                    recipe.image.content,
                    0,
                    recipe.image.length
                )
                image.setImageBitmap(bitmap)
            }
        }
    }
}