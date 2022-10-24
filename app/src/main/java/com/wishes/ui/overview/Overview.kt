package com.wishes.ui.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
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
import com.wishes.data.model.Link
import com.wishes.data.model.Wish
import com.wishes.database.entity.LinkEntity
import com.wishes.ui.commons.components.*
import com.wishes.ui.navigation.WishesNavigationDestination
import com.wishes.ui.overview.ComprarUiState
import com.wishes.ui.overview.OverviewViewModel
import com.wishes.util.checkHttps
import com.wishes.util.formatDotToPeriod

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
    var comprarExpanded by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }
    var deleteExpanded by remember { mutableStateOf(false) }
    var link by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val uriHandler = LocalUriHandler.current

    comprarState.message?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message.message)
            deleteMessage()
        }
    }

    comprarState.temSaldo?.let { temSaldo ->
        if(temSaldo) onBackClick()
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
                            onClick = { deleteExpanded = !deleteExpanded },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Delete,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .requiredSize(24.dp)
                                )
                            }
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
                        .padding(bottom = 70.dp)
                        .background(MaterialTheme.colors.background, RoundedCornerShape(5, 5, 0, 0))
                ) {
                    item{
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                formatDotToPeriod("R$ ${wish.preco}"),
                                color = MaterialTheme.colors.secondary,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.SemiBold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.widthIn(max = 150.dp)
                            )
                            Priority(
                                level = wish.prioridade,
                                modifier = Modifier
                                    .requiredSize(38.dp)
                                    .padding(8.dp)
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp)
                                .clip(RoundedCornerShape(100)),
                            2.dp,
                            MaterialTheme.colors.onBackground,
                        )
                        Text(
                            "Links",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 6.dp)
                        )

                        if (comprarExpanded)
                            Dialog(
                                title = "Deseja marcar como comprado ?",
                                cancelText = "Voltar",
                                confirmText = "Confirmar",
                                confirmAction = {
                                    comprar()
                                    onBackClick()
                                },
                                onDismiss = { comprarExpanded = false }
                            )

                        if (deleteExpanded)
                            Dialog(
                                title = "Deseja deletar este item ?",
                                cancelText = "Cancelar",
                                confirmText = "Deletar",
                                confirmAction = {
                                    delete()
                                    onBackClick()
                                },
                                onDismiss = { deleteExpanded = false }
                            )
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
                                            MaterialTheme.colors.onBackground,
                                            MaterialTheme.shapes.small
                                        )
                                        .clip(MaterialTheme.shapes.small)
                                        .clickable { uriHandler.openUri(checkHttps(link.link)) }
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
                                    },
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Remove,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.secondary,
                                        modifier = Modifier
                                            .requiredSize(32.dp)
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
                                .padding(16.dp)
                                .requiredHeight(48.dp)
                        )
                    }
            }
        }
    }
}
