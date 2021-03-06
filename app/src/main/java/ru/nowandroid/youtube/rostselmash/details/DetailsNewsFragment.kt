package ru.nowandroid.youtube.rostselmash.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.details_news_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import ru.nowandroid.youtube.rostselmash.R
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class DetailsNewsFragment : androidx.fragment.app.Fragment(), CoroutineScope {

  private var job = Job()
  override val coroutineContext: CoroutineContext = Dispatchers.Main + job

  private lateinit var viewModel: DetailsNewsViewModel

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
                           ): View? {
    return inflater.inflate(R.layout.details_news_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(DetailsNewsViewModel::class.java)

    job = launch(Dispatchers.IO) {
      getData()
    }
  }

  private fun getData() {
    try {
      val document = Jsoup.connect(arguments?.getString("link")).get()
      val elements = document.select("div[id=main_content_column]")

      val title = elements.select("h1[class=b-column_main__title]").text()

      val description = elements.select("div[class=b-column_main__text]").select("p").text()

      val linkImage = document.baseUri() +
                      elements.select("div[class=b-column_main__text]").select("img").attr("src")

      job = launch {
        det_title.text = title.toString()
        det_description.text = description.toString()
        Picasso.with(activity)
            .load(linkImage)
            .into(det_main_photo)
      }
    } catch (e: IOException) {
      Log.e("TEST) ", e.message.toString())
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    job.cancel()
  }

}
