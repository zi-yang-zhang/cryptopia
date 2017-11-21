package com.cryptopia.android.repository

import com.cryptopia.android.NetworkModule
import com.cryptopia.android.model.local.PricePairs
import com.cryptopia.android.model.remote.CryptoCompareCoinDetail
import com.cryptopia.android.network.CryptoCompareAPI
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
        fun providePriceRepository(api: CryptoCompareAPI): PriceRepository = PriceRepository(api)

        @Provides
        fun provideCoinRepository(api: CryptoCompareAPI): CoinRepository = CoinRepository(api)
    }

    @Singleton()
    @Component(modules = arrayOf(NetworkModule::class, TestModule::class))
    interface TestComponent {
        fun inject(test: PriceRepositoryTest)
    }

    @Inject lateinit var priceRepository: PriceRepository

    @Inject lateinit var coinRepository: CoinRepository

    @Before
    fun setup() {
        DaggerPriceRepositoryTest_TestComponent.builder().build().inject(this)
    }

    @Test
    fun testPriceRepository() {
        val testSubscriber = TestObserver<List<PricePairs>>()
        priceRepository.getPricePairs(listOf("BTC", "ETH"), listOf("USD", "CAD")).subscribe(testSubscriber)
        testSubscriber.await(10, TimeUnit.SECONDS)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        val result = testSubscriber.values()[0]
        assert(result.find { pricePairs -> pricePairs.from == "BTC" } != null)
        assert(result.find { pricePairs -> pricePairs.from == "ETH" } != null)

        assert(result.size == 2)

        assert(result[0].pairs.size == 2)
        assert(result[1].pairs.size == 2)

        assert(result[0].pairs.find { pricePairs -> pricePairs.to == "USD" } != null)
        assert(result[0].pairs.find { pricePairs -> pricePairs.to == "CAD" } != null)

        assert(result[1].pairs.find { pricePairs -> pricePairs.to == "USD" } != null)
        assert(result[1].pairs.find { pricePairs -> pricePairs.to == "CAD" } != null)
    }


    @Test
    fun testGetCoinList() {
        var testSubscriber = TestObserver<List<CryptoCompareCoinDetail>>()
        coinRepository.getAllCoinList().subscribe(testSubscriber)
        testSubscriber.await(10, TimeUnit.SECONDS)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        var result = testSubscriber.values()[0]
        assert(result.isNotEmpty())
        testSubscriber = TestObserver()
        coinRepository.getDefaultCoinList().subscribe(testSubscriber)
        testSubscriber.await(10, TimeUnit.SECONDS)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        result = testSubscriber.values()[0]
        assert(result.isNotEmpty())
    }
}
