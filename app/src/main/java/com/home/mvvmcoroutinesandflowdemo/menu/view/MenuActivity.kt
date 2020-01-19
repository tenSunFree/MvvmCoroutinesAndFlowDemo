package com.home.mvvmcoroutinesandflowdemo.menu.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.home.mvvmcoroutinesandflowdemo.R
import com.home.mvvmcoroutinesandflowdemo.common.MCAFDApplication
import com.home.mvvmcoroutinesandflowdemo.menu.di.MenuComponent

class MenuActivity : AppCompatActivity() {

    var menuComponent: MenuComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        menuComponent = (applicationContext as MCAFDApplication)
            .appComponent.homeComponent().create()
        menuComponent?.inject(this)
    }
}
