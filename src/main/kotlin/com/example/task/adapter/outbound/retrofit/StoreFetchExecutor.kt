package com.example.task.adapter.outbound.retrofit

import org.springframework.stereotype.Component
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitFakeStoreApi {
    @GET("/products")
    suspend fun callProducts(): Response<List<ResponseProductDto>>

    data class ResponseProductDto(
        val id: Long,
        val title: String,
        val price: String,
        val description: String,
        val category: String,
        val image: String,
        val rating: ResponseProductRatingDto,
    )

    data class ResponseProductRatingDto(
        val rate: Double,
        val count: Long,
    )
}

@Component
class StoreApiExecutor(private val retrofitFakeStoreApi: RetrofitFakeStoreApi) {
    suspend fun fetchProducts(): List<RetrofitFakeStoreApi.ResponseProductDto> {
        return retrofitFakeStoreApi.callProducts().body() ?: listOf()
    }
}
