// Person.kt
package com.example.flixsterpart2

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonResponse(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<Person>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class Person(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("profile_path")
    val profilePath: String?,

    @SerialName("known_for_department")
    val knownForDepartment: String
) : java.io.Serializable {
    val profileImageUrl: String
        get() = "https://image.tmdb.org/t/p/w185${profilePath}"
}

@Serializable
data class PersonDetails(
    @SerialName("biography")
    val biography: String?,

    @SerialName("birthday")
    val birthday: String?,

    @SerialName("deathday")
    val deathday: String?,

    @SerialName("place_of_birth")
    val placeOfBirth: String?,

    @SerialName("also_known_as")
    val alsoKnownAs: List<String>?,

    @SerialName("gender")
    val gender: Int?,

    @SerialName("imdb_id")
    val imdbId: String?,

    @SerialName("homepage")
    val homepage: String?
)
