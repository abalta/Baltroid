package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.WelcomeRepository
import javax.inject.Inject

class WelcomeUseCase @Inject constructor(private val repository: WelcomeRepository) {
    operator fun invoke() = repository.getWelcomeList()
}
