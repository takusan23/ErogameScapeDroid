package io.github.takusan23.erogamescapedroid.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.takusan23.erogamescapedroid.viewmodel.InfoViewModel

class InfoViewModelFactory (val application: Application, val gameId:Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InfoViewModel(application, gameId) as T
    }

}