package com.example.audioplayer.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
//first lesson

enum class RepositoryScenario {
    SUCCESS,
    EMPTY,
    ERROR,
    UPDATED
}


@Singleton
class RepositoryScenarioProvider @Inject constructor() {
    private val _scenario = MutableStateFlow(RepositoryScenario.UPDATED)
    val scenario = _scenario.asStateFlow()

    fun setScenario(scenario: RepositoryScenario) {
        _scenario.value = scenario
    }
}


