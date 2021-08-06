package be.collin.recipemaster.recipes.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.liveData
import be.collin.recipemaster.recipes.BitmapFactory
import be.collin.recipemaster.recipes.overview.Base64Image
import be.collin.recipemaster.recipes.overview.Recipe

@Composable
fun RecipeDetailsScreen(recipe: State<RecipeDetailsUIModel?>) {
    Column {
        recipe.value?.let {
            Text(
                it.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            Image(
                bitmap = BitmapFactory.fromBase64Bytes(it.imageContent).asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                it.duration,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun RecipeDetailScreenPreview() {
    val recipe = liveData {
        emit(
            RecipeDetailsUIModel(
                Recipe(
                    "Pasta pesto",
                    15,
                    Base64Image("")
                )
            )
        )
    }
    RecipeDetailsScreen(recipe.observeAsState())
}