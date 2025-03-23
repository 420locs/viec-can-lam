package com.ninhtbm.vcl.feature

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ninhtbm.vcl.feature.home.HomeNavigation
import com.ninhtbm.vcl.feature.home.HomeScreen
import com.ninhtbm.vcl.feature.search.SearchNavigation
import com.ninhtbm.vcl.feature.search.SearchScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeNavigation) {
        composable<HomeNavigation> {
            HomeScreen(navController = navController)
        }
        composable<SearchNavigation> { backStackEntry ->
            val argument = backStackEntry.toRoute<SearchNavigation>()
            SearchScreen(
                navController = navController,
                keyword = argument.keyword
            )
        }
    }
}

