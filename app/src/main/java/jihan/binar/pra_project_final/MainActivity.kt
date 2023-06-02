package jihan.binar.pra_project_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.databinding.ActivityMainBinding
import jihan.binar.pra_project_final.model.cart.CartResponseItem
import jihan.binar.pra_project_final.viewmodel.CartViewModel
import jihan.binar.pra_project_final.viewmodel.LoginViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var navController:NavController
    private val cartViewModel:CartViewModel by viewModels()
    private val loginViewModel:LoginViewModel by viewModels()
    private var cartQuantity:Int? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
        navController = navHostFragment!!.findNavController()

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.splashFragment,R.id.loginFragment,R.id.registFragment,R.id.detailFragment -> {
                    binding.bottomNav.visibility = View.GONE
                    binding.layoutToolbar.visibility = View.GONE
                } R.id.homeFragment,R.id.favouriteFragment ,R.id.profileFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.layoutToolbar.visibility = View.VISIBLE
                } else -> {
                    binding.layoutToolbar.visibility = View.VISIBLE
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }

        binding.bottomNav.setupWithNavController(navController)

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu_appbar,menu)
        val menuItem = menu?.findItem(R.id.cartFragment)
        val actionView = menuItem?.actionView

        val cartBadgeTextView = actionView?.findViewById<TextView>(R.id.tv_cart_badge)

        if (cartQuantity != 0){
            cartBadgeTextView?.text = cartQuantity.toString()
        } else {
            cartBadgeTextView?.visibility = View.GONE
        }

        cartViewModel.listCart.observe(this){
            isEmpty(it, cartBadgeTextView)
        }

        isLoading(cartBadgeTextView)

        loginViewModel.getLoginState().observe(this){
            if (it){
                cartBadgeTextView?.visibility = View.VISIBLE
            }else{
                cartBadgeTextView?.visibility = View.GONE
            }
        }


        actionView?.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }

        return true
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }


    private fun isEmpty(
        it: List<CartResponseItem>,
        cartBadgeTextView: TextView?
    ) {
        if (it.isNotEmpty()) {
                cartQuantity = it.size
                cartBadgeTextView?.text = cartQuantity.toString()
            } else {
                cartBadgeTextView?.visibility = View.GONE
            }
        }

    private fun isLoading(cartBadgeTextView: TextView?) {
        cartViewModel.isLoading.observe(this) {
            if (it) {
                cartBadgeTextView?.visibility = View.GONE
            } else {
                cartBadgeTextView?.visibility = View.VISIBLE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
        return NavigationUI.onNavDestinationSelected(item,navController) || super.onOptionsItemSelected(item)
    }

}