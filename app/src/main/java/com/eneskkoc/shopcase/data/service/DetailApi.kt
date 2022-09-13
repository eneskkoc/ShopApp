package com.eneskkoc.shopcase.data.service

import com.eneskkoc.shopcase.data.model.detail.Detail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {
    @Headers(
        "Api-Key: xXspnfUxPzOGKNu90bFAjlOTnMLpN8veiixvEFXUw9I=",
        "Alias-Key: AtS1aPFxlIdVLth6ee2SEETlRxk="
    )
    @GET("products/{id}")
    suspend fun detail(
        @Path("id") id: String,
    ): Response<Detail>
}