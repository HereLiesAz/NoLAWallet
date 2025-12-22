package com.hereliesaz.nolawallet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hereliesaz.nolawallet.data.ProjectRepository
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {

    var projectName by mutableStateOf("")
    var projectDescription by mutableStateOf("")
    var showTokenDialog by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)

    fun createProject() {
        viewModelScope.launch {
            isLoading = true
            error = null
            successMessage = null

            val token = repository.getGithubToken()
            if (token.isNullOrBlank()) {
                showTokenDialog = true
                isLoading = false
            } else {
                performCreateProject(token)
            }
        }
    }

    fun saveTokenAndCreateProject(token: String) {
        viewModelScope.launch {
            repository.saveGithubToken(token)
            showTokenDialog = false
            isLoading = true
            performCreateProject(token)
        }
    }

    fun dismissDialog() {
        showTokenDialog = false
    }

    private suspend fun performCreateProject(token: String) {
        val result = repository.createProject(projectName, projectDescription, token)
        result.onSuccess { htmlUrl ->
            successMessage = "Project created successfully! URL: $htmlUrl"
            projectName = ""
            projectDescription = ""
        }.onFailure { exception ->
            error = exception.message
        }
        isLoading = false
    }
}

class ProjectViewModelFactory(private val repository: ProjectRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
