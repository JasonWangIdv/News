package com.falcon.news.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.news.databinding.ActivityMainBinding
import com.falcon.news.entity.BaseItemEntity
import com.falcon.news.entity.DividerItemEntity
import com.falcon.news.entity.NewsItemEntity
import com.falcon.news.repository.bean.Appearance
import com.falcon.news.repository.bean.Extra
import com.falcon.news.repository.bean.NewsBean
import com.falcon.news.repository.retrofit.RetrofitRepo
import com.falcon.news.view.news.NewsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<NewsBean> = RetrofitRepo.getApis().getNews()
            if (response.isSuccessful) {
                val data = mutableListOf<BaseItemEntity>()

                val news = response.body()?.getVector?.items ?: emptyList()
                for (item in news) {
                    if ("divider" == item.type) {
                        val divider = DividerItemEntity("divider", item.title)
                        data.add(divider)
                    } else if ("news" == item.type) {
                        val appearance = item.appearance ?: Appearance()
                        val extra = item.extra ?: Extra()
                        val ref = item.ref ?: ""
                        val dateStr = if (extra.created > 0) {
                            val sdf = SimpleDateFormat("MMM dd, yyyy 'at' h:mm a")
                            val date = Date(extra.created * 1000)
                            sdf.format(date)
                        } else {
                            ""
                        }
                        val news = NewsItemEntity(
                            "news",
                            appearance.thumbnail,
                            appearance.subscript,
                            dateStr,
                            appearance.mainTitle ?: "",
                            appearance.subTitle ?: "",
                            ref
                        )

                        data.add(news)
                    }
                }
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.replaceDataAll(data)
                }
            } else {
                throw HttpException(response)
            }
        }
    }

}