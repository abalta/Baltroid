package com.hitreads.core.domain.model

import androidx.annotation.StringDef
import com.hitreads.core.domain.model.OriginalType.Companion.INTERACTIVE
import com.hitreads.core.domain.model.OriginalType.Companion.TEXT

data class IndexOriginalModel(
    val author: IndexAuthorModel?,
    val banner: String?,
    val cover: String?,
    val isBulkPurchasable: Boolean?,
    val description: String?,
    val id: Int?,
    val isActual: Boolean?,
    val isLocked: Boolean?,
    val likeCount: Int?,
    val commentCount: Int?,
    val viewCount: Int?,
    val `package`: IndexPackageModel?,
    val sort: Int?,
    val status: Boolean?,
    val title: String?,
    val type: String?,
    val userData: IndexUserDataModel?,
    val subtitle: String?,
    val tags: List<IndexTagModel>?,
    val episodeCount: Int?,
    val hashtag: String?,
    val isNew: Boolean?,
    val barcode: String?,
    val continueReadingEpisode: IndexContinueReadingEpisodeModel?,
    val episodes: List<ShowEpisodeModel>?
)

@Retention(AnnotationRetention.SOURCE)
@StringDef(INTERACTIVE, TEXT)
annotation class OriginalType {
    companion object {
        const val INTERACTIVE = "INTERACTIVE"
        const val TEXT = "TEXT"
    }
}
