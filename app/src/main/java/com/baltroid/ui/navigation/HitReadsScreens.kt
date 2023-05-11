package com.baltroid.ui.navigation

sealed class HitReadsScreens(val route: String) {
    object OnboardingScreen : HitReadsScreens("onboarding")
    object HomeScreen : HitReadsScreens("home")
    object HomeDetailScreen : HitReadsScreens("home_detail")
    object ReadingScreen : HitReadsScreens("reading")
    object InteractiveScreen : HitReadsScreens("interactive")
    object MenuScreen : HitReadsScreens("menu")
    object PlaceMarksScreen : HitReadsScreens("place_marks")
    object FavoritesScreen : HitReadsScreens("favorites")
    object SettingsScreen : HitReadsScreens("settings")
    object ProfileScreen : HitReadsScreens("profile")
    object ShopScreen : HitReadsScreens("shop")
    object AuthorScreen : HitReadsScreens("author")
    object FilterScreen : HitReadsScreens("filter")
}

enum class HitReadsGraphs(val route: String) {
    Menu("graph_menu")
}
