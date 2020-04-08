package br.hendria.desafio.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --------------------------------------------------------------
        // Configuração listview
        // --------------------------------------------------------------

        var listView = findViewById<ListView>(R.id.listView)
        var listaPosts = mutableListOf<String>()
        var listAdapter = ArrayAdapter(this,  android.R.layout.simple_list_item_1, listaPosts)
        listView.adapter = listAdapter

        // --------------------------------------------------------------
        // Requisição GET
        // --------------------------------------------------------------

        Toast.makeText(applicationContext, "Pesquisando postagens...", Toast.LENGTH_LONG).show()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create<PostagemService>(PostagemService::class.java)
        val requisicaoPostagens = service.listarPostagens()
        requisicaoPostagens.enqueue(object : Callback<List<Postagem>> {
            override fun onFailure(call: Call<List<Postagem>>, t: Throwable) {
                Toast.makeText(applicationContext, "Falhou", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Postagem>>,
                response: Response<List<Postagem>>
            ) {
                Toast.makeText(applicationContext, "Sucesso!!!", Toast.LENGTH_LONG).show()

                val lista = response.body()

                lista?.forEach { postagem ->
                    listaPosts.add(postagem.title)
                }

                // Recarrega listview
                listAdapter.notifyDataSetChanged()

            }

        }
        )

    }
}
