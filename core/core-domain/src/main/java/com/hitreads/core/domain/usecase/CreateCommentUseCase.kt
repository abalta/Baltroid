package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.CommentRepository
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(private val repository: CommentRepository) {
    operator fun invoke(type: String, id: Int, content: String, responseId: Int?) =
        repository.createComment(type, id, content, responseId)
}
