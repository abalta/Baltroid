package com.baltroid.apps

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import com.baltroid.data.Area
import com.baltroid.data.Participant

class CompetitionViewModel: ViewModel() {
    private var _participants = mutableStateListOf<Participant>()
    val participants: List<Participant> get() = _participants

    init {
        initializeParticipants()
    }

    private fun initializeParticipants() {
        var id = 0
        for (i in 0 until 10) {
            for (j in 0 until 10) {
                _participants.add(Participant(id, "$id", mutableListOf(
                    Area(id, i, j),
                )))
                id++
            }
        }
    }

    fun selectRandomParticipant(): Participant? {
        val availableParticipant = _participants.filter {
            it.areas.size == 1
        }
        if (availableParticipant.isNotEmpty()) {
            val randomParticipant = availableParticipant.random()
            return randomParticipant
        }
        return null
    }

    fun getNeighbors(participant: Participant?): List<Participant> {
        if (participant == null) return emptyList()
        val neighborAreas = mutableListOf<Participant>()
        participant.areas.forEach { participantArea ->
            val x = participantArea.x
            val y = participantArea.y
            val top = _participants.find { area -> area.areas.any { it.x == x - 1 && it.y == y } }
            val right = _participants.find { area -> area.areas.any { it.x == x && it.y == y + 1 } }
            val bottom = _participants.find { area -> area.areas.any { it.x == x + 1 && it.y == y } }
            val left = _participants.find { area -> area.areas.any { it.x == x && it.y == y - 1 } }
            top?.let { neighborAreas.add(it) }
            right?.let { neighborAreas.add(it) }
            bottom?.let { neighborAreas.add(it) }
            left?.let { neighborAreas.add(it) }
        }
        return neighborAreas
    }

    fun handleVictory(selected: Participant, neighbor: Participant) {
        selected.areas.addAll(neighbor.areas)
        val newParticipantList = mutableListOf<Participant>()
        _participants.forEach {
            if (it.id == neighbor.id) {
                newParticipantList.add(selected)
            } else {
                newParticipantList.add(it)
            }
        }
        _participants.clear()
        _participants.addAll(newParticipantList)
    }
}