package com.cryptopia.android.repository

import com.cryptopia.android.di.NetworkModule
import com.cryptopia.android.model.local.CoinPairDao
import com.cryptopia.android.model.local.PricePair
import com.cryptopia.android.model.local.PricePairDAO
import com.cryptopia.android.model.remote.CryptoCompareCoinDetail
import com.cryptopia.android.network.CryptoCompareAPI
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by robertzzy on 20/11/17.
 */
@RunWith(MockitoJUnitRunner::class)
class PriceRepositoryTest {

    @Module()
    class TestModule {
        @Provides
        fun providePriceRepository(api: CryptoCompareAPI): PriceRepository = PriceRepositoryImpl(api, Mockito.mock(PricePairDAO::class.java))

        @Provides
        fun provideCoinRepository(api: CryptoCompareAPI): CoinRepository = CoinRepositoryImpl(api, Mockito.mock(CoinPairDao::class.java))
    }

    @Singleton()
    @Component(modules = arrayOf(NetworkModule::class, TestModule::class))
    interface TestComponent {
        fun inject(test: PriceRepositoryTest)
    }

    @Inject lateinit var priceRepository: PriceRepository

    @Inject lateinit var coinRepository: CoinRepositoryImpl

    @Before
    fun setup() {
        DaggerPriceRepositoryTest_TestComponent.builder().build().inject(this)
    }

    @Test
    fun testPriceRepository() {
        val testSubscriber = TestSubscriber<List<PricePair>>()
        priceRepository.updateCache(listOf("BTC", "ETH"), listOf("USD", "CAD"), null).subscribe(testSubscriber)
        testSubscriber.await(10, TimeUnit.SECONDS)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        val result = testSubscriber.values()[0]
        assertTrue(result.find { it.fromCoin == "BTC" } != null)
        assertTrue(result.find { it.fromCoin == "ETH" } != null)

        assertTrue(result.size == 4)

        assertTrue(result.find { it.toCoin == "USD" } != null)
        assertTrue(result.find { it.toCoin == "CAD" } != null)

        assertTrue(result.find { it.toCoin == "USD" } != null)
        assertTrue(result.find { it.toCoin == "CAD" } != null)
    }


    @Test
    fun testGetCoinList() {
        var testSubscriber = TestSubscriber<List<CryptoCompareCoinDetail>>()
        coinRepository.getAllCoinList().subscribe(testSubscriber)
        testSubscriber.await(10, TimeUnit.SECONDS)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        var result = testSubscriber.values()[0]
        assertTrue(result.isNotEmpty())
        testSubscriber = TestSubscriber()
        coinRepository.getDefaultCoinList().subscribe(testSubscriber)
        testSubscriber.await(10, TimeUnit.SECONDS)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        result = testSubscriber.values()[0]
        assertTrue(result.isNotEmpty())
    }
}
