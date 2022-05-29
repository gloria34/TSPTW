package dnu.fpm.tsptw.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import dnu.fpm.tsptw.ui.fragment.splash.SplashFragment

class MainActivity : AppCompatActivity(), OnFragmentChangeListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onFragmentChange(fragment: Fragment) {
        if(fragment is SplashFragment){

        }
    }

}