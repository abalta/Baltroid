package com.baltroid.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baltroid.data.Participant

@Composable
fun Grid(
    participantList: List<Participant>,
    selectedParticipant: Participant? = null,
    neighbors: List<Participant> = emptyList(),
    onNeighborSelected: (Participant?) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(10),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(participantList) { item ->
            AreaBox(item, selectedParticipant, neighbors, onNeighborSelected)
        }
    }
}

@Composable
fun AreaBox(
    participant: Participant?,
    selectedParticipant: Participant?,
    neighbors: List<Participant>,
    onNeighborSelected: (Participant?) -> Unit = {}
) {
    val defaultColor = Color(124, 157, 169)
    val selectedColor = Color(181, 226, 240)
    val neighborColor = Color(194, 253, 254)

    val backgroundColor = when {
        selectedParticipant == participant -> selectedColor
        neighbors.contains(participant) -> neighborColor
        else -> defaultColor
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(backgroundColor)
            .conditional(
                condition = (participant?.areas?.size ?: 0) == 1,
                ifTrue = { border(1.dp, Color.Black) }
            )
            .clickable {
                if (backgroundColor == neighborColor) {
                    onNeighborSelected(participant)
                }
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "P: ${participant?.id}", fontSize = 12.sp)
            Text(text = "Cat: ${participant?.category}", fontSize = 12.sp)
        }
    }
}

@Composable
fun SelectedParticipantInfo(participant: Participant?) {
    if (participant != null) {
        Text("Seçilen Yarışmacı: ${participant.id}, Kategori: ${participant.category}")
    } else {
        Text("Yarışmacı seçilmedi.")
    }
}

@Composable
fun SelectedNeighborInfo(participant: Participant?) {
    if (participant != null) {
        Text("Seçilen Komşu: ${participant.id}, Kategori: ${participant.category}")
    } else {
        Text("Komşu seçilmedi.")
    }
}

@Composable
fun CompetedCategory(participant: Participant?) {
    if (participant != null) {
        Text("Yarışılacak Kategori: ${participant.category}")
    }
}

fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: (Modifier.() -> Modifier)? = null,
): Modifier {
    return if (condition) {
        then(ifTrue(Modifier))
    } else if (ifFalse != null) {
        then(ifFalse(Modifier))
    } else {
        this
    }
}