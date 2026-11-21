package edu.lemoyne.campusapp

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ShowcaseApi {
    @GET("/rest/v1/showcase_items")
    suspend fun  getShowcaseItems(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String
    ): List<ShowcaseItem>

    @POST("/auth/v1/token?grant_type=password")
    suspend fun signIn(
        @Header("apikey") apiKey: String,
        @Body request: LoginRequest
    ): LoginResponse
}

object ShowcaseService {
    const val API_KEY = "sb_publishable_gWIm9wmoz4u9TYbs83tkgQ_ITjWUSxk"
    const val BASE_URL = "https://qrpaeziejyaqsnwzfgwp.supabase.co"

    val api: ShowcaseApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ShowcaseApi::class.java)
}

data class LoginRequest(
    val email: String,
    val password: String
)

data class  LoginResponse(
    @SerializedName ("access_token") val accessToken: String
)