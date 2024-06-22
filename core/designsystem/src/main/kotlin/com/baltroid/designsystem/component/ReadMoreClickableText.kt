package com.baltroid.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.baltroid.designsystem.theme.captionStyle
import com.baltroid.designsystem.theme.electricVioletColor
import com.baltroid.designsystem.theme.regularStyle

@Composable
fun ReadMoreClickableText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.regularStyle,
    minimizedMaxLines: Int = 4,
) {
    var expanded by remember { mutableStateOf(false) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }

    Column(modifier) {
        Text(
            text = text,
            style = textStyle,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = if (expanded) "Daha az" else "Devamını oku",
            style = MaterialTheme.typography.captionStyle,
            color = MaterialTheme.colorScheme.electricVioletColor,
            onTextLayout = { seeMoreSizeState.value = it.size },
            modifier = Modifier.padding(top = 8.dp)
                .clickable {
                    expanded = expanded.not()
                }
        )
    }
}

@Preview
@Composable
fun PreviewReadMoreClickableText() {
    ReadMoreClickableText(
        text = "Eğitimin bu 1.bölümünde, Satışın ne olduğu hakkında bilgiler paylaşılıyor. Amaç, Satış mesleğini anlamak, hangi yetenek ve becerilere sahip olmamız ve geliştirmemiz gerektiği konusunda bilgi sahip olmaktır. Diğer bölümlerde ise bu yetenek ve becerilere tek tek yer veriliyor."
    )
}