package com.aspiresys.testapp

import com.google.gson.annotations.SerializedName

data class ResponseModel(
	@SerializedName("total_count")
	val totalCount: Int? = null,
	val items: List<ItemsItem?>? = null,
	val searchCriteria: SearchCriteria? = null
)

data class MediaGalleryEntriesItem(
	val types: List<String?>? = null,
	val file: String? = null,
	val mediaType: String? = null,
	val disabled: Boolean? = null,
	val id: Int? = null,
	val label: Any? = null,
	val position: Int? = null
)

data class FiltersItem(
	val field: String? = null,
	val conditionType: String? = null,
	val value: String? = null
)

data class SortOrdersItem(
	val field: String? = null,
	val direction: String? = null
)

data class ExtensionAttributes(
	val websiteIds: List<Int?>? = null,
	val categoryLinks: List<CategoryLinksItem?>? = null
)

data class ItemsItem(
	val visibility: Int? = null,
	@SerializedName("type_id")
	val typeId: String? = null,
	val createdAt: String? = null,
	val weight: Any? = null,
	val extensionAttributes: ExtensionAttributes? = null,
	val tierPrices: List<Any?>? = null,
	val customAttributes: List<CustomAttributesItem?>? = null,
	val attributeSetId: Int? = null,
	val updatedAt: String? = null,
	val price: Int? = null,
	@SerializedName("media_gallery_entries")
	val mediaGalleryEntries: List<MediaGalleryEntriesItem?>? = null,
	val name: String? = null,
	val options: List<Any?>? = null,
	val id: Int? = null,
	val sku: String? = null,
	val productLinks: List<Any?>? = null,
	val status: Int? = null
)

data class FilterGroupsItem(
	val filters: List<FiltersItem?>? = null
)

data class CategoryLinksItem(
	val categoryId: String? = null,
	val position: Int? = null
)

data class CustomAttributesItem(
	val value: String? = null,
	val attributeCode: String? = null
)

data class SearchCriteria(
	val sortOrders: List<SortOrdersItem?>? = null,
	val filterGroups: List<FilterGroupsItem?>? = null,
	val currentPage: Int? = null,
	val pageSize: Int? = null
)

