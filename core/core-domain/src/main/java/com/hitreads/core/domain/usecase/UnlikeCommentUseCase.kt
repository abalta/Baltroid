package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.CommentRepository
import javax.inject.Inject

class UnlikeCommentUseCase @Inject constructor(private val repository: CommentRepository) {
    operator fun invoke(id: Int) = repository.unlikeComment(id)
}
