/*
 * Copyright (c) 2019 Hemanth Savarala.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by
 *  the Free Software Foundation either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package code.name.monkey.retromusic.mvp.presenter

import code.name.monkey.retromusic.model.Song
import code.name.monkey.retromusic.mvp.Presenter
import code.name.monkey.retromusic.mvp.contract.SongContract
import java.util.*

/**
 * Created by hemanths on 10/08/17.
 */

class SongPresenter(private val view: SongContract.SongView) : Presenter(), SongContract.Presenter {

    override fun loadSongs() {
        disposable.add(repository.allSongsFlowable
                .doOnSubscribe { view.loading() }
                .subscribe({ this.showList(it) },
                        { view.showEmptyView() },
                        { view.completed() }))
    }

    override fun subscribe() {
        loadSongs()
    }

    private fun showList(songs: ArrayList<Song>) {
        if (songs.isEmpty()) {
            view.showEmptyView()
        } else {
            view.showData(songs)
        }
    }

    override fun unsubscribe() {
        disposable.clear()
    }
}
