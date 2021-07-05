package be.collin.recipemaster

import android.app.Application

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        RecipesInitializer.init()
    }
}