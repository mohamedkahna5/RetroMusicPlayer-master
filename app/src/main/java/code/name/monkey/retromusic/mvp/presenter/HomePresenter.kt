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

import code.name.monkey.retromusic.R
import code.name.monkey.retromusic.adapter.HomeAdapter.Companion.GENRES
import code.name.monkey.retromusic.adapter.HomeAdapter.Companion.PLAYLISTS
import code.name.monkey.retromusic.adapter.HomeAdapter.Companion.RECENT_ALBUMS
import code.name.monkey.retromusic.adapter.HomeAdapter.Companion.RECENT_ARTISTS
import code.name.monkey.retromusic.adapter.HomeAdapter.Companion.TOP_ALBUMS
import code.name.monkey.retromusic.adapter.HomeAdapter.Companion.TOP_ARTISTS
import code.name.monkey.retromusic.model.Home
import code.name.monkey.retromusic.mvp.Presenter
import code.name.monkey.retromusic.mvp.contract.HomeContract
import code.name.monkey.retromusic.util.PreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

class HomePresenter(private val view: HomeContract.HomeView) : Presenter(), HomeContract.HomePresenter {
    private val hashSet: HashSet<Home> = HashSet()

    override fun homeSections() {
        loadRecentArtists()
        loadRecentAlbums()
        loadTopArtists()
        loadATopAlbums()
        loadFavorite()
    }

    override fun subscribe() {
        homeSections()
    }

    override fun unsubscribe() {
        disposable.dispose()
    }

    private fun loadRecentArtists() {
        disposable += repository.recentArtistsFlowable
                .subscribe({
                    if (it.isNotEmpty()) hashSet.add(Home(0, R.string.recent_artists, 0, it, RECENT_ARTISTS, R.drawable.ic_artist_white_24dp))
                    view.showData(ArrayList(hashSet))
                }, {
                    view.showEmptyView()
                })
    }

    private fun loadRecentAlbums() {
        disposable += repository.recentAlbumsFlowable
                .subscribe({
                    if (it.isNotEmpty()) hashSet.add(Home(1, R.string.recent_albums, 0, it, RECENT_ALBUMS, R.drawable.ic_album_white_24dp))
                    view.showData(ArrayList(hashSet))
                }, {
                    view.showEmptyView()
                })
    }

    private fun loadATopAlbums() {
        disposable += repository.topAlbumsFlowable
                .subscribe({
                    if (it.isNotEmpty()) hashSet.add(Home(3, R.string.top_albums, 0, it, TOP_ALBUMS, R.drawable.ic_album_white_24dp))
                    view.showData(ArrayList(hashSet))
                }, {
                    view.showEmptyView()
                })
    }

    private fun loadTopArtists() {
        disposable += repository.topArtistsFlowable
                .subscribe({
                    if (it.isNotEmpty()) hashSet.add(Home(2, R.string.top_artists, 0, it, TOP_ARTISTS, R.drawable.ic_artist_white_24dp))
                    view.showData(ArrayList(hashSet))
                }, {
                    view.showEmptyView()
                })
    }

    private fun loadFavorite() {
        disposable += repository.favoritePlaylistFlowable
                .subscribe({
                    if (it.isNotEmpty()) hashSet.add(Home(4, R.string.favorites, 0, it, PLAYLISTS, R.drawable.ic_favorite_white_24dp))
                    view.showData(ArrayList(hashSet))
                }, {
                    view.showEmptyView()
                })
    }
}
