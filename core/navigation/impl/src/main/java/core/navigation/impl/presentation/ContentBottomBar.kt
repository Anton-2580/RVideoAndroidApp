package core.navigation.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.ui.ripple.CoolRippleIconButton
import common.ui.theme.Home
import common.ui.theme.Shorts
import common.ui.theme.StandardDefaultUser
import common.ui.theme.Subscribes
import core.navigation.api.domain.AppDestinations
import core.navigation.api.domain.ContentDestinations
import core.navigation.impl.domain.ContentBottomBarOnActions


@Composable
fun ContentBottomBar(
    selectDestination: AppDestinations,
    contentBottomBarOnActions: ContentBottomBarOnActions,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider()
        Spacer(Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CoolRippleIconButton(
                onClick = contentBottomBarOnActions.onHomeAction,
                enabled = selectDestination !== ContentDestinations.HomePage,
            ) { Home() }

            CoolRippleIconButton(
                onClick = contentBottomBarOnActions.onShortsAction,
                enabled = selectDestination !== ContentDestinations.ShortsPage,
            ) { Shorts() }

            CoolRippleIconButton(
                onClick = contentBottomBarOnActions.onSubscribeAction,
                enabled = selectDestination !== ContentDestinations.SubscribesPage,
            ) { Subscribes() }

            CoolRippleIconButton(
                onClick = contentBottomBarOnActions.onProfileAction,
                enabled = selectDestination !== ContentDestinations.ProfilePage,
            ) { StandardDefaultUser() }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun ContentBottomBarPreview() {
    ContentBottomBar(
        selectDestination = ContentDestinations.HomePage,
        contentBottomBarOnActions = ContentBottomBarOnActions(
            onHomeAction = {},
            onShortsAction = {},
            onSubscribeAction = {},
            onProfileAction = {},
        ),
    )
}