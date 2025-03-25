package com.example.resumegenerator.screens

const val ROOT = "root"
const val HOME = "home_route"


sealed class Screens(val route:String) {
    data object Home: Screens("home")
}