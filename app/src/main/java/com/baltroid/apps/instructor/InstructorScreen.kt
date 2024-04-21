package com.baltroid.apps.instructor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.CircularButton
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MediumBold
import com.baltroid.designsystem.component.MediumTitleText
import com.baltroid.designsystem.component.MekikCard
import com.baltroid.designsystem.component.ReadMoreClickableText
import com.baltroid.designsystem.theme.electricVioletColor

@Composable
fun InstructorScreen(onAction: OnAction) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sample_instructor),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "instructor",
                    contentScale = ContentScale.FillWidth
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    CircularButton(
                        icon = R.drawable.ic_back,
                        Modifier.padding(top = 28.dp, start = 24.dp)
                    ) {

                    }
                    CircularButton(
                        icon = R.drawable.ic_fav,
                        Modifier.padding(top = 28.dp, end = 24.dp)
                    ) {

                    }
                }
            }
        }
        item {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MediumTitleText(
                    "Zeynep Begüm Kocaçal",
                    Modifier
                        .padding(start = 24.dp, top = 16.dp, end = 24.dp),
                )
                MediumBold(
                    text = "CCIM Institute",
                    color = MaterialTheme.colorScheme.electricVioletColor,
                    modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
                )
                ReadMoreClickableText(
                    text = "Eğitimin bu 1.bölümünde, Satışın ne olduğu hakkında bilgiler paylaşılıyor. Amaç, Satış mesleğini anlamak, hangi yetenek ve becerilere sahip olmamız ve geliştirmemiz gerektiği konusunda bilgi sahip olmaktır. Diğer bölümlerde ise bu yetenek ve becerilere tek tek yer veriliyor.",
                    Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
                )
            }
        }
        item {
            FilterHeadText(
                text = "Eğitimler",
                Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 22.dp, end = 24.dp)
            )
        }
        items(12) {
            MekikCard(
                caption = "Taner Özdeş",
                title = "Dijital Dünyanın Antidijital Nefesi",
                category = "Popüler"
            ) {
                onAction(
                    UiAction.OnCourseClick
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewInstructorScreen() {
    InstructorScreen {

    }
}