package com.example.crypto.crypto.data.networking

import com.example.crypto.core.data.networking.constructUrl
import com.example.crypto.core.data.networking.safeCall
import com.example.crypto.core.domain.util.NetworkError
import com.example.crypto.core.domain.util.Result
import com.example.crypto.core.domain.util.map
import com.example.crypto.crypto.data.mappers.toCoin
import com.example.crypto.crypto.data.networking.dto.CoinHistoryDto
import com.example.crypto.crypto.data.networking.dto.CoinResponseDto
import com.example.crypto.crypto.domain.Coin
import com.example.crypto.crypto.domain.CoinDataSource
import com.example.crypto.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import java.time.ZonedDateTime

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) :CoinDataSource{
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        TODO("Not yet implemented")
    }
    /*  return  safeCall<CoinHistoryDto> {
          httpClient.get(
              urlString = constructUrl("/assets/$coinId/history"),
          )
      }{
          parameter("start", start.toEpochSecond())

      }
    }*/
}