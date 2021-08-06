package be.collin.recipemaster.recipes.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RecipeDetailsFragment : Fragment() {

    private val args: RecipeDetailsFragmentArgs by navArgs()

    private val viewModel: RecipeDetailsViewModel by viewModel { parametersOf(args.recipe) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                RecipeDetailsScreen(viewModel.recipeDetails.observeAsState())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipeDetails.observe(viewLifecycleOwner, {
            updateToolbar(it.title)
        })
    }

    private fun updateToolbar(title: String) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.title = title
        }
    }
}
