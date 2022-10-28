package com.wishes.ui.receipt

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavDestination
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.wishes.data.model.Wish
import com.wishes.ui.commons.components.ReceiptItem
import com.wishes.ui.commons.components.TopBar
import com.wishes.ui.commons.components.topPadding
import com.wishes.ui.navigation.WishesNavigationDestination
import com.wishes.util.checkSameDay
import com.wishes.util.formatDayNumberMonthName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ReceiptRoute(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    onNavigateToOverview: (Long) -> Unit,
    viewModel: ReceiptViewModel = hiltViewModel(),
) {
    val items = viewModel.pagingItems.collectAsLazyPagingItems()
    val itemsMes = viewModel.pagingItemsUltimoMes.collectAsLazyPagingItems()

    ReceiptScreen(
        onNavigateToDestination,
        onNavigateToOverview,
        onBackClick,
        openDrawer,
        items,
        itemsMes,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScreen(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    onNavigateToOverview: (Long) -> Unit,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    items: LazyPagingItems<Wish>,
    itemsMes: LazyPagingItems<Wish>,
) {
    var filtroExpanded by remember { mutableStateOf(false)}
    var selectedItems by remember { mutableStateOf(items)}
    var slideUp by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    scope.launch {
        delay(50)
        slideUp = true
    }

    Scaffold(
        contentColor = MaterialTheme.colors.secondary,
        containerColor = MaterialTheme.colors.primary,
        topBar = {
            TopBar(
                "Extrato",
                onBackClick
            ) {
                Box {
                    IconButton(
                        onClick = { filtroExpanded = !filtroExpanded }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary,
                        )
                    }
                    DropdownMenu(
                        expanded = filtroExpanded,
                        onDismissRequest = { filtroExpanded = false },
                        modifier = Modifier
                            .background(MaterialTheme.colors.secondary)
                            .clip(MaterialTheme.shapes.small)
                            .align(Alignment.CenterStart)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Todos",
                                    style = MaterialTheme.typography.body1,
                                    color =
                                    if (selectedItems == items){
                                        MaterialTheme.colors.secondaryVariant
                                    } else {
                                        MaterialTheme.colors.background
                                    }
                                )
                            },
                            onClick = { selectedItems = items },
                            colors = MenuDefaults.itemColors(

                            ),
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Ultimo Mês",
                                    style = MaterialTheme.typography.body1,
                                    color =
                                    if (selectedItems == itemsMes){
                                        MaterialTheme.colors.secondaryVariant
                                    } else {
                                        MaterialTheme.colors.background
                                    }
                                )
                            },
                            onClick = { selectedItems = itemsMes }
                        )
                    }
                }
            }
        }
    ) {
        AnimatedVisibility(
            visible = slideUp,
            enter = slideInVertically(initialOffsetY = { it / 2 }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .topPadding()
                    .background(MaterialTheme.colors.background, RoundedCornerShape(5, 5, 0, 0))
                    .fillMaxSize()
            ) {
                itemsIndexed(selectedItems) { index, obj ->
                    obj?.let {
                        val isSameDay = index == 0 || !(checkSameDay(selectedItems[index - 1]!!.data, obj.data))
                        val isNextSameDay =
                            if (index + 1 < selectedItems.itemCount){
                                !(checkSameDay(selectedItems[index + 1]?.data ?: obj.data, obj.data))
                            } else true

                        if (isSameDay)
                            Text(
                                formatDayNumberMonthName(obj.data),
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = if (index == 0) 16.dp else 0.dp)
                            )

                        ReceiptItem(
                            obj,
                            index == 0 || isSameDay,
                            index == selectedItems.itemCount - 1 || isNextSameDay,
                            { onNavigateToOverview(obj.id) },
                        )
                    }
                }
            }
        }
    }
}
