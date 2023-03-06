package com.example.busschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.busschedule.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //SE INFLA EL DISEÑO
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //SE RECUPERA EL HOST DE LA NAVEGACIÓN
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        //DEL HOST DE NAVEGACIÓN RECUPERAMOS EL NAVCONTROLLER
        navController = navHostFragment.navController

        //LE INDICAMOS AL ACTION BAR QUE VA A TRABAJAR CON NAVCONTROLLER
        //ESTO AYUDA A CAMBIAR EL TITULO DE LAS PAGINAS MEDIANTE EL NAVCONTROLLER
        setupActionBarWithNavController(navController)
    }

    //CONTROLLAR EL BOTON ATRAS POR EL NAVCONTROLLER
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
