package com.eneskkoc.shopcase.data.service

import com.eneskkoc.shopcase.data.model.category.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CategoryApi {
    @Headers(
        "Api-Key: xXspnfUxPzOGKNu90bFAjlOTnMLpN8veiixvEFXUw9I=",
        "Alias-Key: AtS1aPFxlIdVLth6ee2SEETlRxk="
    )
    @GET("categories")
    suspend fun category(): Response<Category>
}