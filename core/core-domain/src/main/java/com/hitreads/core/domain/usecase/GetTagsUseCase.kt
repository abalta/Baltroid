package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.TagRepository
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(private val repository: TagRepository) {
    operator fun invoke() = repository.getTagList()
}
