package jihan.binar.pra_project_final.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.R
import jihan.binar.pra_project_final.Util
import jihan.binar.pra_project_final.databinding.FragmentDetailNewsBinding
import jihan.binar.pra_project_final.viewmodel.DetailNewsViewModel


@AndroidEntryPoint
class DetailNewsFragment : Fragment() {

    lateinit var binding : FragmentDetailNewsBinding
    private val detailNewsViewModel : DetailNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsId = arguments?.getString("newsId")
        if (newsId != null) {
            detailNewsUpdate(newsId)
        }

    }

    private fun detailNewsUpdate(newsId: String) {
        detailNewsViewModel.getDetailNewsUpdate(newsId)
        detailNewsViewModel.itemNewsDetail.observe(requireActivity()){
            binding.apply {
                txtDescNews.text = it.content
                txtTitleNews.text = it.title
                Glide.with(requireContext()).load(it.newsImage).into(imgNews)
            }
        }
    }

}