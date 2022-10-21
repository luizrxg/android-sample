package com.wishes.ui.receipt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import com.wishes.R
import com.wishes.data.model.Wish
import com.wishes.database.entity.SaldoEntity
import com.wishes.ui.commons.components.*
import com.wishes.ui.create.CreateDestination
import com.wishes.ui.navigation.WishesNavigationDestination
import com.wishes.util.checkSameDay
import com.wishes.util.formatDayNumberMonthName
import java.time.LocalDateTime
import java.math.BigDecimal

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
                            text = { Text("Todos") },
                            onClick = { selectedItems = items }
                        )
                        DropdownMenuItem(
                            text = { Text("Ultimo Mês") },
                            onClick = { selectedItems = items }
                        )
                    }
                }
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .topPadding()
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
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
