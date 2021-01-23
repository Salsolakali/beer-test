package com.example.beers.features.beers.repository


import com.example.beers.core.exception.Failure
import com.example.beers.core.functional.Either
import com.example.beers.core.platform.NetworkHandler
import com.example.beers.features.beers.service.BeerEntity
import com.example.beers.features.beers.service.BeersService
import retrofit2.Call

interface BeersRepository {

    fun getBeers(): Either<Failure, List<Beer>>

    fun getBeersFiltered(s: String): Either<Failure, List<Beer>>

    class Network(
        private val networkHandler: NetworkHandler,
        private val beersService: BeersService
    ) : BeersRepository {
        override fun getBeersFiltered(s: String): Either<Failure, List<Beer>> {
            return when (networkHandler.isConnected) {
                true -> request(
                    beersService.getBeersFiltered(s), {
                        val beersList: List<BeerEntity> = it
                        beersList.map {
                            it.toBeer()
                        }

                    }, emptyList()
                )
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

        override fun getBeers(): Either<Failure, List<Beer>> {
            return when (networkHandler.isConnected) {
                true -> request(
                    beersService.getBeers(), {
                        val beersList: List<BeerEntity> = it
                        beersList.map {
                            it.toBeer()
                        }

                    }, emptyList()
                )
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }


        private fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            default: T
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform(response.body() ?: default))
                    false -> Either.Left(Failure.ServerError())
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError())
            }
        }
    }
}
