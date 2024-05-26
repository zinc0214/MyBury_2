package com.zinc.domain.usecases.detail

import com.zinc.domain.repository.DetailRepository
import javax.inject.Inject

class GoalCountUpdate @Inject constructor(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(id: String, goalCount: Int) =
        detailRepository.requestGoalCountUpdate(id, goalCount)
}
