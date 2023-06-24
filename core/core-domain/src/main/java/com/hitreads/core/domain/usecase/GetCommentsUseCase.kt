package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repository: CommentRepository) {
    operator fun invoke(type: String, id: Int) = repository.getComments(type, id)
}
