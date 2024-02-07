package com.example.pitchcatalyst.ui.screens
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pitchcatalyst.data.Item
import com.example.pitchcatalyst.data.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository): ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items


    fun loadItems() {
        viewModelScope.launch {
            itemRepository.getItems().collect { items ->
                _items.value = items
            }
        }
    }

    fun addItem(title: String, body: String) {
        viewModelScope.launch {
            itemRepository.addItem(title, body)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemRepository.deleteItem(item)
        }
    }

    fun updateItemCheckedState(item: Item, isChecked: Boolean) {
        viewModelScope.launch {
            itemRepository.updateItemCheckedState(item, isChecked)
        }
    }
}