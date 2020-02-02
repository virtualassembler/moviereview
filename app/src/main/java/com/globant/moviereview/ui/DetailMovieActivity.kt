package com.globant.moviereview.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.globant.moviereview.model.MovieReview
import com.globant.moviereview.model.MovieReviewDatabase.Companion.getMovieDatabase
import com.globant.moviereview.utils.ID_MOVIE
import com.globant.moviereview.utils.getFactorMovieReviewRating
import kotlinx.android.synthetic.main.detail_item.image_view_ic_star
import kotlinx.android.synthetic.main.detail_item.image_view_movie_picture
import kotlinx.android.synthetic.main.detail_item.rating_bar_average
import kotlinx.android.synthetic.main.detail_item.text_view_average
import kotlinx.android.synthetic.main.detail_item.text_view_movie_title
import kotlinx.android.synthetic.main.detail_item.text_view_summary
import kotlinx.android.synthetic.main.detail_item.text_view_title_release_date


/**
 * DetailMovieActivity
 *
 * Show the detail of a selected movie in the recyclerView of the MainActivity
 *
 * @author juan.rendon
 */
class DetailMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.globant.moviereview.R.layout.detail_item)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val idMovieReview = getMovieDatabase(this).getMovieDAO().getMovieReviewDetail(intent.getIntExtra(ID_MOVIE, 0))
        bindMovieReview(idMovieReview)
    }

    private fun bindMovieReview(movieReview: MovieReview) {
        text_view_movie_title.text = movieReview.title
        text_view_title_release_date.text = movieReview.releaseDate
        text_view_summary.text = movieReview.summary
        rating_bar_average.rating = movieReview.voteAverage.toFloat() / getFactorMovieReviewRating()
        text_view_average.text = (movieReview.voteAverage.toFloat() / getFactorMovieReviewRating()).toString()
        Glide.with(image_view_movie_picture)
                .load("http://image.tmdb.org/t/p/w500" + movieReview.posterPath)
                .centerCrop()
                .fitCenter()
                .override(1000, 1000)
                .into(image_view_movie_picture)
        Glide.with(image_view_ic_star)
                .load("http://image.tmdb.org/t/p/w500" + movieReview.backdropPath)
                .centerCrop()
                .fitCenter()
                .override(1000, 1000)
                .into(image_view_ic_star)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, DetailMovieActivity::class.java)
        }
    }
}
