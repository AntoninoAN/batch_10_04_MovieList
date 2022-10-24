package com.example.movielisttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movielisttest.model.MovieResponse
import com.example.movielisttest.model.remote.MovieNetwork
import java.util.concurrent.Executors

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var rvMovieList: RecyclerView
    private val movieHandler =
    object: Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                when (msg.what){
                    1 -> {
                        val listOfMovies: List<MovieResponse> =
                            msg.obj as List<MovieResponse>
                        Log.d(TAG, "handleMessage: $listOfMovies")
                    }
                    else -> {
                        msg.data?.getString("KEY")?.let {
                            Toast.makeText(this@MainActivity,
                                it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        getMovieList()
    }

    private fun initViews() {
        rvMovieList = findViewById(R.id.movie_list)
        //rvMovieList.adapter = MovieAdapter(getMovieList())
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

    private fun getMovieList(){
        val network = MovieNetwork()
        var networkResult = mutableListOf<String>()

        Thread(Runnable {
            Log.d(TAG,
                "getMovieList:${Thread.currentThread().name}")
            val message = Message()
            message.what = 1
            message.obj = network.getMovieList()
            movieHandler.sendMessage( message )
        }).start()

        Thread(Runnable {
            movieHandler.sendMessage(
                Message().apply {
                    what = 2
                    data = Bundle().apply {
                        putString("KEY","${Thread.currentThread().name}")
                    }

                }
            )
        }).start()
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