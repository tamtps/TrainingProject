package com.example.trainingproject.models

import android.app.Activity
import com.example.trainingproject.screens.MainScreen

class Menu {
    var name : String = ""
    var icon : Int = 0
    var notification : Int = 0
    var list : List<Menu> = listOf()
    var activity : Class<*> ?= null

    constructor(name: String){
        this.name = name
    }



    constructor(name: String, icon: Int) {
        this.name = name
        this.icon = icon
    }

    constructor(name: String, icon: Int, notification: Int, list: List<Menu>) {
        this.name = name
        this.icon = icon
        this.notification = notification
        this.list = list
    }

    constructor(name: String, icon: Int, notification: Int) {
        this.name = name
        this.icon = icon
        this.notification = notification
    }

    constructor(name: String, icon: Int, list: List<Menu>) {
        this.name = name
        this.icon = icon
        this.list = list
    }

    constructor(
        name: String,
        icon: Int,
        notification: Int,
        list: List<Menu>,
        activity: Class<*>
    ) {
        this.name = name
        this.icon = icon
        this.notification = notification
        this.list = list
        this.activity = activity
    }

    constructor(name: String, list: List<Menu>) {
        this.name = name
        this.list = list
    }

    constructor(name: String, activity: Class<*>) {
        this.name = name
        this.activity = activity
    }
}