/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：9/11/19 6:49 PM 
 */

package com.drake.brv.sample.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.drake.brv.sample.R
import com.drake.statusbar.immersiveDark
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer_content.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        immersiveDark(toolbar)

        fragment_nav.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            toolbar.title = destination.label
            toolbar.menu.clear()
        }

        toolbar.setNavigationOnClickListener { drawer.openDrawer(GravityCompat.START) }
        nav.setupWithNavController(fragment_nav.findNavController())
    }

    override fun onSupportNavigateUp(): Boolean {
        return false
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers()
        } else super.onBackPressed()
    }
}
