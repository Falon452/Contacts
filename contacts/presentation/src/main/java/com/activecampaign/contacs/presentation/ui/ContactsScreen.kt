package com.activecampaign.contacs.presentation.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.activecampaign.contacs.presentation.R
import com.activecampaign.contacs.presentation.model.ContactsContent.CONTACTS
import com.activecampaign.contacs.presentation.model.ContactsContent.EMPTY_CONTACTS
import com.activecampaign.contacs.presentation.model.ContactsContent.ERROR_GETTING_CONTACTS
import com.activecampaign.contacs.presentation.viewmodel.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel = hiltViewModel<ContactsViewModel>(),
) {
    val state by viewModel.viewState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(lifecycleOwner.lifecycle, viewModel.events) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                viewModel.onEvent(event) { msg ->
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    PullToRefreshBox(
        modifier = modifier,
        state = pullToRefreshState,
        isRefreshing = state.isSpinnerVisible,
        onRefresh = viewModel::onRefresh,
    ) {
        AnimatedContent(state.contactsContent) { content ->
            when (content) {
                CONTACTS ->
                    ContactsContent(state)
                EMPTY_CONTACTS ->
                    ErrorState(
                        onRefresh = viewModel::onRefresh,
                        painter = painterResource(R.drawable.search),
                        reasonText = stringResource(R.string.no_results),
                    )
                ERROR_GETTING_CONTACTS ->
                    ErrorState(
                        onRefresh = viewModel::onRefresh,
                        painter = painterResource(R.drawable.no_wifi_icon),
                        reasonText = stringResource(R.string.something_went_wrong),
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactsScreenPreview() {
    ContactsScreen()
}
