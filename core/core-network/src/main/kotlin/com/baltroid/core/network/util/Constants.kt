package com.baltroid.core.network.util

internal object Constants {
    internal const val API_URL = ""
    internal const val IMAGE_URL = ""
    internal const val API_KEY_QUERY_PARAM = "api_key"
    internal const val PAGE_SIZE = 20
    internal const val DEFAULT_PAGE = 1

    internal object Path {

    }

    internal object Fields {
        internal const val ID = "id"
        internal const val CREATED_BY = "created_by"
        internal const val STATUS = "status"
        internal const val TYPE = "type"
        internal const val PAGE = "page"
        internal const val TOTAL_PAGES = "total_pages"
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
        private fun buildAppendToResponse(vararg fields: String) =
            fields.joinToString(separator = APPEND_TO_RESPONSE_SEPARATOR)

        private const val APPEND_TO_RESPONSE_SEPARATOR = ","
    }
}

const val PAGE_SIZE = Constants.PAGE_SIZE
const val DEFAULT_PAGE = Constants.DEFAULT_PAGE

@PublishedApi
internal const val MESSAGE_UNHANDLED_STATE = "Unhandled state."
