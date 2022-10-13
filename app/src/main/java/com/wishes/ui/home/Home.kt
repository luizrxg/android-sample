package com.wishes.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.wishes.R
import com.wishes.data.model.Wish
import com.wishes.database.entity.SaldoEntity
import com.wishes.ui.commons.components.Button
import com.wishes.ui.commons.components.InputDialog
import com.wishes.ui.commons.components.Wish
import com.wishes.ui.create.CreateDestination
import com.wishes.ui.navigation.WishesNavigationDestination
import com.wishes.ui.receipt.ReceiptDestination
import com.wishes.ui.receipt.ReceiptViewModel
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

    HomeScreen(
        onNavigateToDestination,
        onBackClick,
        openDrawer,
        onNavigateToOverview,
        wishes,
        saldo.value,
        viewModel::adicionarSaldo,
        viewModel::subtrairSaldo,
        viewModel::criarSaldo
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
    saldo: BigDecimal? = 0.toBigDecimal(),
    adicionarSaldo: (saldo: BigDecimal) -> Unit,
    subtrairSaldo: (saldo: BigDecimal) -> Unit,
    criarSaldo: (saldo: SaldoEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var adicionarExpanded by remember { mutableStateOf(false) }
    var subtrairExpanded by remember { mutableStateOf(false) }
    var novoSaldo by remember { mutableStateOf(saldo ?: 0.toBigDecimal()) }

    Scaffold(
        contentColor = MaterialTheme.colors.secondary,
        containerColor = MaterialTheme.colors.primary,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateToDestination(CreateDestination, CreateDestination.route)
                },
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(100)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary,

                )
            }
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
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
                        .padding(20.dp, 12.dp, 8.dp, 0.dp)
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_logo),
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.requiredHeight(38.dp)
                    )
                    Box {
                        IconButton(
                            onClick = { expanded = !expanded }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = null,
                                tint = MaterialTheme.colors.secondary,
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .background(MaterialTheme.colors.secondary)
                                .clip(MaterialTheme.shapes.small)
                                .align(Alignment.CenterStart)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Adicionar saldo") },
                                onClick = { adicionarExpanded = !adicionarExpanded },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_more_cash),
                                        contentDescription = null,
                                        modifier = Modifier.requiredSize(32.dp)
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Subtrair saldo") },
                                onClick = { subtrairExpanded = !subtrairExpanded },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_minus_cash),
                                        contentDescription = null,
                                        modifier = Modifier.requiredSize(32.dp)
                                    )
                                }
                            )
                        }

                    }
                }
                if (adicionarExpanded)
                InputDialog(
                    title = "Adicionar saldo",
                    value = novoSaldo.toString(),
                    onValueChange = { novoSaldo = if (it.isNotEmpty()) it.toBigDecimal() else 0.toBigDecimal() },
                    cancelText = "Voltar",
                    confirmText = "Confirmar",
                    confirmAction = {
                        if (saldo != null) {
                            adicionarSaldo(novoSaldo)
                        } else {
                            criarSaldo(SaldoEntity(0, novoSaldo))
                        }
                        novoSaldo = 0.toBigDecimal()
                    },
                    onDismiss = { adicionarExpanded = false }
                )
                if (subtrairExpanded)
                InputDialog(
                    title = "Subtrair saldo",
                    value = novoSaldo.toString(),
                    onValueChange = { novoSaldo = if (it.isNotEmpty()) it.toBigDecimal() else 0.toBigDecimal() },
                    cancelText = "Voltar",
                    confirmText = "Confirmar",
                    confirmAction = {
                        if (saldo != null) {
                            subtrairSaldo(novoSaldo)
                        } else {
                            criarSaldo(SaldoEntity(0, novoSaldo))
                        }
                        novoSaldo = 0.toBigDecimal()
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
                        .padding(20.dp, 4.dp, 20.dp, 24.dp)
                ){
                    Column {
                        Text(
                            "Saldo atual",
                            color = MaterialTheme.colors.secondary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                        )
                        Text(
                            "R$ ${saldo ?: 0}",
                            color = MaterialTheme.colors.secondary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                        )
                    }
                    Button(
                        text = "Ver extrato",
                        onClick = { onNavigateToDestination(ReceiptDestination, ReceiptDestination.route) },
                        variant = "translucent"
                    )
                }
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.requiredHeight(16.dp))
                }
                items(wishes) { obj ->
                    obj?.let {
                        if (!obj.comprado){
                            Wish(
                                obj,
                                { onNavigateToOverview(obj.id) }
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.requiredHeight(64.dp))
                }
            }
        }
    }
}
