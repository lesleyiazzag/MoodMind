package com.example.moodmind.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodmind.R
import com.example.moodmind.ui.theme.Cream
import com.example.moodmind.ui.theme.Kikyoiru

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
    viewModel: SummaryViewModel = viewModel(),
) {
    val aiSummary by viewModel.aiSummary.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.summary_bar_title),
                        fontFamily = FontFamily.Monospace,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Kikyoiru,
                    titleContentColor = White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.cactus),
                contentDescription = stringResource(id = R.string.cute_bg),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(220.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                        .border(
                            width = 2.dp,
                            color = Kikyoiru,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .background(
                            color = Cream,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = aiSummary,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Black,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
