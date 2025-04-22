package com.activecampaign.contacs.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.activecampaign.contacs.presentation.R
import com.activecampaign.contacs.presentation.model.ContactsContentState.CONTACTS
import com.activecampaign.contacs.presentation.model.ContactsContentState.EMPTY_CONTACTS
import com.activecampaign.contacs.presentation.model.ContactsContentState.ERROR_GETTING_CONTACTS
import com.activecampaign.contacs.presentation.model.ContactsViewState
import com.activecampaign.theme.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsContent(
    state: ContactsViewState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier,
        state = pullToRefreshState,
        isRefreshing = state.isSpinnerVisible,
        onRefresh = onRefresh,
    ) {
        AnimatedContent(state.contactsContentState) { content ->
            when (content) {
                CONTACTS ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (state.contactItemRows.isEmpty()) {
                            items(50) { _ ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    repeat(2) { _ ->
                                        ShimmerPlaceholder(
                                            Modifier
                                                .weight(1f)
                                                .wrapContentHeight()
                                        )
                                    }
                                }
                            }
                        } else {
                            items(state.contactItemRows) { row ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    row.forEachIndexed { ix, contactItem ->
                                        ContactCard(
                                            contactItem = contactItem,
                                            isRight = ix == 1,
                                            modifier = Modifier
                                                .weight(1f)
                                                .wrapContentHeight()
                                        )
                                    }
                                }
                            }
                        }
                    }
                EMPTY_CONTACTS ->
                    ErrorState(
                        onRefresh = onRefresh,
                        painter = painterResource(R.drawable.search),
                        reasonText = stringResource(R.string.no_results),
                    )
                ERROR_GETTING_CONTACTS ->
                    ErrorState(
                        onRefresh = onRefresh,
                        painter = painterResource(R.drawable.no_wifi_icon),
                        reasonText = stringResource(R.string.something_went_wrong),
                    )
            }
        }
    }
}


@Preview(showBackground = true, name = "Empty Contacts")
@Composable
fun ContactsContentEmptyPreview() {
    AppTheme {
        ContactsContent(
            state = ContactsViewState(
                contactItemRows = emptyList(),
                contactsContentState = EMPTY_CONTACTS,
                isSpinnerVisible = false
            ),
            onRefresh = {},
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun ContactsContentErrorPreview() {
    AppTheme {
        ContactsContent(
            state = ContactsViewState(
                contactItemRows = emptyList(),
                contactsContentState = ERROR_GETTING_CONTACTS,
                isSpinnerVisible = false
            ),
            onRefresh = {},
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun ContactsContentLoadingPreview() {
    AppTheme {
        ContactsContent(
            state = ContactsViewState(
                contactItemRows = emptyList(),
                contactsContentState = CONTACTS,
                isSpinnerVisible = true
            ),
            onRefresh = {},
            modifier = Modifier
        )
    }
}
