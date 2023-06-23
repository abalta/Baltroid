package com.hitreads.core.domain.model

import com.google.gson.annotations.SerializedName

data class EpisodeModel(
    val id: Int,
    val name: String,
    val price: Int,
    val priceType: String,
    val userPurchase: String?,
    val assetContents: String?,
    val xmlContents: XmlContent?
)

data class XmlContent(
    @SerializedName("episode")
    val episode: EpisodeXml?
)

data class EpisodeXml(
    @SerializedName("dialogue")
    val dialogue: List<DialogueXml?>?
)

data class DialogueXml(
    @SerializedName("background")
    val background: String?,
    @SerializedName("emotion")
    val emotion: String?,
    @SerializedName("focus")
    val focus: String?,
    @SerializedName("-line_id")
    val lineId: String?,
    @SerializedName("line_type")
    val lineType: String?,
    @SerializedName("next_line_id")
    val nextLineId: String?,
    @SerializedName("talker")
    val talker: String?,
    @SerializedName("text")
    val text: String?
)
