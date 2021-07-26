package com.example.trainingproject.models

class Menu {
    lateinit var name: String
    var icon: Int = 0
    var notification: Int = 0
    var list: List<String> = listOf()

    constructor(name: String, icon: Int) {
        this.name = name
        this.icon = icon
    }


    constructor(name: String, icon: Int, notification: Int, list: List<String>) {
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

    constructor(name: String, icon: Int, list: List<String>) {
        this.name = name
        this.icon = icon
        this.list = list
    }


}