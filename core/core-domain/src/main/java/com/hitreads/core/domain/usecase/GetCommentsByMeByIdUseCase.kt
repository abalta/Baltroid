package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentsByMeByIdUseCase @Inject constructor(private val repository: CommentRepository) {
    operator fun invoke(originalId: Int) = repository.getCommentsByMeById(originalId)
}
