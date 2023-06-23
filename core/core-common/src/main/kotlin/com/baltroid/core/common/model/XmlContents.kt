package com.baltroid.core.common.model

import com.google.gson.annotations.SerializedName

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