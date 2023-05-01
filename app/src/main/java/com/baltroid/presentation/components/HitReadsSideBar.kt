package com.baltroid.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.baltroid.presentation.screens.reading.SideBarBottomSection
import com.baltroid.presentation.screens.reading.SideBarTopSection
import com.baltroid.presentation.screens.reading.SideBarVerticalDivider

@Composable
fun HitReadsSideBar(
    modifier: Modifier,
    numberOfViews: Int,
    numberOfComments: Int,
    hasSmallHeight: Boolean
) {
    Row(
        modifier = modifier
    ) {
        SideBarVerticalDivider()
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Min)
        ) {
            SideBarTopSection(
                numberOfViews = numberOfViews,
                numberOfComments = numberOfComments,
                hasSmallHeight = hasSmallHeight, modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            )
            SideBarBottomSection(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}