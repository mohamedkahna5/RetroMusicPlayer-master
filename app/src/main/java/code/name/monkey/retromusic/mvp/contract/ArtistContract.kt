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

package code.name.monkey.retromusic.mvp.contract


import code.name.monkey.retromusic.model.Artist
import code.name.monkey.retromusic.mvp.BasePresenter
import code.name.monkey.retromusic.mvp.BaseView

import java.util.ArrayList


/**
 * Created by hemanths on 16/08/17.
 */

interface ArtistContract {
    interface ArtistView : BaseView<ArrayList<Artist>>

    interface Presenter : BasePresenter<ArtistView> {
        fun loadArtists()
    }
}
