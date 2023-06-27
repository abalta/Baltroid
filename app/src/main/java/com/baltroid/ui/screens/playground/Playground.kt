package com.baltroid.ui.screens.playground

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.baltroid.ui.screens.menu.comments.CommentViewModel

@Composable
fun PlaygroundScreen(
    viewModel: CommentViewModel,
) {
    Button(onClick = {
        viewModel.likeComment(2)
    }) {
        Text(text = "Service Demo")
    }
}