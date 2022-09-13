package com.eneskkoc.shopcase.data.model.detail

data class Category(
    val categoryId: String?,
    val createDate: String?,
    val icon: İcon?,
    val isActive: Boolean?,
    val name: String?,
    val orderIndex: Int?,
    val subCategories: List<Any>?,
    val totalProducts: Int?,
    val updateDate: String?
)