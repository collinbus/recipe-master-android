package be.collin.recipemaster

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.recipes_graph, R.id.fragment_refrigerator, R.id.fragment_shopping_list))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.recipeOverviewFragment -> showBottomNavBar()
                R.id.fragment_refrigerator -> showBottomNavBar()
                R.id.fragment_shopping_list -> showBottomNavBar()
                else -> {
                    hideBottomBar()
                    enableBackButton()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun enableBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showBottomNavBar() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.visibility = View.VISIBLE
    }

    private fun hideBottomBar() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.visibility = View.GONE
    }
}