package com.android.medicineapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.medicineapp.data.model.Medicine
import com.android.medicineapp.ui.login.UserViewModel
import com.android.medicineapp.utils.UIState
import com.android.medicineapp.utils.getGreetingMessage
import com.android.medicineapp.utils.isDayTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: UserViewModel,
    onLogout: () -> Unit,
    onMedicineSelected: (Medicine) -> Unit
) {
    val user by viewModel.user.collectAsState()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val username = user?.identifier ?: "Guest" // Show guest if no user data is present
    val uiState by homeViewModel.uiState.collectAsState()

    val greeting = getGreetingMessage()
    val isDaytime = isDayTime()

    // Toolbar with the title "Home"
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    // Logout button
                    IconButton(onClick = {
                        viewModel.logout() // Clear session
                        onLogout() // Trigger logout action
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            // Greeting message with sun/moon icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isDaytime) {
                    Icon(
                        imageVector = Icons.Filled.WbSunny,
                        contentDescription = "Sun Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                } else {
                    Icon(
                        imageVector = Icons.Filled.NightsStay,
                        contentDescription = "Moon Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text("Hello, $username! $greeting", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Handle UI States
            when (uiState) {
                is UIState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is UIState.Success -> {
                    // Show medicines when data is loaded
                    LazyColumn {
                        items((uiState as UIState.Success<List<Medicine>>).data) { medicine ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable { onMedicineSelected(medicine) }
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Name: ${medicine.name}")
                                    Text("Dose: ${medicine.dose}")
                                    Text("Strength: ${medicine.strength}")
                                }
                            }
                        }
                    }
                }
                is UIState.Error -> {
                    // Show error message when data fails to load
                    Text(
                        text = (uiState as UIState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}







