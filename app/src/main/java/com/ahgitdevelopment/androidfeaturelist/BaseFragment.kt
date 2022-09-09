package com.ahgitdevelopment.androidfeaturelist

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ahgitdevelopment.androidfeaturelist.extensions.logE

open class BaseFragment : Fragment(), MenuProvider {

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> {
                IllegalStateException("Wrong menu Item id").logE(this.javaClass.name)
                false
            }
        }
    }
}
