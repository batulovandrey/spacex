package com.example.butul0ve.spacex

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.YouTubePlayerTracker

const val VIDEO_EXTRA = "video_extra"

class PlayerActivity: AppCompatActivity() {

    private var videoId = ""
    private lateinit var playerView: YouTubePlayerView
    private lateinit var tracker: YouTubePlayerTracker
    private lateinit var errorView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        playerView = findViewById(R.id.youtube_player_view)
        errorView = findViewById(R.id.error_layout)

        if (intent.hasExtra(VIDEO_EXTRA)) {
            videoId = intent.getStringExtra(VIDEO_EXTRA)
        }

        if (videoId.isNotEmpty()) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            initializePlayer()
        } else {
            showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        playerView.release()
    }

    private fun initializePlayer() {
        tracker = YouTubePlayerTracker()

        playerView.enterFullScreen()
        playerView.initialize({ youTubePlayer ->
            youTubePlayer.addListener(object: AbstractYouTubePlayerListener() {

                override fun onReady() {
                    youTubePlayer.addListener(tracker)
                    youTubePlayer.loadVideo(videoId, 0f)

                }

                override fun onError(error: PlayerConstants.PlayerError) {
                    super.onError(error)
                    showError()
                }
            })
        }, true)

        playerView.playerUIController.showFullscreenButton(false)
    }

    private fun showError() {
        errorView.visibility = View.VISIBLE
        playerView.visibility = View.GONE
    }
}