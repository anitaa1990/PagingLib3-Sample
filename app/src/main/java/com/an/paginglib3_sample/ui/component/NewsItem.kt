package com.an.paginglib3_sample.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.an.paginglib3_sample.model.Article
import com.an.paginglib3_sample.model.Source
import com.an.paginglib3_sample.ui.theme.PagingLib3SampleTheme

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    article: Article
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .widthIn(max = 480.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = .1f))
        ) {
            // Article image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Source name
            Text(
                text = article.source.name.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .56f)
            )
            // Article title
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            // article content
            Text(
                text = article.description,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .84f)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NewsItemPreview() {
    PagingLib3SampleTheme {
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
}