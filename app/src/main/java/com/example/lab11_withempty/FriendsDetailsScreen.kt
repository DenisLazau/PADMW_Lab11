package com.example.lab11_withempty

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun FriendsDetailsScreen(navController: NavController, friendId:Int, modifier: Modifier){
    Column(modifier = modifier){
        Text(text = "Setting Screen")
    }
}