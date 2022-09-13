package com.eneskkoc.shopcase.data.service

import com.eneskkoc.shopcase.data.model.product.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ProductApi {
    @Headers(
        "Api-Key: xXspnfUxPzOGKNu90bFAjlOTnMLpN8veiixvEFXUw9I=",
        "Alias-Key: AtS1aPFxlIdVLth6ee2SEETlRxk="
    )
    @GET("products/advanced-filtered?")
    suspend fun product(
        @Query("categoryId") categoryId: String,
        @Query("sort") sort: String,
    ): Response<Product>
}