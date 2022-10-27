package com.wishes.ui.simulation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.wishes.R
import com.wishes.data.model.Wish
import com.wishes.ui.commons.components.SearchBar
import com.wishes.ui.commons.components.TopBar
import com.wishes.ui.commons.components.Wish
import com.wishes.ui.commons.components.topPadding
import com.wishes.ui.navigation.WishesNavigationDestination
import com.wishes.util.formatDotToPeriod
import com.wishes.util.formatToFixed2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SimulationRoute(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    viewModel: SimulationViewModel = hiltViewModel(),
) {
    val wishes = viewModel.pagingWishes.collectAsLazyPagingItems()
    val saldo = viewModel.stateSaldo.collectAsStateWithLifecycle()
    val selectedWishes = viewModel.stateSelectedWishes.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    SimulationScreen(
        onNavigateToDestination,
        onBackClick,
        openDrawer,
        wishes,
        saldo.value,
        selectedWishes.value,
        viewModel::selectWish,
        viewModel::unselectWish,
        uiState.value.total,
        uiState.value.resto,
        viewModel::setSearch
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulationScreen(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    wishes: LazyPagingItems<Wish>,
    saldo: BigDecimal? = BigDecimal.ZERO,
    selectedWishes: List<Wish>,
    selectWish: (Wish) -> Unit,
    unselectWish: (Wish) -> Unit,
    total: BigDecimal? = BigDecimal.ZERO,
    resto: BigDecimal? = BigDecimal.ZERO,
    setSearch: (String) -> Unit,
) {
    var search by remember { mutableStateOf("") }
    var slideUp by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    scope.launch {
        delay(50)
        slideUp = true
    }

    fun handleSelect(wish: Wish) {
        if (selectedWishes.contains(wish))
            unselectWish(wish)
        else
            selectWish(wish)
    }

    Scaffold(
        contentColor = MaterialTheme.colors.secondary,
        containerColor = MaterialTheme.colors.primary,
        topBar = {
            TopBar(
                "Simulação",
                onBackClick
            )
        },
    ) {
        AnimatedVisibility(
            visible = slideUp,
            enter = slideInVertically(initialOffsetY = { it / 2 }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .topPadding()
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background, RoundedCornerShape(5, 5, 0, 0))
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        Text(
                            "Selecione os itens",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 16.dp, 0.dp, 0.dp)
                        )
                        SearchBar(
                            value = search,
                            onValueChange = {
                                setSearch(it)
                                search = it
                            },
                            onClear = {
                                setSearch("")
                                search = ""
                            },
                            placeholder = "Pesquisar...",
                            variant = "empty",
                            modifier = Modifier
                                .padding(8.dp, 0.dp, 8.dp, 8.dp)
                        )
                    }
                    items(wishes) { obj ->
                        obj?.let {
                            if (!obj.comprado) {
                                Wish(
                                    obj,
                                    { handleSelect(obj) },
                                    selectedWishes.contains(obj)
                                )
                            }
                        }
                    }
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    formatDotToPeriod("Saldo"),
                                    color = MaterialTheme.colors.secondary,
                                    fontSize = 16.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.padding()
                                )
                                Text(
                                    formatDotToPeriod("R$ ${formatToFixed2(saldo ?: BigDecimal.ZERO)}"),
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.padding()
                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 8.dp)
                                    .clip(RoundedCornerShape(100)),
                                2.dp,
                                MaterialTheme.colors.onBackground,
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    formatDotToPeriod("Total"),
                                    color = MaterialTheme.colors.secondary,
                                    fontSize = 16.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.padding()
                                )
                                Text(
                                    formatDotToPeriod("R$ ${formatToFixed2(total ?: BigDecimal.ZERO)}"),
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.padding()
                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 8.dp)
                                    .clip(RoundedCornerShape(100)),
                                2.dp,
                                MaterialTheme.colors.onBackground,
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    formatDotToPeriod("Resto"),
                                    color = MaterialTheme.colors.secondary,
                                    fontSize = 16.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.padding()
                                )
                                Text(
                                    formatDotToPeriod("R$ ${formatToFixed2(resto ?: BigDecimal.ZERO)}"),
                                    color =
                                    if ((resto ?: BigDecimal.ZERO) > BigDecimal.ZERO)
                                        MaterialTheme.colors.secondary
                                    else
                                        colorResource(R.color.red),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.padding()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
