package com.example.task.adapter.inbound.rest

import com.example.task.adapter.outbound.retrofit.RetrofitFakeStoreApi
import com.example.task.adapter.outbound.retrofit.StoreApiExecutor
import com.example.task.common.response.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/fake-store")
class StoreApiRestController(
    private val storeApiExecutor: StoreApiExecutor,
) {
    @GetMapping("/products")
    suspend fun retrieveProducts(): Response.Success<List<RetrofitFakeStoreApi.ResponseProductDto>> =
        Response.success(data = storeApiExecutor.fetchProducts())
}
