package com.example.country.domain.usecase

import com.example.country.data.repository.FakeRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.example.country.domain.Result
import com.example.country.domain.model.Countries
import org.junit.Assert.*
import org.junit.Before


class GetCountriesUseCaseTest {


    private lateinit var getCountriesUseCase: GetCountriesUseCase
    private lateinit var repository: FakeRepository


    @Before
    fun setUp() {
        repository = FakeRepository()
        getCountriesUseCase = GetCountriesUseCase(repository)
    }


    @Test
    fun `Check return success`() = runTest {
        getCountriesUseCase.getCountries().collectLatest {
            when (it) {
                is Result.Success<*> -> {
                    val post = it.data as Countries
                    assertNotNull(post)
                }

                else -> {}
            }
        }
    }
}