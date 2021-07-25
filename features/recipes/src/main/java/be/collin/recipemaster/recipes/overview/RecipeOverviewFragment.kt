package be.collin.recipemaster.recipes.overview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.collin.recipemaster.recipes.R
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
                    recipeList.adapter = RecipesAdapter(uiState.recipeUIModels) {
                        viewModel.onRecipeClicked(it.title)
                    }
                    swipeRefreshLayout.isRefreshing = false
                    swipeRefreshLayout.setOnRefreshListener { viewModel.refreshRecipes() }
                }
                RecipeOverviewViewModel.UIState.Loading -> swipeRefreshLayout.isRefreshing = true
            }
        }

        viewModel.selectedRecipe.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.resetSelectedRecipe()
                val directions =
                    RecipeOverviewFragmentDirections.actionRecipeOverviewFragmentToRecipeDetailsFragment(
                        it
                    )
                findNavController().navigate(directions)
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
}