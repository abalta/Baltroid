package com.baltroid.core.network.util

internal object Constants {
    internal const val API_URL = "https://www.mekik.org/api/"
    internal const val IMAGE_URL = ""
    internal const val AUTHORIZATION = "Authorization"
    internal const val BEARER = "Bearer"
    internal const val ACCEPT = "Accept"
    internal const val ACCEPT_LANG = "Accept-Language"
    internal const val PAGE_SIZE = 20
    internal const val DEFAULT_PAGE = 1

    internal object Path {
        internal const val LOGIN = "auth/login"
        internal const val REGISTER = "auth/register"
        internal const val COURSES = "courses"
        internal const val TEACHER = "teacher"
        internal const val ACADEMY = "academy"
        internal const val PROFILE = "profile"
        internal const val COMMENT = "comment"
        internal const val SEARCH = "search"
        internal const val ALLTOTAL = "all-total"
        internal const val VIDEO = "video"
        internal const val FAVORITE = "favorite"
    }

    internal object Fields {
        internal const val EMAIL = "email"
        internal const val PASSWORD = "password"
        internal const val PASSWORD_CONFIRMATION = "password_confirmation"
        internal const val CUSTOMER_ID = "customer_id"
        internal const val TOKEN = "token"
        internal const val DATA = "data"
        internal const val STATUS = "status"
        internal const val MESSAGE = "message"
        internal const val PAGE = "page"
        internal const val LIMIT = "limit"
        internal const val SORT = "sort"
        internal const val ID = "id"
        internal const val FIRST_NAME = "firstname"
        internal const val LAST_NAME = "lastname"
        internal const val AGREEMENT = "agreement"
        internal const val COMMENT = "comment"
        internal const val COURSE_ID = "course_id"
        internal const val RATING = "rating"
        internal const val TITLE = "title"
        internal const val PLAYER_ID = "player_id"

        private fun buildAppendToResponse(vararg fields: String) =
            fields.joinToString(separator = APPEND_TO_RESPONSE_SEPARATOR)

        private const val APPEND_TO_RESPONSE_SEPARATOR = ","
    }
}

const val PAGE_SIZE = Constants.PAGE_SIZE
const val DEFAULT_PAGE = Constants.DEFAULT_PAGE

@PublishedApi
internal const val MESSAGE_UNHANDLED_STATE = "Unhandled state."
