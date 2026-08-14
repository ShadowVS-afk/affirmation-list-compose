package com.example.naturuserinterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.naturuserinterface.data.Datasource
import com.example.naturuserinterface.model.Affirmation
import com.example.naturuserinterface.model.AffirmationType
import com.example.naturuserinterface.ui.theme.NaturUserInterfaceTheme


enum class NatureScreen {
    Start,
    Detail
}


@Composable
fun NatureApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            NatureTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val affirmations = Datasource().loadAffirmation()
        
        NavHost(
            navController = navController,
            startDestination = NatureScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NatureScreen.Start.name) {
                AffirmationList(
                    affirmationList = affirmations,
                    onAffirmationClick = { affirmation ->
                        navController.navigate("${NatureScreen.Detail.name}/${affirmation.stringResourceId}")
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = "${NatureScreen.Detail.name}/{affirmationId}") { backStackEntry ->
                val affirmationId = backStackEntry.arguments?.getString("affirmationId")?.toIntOrNull()
                val selectedAffirmation = affirmations.find { it.stringResourceId == affirmationId }
                
                selectedAffirmation?.let {
                    AffirmationDetailScreen(affirmation = it)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NatureTopAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("AFFIRMATIONS") },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}


@Composable
fun AffirmationList(
    affirmationList: List<Affirmation>,
    onAffirmationClick: (Affirmation) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(affirmationList) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                onAffirmationClick = onAffirmationClick,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun AffirmationCard(
    affirmation: Affirmation,
    onAffirmationClick: (Affirmation) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        onClick = { onAffirmationClick(affirmation) }
    ) {
        when (affirmation.type) {
            AffirmationType.text -> {
                Text(
                    text = stringResource(affirmation.stringResourceId),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            AffirmationType.image -> {
                Image(
                    painter = painterResource(affirmation.imageResourceId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
            }

            AffirmationType.all -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(affirmation.imageResourceId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = stringResource(affirmation.stringResourceId),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AffirmationDetailScreen(
    affirmation: Affirmation,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (affirmation.type) {
            AffirmationType.text -> {
                Text(
                    text = stringResource(affirmation.stringResourceId),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            AffirmationType.image -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    Image(
                        painter = painterResource(affirmation.imageResourceId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            AffirmationType.all -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    Image(
                        painter = painterResource(affirmation.imageResourceId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(affirmation.stringResourceId),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Type: ${affirmation.type.name.uppercase()}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NatureAppPreview() {
    NaturUserInterfaceTheme {
        NatureApp()
    }
}
