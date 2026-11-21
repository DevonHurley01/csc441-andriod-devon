package edu.lemoyne.campusapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShowcaseRepository(private val api: ShowcaseApi = ShowcaseService.api) {

    suspend fun loadItems(token: String): List<ShowcaseItem> =
        withContext(Dispatchers.IO) {
            api.getShowcaseItems(ShowcaseService.API_KEY, authorization = "Bearer $token")
        }

    suspend fun signIn(email: String, password: String): String =
        withContext(Dispatchers.IO){
            api.signIn(
                ShowcaseService.API_KEY,
                LoginRequest(email, password)
            ).accessToken
    }
}