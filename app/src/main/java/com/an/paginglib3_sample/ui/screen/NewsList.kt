package com.an.paginglib3_sample.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.an.paginglib3_sample.R
import com.an.paginglib3_sample.ext.getDate
import com.an.paginglib3_sample.ext.getTime
import com.an.paginglib3_sample.model.Article
import com.an.paginglib3_sample.model.Source
import com.an.paginglib3_sample.ui.component.PullToRefreshLazyColumn
import com.an.paginglib3_sample.ui.component.SnackBarAppState

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<Article>,
    isRefreshing: State<Boolean>,
    onRefresh: () -> Unit,
    snackBarAppState: SnackBarAppState
) {
    PullToRefreshLazyColumn(
        items = items,
        content = { NewsItem(it) },
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        onError = { snackBarAppState.showSnackBar("Error fetching data") },
        modifier = modifier
            .fillMaxSize()
            .wrapContentHeight()
    )
}

@Composable
fun LoadingItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(
                Alignment.CenterHorizontally
            )
    ) {
        CircularProgressIndicator(
            trackColor = MaterialTheme.colorScheme.primary,
            color = MaterialTheme.colorScheme.secondaryContainer //progress color
        )
    }
}

@Preview
@Composable
fun LoadingItemPreview() {
    LoadingItem()
}

@Composable
fun NewsItem(article: Article) {
    Card (
        modifier = Modifier
            .wrapContentHeight()
            .padding(bottom = 5.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column (modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(6.dp)
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Column {
                    Text(
                        text = buildAnnotatedString {
                                append(article.author)
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.outline,
                                        fontWeight = FontWeight.Medium
                                    )) {
                                    append(stringResource(id = R.string.item_title))
                                }
                                append(article.title)
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                    Text(
                        text = String.format(
                            stringResource(id = R.string.item_date),
                            article.publishedAt.getDate(), article.publishedAt.getTime()
                        ),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
            Text(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 15.dp, top = 5.dp),
                text = article.description,
                fontSize = 14.sp,
                maxLines = 3,
                textAlign = TextAlign.Justify,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "",
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 12.dp, bottom = 12.dp, start = 4.dp, end = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(
                    onClick = {  }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = String.format(
                            stringResource(id = R.string.item_likes),
                            (1..100).random()
                        ),
                        modifier = Modifier.padding(start = 6.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                TextButton(
                    onClick = {  }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_comment),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = String.format(
                            stringResource(id = R.string.item_comments),
                            (1..1000).random()
                        ),
                        modifier = Modifier.padding(start = 6.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                TextButton(
                    onClick = {  }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = String.format(
                            stringResource(id = R.string.item_shares),
                            (1..1000).random()
                        ),
                        modifier = Modifier.padding(start = 6.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NewsItemPreview() {
    val article = Article(
        source = Source("1", "The Verge"),
        author = "Chris Welch",
        title = "10 Weird, Wild Movies to Stream on Shudder Tonight",
        description = "You can certainly comb through the big streaming services—Netflix, Peacock, Prime Video, Max—to get your horror-movie fix. But if horror is all you want, Shudder should top your list of subscriptions. It has an excellent library spanning many sub-genres, with…",
        url = "https://gizmodo.com/10-weird-wild-horror-movies-streaming-now-on-shudder-1851498003",
        urlToImage = "https://cdn.vox-cdn.com/thumbor/W1ZCya7V4dnIEthMsS1y-utTq9w=/0x0:2040x1360/1200x628/filters:focal(1020x680:1021x681)/cdn.vox-cdn.com/uploads/chorus_asset/file/25453360/DSCF7202_Enhanced_NR_3.jpg",
        publishedAt = "2024-05-21T13:00:00Z",
        content = "The company’s app redesign fumble threatens to steal the thunder from what otherwise looks (and feels) like a strong debut in a new category. There’s so much riding on the new \$450 Sonos Ace headphone…",
    )
    NewsItem(article = article)
}