package com.baltroid.core.network.util

internal object Constants {
    internal const val API_URL = "http://3.73.140.195/api/"
    internal const val PROD = "http://3.76.153.71/api/"
    internal const val IMAGE_URL = ""
    internal const val API_KEY_QUERY_PARAM = "api_key"
    internal const val PAGE_SIZE = 20
    internal const val DEFAULT_PAGE = 1

    internal object Path {
        internal const val ORIGINALS_INDEX = "original"
        internal const val LOGIN = "login"
        internal const val LIKE = "like"
        internal const val UNLIKE = "unlike"
        internal const val TAG = "tag"
        internal const val EPISODE = "episode"
        internal const val SHOW = "show"
        internal const val COMMENT = "comment"
        internal const val BY_ME = "byme"
        internal const val WELCOME = "welcome-screen"
        internal const val BOOKMARK = "bookmark"
        internal const val REGISTER = "register"
        internal const val FAVORITE = "favorite"
    }

    internal object Fields {
        internal const val ID = "id"
        internal const val CREATED_BY = "created_by"
        internal const val STATUS = "status"
        internal const val TYPE = "type"
        internal const val PAGE = "page"
        internal const val TOTAL_PAGE = "total_page"
        internal const val RESULTS = "results"
        internal const val TOTAL_RESULTS = "total_results"
        internal const val DATES = "dates"
        internal const val TITLE = "title"
        internal const val NAME = "name"
        internal const val LANGUAGES = "languages"
        internal const val ORDER = "order"
        internal const val MAXIMUM = "maximum"
        internal const val MINIMUM = "minimum"
        internal const val QUERY = "query"
        internal const val LANGUAGE = "language"
        internal const val MESSAGE = "message"
        internal const val DATA = "data"
        internal const val DATA_COUNT = "data_count"
        internal const val ORIGINALS = "originals"
        internal const val ACTIVE_PAGE = "active_page"
        internal const val EMAIL = "email"
        internal const val PASSWORD = "password"
        internal const val USER_ID = "user_id"
        internal const val TOKEN = "token"
        internal const val USERNAME = "username"
        internal const val AVATAR = "avatar"
        internal const val WALLET = "wallet"
        internal const val GEM = "gem"
        internal const val FILTER_TAG = "filter_tag"
        internal const val EPISODE = "episode"
        internal const val DIALOGUE = "dialogue"
        internal const val REPUTATION = "reputation"
        internal const val GET_BY_FAV = "get_by_fav"
        internal const val EPISODE_ID = "episode_id"
        internal const val ORIGINAL_ID = "original_id"

        private fun buildAppendToResponse(vararg fields: String) =
            fields.joinToString(separator = APPEND_TO_RESPONSE_SEPARATOR)

        private const val APPEND_TO_RESPONSE_SEPARATOR = ","
    }
}

const val PAGE_SIZE = Constants.PAGE_SIZE
const val DEFAULT_PAGE = Constants.DEFAULT_PAGE

const val MESSAGE_UNHANDLED_STATE = "Unhandled state."
