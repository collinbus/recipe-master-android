package be.collin.recipemaster.recipes.overview

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.collin.recipemaster.recipes.R
import be.collin.recipemaster.recipes.overview.RecipesAdapter.RecipeViewHolder

class RecipesAdapter(private val recipes: RecipeUIModels) : RecyclerView.Adapter<RecipeViewHolder>() {
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

            val decodedBase64String = Base64.decode(recipe.image.content, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(
                decodedBase64String,
                0,
                decodedBase64String.size
            )
            image.setImageBitmap(bitmap)
        }
    }
}