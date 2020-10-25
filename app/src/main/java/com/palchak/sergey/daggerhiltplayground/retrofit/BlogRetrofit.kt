package com.palchak.sergey.daggerhiltplayground.retrofit

import retrofit2.http.GET

interface BlogRetrofit {

    @GET
    suspend fun get(): List<BlogNetworkEntity>
}