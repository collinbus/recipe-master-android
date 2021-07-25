package be.collin.recipemaster.recipes.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import be.collin.recipemaster.recipes.BitmapFactory
import be.collin.recipemaster.recipes.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {

    private val args: RecipeDetailsFragmentArgs by navArgs()

    private val viewModel: RecipeDetailsViewModel by viewModel { parametersOf(args.recipe) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipeDetails.observe(viewLifecycleOwner, {
            updateUIValues(view, it)
            updateToolbar(it.title)
        })
    }

    private fun updateUIValues(view: View, uiModel: RecipeDetailsUIModel) {
        val title = view.findViewById<TextView>(R.id.recipeTitle)
        val image = view.findViewById<ImageView>(R.id.recipeImage)
        val duration = view.findViewById<TextView>(R.id.recipeDuration)

        title.text = uiModel.title
        val bitmap = BitmapFactory.fromBase64Bytes(uiModel.imageContent)
        image.setImageBitmap(bitmap)
        duration.text = uiModel.duration
    }

    private fun updateToolbar(title: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }
}