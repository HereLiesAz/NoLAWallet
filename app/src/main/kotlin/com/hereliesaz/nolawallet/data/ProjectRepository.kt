package com.hereliesaz.nolawallet.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hereliesaz.nolawallet.data.remote.GithubService
import com.hereliesaz.nolawallet.data.remote.ProjectRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.awaitResponse

// Create a separate DataStore for settings
val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class ProjectRepository(private val context: Context) {

    private object Keys {
        val GITHUB_TOKEN = stringPreferencesKey("github_token")
    }

    val githubTokenFlow: Flow<String?> = context.settingsDataStore.data.map { prefs ->
        prefs[Keys.GITHUB_TOKEN]
    }

    suspend fun saveGithubToken(token: String) {
        context.settingsDataStore.edit { prefs ->
            prefs[Keys.GITHUB_TOKEN] = token
        }
    }

    suspend fun getGithubToken(): String? {
        return githubTokenFlow.first()
    }

    suspend fun createProject(name: String, description: String, token: String): Result<String> {
        return try {
            val response = GithubService.api.createRepo(
                token = "Bearer $token",
                project = ProjectRequest(name, description)
            ).awaitResponse()

            if (response.isSuccessful) {
                val htmlUrl = response.body()?.html_url ?: ""
                Result.success(htmlUrl)
            } else {
                Result.failure(Exception("Failed to create repo: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
