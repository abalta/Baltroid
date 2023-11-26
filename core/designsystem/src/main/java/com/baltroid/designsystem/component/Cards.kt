package com.baltroid.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.theme.Eucalyptus
import com.baltroid.designsystem.theme.Holly
import com.baltroid.designsystem.theme.Holly64
import com.baltroid.designsystem.theme.Holly74
import com.baltroid.model.Mall

@Composable
fun CardMedium(mall: Mall, painter: Painter, onMallClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .height(254.dp)
            .clickable {
                onMallClick(mall.id)
            }
    ) {
        MallLogo(painter)
        Subhead(text = mall.name, modifier = Modifier.padding(top = 14.dp))
        Body(text = mall.district)
        Spacer(modifier = Modifier.height(10.dp))
        if(mall.rating.isNotEmpty()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    mall.rating,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(
                            Font(R.font.sf_pro_regular)
                        )
                    ),
                    modifier = Modifier
                        .background(
                            color = Eucalyptus,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .padding(8.dp, 4.dp, 8.dp, 4.dp)
                )
                Text(
                    stringResource(id = R.string.comment, mall.reviews),
                    style = TextStyle(
                        color = Holly,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        fontFamily = FontFamily(
                            Font(R.font.sf_pro_regular)
                        )
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}


@Composable
@Preview
fun previewCardMedium() = CardMedium(
    mall = Mall("1",
        cityCode = 7108,
        address = "labores",
        email = "frankie.greene@example.com",
        floors = listOf(),
        location = Pair(0.0, 0.0),
        name = "Erin Duffy",
        phone = "(973) 917-5437",
        services = mutableMapOf(),
        web = "intellegebat",
        logo = "quem",
        photos = listOf(),
        rating = "4.3",
        reviews = "6.3k",
        district = "Colarodo, San Francisco",
        shops = mutableMapOf()
    ),
    painter = painterResource(id = R.drawable.bg_banner),
    onMallClick = { _ ->

    })

@Composable
fun ServiceCard(serviceName: String, serviceIcon: Int) {
    Column(modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .border(BorderStroke(1.dp, Color(0xFFF3F2F2)), RoundedCornerShape(10.dp))
                .padding(30.dp), painter = painterResource(id = serviceIcon), contentDescription = "Service Logo", contentScale = ContentScale.FillWidth
        )
        Text(
            serviceName,
            style = TextStyle(
                color = Holly,
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily(
                    Font(R.font.sf_pro_regular)
                )
            ),
            modifier = Modifier
                .padding(top = 10.dp)
        )
    }
}

@Composable
@Preview
fun previewServiceCard() {
    ServiceCard(serviceName = "ATM", serviceIcon = R.drawable.icon_atm)
}

@Composable
fun ShopCard(painter: Painter, shopName: String, floor: String, phoneNumber: String) {
    Row(modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth()
        .height(IntrinsicSize.Min)) {
        ShopLogo(painter = painter)
        Column(modifier = Modifier
            .padding(start = 18.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween) {

            Column {
                Text(
                    shopName,
                    style = TextStyle(
                        color = Holly,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(500),
                        fontFamily = FontFamily(
                            Font(R.font.sf_pro_regular)
                        )
                    )
                )

                Row(modifier = Modifier.padding(top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    MQIcon(resourceId = R.drawable.phone, modifier = Modifier.size(16.dp), tint = Holly64)

                    Text(
                        phoneNumber,
                        modifier = Modifier.padding(start = 8.dp),
                        style = TextStyle(
                            color = Holly64,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            fontFamily = FontFamily(
                                Font(R.font.sf_pro_regular)
                            )
                        )
                    )
                }
            }


            Text(
                floor,
                style = TextStyle(
                    color = Holly74,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily(
                        Font(R.font.sf_pro_regular)
                    )
                )
            )
        }
        
    }
}

@Composable
@Preview
fun previewShopCard() {
    ShopCard(painterResource(id = R.drawable.bg_banner), "Adidas", "1. Kat", "03225038573")
}
