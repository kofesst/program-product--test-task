package me.kofesst.testtask.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import me.kofesst.testtask.domain.NumberGenerator
import java.math.BigInteger

private const val PRELOAD_ITEMS_OFFSET = 20

@Composable
fun GeneratorsScreen(
    modifier: Modifier = Modifier,
    columnCount: Int = 2
) {
    val viewModel = hiltViewModel<GeneratorsViewModel>()
    val lazyState = rememberLazyGridState()
    LaunchedEffect(Unit) {
        viewModel.loadGenerators()
        viewModel.increaseNumbersVisibleSize()
        snapshotFlow { lazyState.layoutInfo }
            .filter { it.totalItemsCount > 0 }
            .map {
                val maxItemsToPreload = it.totalItemsCount - 1 - PRELOAD_ITEMS_OFFSET
                val lastVisibleItemIndex = it.visibleItemsInfo.lastOrNull()?.index ?: -1
                maxItemsToPreload <= lastVisibleItemIndex
            }
            .filter { it }
            .collectLatest { viewModel.increaseNumbersVisibleSize() }
    }
    Column(
        modifier = modifier
    ) {
        val generators by viewModel.generators.collectAsState()
        val selectedGenerator by viewModel.selectedGenerator.collectAsState()
        GeneratorsTab(
            modifier = Modifier.fillMaxWidth(),
            generators = generators,
            selectedGenerator = selectedGenerator,
            onSelect = viewModel::selectGenerator
        )
        val numbers by viewModel.numbersFlow.collectAsState(initial = emptyList())
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            state = lazyState,
            columns = GridCells.Fixed(columnCount),
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
            verticalArrangement = Arrangement.spacedBy(space = 4.dp)
        ) {
            itemsIndexed(numbers) { index, number ->
                val rowHighlightMode = (index / columnCount.toDouble()).toInt() % 2 == 0
                val isHighlighted = if (rowHighlightMode) index % 2 == 0 else index % 2 != 0
                NumberElement(
                    modifier = Modifier,
                    number = number,
                    isHighlighted = isHighlighted
                )
            }
        }
    }
}

@Composable
private fun GeneratorsTab(
    modifier: Modifier = Modifier,
    generators: List<NumberGenerator>,
    selectedGenerator: NumberGenerator?,
    onSelect: (NumberGenerator) -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = generators.isNotEmpty(),
        enter = fadeIn() + slideInVertically { -it },
        exit = fadeOut() + slideOutVertically { -it }
    ) {
        val selectedTabIndex = remember(selectedGenerator) {
            generators.indexOf(selectedGenerator).coerceAtLeast(0)
        }
        ScrollableTabRow(
            modifier = modifier,
            selectedTabIndex = selectedTabIndex
        ) {
            generators.forEach { generator ->
                Tab(
                    selected = generator == selectedGenerator,
                    onClick = {
                        if (generators != selectedGenerator) {
                            onSelect(generator)
                        }
                    },
                    text = {
                        Text(
                            text = generator.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun NumberElement(
    modifier: Modifier = Modifier,
    number: BigInteger,
    isHighlighted: Boolean
) {
    Box(
        modifier = modifier
            .defaultMinSize(minHeight = 150.dp)
            .clip(RoundedCornerShape(size = 8.dp))
            .background(
                color = if (isHighlighted) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = number.toString(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}