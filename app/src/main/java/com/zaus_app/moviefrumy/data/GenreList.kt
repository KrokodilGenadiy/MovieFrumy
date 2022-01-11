package com.zaus_app.moviefrumy.data

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
        Genre( 28 , "Action", R.drawable.ic_ball),
        Genre( 12 , "Adventure", R.drawable.ic_ball),
        Genre( 16 , "Animation", R.drawable.ic_ball),
        Genre( 35 , "Comedy", R.drawable.ic_ball),
        Genre( 80 , "Crime", R.drawable.ic_ball),
        Genre( 99 , "Documentary", R.drawable.ic_ball),
        Genre( 18 , "Drama", R.drawable.ic_ball),
        Genre( 10751 , "Family", R.drawable.ic_ball),
        Genre( 14 , "Fantasy", R.drawable.ic_ball),
        Genre( 36 , "History", R.drawable.ic_ball),
        Genre( 27 , "Horror", R.drawable.ic_ball),
        Genre( 10402 , "Music", R.drawable.ic_ball),
        Genre( 9648 , "Mystery", R.drawable.ic_ball),
        Genre( 10749 , "Romance", R.drawable.ic_ball),
        Genre( 878 , "Science Fiction", R.drawable.ic_ball),
        Genre( 10770 , "TV Movie", R.drawable.ic_ball),
        Genre( 53 , "Thriller", R.drawable.ic_ball),
        Genre( 10752 , "War", R.drawable.ic_ball),
        Genre( 37 , "Western", R.drawable.ic_ball)
    )
    //just trying hashmap for learning purposes
    val genres: HashMap<Int, String> = hashMapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western",
    )
}