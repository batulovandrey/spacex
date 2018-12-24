package com.github.butul0ve.spacexchecker

import android.os.Bundle
import android.view.View
import com.github.butul0ve.spacexchecker.utils.convertYoutubeUrlToVideoId
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val VIDEO_EXTRA = "video_extra"

class PlayerActivity: YouTubeBaseActivity() {

    private var videoId = ""
    private lateinit var playerView: YouTubePlayerView
    private lateinit var errorView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        playerView = findViewById(R.id.youtube_player_view)
        errorView = findViewById(R.id.error_layout)

        if (intent.hasExtra(VIDEO_EXTRA)) {
            val videoLink = intent.getStringExtra(VIDEO_EXTRA)
            if (videoLink.isNotEmpty()) {
                videoId = videoLink.convertYoutubeUrlToVideoId()
            }
        }

        if (videoId.isNotEmpty()) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            playerView.initialize(BuildConfig.YOUTUBE_API_KEY, object: YouTubePlayer.OnInitializedListener {

                override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
                    player?.loadVideo(videoId)
                }

                override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                    showError()
                    p1?.getErrorDialog(this@PlayerActivity, 0)?.show()
                }
            })
        } else {
            showError()
        }
    }

    private fun showError() {
        errorView.visibility = View.VISIBLE
        playerView.visibility = View.GONE
    }
}