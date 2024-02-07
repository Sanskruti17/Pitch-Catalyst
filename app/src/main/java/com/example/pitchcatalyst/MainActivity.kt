package com.example.pitchcatalyst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pitchcatalyst.data.FirebaseRepository
import com.example.pitchcatalyst.data.ItemRepository
import com.example.pitchcatalyst.ui.screens.ItemViewModel
import com.example.pitchcatalyst.ui.screens.ListScreen
import com.example.pitchcatalyst.ui.theme.PitchCatalystTheme
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FirebaseDatabase
        val firebaseDatabase = FirebaseDatabase.getInstance()

        // Initialize FirebaseRepository with FirebaseDatabase
        val itemRepository: ItemRepository = FirebaseRepository(firebaseDatabase)

        // Create ItemViewModel with the initialized ItemRepository
        val itemViewModel = ItemViewModel(itemRepository)


        setContent {
            PitchCatalystTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListScreen(itemViewModel)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PitchCatalystTheme {

    }
}