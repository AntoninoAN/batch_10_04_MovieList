package com.example.movielisttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var rvMovieList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        rvMovieList = findViewById(R.id.movie_list)
        rvMovieList.adapter = MovieAdapter(generateData())
        rvMovieList.layoutManager = createLayoutManager()
    }

    private fun createLayoutManager(): RecyclerView.LayoutManager {
        val linearLayoutManager =
            LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        val gridLayoutManager =
            GridLayoutManager(this,
            3,
            GridLayoutManager.HORIZONTAL,
            true)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL)
        return gridLayoutManager
    }

    private fun generateData(): List<String> {
        return (0..9).map {
            "Movie with the name: $it"
        }
    }
}

/*
1.- Create a Item_Layout (layout xml file)
2.- Subclass of RecyclerView.ViewHolder
3.- Subclass of RecyclerView.Adapter
4.- Configure the movieList view.
    4.a.- setAdapter
    4.b.- setLayoutManager
 */