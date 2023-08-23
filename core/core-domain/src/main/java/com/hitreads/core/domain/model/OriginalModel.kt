package com.hitreads.core.domain.model

import androidx.annotation.StringDef
import com.hitreads.core.domain.model.OriginalType.Companion.INTERACTIVE
import com.hitreads.core.domain.model.OriginalType.Companion.TEXT

data class OriginalModel(
    val author: AuthorModel,
    val banner: String,
    val cover: String,
    val description: String,
    val id: Int,
    val isActual: Boolean,
    val isLocked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val viewCount: Int,
    val `package`: PackageModel?,
    val sort: Int,
    val status: Boolean,
    val title: String,
    val type: String,
    val userData: UserDataModel,
    val subtitle: String,
    val tags: List<TagModel>,
    val episodeCount: Int,
    val hashtag: String,
    val seasons: List<SeasonModel>,
    val isNew: Boolean,
    val barcode: String
)

@Retention(AnnotationRetention.SOURCE)
@StringDef(INTERACTIVE, TEXT)
annotation class OriginalType {
    companion object {
        const val INTERACTIVE = "INTERACTIVe"
        const val TEXT = "TEXT"
    }
}
