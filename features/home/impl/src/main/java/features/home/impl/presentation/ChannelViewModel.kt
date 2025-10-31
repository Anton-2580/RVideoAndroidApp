package features.home.impl.presentation

import androidx.lifecycle.SavedStateHandle
import common.impl.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ChannelViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): BaseViewModel() {
}