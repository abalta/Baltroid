package com.baltroid.apps.navigation

sealed class UiAction {
    data object OnAllCoursesClick : UiAction()
    data object InstructorClick : UiAction()
    data object AcademyClick : UiAction()
    data object OnCourseClick : UiAction()
    data object OnLoginClick : UiAction()
}