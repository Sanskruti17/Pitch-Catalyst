package com.example.pitchcatalyst.data

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun getItems(): Flow<List<Item>>
    suspend fun addItem(title: String, body: String)
    suspend fun deleteItem(item: Item)
    suspend fun updateItemCheckedState(item: Item, isChecked: Boolean)
}