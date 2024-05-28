package com.baltroid.apps.about

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
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.apps.ext.showCall
import com.baltroid.apps.ext.showMail
import com.baltroid.apps.ext.showWeb
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.CircularButton
import com.baltroid.designsystem.component.HeadText
import com.baltroid.designsystem.component.MediumText
import com.baltroid.designsystem.component.MekikOutlinedButton

@Composable
fun AboutScreen(onAction: OnAction) {
    val about = mutableListOf(
        Pair(
            "Mekik Nedir ?",
            "Mekik.org, geçmişten günümüze gelen Kapalı Çarşı’nın bir arada olma kültürünü eğitim dünyasına entegre ederek, B2B (“Business to Business”, “Şirketten Şirkete”) İş Modeli ve B2C (“Business to Consumer”, “Firmadan Tüketiciye” ) İş Modelini hedefleyen pazarlama ve hizmet sektörlerindeki eğitmen , akademi ve şirketler için dizayn edilmiş geleceğin dijital kütüphanesidir.\n" +
                    "\n" +
                    "Bu kütüphane içeriğinde aynı zamanda şirketler şirket içi yönetim planlarını ekleyerek çalışanların hangi videoyu ne kadar izlediğini onay takip ve ölçme değerlendirme sistemli kontrol edebilecek bir yapı tasarımı sunulmuştur."
        ),
        Pair(
            "Felsefemiz",
            "EĞİTİM - TEKRAR – TAKİP. Mekik.org Dijital Kütüphane düzenli aralıklarla tekrar felsefesi üzerine kurulmuştur. Gelecek Dünyaya tekrar ve takip sistemi ile dizayn edilmiş online bir dijital kütüphane tasarımı sunmaktır."
        ),
        Pair(
            "Vizyonumuz",
            "Farklı mesleki vizyonların kişisel vizyonları geliştirmesine hız kazandırmaktır.\n" +
                    "Hedefimiz, internet dünyasının pazarlama ve hizmet sektörlerinde en çok tercih edilen ve ilk online kütüphanesi olmaktır.\n" +
                    "Mekik ile herkes kendi kütüphanelerini kurarak gerek oryantasyon, yetiştirme, motivasyon ve benzeri eğitimlerini kütüphanelerine yükleyebilirler. Herkes sadece kendilerine ait bilgiye ulaşarak şirket içi pozisyonlarına uygun kaynaklara çok daha kısa yoldan varacaktır.\n" +
                    "Şirket, patron ya da yöneticiler kendi şirketleri için hazırladıkları eğitimlerden istediklerini mekik ile satışa sunabilecekler, ve ekiplerini takip ederek eğitime gösterilen ilgiyi görebilecek, anlamayı ölçebilecekler. Böylece insan kaynakları ve eğitim departmanı işlerini kolaylaştırarak şirket içi iletişimi ve yönetimi hızlandırıp şirketin bireylere bağlı gelişimini kurumsal boyuta taşımış olacaklar.\n" +
                    "Bunun beraberinde de kulaktan kulağa azalan ve değişen bilgiler yerine herkes kaynaktan, aynı orijinallikte bilgiyi elde edebileceklerdir.\n" +
                    "Böylece eğitmenler aynı şeyi tekrarlamak yerine eğitmenlerin yeni eğitimleri üretmesine fırsat verecektir."
        ),
        Pair(
            "Misyonumuz",
            "Misyonumuz, Pazarlama ve hizmeti oluşturan sektörel eğitimleri bir araya getirerek sinerji yaratmak ve sektörel gelişmelere ortam sağlamaktır.\n" +
                    "Kurumları oluşturan tüm bireyler istedikleri zaman interneti olan her yerden istedikleri zaman ulaşabilecekleri bu bilgiler ile şirket içinde bilgi akışı için oluşan toplanmaları azaltarak mesai saatlerinden çok ciddi kazanç yaratacaktır.\n" +
                    "İş planı ayarlamak için harcanacak çabanın önüne geçilerek şirketlerin ve kurumların insan kaynağı giderini azaltacaktır.\n" +
                    "Özellikle eğitmenler için ayni anda birçok yerde olabilme, farklı dillerde tüm dünyaya eğitim verebilme fikri yaratabilmektedir."
        ),
        Pair(
            "Amacımız",
            "Mekik olarak amacımız akademileri ve şirketlerin yöneticilerini, eğitmenlerini ve çalışanlarını hızlı ve efektif hale getirmektir.\n" +
                    "Eğitmenler istedikleri eğitimi istedikleri kurumlara sunarak kurumlara özel eğitimler hazırlayabilecek, ya da mekik portalında B2C olarak da tüm pazara sunabilecektir.\n" +
                    "Böylece şirketleri şirket yapan bilgiler tasniflenerek üretim ve hizmet bilgileri bir değer haline getirilip bilgiyi paketlenerek bir urun haline getirecektir."
        ),
        Pair("Hedefimiz", "Kurumların en çok tercih ettiği online kütüphane olmaktır."),
    )

    val context = LocalContext.current

    val buttons = linkedMapOf(
        "İletişim" to { context.showCall("0850 480 68 48") },
        "Eğitmen Ol" to { context.showMail("info@mekik.org") },
        "Uygulamayı Paylaş" to { context.showWeb("https://play.google.com/") },
    )

    val socialMedia = linkedMapOf(
        R.drawable.facebook to "https://www.facebook.com/mekik.org",
        R.drawable.instagram to "https://www.instagram.com/mekikorg/",
        R.drawable.twitter to "https://x.com/MekikOrg",
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.about),
                    modifier = Modifier
                        .height(165.dp)
                        .fillMaxWidth(),
                    contentDescription = "about",
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CircularButton(
                        icon = R.drawable.ic_back,
                        Modifier.padding(top = 28.dp, start = 24.dp)
                    ) {
                        onAction(UiAction.OnBackClick)
                    }
                }
            }
        }
        items(about.size) {
            val item = about[it]
            Column(
                Modifier.fillMaxWidth()
            ) {
                HeadText(
                    item.first,
                    Modifier
                        .padding(start = 24.dp, top = 16.dp, end = 24.dp),
                )
                MediumText(
                    text = item.second,
                    color = Color.Black.copy(0.5f),
                    modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
                )
            }
        }
        items(buttons.size) {
            val item = buttons.entries.elementAt(it)
            MekikOutlinedButton(
                text = item.key,
                Modifier.padding(top = 16.dp, start = 11.dp, end = 11.dp)
            ) {
                item.value()
            }
        }
        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                socialMedia.forEach { (icon, action) ->
                    IconButton(
                        onClick = { context.showWeb(action) },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = "social"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen(onAction = {})
}