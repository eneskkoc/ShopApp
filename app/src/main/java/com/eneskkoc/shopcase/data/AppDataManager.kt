package com.eneskkoc.shopcase.data

import com.eneskkoc.shopcase.data.model.category.Category
import com.eneskkoc.shopcase.data.model.detail.Detail
import com.eneskkoc.shopcase.data.model.product.Product
import com.eneskkoc.shopcase.data.service.CategoryApi
import com.eneskkoc.shopcase.data.service.DetailApi
import com.eneskkoc.shopcase.data.service.ProductApi
import retrofit2.Response
import javax.inject.Inject

class AppDataManager @Inject constructor(private val categoryApi: CategoryApi,private val productApi: ProductApi,private val detailApi: DetailApi) {

    suspend fun api() : Response<Category> {
        return categoryApi.category()
    }

    suspend fun productApi(categoryId:String,sort:String) : Response<Product> {
        return productApi.product(categoryId, sort)
    }

    suspend fun detailApi(id:String) : Response<Detail> {
        return detailApi.detail(id)
    }
}