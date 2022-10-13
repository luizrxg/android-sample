package com.wishes.ui.overview

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.wishes.R
import com.wishes.data.model.Link
import com.wishes.data.model.Wish
import com.wishes.database.entity.LinkEntity
import com.wishes.ui.commons.components.*
import com.wishes.ui.home.HomeDestination
import com.wishes.ui.navigation.WishesNavigationDestination

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun OverviewRoute(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    viewModel: OverviewViewModel = hiltViewModel(),
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val links = viewModel.pagingLinks.collectAsLazyPagingItems()
    val comprarState = viewModel.stateComprar.collectAsStateWithLifecycle()

    OverviewScreen(
        onNavigateToDestination,
        onBackClick,
        openDrawer,
        uiState.value.wish,
        links,
        { viewModel.comprarWish() },
        { viewModel.deleteWish() },
        viewModel::criarLink,
        viewModel::deleteLink,
        viewModel::clearMessage,
        comprarState.value
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    wish: Wish?,
    links: LazyPagingItems<Link>,
    comprar: () -> Unit,
    delete: () -> Unit,
    criarLink: (link: LinkEntity) -> Unit,
    deleteLink: (link: LinkEntity) -> Unit,
    deleteMessage: () -> Unit,
    comprarState: ComprarUiState,
) {
    val context = LocalContext.current
    var comprarExpanded by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }
    var deleteExpanded by remember { mutableStateOf(false) }
    var link by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    comprarState.message?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message.message)
            deleteMessage()
        }
    }

    comprarState.temSaldo?.let { saldo ->
        if(saldo) onBackClick()
    }

    fun addLink(){
        if (link.isNotEmpty()){
            criarLink(
                LinkEntity(
                    0,
                    wish!!.id,
                    link
                )
            )
            link = ""
        }
    }

    Scaffold(
        contentColor = MaterialTheme.colors.secondary,
        containerColor = MaterialTheme.colors.primary,
        topBar = {
            TopBar(
                wish?.nome ?: "",
                onBackClick
            ) {
                Box {
                    IconButton(
                        onClick = { menuExpanded = !menuExpanded }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary,
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier
                            .background(MaterialTheme.colors.secondary)
                            .clip(MaterialTheme.shapes.small)
                            .align(Alignment.CenterStart)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Deletar item") },
                            onClick = { deleteExpanded = !deleteExpanded }
                        )
                    }
                }
            }
        },
        snackbarHost = {
            SwipeDismissSnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) {
        wish?.let {
            Box{
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .topPadding()
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    item{
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp)
                                .clip(RoundedCornerShape(100)),
                            2.dp,
                            colorResource(R.color.dark),
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                "R$ ${wish.preco}",
                                color = MaterialTheme.colors.primary,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.widthIn(max = 150.dp)
                            )
                            Icon(
                                painter = painterResource(
                                    when (wish.prioridade){
                                        0 -> { R.drawable.ic_arrow }
                                        1 -> { R.drawable.ic_arrow2 }
                                        2 -> { R.drawable.ic_arrow3 }
                                        3 -> { R.drawable.ic_more_cash }
                                        4 -> { R.drawable.ic_minus_cash }
                                        else -> { 0 }
                                    }
                                ),
                                contentDescription = null,
                                tint =
                                    when (wish.prioridade){
                                        0 -> { MaterialTheme.colors.primary }
                                        1 -> { MaterialTheme.colors.primary }
                                        2 -> { MaterialTheme.colors.background }
                                        3 -> { colorResource(R.color.green) }
                                        4 -> { colorResource(R.color.red) }
                                        else -> { MaterialTheme.colors.primary }
                                    },
                                modifier = Modifier
                                    .requiredSize(38.dp)
                                    .background(
                                        when (wish.prioridade){
                                            0 -> { Color.Transparent }
                                            1 -> { Color.Transparent }
                                            2 -> { MaterialTheme.colors.primary }
                                            3 -> { Color.Transparent }
                                            4 -> { Color.Transparent }
                                            else -> { Color.Transparent }
                                        },
                                        RoundedCornerShape(100)
                                    )
                                    .border(
                                        2.dp,
                                        when (wish.prioridade){
                                            0 -> { Color.Transparent }
                                            1 -> { MaterialTheme.colors.primary }
                                            2 -> { MaterialTheme.colors.primary }
                                            3 -> { Color.Transparent }
                                            4 -> { Color.Transparent }
                                            else -> { Color.Transparent }
                                        },
                                        RoundedCornerShape(100)
                                    )
                                    .padding(8.dp)
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp)
                                .clip(RoundedCornerShape(100)),
                            2.dp,
                            colorResource(R.color.dark),
                        )
                        if (wish.prioridade < 3)
                            Text(
                                "Links",
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 6.dp)
                            )

                        if (comprarExpanded)
                            ConfirmDialog(
                                title = "Deseja marcar como comprado ?",
                                cancelText = "Voltar",
                                confirmText = "Confirmar",
                                confirmAction = { comprar() },
                                onDismiss = { comprarExpanded = false }
                            )

                        if (deleteExpanded)
                            ConfirmDialog(
                                title = "Deseja deletar este item ?",
                                cancelText = "Cancelar",
                                confirmText = "Deletar",
                                confirmAction = {
                                    delete()
                                    onBackClick()
                                },
                                onDismiss = { deleteExpanded = false }
                            )
                        if (wish.prioridade < 3)
                        TextField(
                            link,
                            { link = it },
                            trailingIcon = {
                                IconButton(
                                    onClick = { addLink() }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Add,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.secondary,
                                    )
                                }
                            },
                            keyboardActions = KeyboardActions(
                                onDone = { addLink() }
                            )
                        )
                    }
                    items(links){ link ->
                        link?.let {
                            val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(link.link)) }

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(16.dp, 0.dp)
                            ){
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth(.9f)
                                        .padding(0.dp, 4.dp)
                                        .background(
                                            colorResource(R.color.dark),
                                            MaterialTheme.shapes.small
                                        )
                                        .clip(MaterialTheme.shapes.small)
                                        .clickable { context.startActivity(intent) }
                                        .padding(16.dp, 12.dp, 12.dp, 12.dp)
                                ){
                                    Text(
                                        link.link,
                                        textAlign = TextAlign.Start,
                                        style = MaterialTheme.typography.body1,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                        modifier = Modifier.widthIn(max = 150.dp)
                                    )
                                    Icon(
                                        imageVector = Icons.Rounded.OpenInNew,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.secondary,
                                        modifier = Modifier.requiredSize(24.dp)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        deleteLink(
                                            LinkEntity(
                                                link.id,
                                                link.id_wish,
                                                link.link
                                            )
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Remove,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.secondary,
                                        modifier = Modifier
                                            .requiredSize(32.dp)
                                            .padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                if (!wish.comprado)
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .background(MaterialTheme.colors.background)
                    ){
                        Button(
                            text = "COMPRAR" ,
                            onClick = { comprarExpanded = true },
                            variant = "filled",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 16.dp)
                                .requiredHeight(48.dp)
                        )
                    }
            }
        }
    }
}
