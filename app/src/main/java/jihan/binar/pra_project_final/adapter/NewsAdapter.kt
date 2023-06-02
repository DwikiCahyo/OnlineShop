package jihan.binar.pra_project_final.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.databinding.ItemNewsBinding
import jihan.binar.pra_project_final.model.news.ResponseNewsItem

class NewsAdapter (private val listNews:List<ResponseNewsItem>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder (private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(news: ResponseNewsItem){
            Glide.with(itemView).load(news.newsImage).into(binding.imgNews)
            binding.txtTitleNews.text = news.title

            binding.cvNews.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("newsId",news.idNews)
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailNewsFragment, bundle)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int = listNews.size

}