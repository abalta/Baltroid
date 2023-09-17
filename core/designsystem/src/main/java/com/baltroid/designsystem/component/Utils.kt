package com.baltroid.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.theme.Eucalyptus

@Composable
fun MallFeature(featureCount: String, featureIcon: Int, featureName: String) {
    Row {
        MQIcon(resourceId = featureIcon, modifier = Modifier.size(36.dp), tint = Eucalyptus)
        Column(modifier = Modifier.padding(start = 5.dp)) {
            Subhead(text = featureCount)
            Body(text = featureName)
        }
    }
}

@Preview
@Composable
fun PreviewMallFeature() {
    MallFeature("4", R.drawable.icon_floors, "Katlı")
}