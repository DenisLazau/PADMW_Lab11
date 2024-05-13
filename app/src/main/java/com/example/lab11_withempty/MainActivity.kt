package com.example.lab11_withempty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lab11_withempty.ui.theme.Lab11withEmptyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            Lab11withEmptyTheme {
                val navController = rememberNavController()
                MyBottomNavigation(navController)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab11withEmptyTheme {
        Greeting("Android")
    }
}

@Composable
fun MyNav(navController: NavHostController , modifier: Modifier) {
    NavHost(navController = navController, startDestination = Destination.Home.route) {
        composable(Destination.Home.route) { HomeScreen(navController, modifier) }
        composable(Destination.FriendsDetails.route) {
            val friendId = 0
            FriendsDetailsScreen(navController, friendId, modifier) }
        composable(Destination.Friends.route) { FriendsScreen(navController, modifier) }
        composable(Destination.Settings.route) { SettingScreen(navController, modifier) }
    }
}

@Composable
fun MyHome(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Home")
        Button(onClick = { navController.navigate(Destination.Friends.route) }) {
            Text(text = "Go to Profile")
        }
        Button(onClick = { navController.navigate(Destination.FriendsDetails.route) }) {
            Text(text = "Go to About")
        }
    }
}
@Composable
fun MyProfile(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Profile")
        Button(onClick = { navController.navigate(Destination.FriendsDetails.route) }) {
            Text(text = "Go to About")
        }
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Back")
        }
    }
}
@Composable
fun MyAbout(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "About")
        Button(onClick = { navController.navigate(Destination.Friends.route) }) {
            Text(text = "Go to Profile")
        }
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Back")
        }
    }
}

sealed class Destination(val route: String) {
    object Home : Destination("home")
    object Friends : Destination("friends")
    object FriendsDetails : Destination("friendsDetails/{friendId}"){
        fun createRoute(friendId: Int) = "friendsDetails/$friendId"
    }
    object Settings : Destination("settings")
}

@Composable
fun MyBottomNavigation(navController: NavHostController) {
    Scaffold(
        bottomBar = { MyBottomNav(navController) },
        modifier = Modifier.fillMaxSize()
    ) { internalPaddings ->
        val modifier = Modifier.padding(internalPaddings)
        NavHost(navController = navController, startDestination = Destination.Home.route) {
            composable(Destination.Home.route) { MyHome(navController) }
            composable(Destination.FriendsDetails.route) { MyProfile(navController) }
            composable(Destination.Friends.route) { MyAbout(navController) }
            composable(Destination.Settings.route) { MyAbout(navController) }
        }
    }
}

@Composable
fun MyBottomNav(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Home.route,
            onClick = { navController.navigate(Destination.Home.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text(text = Destination.Home.route) }
        )
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Friends.route,
            onClick = { navController.navigate(Destination.Friends.route) },
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text(text = Destination.Friends.route) }
        )
        NavigationBarItem(
            selected = currentDestination?.route == Destination.FriendsDetails.route,
            onClick = { navController.navigate(Destination.FriendsDetails.route) },
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            label = { Text(text = Destination.FriendsDetails.route) }
        )
    }
}
