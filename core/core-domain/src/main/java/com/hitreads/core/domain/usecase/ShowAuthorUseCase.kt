package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.AuthorRepository
import javax.inject.Inject

class ShowAuthorUseCase @Inject constructor(private val repository: AuthorRepository) {
    operator fun invoke(id: Int) = repository.showAuthor(id)
}