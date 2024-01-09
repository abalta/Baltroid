package com.baltroid.designsystem.dialog

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.Body
import com.baltroid.designsystem.component.MQIcon
import com.baltroid.designsystem.component.Subhead
import com.baltroid.designsystem.extension.launchCustomChromeTab
import com.baltroid.designsystem.extension.showCall
import com.baltroid.designsystem.extension.showMail
import com.baltroid.designsystem.theme.eucalyptusColor
import com.baltroid.designsystem.theme.hollyColor
import com.baltroid.model.Mall

@Composable
fun MqDialog(
    mall: Mall,
    onDismiss: () -> Unit
) {
    val context: Context = LocalContext.current
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = true
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .padding(top = 32.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(18.dp))
                    .fillMaxWidth()
            ) {
                Subhead(
                    text = "İletişim Bilgileri",
                    modifier = Modifier
                        .padding(top = 62.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = buildAnnotatedString {
                        append("Telefon: ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.eucalyptusColor)) {
                            append(mall.phone)
                        }
                    },
                    Modifier
                        .padding(top = 24.dp, start = 18.dp, end = 18.dp)
                        .clickable {
                            context.showCall(mall.phone)
                        })
                Text(
                    text = buildAnnotatedString {
                        append("E-posta: ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.eucalyptusColor)) {
                            append(mall.email)
                        }
                    },
                    Modifier
                        .padding(start = 18.dp, end = 18.dp, top = 8.dp)
                        .clickable {
                            context.showMail(mall.email)
                        })
                Text(
                    buildAnnotatedString {
                        append("Web Adresi: ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.eucalyptusColor)) {
                            append(mall.web)
                        }
                    },
                    Modifier
                        .padding(start = 18.dp, end = 18.dp, bottom = 30.dp, top = 8.dp)
                        .clickable {
                            context.launchCustomChromeTab(mall.web, Color.White.toArgb())
                        })
                TextButton(onClick = { onDismiss() }, Modifier.padding(bottom = 18.dp, start = 18.dp, end = 18.dp).fillMaxWidth().align(Alignment.CenterHorizontally)) {
                    Body("Tamam", color = MaterialTheme.colorScheme.eucalyptusColor)
                }
            }
            MQIcon(
                resourceId = R.drawable.info_circle, modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.TopCenter)
            )
        }

    }
}

@Preview
@Composable
fun MqDialogPreview() {
    MqDialog(
        mall = Mall(
            id = "ante",
            cityCode = 4053,
            address = "voluptatibus",
            email = "herbert.noel@example.com",
            floors = listOf(),
            location = Pair(0.0, 0.0),
            name = "Kathy Butler",
            phone = "(599) 865-2573",
            services = mutableMapOf(),
            web = "justo",
            logo = "expetenda",
            photos = listOf(),
            rating = "option",
            reviews = "voluptatum",
            district = "parturient",
            shops = mutableMapOf()
        ),
        onDismiss = { }
    )
}