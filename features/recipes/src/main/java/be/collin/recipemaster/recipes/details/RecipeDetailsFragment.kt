package be.collin.recipemaster.recipes.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
        val title = view.findViewById<TextView>(R.id.recipeTitle)
        val image = view.findViewById<ImageView>(R.id.recipeImage)
        val duration = view.findViewById<TextView>(R.id.recipeDuration)

        viewModel.recipeDetails.observe(viewLifecycleOwner, {
            title.text = it.title
            val bitmap = BitmapFactory.fromBase64Bytes(it.imageContent)
            image.setImageBitmap(bitmap)
            duration.text = it.duration
        })
    }
}