/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
