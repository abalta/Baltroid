package com.baltroid.core.network.model.response

import com.baltroid.core.network.model.originals.NetworkEpisode
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.util.Constants
import com.ryanharter.kotlinx.serialization.xml.XmlAttribute
import com.ryanharter.kotlinx.serialization.xml.XmlContent
import com.ryanharter.kotlinx.serialization.xml.XmlName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OriginalResponseDto(
    @SerialName(Constants.Fields.DATA_COUNT)
    val dataCount: Int,

    @SerialName(Constants.Fields.ORIGINALS)
    val originals: List<NetworkOriginal>,

    @SerialName(Constants.Fields.ACTIVE_PAGE)
    val totalPages: Int,

    @SerialName(Constants.Fields.TOTAL_PAGE)
    val totalResults: Int,
)

@Serializable
data class LoginDto(
    @SerialName(Constants.Fields.USER_ID)
    val userId: Int,

    @SerialName(Constants.Fields.TOKEN)
    val token: String,

    @SerialName(Constants.Fields.USERNAME)
    val username: String,

    @SerialName(Constants.Fields.AVATAR)
    val avatar: String,

    @SerialName(Constants.Fields.WALLET)
    val wallet: Int,
)

@Serializable
data class EpisodeResponseDto(
    @SerialName(Constants.Fields.EPISODE)
    val episode: NetworkEpisode,

    @SerialName(Constants.Fields.REPUTATION)
    val reputation: List<Unit>
)

@Serializable
data class Episode(
    val dialogueList: List<Dialogue>
)

/*
* @Serializable
data class Greeting(
  @XmlAttribute val from: String,
  @XmlAttribute val to: String,
  val message: Message
)

@Serializable
data class Message(
  @XmlContent val content: String
)

val xml = """
      <Greeting from="Ryan" to="Bill">
        <message>Hi</message>
      </Greeting>
    """.trimIndent()
val actual = Xml.Default.decodeFromString<Greeting>(xml)*/

/*
<episode>
<dialogue line_id="B01">
<line_type>NOT</line_type>
<text>1. BÖLÜM</text>
<background>15</background>
<next_line_id>1B02</next_line_id>
</dialogue>
*/

@Serializable
data class Dialogue(
    @XmlAttribute val lineId: String,
    val lineType: LineType,
    val text: Text,
    val background: Background,
    val nextLineId: NextLineId,
    val focus: Focus,
    val talker: Talker
)

@Serializable
data class LineType(
    @XmlContent val content: String
)

@Serializable
data class Text(
    @XmlContent val content: String
)

@Serializable
data class Background(
    @XmlContent val content: String
)

@Serializable
data class NextLineId(
    @XmlContent val content: String
)

@Serializable
data class Focus(
    @XmlContent val content: String
)

@Serializable
data class Talker(
    @XmlContent val content: String
)

