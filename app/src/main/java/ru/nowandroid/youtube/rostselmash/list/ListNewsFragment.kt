package ru.nowandroid.youtube.rostselmash.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.list_news_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import ru.nowandroid.youtube.rostselmash.R
import java.io.IOException

class ListNewsFragment : androidx.fragment.app.Fragment() {

    private val url = "https://rostselmash.com/company/press/news/"
    private val listNews = mutableListOf<News>()
    private lateinit var adapter: DataAdapter

    companion object {
        fun newInstance() = ListNewsFragment()
    }

    private lateinit var viewModel: ListNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListNewsViewModel::class.java)
        // TODO: Use the ViewModel

        adapter = DataAdapter()
        val llm = androidx.recyclerview.widget.LinearLayoutManager(this.context)
        rv.layoutManager = llm
        rv.adapter = adapter

        GlobalScope.launch {
            getData()
        }
    }

    private fun getData() {
        try {
            val document = Jsoup.connect(url).get()
            val elements = document.select("li[class=b-action-list__item]")

            for (i in 0 until elements.size) {
                val title = elements.select("h3[class=b-action-list__title]")
                    .select("a")
                    .eq(i)
                    .text()

                val description =
                    elements.select("div[class=b-action-list__description]")
                        .select("p[class=b-action-list__text]")
                        .eq(i)
                        .text()

                val linkImage =
                    document.baseUri() +
                            elements.select("li[class=b-action-list__item]").select("a")
                                .select("img")
                                .eq(i)
                                .attr("src")

                val additionalInfo = elements.select("div[class=b-action-list__description]")
                    .select("div[class=b-action-list__date]")
                    .eq(i)
                    .text()

                val linkDetails =
                    document.baseUri() +
                    elements.select("li[class=b-action-list__item]")
                        .eq(i)
                        .select("a")
                        .attr("href")

                listNews.add(News(title, description, linkImage, additionalInfo, linkDetails))
            }
            GlobalScope.launch(Dispatchers.Main) {
                adapter.set(listNews)
            }
        } catch (e: IOException) {
            Log.e("TEST) exception", e.message.toString())
        }
    }

}
