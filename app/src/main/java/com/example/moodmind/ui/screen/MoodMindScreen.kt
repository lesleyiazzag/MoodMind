package com.example.moodmind.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.example.moodmind.data.MoodItem

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodmind.data.MoodCategory
import com.example.moodmind.ui.theme.Kikyoiru
import kotlinx.coroutines.launch
import java.util.Date
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.moodmind.R
import com.example.moodmind.ui.navigation.SummaryScreenRoute
import com.example.moodmind.ui.theme.Cream
import com.example.moodmind.ui.theme.Thistle
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodMindScreen(
    viewModel: MoodMindViewModel = hiltViewModel(),
    onInfoClicked: (Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val moodmindList by viewModel.getAllEntryList().collectAsState(emptyList())

    var moodmindTitle by rememberSaveable { mutableStateOf("") }

    var showMoodDialog by rememberSaveable { mutableStateOf(false) }

    var itemToEdit: MoodItem? by rememberSaveable {
        mutableStateOf(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.top_bar_title),
                        fontFamily = FontFamily.Monospace,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Kikyoiru,
                    titleContentColor = White,
                    navigationIconContentColor = White,
                    actionIconContentColor = White
                ),
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.clearAllEntries()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.delete_all)
                        )
                    }

                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                val allEntries = viewModel.getAllEntryNum()
                                val sadEntries = viewModel.getSadItemsNum()
                                val happyEntries = viewModel.getHappyItemsNum()
                                val angryEntries = viewModel.getAngryItemsNum()
                                val surprisedEntries = viewModel.getSurprisedItemsNum()
                                val disgustedEntries = viewModel.getDisgustedItemsNum()
                                val motivatedEntries = viewModel.getMotivatedItemsNum()
                                val unmotivatedEntries = viewModel.getUnmotivatedItemsNum()
                                val apatheticEntries = viewModel.getApatheticItemsNum()
                                val stressedEntries = viewModel.getStressedItemsNum()
                                val creativeEntries = viewModel.getCreativeItemsNum()
                                val productiveEntries = viewModel.getProductiveItemsNum()
                                val unproductiveEntries = viewModel.getUnproductiveItemsNum()


                                onInfoClicked(
                                    allEntries, sadEntries, happyEntries, angryEntries, surprisedEntries, disgustedEntries, motivatedEntries, unmotivatedEntries, apatheticEntries, stressedEntries, creativeEntries, productiveEntries, unproductiveEntries
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(id = R.string.summary)
                        )
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    itemToEdit = null
                    showMoodDialog = true
                },
                containerColor = Kikyoiru
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = stringResource(id = R.string.add_item),
                    tint = White
                )
            }
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(color = Cream)) {
                if (showMoodDialog) {
                    MoodDialog(
                        viewModel = viewModel,
                        itemToEdit = itemToEdit,
                        onCancel = {
                            showMoodDialog = false
                        }
                    )
                }

                if (moodmindList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.nothing_to_say),
                            fontSize = 24.sp,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(moodmindList) { moodItem ->
                            MoodCard(
                                moodItem = moodItem,
                                onDelete = {
                                    viewModel.removeEntryItem(moodItem)
                                },
                                onEdit = { todoItemToEdit ->
                                    itemToEdit = todoItemToEdit
                                    showMoodDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MoodCard(
    moodItem: MoodItem,
    onDelete: () -> Unit,
    onEdit: (MoodItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Thistle,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = moodItem.category.getIcon()),
                    contentDescription = stringResource(id = R.string.category_icon_desc),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )

                Text(
                    moodItem.title,
                    fontFamily = FontFamily.Monospace,
                    color = Kikyoiru,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.delete),
                    modifier = Modifier.clickable { onDelete() },
                    tint = Kikyoiru
                )

                Icon(
                    imageVector = Icons.Filled.Build,
                    contentDescription = stringResource(id = R.string.edit),
                    modifier = Modifier.clickable { onEdit(moodItem) },
                    tint = Kikyoiru
                )

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = stringResource(id = R.string.expand_collapse)
                    )
                }

            }
            Text(
                moodItem.createDate,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Kikyoiru
                )
            )
            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(color = Cream, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = moodItem.description,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily.Monospace,
                            color = Color.Black
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun MoodDialog(
    viewModel: MoodMindViewModel,
    itemToEdit: MoodItem? = null,
    onCancel: () -> Unit
) {
    var selectedCategory by rememberSaveable {
        mutableStateOf(itemToEdit?.category ?: MoodCategory.Sad)
    }

    var moodTitle by remember {
        mutableStateOf(
            itemToEdit?.title ?: ""
        )
    }
    var moodDesc by remember {
        mutableStateOf(
            itemToEdit?.description ?: ""
        )
    }

    var titleError by remember { mutableStateOf(false) }
    var descError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = {
        onCancel()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    if (itemToEdit == null) stringResource(id = R.string.new_entry) else stringResource(id = R.string.edit_entry),
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(id = R.string.entry_title)) },
                    value = moodTitle,
                    onValueChange = {
                        moodTitle = it
                        if (titleError) titleError = false
                    },
                    isError = titleError
                )
                if (titleError) {
                    Text(
                        text = stringResource(id = R.string.title_error),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(id = R.string.journal_entry)) },
                    value = moodDesc,
                    onValueChange = {
                        moodDesc = it
                        if (descError) descError = false
                    },
                    isError = descError
                )
                if (descError) {
                    Text(
                        text = stringResource(id = R.string.journal_error),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
                SpinnerSample(
                    list = MoodCategory.entries.map { it.name },
                    preselected = selectedCategory.name,
                    onSelectionChanged = { categoryName ->
                        selectedCategory = MoodCategory.valueOf(categoryName)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { onCancel() }
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = {
                        var hasError = false

                        if (moodTitle.isBlank()) {
                            titleError = true
                            hasError = true
                        }

                        if (moodDesc.isBlank()) {
                            descError = true
                            hasError = true
                        }

                        if (hasError) return@TextButton

                        val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                        val formattedDate = formatter.format(Date())

                        if (itemToEdit == null) {
                            viewModel.addEntryList(
                                MoodItem(
                                    id = 0,
                                    title = moodTitle,
                                    description = moodDesc,
                                    createDate = formattedDate,
                                    category = selectedCategory
                                )
                            )
                        } else {

                            val editedTodo = itemToEdit.copy(
                                title = moodTitle,
                                description = moodDesc,
                                createDate = formattedDate,
                                category = selectedCategory
                            )
                            viewModel.editEntryItem(editedTodo)
                        }

                        onCancel()
                    }) {
                        Text(stringResource(id = R.string.save))
                    }
                }
            }
        }
    }
}

@Composable
fun SpinnerSample(
    list: List<String>,
    preselected: String,
    onSelectionChanged: (myData: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = modifier.clickable {
            expanded = !expanded
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = selected,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Icon(Icons.Outlined.ArrowDropDown, null, modifier =
            Modifier.padding(8.dp))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = listEntry,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Start)
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(3000L)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.calm),
            contentDescription = stringResource(id = R.string.app_logo_desc),
            modifier = Modifier
                .size(280.dp)
        )
    }
}

