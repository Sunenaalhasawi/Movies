package com.example.movies_viper

import com.example.movies_viper.model.Movie

/**
 * This is the DetailContract class. Here we register all the methods that we are using in five levels of Viper architecture pattern
 */
interface DetailContract {

    interface View {
        fun publishData(movie: Movie)
        fun showMessage(msg: Int)
    }

    interface Presenter {

        fun bindView(view: DetailContract.View)
        fun unbindView()
        fun onViewCreated(movie: Movie)
        fun onBackClicked()
        fun onEmptyData(msg: Int)
    }

    interface Router {
        fun finish()
    }
}