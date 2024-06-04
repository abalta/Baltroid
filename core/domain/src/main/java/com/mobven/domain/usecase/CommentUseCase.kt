package com.mobven.domain.usecase

import com.mobven.domain.repository.MekikRepository
import javax.inject.Inject

class CommentUseCase @Inject constructor(
    private val mekikRepository: MekikRepository
){
    operator fun invoke(
        id: Int,
        comment: String,
        rating: Int
    ) = mekikRepository.addComment(
        courseId = id,
        comment = comment,
        rating = rating
    )
}