package br.hendria.desafio.android

import retrofit2.Call
import retrofit2.http.GET

interface PostagemService {
    @GET("posts")
    fun listarPostagens(): Call<List<Postagem>>
}