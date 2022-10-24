package com.wishes.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.wishes.database.entity.SaldoEntity
import com.wishes.ui.commons.components.Button
import com.wishes.ui.commons.components.Dialog
import com.wishes.ui.commons.components.SwipeDismissSnackbarHost
import com.wishes.ui.commons.components.Wish
import com.wishes.ui.create.CreateDestination
import com.wishes.ui.navigation.WishesNavigationDestination
import com.wishes.ui.receipt.ReceiptDestination
import com.wishes.ui.receipt.ReceiptViewModel
import com.wishes.ui.simulation.SimulationDestination
import com.wishes.util.formatDotToPeriod
import com.wishes.util.isBigDecimal
import java.math.BigDecimal

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeRoute(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    onNavigateToOverview: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val wishes = viewModel.pagingWishes.collectAsLazyPagingItems()
    val saldo = viewModel.stateSaldo.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        onNavigateToDestination,
        onBackClick,
        openDrawer,
        onNavigateToOverview,
        wishes,
        saldo.value,
        viewModel::adicionarSaldo,
        viewModel::subtrairSaldo,
        viewModel::criarSaldo,
        viewModel::clearMessage,
        uiState.value
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    onNavigateToOverview: (Long) -> Unit,
    wishes: LazyPagingItems<Wish>,
    saldo: BigDecimal? = BigDecimal.ZERO,
    adicionarSaldo: (saldo: BigDecimal) -> Unit,
    subtrairSaldo: (saldo: BigDecimal) -> Unit,
    criarSaldo: (saldo: SaldoEntity) -> Unit,
    deleteMessage: () -> Unit,
    uiState: UiState
) {
    var expanded by remember { mutableStateOf(false) }
    var adicionarExpanded by remember { mutableStateOf(false) }
    var subtrairExpanded by remember { mutableStateOf(false) }
    var showSaldo by remember { mutableStateOf(true) }
    var novoSaldo by remember { mutableStateOf("0") }
    val snackbarHostState = remember { SnackbarHostState() }

    fun setNovoSaldo(value: String) { novoSaldo = value }

    uiState.message?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message.message)
            deleteMessage()
        }
    }

    uiState.temSaldo?.let { temSaldo ->
        if(temSaldo) subtrairExpanded = false
    }

    Scaffold(
        contentColor = MaterialTheme.colors.secondary,
        containerColor = MaterialTheme.colors.primary,
        snackbarHost = {
            SwipeDismissSnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 12.dp, 8.dp, 0.dp)
                    ){
                        Icon(
                            painter = painterResource(R.drawable.ic_logo),
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary,
                            modifier = Modifier.requiredHeight(38.dp)
                        )
                    }
                    if (adicionarExpanded)
                        Dialog(
                            title = "Adicionar saldo",
                            value = novoSaldo,
                            onValueChange = { isBigDecimal(it, ::setNovoSaldo) },
                            cancelText = "Voltar",
                            confirmText = "Confirmar",
                            confirmAction = {
                                if (saldo != null) {
                                    adicionarSaldo(novoSaldo.toBigDecimal())
                                } else {
                                    criarSaldo(SaldoEntity(0, novoSaldo.toBigDecimal()))
                                }
                                novoSaldo = "0"
                            },
                            onDismiss = { adicionarExpanded = false }
                        )
                    if (subtrairExpanded)
                        Dialog(
                            title = "Subtrair saldo",
                            value = novoSaldo,
                            onValueChange = { isBigDecimal(it, ::setNovoSaldo) },
                            cancelText = "Voltar",
                            confirmText = "Confirmar",
                            confirmAction = {
                                subtrairSaldo(novoSaldo.toBigDecimal())
                                novoSaldo = "0"
                            },
                            onDismiss = { subtrairExpanded = false }
                        )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 10.dp, 10.dp, 20.dp),
                        thickness = 1.dp,
                        color = Color.Black.copy(.2f),
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 4.dp, 16.dp, 20.dp)
                    ){
                        Column {
                            Text(
                                "Saldo atual",
                                color = MaterialTheme.colors.secondary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                            )
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                            ){
                                Text(
                                    "R$",
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                )
                                Text(
                                    formatDotToPeriod("${saldo ?: "0.00"}"),
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier =
                                    if (showSaldo){
                                        Modifier
                                            .padding(start = 10.dp)
                                    } else {
                                        Modifier
                                            .requiredHeight(2.dp)
                                            .padding(start = 10.dp)
                                            .background(
                                                MaterialTheme.colors.secondary,
                                                RoundedCornerShape(100)
                                            )
                                    }
                                )
                                IconButton(
                                    onClick = { showSaldo = !showSaldo },
                                    modifier = Modifier.requiredHeight(18.dp)
                                ) {
                                    Icon(
                                        imageVector =
                                        if (showSaldo) Icons.Rounded.VisibilityOff
                                        else Icons.Rounded.Visibility,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.secondary,
                                        modifier = Modifier.requiredSize(24.dp)
                                    )
                                }
                            }
                        }
                        Button(
                            text = "Extrato",
                            onClick = { onNavigateToDestination(ReceiptDestination, ReceiptDestination.route) },
                            variant = "translucent",
                            modifier = Modifier.requiredWidth(148.dp)
                        )
                    }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 16.dp)
                    ){
                        item {
                            Button(
                                text = "Criar wish",
                                onClick = { onNavigateToDestination(CreateDestination, CreateDestination.route) },
                                variant = "translucent",
                                box = true,
                                painterIcon = painterResource(R.drawable.ic_star),
                                modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                            )
                            Button(
                                text = "Adicionar saldo",
                                onClick = { adicionarExpanded = !adicionarExpanded },
                                variant = "translucent",
                                box = true,
                                painterIcon = painterResource(R.drawable.ic_more_cash),
                                modifier = Modifier.padding(8.dp, 0.dp)
                            )
                            if (saldo != null)
                                Button(
                                    text = "Subtrair saldo",
                                    onClick = { subtrairExpanded = !subtrairExpanded },
                                    variant = "translucent",
                                    box = true,
                                    painterIcon = painterResource(R.drawable.ic_minus_cash),
                                    modifier = Modifier.padding(8.dp, 0.dp)
                                )
                            Button(
                                text = "Simular gastos",
                                onClick = { onNavigateToDestination(SimulationDestination, SimulationDestination.route) },
                                variant = "translucent",
                                box = true,
                                imageVectorIcon = Icons.Rounded.List,
                                modifier = Modifier.padding(8.dp, 0.dp)
                            )
                            Button(
                                text = "Configurar salário",
                                onClick = {  },
                                variant = "translucent",
                                box = true,
                                imageVectorIcon = Icons.Rounded.List,
                                modifier = Modifier.padding(start = 8.dp, end = 16.dp)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .requiredHeight(16.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                ){
                    Box(
                        modifier = Modifier
                            .requiredHeight(16.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.background, RoundedCornerShape(80, 80, 0, 0))
                    )
                }
            }
            items(wishes) { obj ->
                obj?.let {
                    if (!obj.comprado){
                        Wish(
                            obj,
                            { onNavigateToOverview(obj.id) },
                            false
                        )
                    }
                }
            }
        }
    }
}
