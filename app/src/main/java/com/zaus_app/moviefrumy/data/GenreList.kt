package com.zaus_app.moviefrumy.data

import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.R
import com.zaus_app.moviefrumy.data.entity.Genre

object GenreList {
    /*
MOVIE
Action          28
Adventure       12
Animation       16
Comedy          35
Crime           80
Documentary     99
Drama           18
Family          10751
Fantasy         14
History         36
Horror          27
Music           10402
Mystery         9648
Romance         10749
Science Fiction 878
TV Movie        10770
Thriller        53
War             10752
Western         37
 */
    val genrelist = listOf(
        Genre( 28 , App.instance.resources.getString(R.string.action), R.drawable.ic_ball),
        Genre( 12 , App.instance.resources.getString(R.string.adventure), R.drawable.ic_ball),
        Genre( 16 , App.instance.resources.getString(R.string.animation), R.drawable.ic_ball),
        Genre( 35 , App.instance.resources.getString(R.string.comedy), R.drawable.ic_ball),
        Genre( 80 , App.instance.resources.getString(R.string.crime), R.drawable.ic_ball),
        Genre( 99 , App.instance.resources.getString(R.string.documentary), R.drawable.ic_ball),
        Genre( 18 , App.instance.resources.getString(R.string.drama), R.drawable.ic_ball),
        Genre( 10751 , App.instance.resources.getString(R.string.family), R.drawable.ic_ball),
        Genre( 14 , App.instance.resources.getString(R.string.fantasy), R.drawable.ic_ball),
        Genre( 36 , App.instance.resources.getString(R.string.history), R.drawable.ic_ball),
        Genre( 27 , App.instance.resources.getString(R.string.horror), R.drawable.ic_ball),
        Genre( 10402 , App.instance.resources.getString(R.string.music), R.drawable.ic_ball),
        Genre( 9648 , App.instance.resources.getString(R.string.mystery), R.drawable.ic_ball),
        Genre( 10749 , App.instance.resources.getString(R.string.romance), R.drawable.ic_ball),
        Genre( 878 , App.instance.resources.getString(R.string.science_fiction), R.drawable.ic_ball),
        Genre( 10770 , App.instance.resources.getString(R.string.tv_movie), R.drawable.ic_ball),
        Genre( 53 , App.instance.resources.getString(R.string.thriller), R.drawable.ic_ball),
        Genre( 10752 , App.instance.resources.getString(R.string.war), R.drawable.ic_ball),
        Genre( 37 , App.instance.resources.getString(R.string.western), R.drawable.ic_ball)
    )
    //just trying hashmap for learning purposes
    val genres: HashMap<Int, String> = hashMapOf(
        28 to App.instance.resources.getString(R.string.action),
        12 to App.instance.resources.getString(R.string.adventure),
        16 to App.instance.resources.getString(R.string.animation),
        35 to App.instance.resources.getString(R.string.comedy),
        80 to App.instance.resources.getString(R.string.crime),
        99 to App.instance.resources.getString(R.string.documentary),
        18 to App.instance.resources.getString(R.string.drama),
        10751 to App.instance.resources.getString(R.string.family),
        14 to App.instance.resources.getString(R.string.fantasy),
        36 to App.instance.resources.getString(R.string.history),
        27 to App.instance.resources.getString(R.string.horror),
        10402 to App.instance.resources.getString(R.string.music),
        9648 to App.instance.resources.getString(R.string.mystery),
        10749 to App.instance.resources.getString(R.string.romance),
        878 to App.instance.resources.getString(R.string.science_fiction),
        10770 to App.instance.resources.getString(R.string.tv_movie),
        53 to App.instance.resources.getString(R.string.thriller),
        10752 to App.instance.resources.getString(R.string.war),
        37 to App.instance.resources.getString(R.string.western),
    )
}