package com.baltroid.apps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.baltroid.designsystem.Grid
import com.baltroid.designsystem.SelectedParticipantInfo
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baltroid.data.Participant
import com.baltroid.designsystem.CompetedCategory
import com.baltroid.designsystem.SelectedNeighborInfo

@Composable
fun CompetitionScreen(viewModel: CompetitionViewModel = viewModel()) {
    val participantList by remember { mutableStateOf(viewModel.participants) }
    var selectedParticipant by remember { mutableStateOf<Participant?>(null) }
    var selectedNeighbor by remember { mutableStateOf<Participant?>(null) }
    var neighbors by remember { mutableStateOf<List<Participant>>(emptyList()) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Grid(participantList, selectedParticipant, neighbors, onNeighborSelected = {
            selectedNeighbor = it
        })
        SelectedParticipantInfo(participant = selectedParticipant)
        SelectedNeighborInfo(participant = selectedNeighbor)
        CompetedCategory(participant = selectedNeighbor)
        Row {
            Button(onClick = {
                selectedParticipant = viewModel.selectRandomParticipant()
                neighbors = emptyList()
                selectedNeighbor = null
            }) {
                Text("Yarışmaya Başla")
            }
            Button(onClick = {
                neighbors = viewModel.getNeighbors(selectedParticipant)
            }) {
                Text("Komşuları Göster")
            }
        }
        Row {
            Button(onClick = {
                viewModel.handleVictory(selectedParticipant!!, selectedNeighbor!!)
                neighbors = emptyList()
                selectedNeighbor = null
            }) {
                Text("Seçilen Yarışmacı Kazansın")
            }
            Button(onClick = {
                viewModel.handleVictory(selectedNeighbor!!, selectedParticipant!!)
                neighbors = emptyList()
                selectedParticipant = selectedNeighbor
                selectedNeighbor = null
            }) {
                Text("Seçilen Komşu Kazansın")
            }
        }
    }
}