package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentsLikedByMeByIdUseCase @Inject constructor(private val repository: CommentRepository) {
    operator fun invoke(type: String, id: Int) = repository.getCommentsLikedByMeById(type, id)
}
