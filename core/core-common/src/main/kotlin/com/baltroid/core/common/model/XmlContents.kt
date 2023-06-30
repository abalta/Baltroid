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
    val text: String?,
    @SerializedName("op_count")
    val optionCount: String?,
    @SerializedName("op_1")
    val optionOne: String?,
    @SerializedName("op_1_next_line")
    val optionOneNextLineId: String?,
    @SerializedName("op_2")
    val optionTwo: String?,
    @SerializedName("op_2_next_line")
    val optionTwoNextLineId: String?,
    @SerializedName("op_3")
    val optionThree: String?,
    @SerializedName("op_3_next_line")
    val optionThreeNextLineId: String?
)