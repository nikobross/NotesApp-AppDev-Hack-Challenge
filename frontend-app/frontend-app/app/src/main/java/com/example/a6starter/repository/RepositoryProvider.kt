package com.example.a6starter.repository

import com.example.a6starter.network.RetrofitInstance

object RepositoryProvider {
    val repository: Repository by lazy {
        Repository(RetrofitInstance.apiService)
    }
}