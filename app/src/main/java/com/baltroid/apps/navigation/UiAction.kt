package com.baltroid.apps.navigation

sealed class UiAction {
    data object OnAllCoursesClick : UiAction()
    data class OnInstructorClick(val id: Int) : UiAction()
    data class OnAcademyClick(val id: Int) : UiAction()
    data class OnCourseClick(val id: Int) : UiAction()
    data object OnLoginClick : UiAction()
    data object OnBackClick : UiAction()
}