package com.example.trainingproject.models

class Menu {
    lateinit var name : String
    var icon : Int = 0

    constructor(name: String, icon: Int) {
        this.name = name
        this.icon = icon
    }
}