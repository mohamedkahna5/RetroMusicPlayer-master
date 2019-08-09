/*
 * Copyright (C) 2017. Alexander Bilchuk <a.bilchuk@sandrlab.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package code.name.monkey.retromusic.adapter.album

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import code.name.monkey.retromusic.R
import code.name.monkey.retromusic.glide.GlideApp
import code.name.monkey.retromusic.glide.RetroGlideExtension
import code.name.monkey.retromusic.glide.RetroMusicColoredTarget
import code.name.monkey.retromusic.helper.MusicPlayerRemote
import code.name.monkey.retromusic.model.Album
import code.name.monkey.retromusic.util.NavigationUtil
import code.name.monkey.retromusic.views.MetalRecyclerViewPager

class AlbumFullWidthAdapter(private val activity: Activity, private val dataSet: ArrayList<Album>, metrics: DisplayMetrics) :
        MetalRecyclerViewPager.MetalAdapter<AlbumFullWidthAdapter.FullMetalViewHolder>(metrics) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullMetalViewHolder {
        return FullMetalViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false))
    }

    override fun onBindViewHolder(holder: FullMetalViewHolder, position: Int) {
        // don't forget about calling supper.onBindViewHolder!
        super.onBindViewHolder(holder, position)

        val album = dataSet[position]

        if (holder.title != null) {
            holder.title!!.text = getAlbumTitle(album)
        }
        if (holder.text != null) {
            holder.text!!.text = getAlbumText(album)
        }
        if (holder.playSongs != null) {
            holder.playSongs!!.setOnClickListener { MusicPlayerRemote.openQueue(album.songs!!, 0, true) }
        }
        loadAlbumCover(album, holder)
    }

    private fun getAlbumTitle(album: Album): String? {
        return album.title
    }

    private fun getAlbumText(album: Album): String? {
        return album.artistName
    }

    private fun loadAlbumCover(album: Album, holder: FullMetalViewHolder) {
        if (holder.image == null) {
            return
        }
        GlideApp.with(activity)
                .asBitmapPalette()
                .load(RetroGlideExtension.getSongModel(album.safeGetFirstSong()))
                .transition(RetroGlideExtension.getDefaultTransition())
                .songOptions(album.safeGetFirstSong())
                .dontAnimate()
                .into(object : RetroMusicColoredTarget(holder.image!!) {
                    override fun onColorReady(color: Int) {

                    }
                })
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class FullMetalViewHolder(itemView: View) : MetalRecyclerViewPager.MetalViewHolder(itemView) {

        override fun onClick(v: View?) {
            val albumPairs = arrayOf<Pair<*, *>>(Pair.create(image, activity.resources.getString(R.string.transition_album_art)))
            NavigationUtil.goToAlbum(activity, dataSet[adapterPosition].id, *albumPairs)
        }
    }
}