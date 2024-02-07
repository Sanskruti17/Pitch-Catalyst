package com.example.pitchcatalyst.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pitchcatalyst.data.Item

@Composable
fun ListScreen(itemViewModel: ItemViewModel) {
    var items by remember { mutableStateOf(emptyList<Item>()) }


    LaunchedEffect(key1 = itemViewModel) {
        itemViewModel.loadItems()
    }


    // Observe changes to the items list
    val itemsState by itemViewModel.items.collectAsState()
    items = itemsState

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.weight(1f)){
            TodoList(
                items = items,
                onCheckedChange = { item, isChecked ->
                    if (isChecked) {
                        itemViewModel.deleteItem(item)
                    } else {
                        itemViewModel.updateItemCheckedState(item, isChecked)
                    }
                }
            )
        }
        AddItemScreen(itemViewModel)
    }
}

@Composable
fun TodoList(
    items: List<Item>,
    onCheckedChange: (Item, Boolean) -> Unit
) {
    LazyColumn {
        items.forEach { item ->
            item {
                ListItem(
                    item = item,
                    onCheckedChange = { isChecked ->
                        onCheckedChange(item, isChecked)
                    }
                )
            }
        }
    }
}

@Composable
fun ListItem(item: Item, onCheckedChange: (Boolean) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)
        ) {
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Column {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = item.body,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Row (verticalAlignment = Alignment.CenterVertically){
                    Checkbox(
                        checked = item.isChecked,
                        onCheckedChange = onCheckedChange,
                        colors = CheckboxDefaults.colors(Color.Blue),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AddItemScreen(itemViewModel: ItemViewModel) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = body,
                onValueChange = { body = it },
                label = { Text("Body") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    itemViewModel.addItem(title, body)
                    // Clear text fields after adding item
                    title = ""
                    body = ""
                }
            ) {
                Text("Add Item")
            }
        }
    }
}

