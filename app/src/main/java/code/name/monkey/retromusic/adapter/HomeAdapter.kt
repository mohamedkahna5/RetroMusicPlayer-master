package code.name.monkey.retromusic.adapter

import android.content.res.ColorStateList
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import code.name.monkey.appthemehelper.ThemeStore
import code.name.monkey.retromusic.R
import code.name.monkey.retromusic.adapter.album.AlbumFullWidthAdapter
import code.name.monkey.retromusic.adapter.artist.ArtistAdapter
import code.name.monkey.retromusic.adapter.song.SongAdapter
import code.name.monkey.retromusic.loaders.PlaylistSongsLoader
import code.name.monkey.retromusic.model.*
import code.name.monkey.retromusic.util.PreferenceUtil
import com.google.android.material.chip.Chip


class HomeAdapter(private val activity: AppCompatActivity, private var homes: List<Home>, private val displayMetrics: DisplayMetrics) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        return homes[position].homeSection
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = LayoutInflater.from(activity).inflate(R.layout.section_recycler_view, parent, false)
        return when (viewType) {
            RECENT_ARTISTS, TOP_ARTISTS -> ArtistViewHolder(layout)
            PLAYLISTS -> PlaylistViewHolder(layout)
            else -> {
                AlbumViewHolder(LayoutInflater.from(activity).inflate(R.layout.metal_section_recycler_view, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val home = homes[position]
        when (getItemViewType(position)) {

            RECENT_ALBUMS, TOP_ALBUMS -> {
                val viewHolder = holder as AlbumViewHolder
                viewHolder.bindView(home)
            }
            RECENT_ARTISTS, TOP_ARTISTS -> {
                val viewHolder = holder as ArtistViewHolder
                viewHolder.bindView(home)
            }
            PLAYLISTS -> {
                val viewHolder = holder as PlaylistViewHolder
                viewHolder.bindView(home)
            }
        }
    }

    override fun getItemCount(): Int {
        return homes.size
    }

    fun swapData(finalList: List<Home>) {
        homes = finalList
        notifyDataSetChanged()
    }

    companion object {

        @IntDef(RECENT_ALBUMS, TOP_ALBUMS, RECENT_ARTISTS, TOP_ARTISTS, GENRES, PLAYLISTS)
        @Retention(AnnotationRetention.SOURCE)
        annotation class HomeSection

        const val RECENT_ALBUMS = 0
        const val TOP_ALBUMS = 1
        const val RECENT_ARTISTS = 2
        const val TOP_ARTISTS = 3
        const val GENRES = 4
        const val PLAYLISTS = 5

    }

    private inner class AlbumViewHolder(view: View) : AbsHomeViewItem(view) {
        fun bindView(home: Home) {
            recyclerView.apply {
                adapter = AlbumFullWidthAdapter(activity, home.arrayList as ArrayList<Album>, displayMetrics)
            }
            chip.text = activity.getString(home.title)
            chip.setChipIconResource(home.icon)
        }
    }

    private inner class ArtistViewHolder(view: View) : AbsHomeViewItem(view) {
        fun bindView(home: Home) {
            recyclerView.apply {
                layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
                val artistAdapter = ArtistAdapter(activity, home.arrayList as ArrayList<Artist>, PreferenceUtil.getInstance().getHomeGridStyle(context!!), false, null)
                adapter = artistAdapter
            }
            chip.text = activity.getString(home.title)
            chip.setChipIconResource(home.icon)
        }
    }
    private inner class PlaylistViewHolder(view: View) : AbsHomeViewItem(view) {
        fun bindView(home: Home) {
            val songs = PlaylistSongsLoader.getPlaylistSongList(activity, home.arrayList[0] as Playlist)
            recyclerView.apply {
                val songAdapter = SongAdapter(activity, songs, R.layout.item_album_card, false, null)
                layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
                adapter = songAdapter

            }
            chip.text = activity.getString(home.title)
            chip.setChipIconResource(home.icon)
        }
    }

    private open inner class AbsHomeViewItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val chip: Chip = itemView.findViewById(R.id.chipHead)

        init {
            chip.apply { chipBackgroundColor = ColorStateList.valueOf(ThemeStore.primaryColor(context)) }
        }
    }
}