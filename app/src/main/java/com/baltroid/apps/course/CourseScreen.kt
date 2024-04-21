package com.baltroid.apps.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.BoldHeadText
import com.baltroid.designsystem.component.Caption
import com.baltroid.designsystem.component.CaptionMedium
import com.baltroid.designsystem.component.CircularButton
import com.baltroid.designsystem.component.ExpandableCard
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MediumSmallText
import com.baltroid.designsystem.component.MediumText
import com.baltroid.designsystem.component.MediumTitleText
import com.baltroid.designsystem.component.MekikCard
import com.baltroid.designsystem.component.MekikCardDouble
import com.baltroid.designsystem.component.ReadMoreClickableText
import com.baltroid.designsystem.theme.electricVioletColor
import com.baltroid.designsystem.theme.goldenTainoiColor

@Composable
fun CourseScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(211.dp)) {
                Image(painter = painterResource(id = R.drawable.sample_instructor), modifier = Modifier.fillMaxSize(), contentDescription = "instructor", contentScale = ContentScale.FillWidth)
                Row(Modifier.fillMaxWidth()) {
                    CircularButton(icon = R.drawable.ic_back, Modifier.padding(top = 28.dp, start = 24.dp)) {

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    CircularButton(icon = R.drawable.ic_share, Modifier.padding(top = 28.dp, end = 6.dp)) {

                    }
                    CircularButton(icon = R.drawable.ic_fav, Modifier.padding(top = 28.dp, end = 24.dp)) {

                    }
                }
            }
        }
        item {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                BoldHeadText("Satış Nedir?",
                    Modifier
                        .padding(start = 20.dp, top = 8.dp, end = 20.dp),
                )
                CaptionMedium(text = "Taner Özdeş", color = MaterialTheme.colorScheme.electricVioletColor, modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp))
                Caption(text = "Pasha Akademi", color = Color.Black.copy(0.5f), modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp))
                Row(modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.ic_duration), modifier = Modifier.size(15.dp), contentDescription = "time")
                    MediumSmallText(text = "17dk", Modifier.padding(start = 8.dp, end = 8.dp))
                    Icon(painter = painterResource(id = R.drawable.ic_level), modifier = Modifier.size(15.dp), contentDescription = "level")
                    MediumSmallText(text = "Temel Seviye", Modifier.padding(start = 8.dp, end = 8.dp))
                    Icon(painter = painterResource(id = R.drawable.ic_comment), modifier = Modifier.size(15.dp), contentDescription = "comment", tint = MaterialTheme.colorScheme.electricVioletColor)
                    MediumSmallText(text = "Yorumlar (2)", Modifier.padding(start = 8.dp, end = 8.dp))
                    Icon(painter = painterResource(id = R.drawable.ic_star), modifier = Modifier.size(15.dp), contentDescription = "star", tint = MaterialTheme.colorScheme.goldenTainoiColor)
                    MediumSmallText(text = "4.5", Modifier.padding(start = 8.dp, end = 8.dp))
                }
                ReadMoreClickableText(text = "Eğitimin bu 1.bölümünde, Satışın ne olduğu hakkında bilgiler paylaşılıyor. Amaç, Satış mesleğini anlamak, hangi yetenek ve becerilere sahip olmamız ve geliştirmemiz gerektiği konusunda bilgi sahip olmaktır. Diğer bölümlerde ise bu yetenek ve becerilere tek tek yer veriliyor.", Modifier.padding(start = 20.dp, top = 16.dp, end = 20.dp))
            }
        }
        items(3) {
            ExpandableCard(title = "Tanıtım")
        }
    }
}

@Preview
@Composable
fun PreviewCourseScreen() {
    CourseScreen()
}