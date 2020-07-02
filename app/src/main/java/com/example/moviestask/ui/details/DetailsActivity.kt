package com.example.moviestask.ui.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.moviestask.R
import com.example.moviestask.common.ApiConstants
import com.example.moviestask.entity.Movie
import com.example.moviestask.network.api.googleutil.Resource
import com.example.moviestask.ui.details.rvadapter.VideoAdapter
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.layout_movie_detail.*
import kotlinx.android.synthetic.main.layout_movie_review.*

class DetailsActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private lateinit var videoAdapter: VideoAdapter
    private val model: MovieViewModel by viewModels() //androidx.activity-ktx dependencies


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initUI()
        model.movieCopy = getMovieFromIntent()
        model.setMovie_ID()


//        model.movieLiveData.observe(this, Observer { resource ->
//            when (resource.status) {
//                Resource.Status.SUCCESS -> resource.data?.let { model._movieIDLiveData.value = it.id }
//            }
//        })

        model.movieTrailersLiveData.observe(this, Observer { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> resource.data?.let {
                    model.movieCopy.videos= it
                    videoAdapter.videoList = model.movieCopy.videos
                }
            }
        })

        loadMovieData()

    }

    private fun initUI() {
        videoAdapter = VideoAdapter()
        rv_movie_videos.apply {
            adapter = videoAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadMovieData() {
        model.movieCopy.apply {
            Glide.with(this@DetailsActivity).load(ApiConstants.getBackDropUrl(backdrop_path)).into(iv_movie_poster)
            toolbar.title = title
            detail_header_title.text = title
            detail_header_release.text = release_date.toString()
            detail_header_star.rating = vote_average
            detail_body_summary.text = overview


        }
    }

    private fun getMovieFromIntent() = intent.getParcelableExtra(MOVIE_ID_KEY) as Movie

    companion object {
        private const val MOVIE_ID_KEY = "MOVIE_ID_KEY"
        fun startDetailsActivity(context: Context?, passedMovie: Movie) {
            context.let {
                val intent = Intent(it, DetailsActivity::class.java).apply { putExtra(MOVIE_ID_KEY, passedMovie) }
                it?.startActivity(intent)
            }
        }
    }

}