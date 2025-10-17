package common.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun MiniLogo(modifier: Modifier = Modifier) {
    Image(
        imageVector = MiniLogo,
        contentDescription = "MiniLogo",
        modifier = modifier,
    )
}

@Composable
fun FullLogo(modifier: Modifier = Modifier) {
    Image(
        imageVector = FullLogo,
        contentDescription = "FullLogo",
        modifier = modifier,
    )
}

@Composable
fun Bell(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Bell,
        contentDescription = "Bell",
        modifier = modifier,
    )
}

@Composable
fun Search(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Search,
        contentDescription = "Search",
        modifier = modifier,
    )
}

@Composable
fun HorizontalDotsMenu(modifier: Modifier = Modifier, contentDescription: String = "Menu") {
    Icon(
        imageVector = HorizontalDotsMenu,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@Composable
fun DefaultUser(modifier: Modifier = Modifier) {
    Icon(
        imageVector = DefaultUser,
        contentDescription = "User",
        modifier = modifier,
    )
}

@Composable
fun Home(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Home,
        contentDescription = "Home",
        modifier = modifier,
    )
}

@Composable
fun Shorts(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Shorts,
        contentDescription = "Shorts",
        modifier = modifier,
    )
}

@Composable
fun Subscribes(modifier: Modifier = Modifier) {
    Box {
        Icon(
            imageVector = Subscribes,
            contentDescription = "Subscribes",
            modifier = modifier,
        )
        Icon(
            imageVector = SubscribesCenter,
            contentDescription = null,
            modifier = modifier,
            tint = LocalContentColor.current.copy(red = 0.8f, green = 0.8f, blue = 0.8f)
        )
    }
}

@Composable
fun StandardDefaultUser(modifier: Modifier = Modifier) {
    Icon(
        imageVector = StandardDefaultUser,
        contentDescription = "Profile",
        modifier = modifier,
    )
}