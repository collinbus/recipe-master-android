package be.collin.recipemaster

import android.app.Application
import be.collin.recipemaster.recipes.di.recipesModule
import be.collin.recipemaster.stock.di.stockModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(
                recipesModule,
                stockModule
            )
        }
    }
}