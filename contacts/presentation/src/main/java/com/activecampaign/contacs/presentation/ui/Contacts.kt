package com.activecampaign.contacs.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.activecampaign.contacs.presentation.viewmodel.ContactsViewModel


@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel<ContactsViewModel>(),
) {
    val state by viewModel.viewState.collectAsState()
    Text(state.contacts.toString())

}
