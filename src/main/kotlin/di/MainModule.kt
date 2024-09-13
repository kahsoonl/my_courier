package org.example.di

import calculator.CostCalculator
import calculator.CostCalculatorImpl
import org.example.calculator.PackageManager
import org.example.data.repository.CourierLocalRepositoryImpl
import org.example.domain.CourierUseCase
import org.example.domain.CourierUseCaseImpl
import org.example.domain.repository.CourierLocalRepository
import org.example.main.viewmodel.CourierViewModel
import org.example.offervalidation.OfferValidator
import org.example.offervalidation.OfferValidatorImpl
import org.example.timeestimation.TimeEstimation
import org.example.timeestimation.TimeEstimationImpl
import org.example.weightmatcher.PackageWeightMatcher
import org.example.weightmatcher.PackageWeightMatcherImpl
import org.koin.dsl.module

val mainModule = module {
    single { CourierLocalRepositoryImpl() }
    single<CourierLocalRepository> { get<CourierLocalRepositoryImpl>() }
    single { CourierUseCaseImpl(get(), get()) }
    single<CourierUseCase> { get<CourierUseCaseImpl>() }
    single { CourierViewModel(get()) }
    single<CostCalculator> { get<CostCalculatorImpl>() }
    single { CostCalculatorImpl() }
    single { PackageManager(get(), get(), get(), get()) }
    single { OfferValidatorImpl() }
    single<OfferValidator> { get<OfferValidatorImpl>() }
    single { PackageWeightMatcherImpl() }
    single<PackageWeightMatcher> { get<PackageWeightMatcherImpl>() }
    single { TimeEstimationImpl() }
    single<TimeEstimation> { get<TimeEstimationImpl>() }
}