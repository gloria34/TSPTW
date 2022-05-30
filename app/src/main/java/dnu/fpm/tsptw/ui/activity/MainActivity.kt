package dnu.fpm.tsptw.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.databinding.ActivityMainBinding
import dnu.fpm.tsptw.ui.base.OnFragmentChangeListener
import dnu.fpm.tsptw.ui.fragment.home.HomeFragment
import dnu.fpm.tsptw.ui.fragment.splash.SplashFragment
import dnu.fpm.tsptw.ui.fragment.triplist.TripListFragment

class MainActivity : AppCompatActivity(), OnFragmentChangeListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.menu[0]
            .setOnMenuItemClickListener {
                findNavController(R.id.container).navigate(R.id.homeFragment)
                false
            }
        binding.bottomNavigationView.menu[1]
            .setOnMenuItemClickListener {
                findNavController(R.id.container).navigate(R.id.tripListFragment)
                false
            }
    }

    @SuppressLint("ResourceType")
    override fun onFragmentChange(fragment: Fragment) {
        if (fragment !is TripListFragment && fragment !is HomeFragment) {
            binding.bottomNavigationView.isSelected = false
        }
        binding.bottomNavigationView.visibility =
            if (fragment is SplashFragment) View.GONE else View.VISIBLE
    }

}