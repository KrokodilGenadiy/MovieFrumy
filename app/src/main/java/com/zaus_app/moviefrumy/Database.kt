package com.zaus_app.moviefrumy

object Database {
    val filmsDataBase = listOf(
        Film("The Shawshank Redemption", R.drawable.dune, "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."),
        Film("The Godfather", R.drawable.casino, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."),
        Film("The Dark Knight", R.drawable.bad, "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice."),
        Film("Pulp Fiction", R.drawable.jedi, "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption."),
        Film("Inception", R.drawable.walle, "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O."),
        Film("Hamilton", R.drawable.fast, "The real life of one of America's foremost founding fathers and first Secretary of the Treasury, Alexander Hamilton. Captured live on Broadway from the Richard Rodgers Theater with the original Broadway cast."),
        Film("Interstellar", R.drawable.gladiator, "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival."),
        Film("Joker", R.drawable.purge, "In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society. He then embarks on a downward spiral of revolution and bloody crime. This path brings him face-to-face with his alter-ego: the Joker."),
        Film("1917", R.drawable.bad, "April 6th, 1917. As a regiment assembles to wage war deep in enemy territory, two soldiers are assigned to race against time and deliver a message that will stop 1,600 men from walking straight into a deadly trap.")
    )
    val favoritesList = mutableListOf<Film>()
}