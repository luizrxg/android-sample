package com.wishes.ui.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import com.wishes.R
import com.wishes.database.entity.LinkEntity
import com.wishes.database.entity.WishEntity
import com.wishes.ui.commons.components.*
import com.wishes.ui.navigation.WishesNavigationDestination
import com.wishes.ui.overview.OverviewViewModel
import com.wishes.util.checkHttps
import com.wishes.util.isBigDecimal
import java.math.BigDecimal
import java.time.LocalDateTime

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CreateRoute(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    currentDestination: NavDestination?,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    viewModel: CreateViewModel = hiltViewModel(),
) {
    val id = viewModel.stateId.collectAsStateWithLifecycle()

    CreateScreen(
        onNavigateToDestination,
        onBackClick,
        openDrawer,
        viewModel::criarWish,
        viewModel::criarLinks,
        id.value
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    onNavigateToDestination: (WishesNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    openDrawer: () -> Unit,
    criarWish: (wish: WishEntity) -> Unit,
    criarLinks: (link: List<String?>, id: Long) -> Unit,
    id: Long? = 0
) {
    var nome by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("0") }
    var prioridade by remember { mutableStateOf(0) }
    var link by remember { mutableStateOf("") }
    val links = remember { mutableStateListOf<String?>() }
    val uriHandler = LocalUriHandler.current

    fun setPreco(value: String){ preco = value }

    fun addLink(){
        if (link.isNotEmpty()){
            links.add(link)
            link = ""
        }
    }

    fun create(){
        criarWish(
            WishEntity(
                id = 0,
                nome = nome,
                preco = preco.toBigDecimal(),
                prioridade = prioridade,
                comprado = false,
                data = "${LocalDateTime.now()}"
            )
        )
        criarLinks(links.toList(), id ?: 0)
        onBackClick()
    }

    Scaffold(
        contentColor = MaterialTheme.colors.secondary,
        containerColor = MaterialTheme.colors.onBackground,
        topBar = {
            TopBar(
                "Criar wish",
                onBackClick
            )
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .topPadding()
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Box {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize()
                ){
                    item {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp)
                                .clip(RoundedCornerShape(100)),
                            2.dp,
                            MaterialTheme.colors.onBackground,
                        )
                        Text(
                            "Nome",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 6.dp)
                        )
                        TextField(
                            nome,
                            { nome = it },
                            maxLength = 30
                        )
                        Text(
                            "Preço",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 6.dp)
                        )
                        TextField(
                            preco,
                            { isBigDecimal(it, ::setPreco) },
                            leadingIcon = {
                                Text(
                                    "R$",
                                    style = MaterialTheme.typography.body1,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colors.secondary
                                )
                            },
                            maxLength = 30,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Text(
                            "Prioridade",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 6.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp)
                        ) {
                            RadioOption(
                                text = "Baixa",
                                selected = prioridade == 0,
                                onClick = { prioridade = 0 }
                            )
                            RadioOption(
                                text = "Média",
                                selected = prioridade == 1,
                                onClick = { prioridade = 1 }
                            )
                            RadioOption(
                                text = "Alta",
                                selected = prioridade == 2,
                                onClick = { prioridade = 2 }
                            )
                        }
                        Text(
                            "Links",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 6.dp)
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
                    itemsIndexed(links){ index, link ->
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
                                        .clickable { uriHandler.openUri(checkHttps(link)) }
                                        .padding(16.dp, 12.dp, 12.dp, 12.dp)
                                ){
                                    Text(
                                        link,
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
                                    onClick = { links.removeAt(index) },
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
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .background(MaterialTheme.colors.background)
                ){
                    Button(
                        text = "CRIAR",
                        onClick = { create() },
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
