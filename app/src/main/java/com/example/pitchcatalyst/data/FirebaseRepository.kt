package com.example.pitchcatalyst.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseRepository(private val database: FirebaseDatabase) : ItemRepository{
    private val ItemsRef = database.getReference("Items")

    override suspend fun getItems(): Flow<List<Item>> = callbackFlow{
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(Item::class.java)
                }.filterNotNull()
                trySend(items).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ItemsRef.addValueEventListener(valueEventListener)

        awaitClose { ItemsRef.removeEventListener(valueEventListener) }
    }

    override suspend fun addItem(title: String, body: String) {
        val newItemRef = ItemsRef.push()
        val newItem = Item(newItemRef.key ?: "", title, body, false)
        newItemRef.setValue(newItem).await()
    }

    override suspend fun deleteItem(item: Item) {
        val itemRef = ItemsRef.child(item.id)
        itemRef.removeValue().await()
    }

    override suspend fun updateItemCheckedState(item: Item, isChecked: Boolean) {
        val itemRef = ItemsRef.child(item.id)
        itemRef.child("isChecked").setValue(isChecked).await()
    }
}