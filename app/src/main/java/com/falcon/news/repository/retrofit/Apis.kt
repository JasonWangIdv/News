package com.falcon.news.repository.retrofit

import com.falcon.news.repository.bean.NewsBean
import retrofit2.Response
import retrofit2.http.GET

interface Apis {

    @GET("/interview/interview_get_vector.json")
    suspend fun getNews(): Response<NewsBean>

}