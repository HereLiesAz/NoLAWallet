package com.hereliesaz.nolawallet.data.remote

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class ProjectRequest(
    val name: String,
    val description: String,
    val private: Boolean = false
)

data class RepoResponse(
    val html_url: String
)

interface GithubApi {
    @POST("user/repos")
    fun createRepo(
        @Header("Authorization") token: String,
        @Body project: ProjectRequest
    ): Call<RepoResponse>
}

object GithubService {
    private const val BASE_URL = "https://api.github.com/"

    val api: GithubApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
}
