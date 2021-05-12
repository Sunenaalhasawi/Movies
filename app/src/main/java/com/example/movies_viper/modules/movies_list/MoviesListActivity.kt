package com.example.movies_viper.modules.movies_list

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies_viper.R
import com.example.movies_viper.adapter.MovieListAdapter
import com.example.movies_viper.databinding.ActivityMoviesListBinding
import com.example.movies_viper.model.Movie

class MoviesListActivity : AppCompatActivity(), MovieListContract.View {

    lateinit var presenter: MovieListPresenter
    lateinit var binding: ActivityMoviesListBinding
    lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager
    lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager
    lateinit var popularMoviesAdapter: MovieListAdapter
    lateinit var topRatedMoviesAdapter: MovieListAdapter
    lateinit var upcomingMoviesAdapter: MovieListAdapter
    var popularMoviesPage: Int = 1;
    var topRatedMoviesPage: Int = 1;
    var upcomingMoviesPage: Int = 1;
    var initialFetching: Boolean = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Data binding to layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_list)
        initView()
        // Initializing the presenter class
        presenter = MovieListPresenter(MovieListRouter(this), MovieListInteractor())
        presenter.bindView(this)
        if (isConnected) {
            // Fetching data from server using function presenter class
            presenter.onPopularMovieViewCreated(popularMoviesPage, initialFetching)
            presenter.onTopRatedViewCreated(topRatedMoviesPage, initialFetching)
            presenter.onUpcomingViewCreated(upcomingMoviesPage, initialFetching)
        } else {
            //Fetching data from local cache based on the category
            presenter.onMovieViewCreated(this, "popular")
            presenter.onMovieViewCreated(this, "top rated")
            presenter.onMovieViewCreated(this, "upcoming")
        }
    }

    /**
     * Function to show loading indicator
     */
    override fun showLoading() {
        binding.cvProgressbar.visibility = View.VISIBLE
    }

    /**
     * Function to hide loading indicator
     */
    override fun hideLoading() {
        binding.cvProgressbar.visibility = View.GONE
        initialFetching = false
    }

    /** @param data
     *  @param category
     *  Function to fetch save movies from local cache
     * */
    override fun fetchMoviesFromLocal(data: List<Movie>, category: String) {
        hideLoading()
        if (category?.equals("popular"))
            popularMoviesAdapter.appendMovies(data)
        else if (category?.equals("top rated"))
            topRatedMoviesAdapter.appendMovies(data)
        else if (category?.equals("upcoming"))
            upcomingMoviesAdapter.appendMovies(data)
    }

    /** @param data
     * function to append popular movies to end of list as user scrolls
     * Sets loaded values to adapter
     * Pagination using recylcerview scroll listener
     */
    override fun publishPopularMoviesData(data: List<Movie>) {
        popularMoviesAdapter.appendMovies(data)
        attachPopularMoviesOnScrollListener()
    }

    /** @param data
     * function to append top rated movies to end of list as user scrolls
     * Sets loaded values to adapter
     * Pagination using recylcerview scroll listener
     */
    override fun publishTopRatedMoviesData(data: List<Movie>) {
        topRatedMoviesAdapter.appendMovies(data)
        attachTopRatedOnScrollListener()
    }

    /** @param data
     * function to append upcoming movies to end of list as user scrolls
     * Sets loaded values to adapter
     * Pagination using recylcerview scroll listener
     */
    override fun publishUpcomingMoviesData(data: List<Movie>) {
        upcomingMoviesAdapter.appendMovies(data)
        attachUpcomingOnScrollListener()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * function to initialize layout managers and adapters required for recyclerviews
     */
    private fun initView() {
        /*
       Setting popular recylcerview layout manager
        */
        popularMoviesLayoutMgr =
            LinearLayoutManager(this).apply { orientation = LinearLayoutManager.HORIZONTAL }
        binding.layoutMovies.recyclerviewPopularMovies.layoutManager = popularMoviesLayoutMgr
        // Setting up popular movie adapter
        popularMoviesAdapter =
            MovieListAdapter(mutableListOf(), object : MovieListAdapter.MovieListener {
                override fun onItemClick(movie: Movie) {
                    presenter.onItemClicked(movie)
                }
            })
        binding.layoutMovies.recyclerviewPopularMovies.adapter = popularMoviesAdapter

        /*
       Setting top rated recylcerview layout manager
        */
        topRatedMoviesLayoutMgr =
            LinearLayoutManager(this).apply { orientation = LinearLayoutManager.HORIZONTAL }
        binding.layoutMovies.recyclerViewTopRated.layoutManager = topRatedMoviesLayoutMgr

        // Setting up toprated movie adapter
        topRatedMoviesAdapter =
            MovieListAdapter(mutableListOf(), object : MovieListAdapter.MovieListener {
                override fun onItemClick(movie: Movie) {
                    presenter.onItemClicked(movie)
                }
            })
        binding.layoutMovies.recyclerViewTopRated.adapter = topRatedMoviesAdapter

        /*
        Setting upcoming recylcerview layout manager
        */
        upcomingMoviesLayoutMgr =
            LinearLayoutManager(this).apply { orientation = LinearLayoutManager.HORIZONTAL }
        binding.layoutMovies.recyclerviewUpcoming.layoutManager = upcomingMoviesLayoutMgr

        // Setting up upcoming movie adapter
        upcomingMoviesAdapter =
            MovieListAdapter(mutableListOf(), object : MovieListAdapter.MovieListener {
                override fun onItemClick(movie: Movie) {
                    presenter.onItemClicked(movie)
                }
            })
        binding.layoutMovies.recyclerviewUpcoming.adapter = upcomingMoviesAdapter

    }

    /**
     * Function to listen to recyclerview scroll. Fetches new data from server as user scrolls using function in Presenter
     * Populates Popular movie recylerview
     */
    private fun attachPopularMoviesOnScrollListener() {
        binding.layoutMovies.recyclerviewPopularMovies.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding.layoutMovies.recyclerviewPopularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    presenter.onPopularMovieViewCreated(popularMoviesPage, initialFetching)
                }
            }
        })
    }

    /**
     * Function to listen to recyclerview scroll. Fetches new data from server as user scrolls using function in Presenter.
     * Populates Top rated movie recylerview
     */
    private fun attachTopRatedOnScrollListener() {
        binding.layoutMovies.recyclerViewTopRated.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding.layoutMovies.recyclerViewTopRated.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    presenter.onTopRatedViewCreated(topRatedMoviesPage, initialFetching)
                }
            }
        })
    }

    /**
     * Function to listen to recyclerview scroll. Fetches new data from server as user scrolls using function in Presenter
     * Populates Upcoming movie recylerview
     */
    private fun attachUpcomingOnScrollListener() {
        binding.layoutMovies.recyclerviewUpcoming.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    binding.layoutMovies.recyclerviewUpcoming.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    presenter.onUpcomingViewCreated(upcomingMoviesPage, initialFetching)
                }
            }
        })
    }

    /**
     * Function to check whether app is connected to internet or not
     */
    val Context.isConnected: Boolean
        get() {
            return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }
}