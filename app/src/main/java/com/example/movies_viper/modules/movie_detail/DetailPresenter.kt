package com.example.movies_viper

import com.example.movies_viper.model.Movie

class DetailPresenter(private val router: DetailContract.Router) : DetailContract.Presenter {

    private var view: DetailContract.View? = null

    /**
     * Function to bind the DetailActivity view to the presenter
     */
    override fun bindView(view: DetailContract.View) {
        this.view = view
    }

    /**
     * Function to unbind the DetailActivity view to the presenter
     */
    override fun unbindView() {
        view = null
    }

    /**
     * Function to set values to UI views in activity
     */
    override fun onViewCreated(movie: Movie) {
        view?.publishData(movie)
    }

    override fun onEmptyData(msg: Int) {
        view?.showMessage(msg)
        router.finish()
    }

    /**
     * Function to handle back click
     */
    override fun onBackClicked() {
        router.finish()
    }
}