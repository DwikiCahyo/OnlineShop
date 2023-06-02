package jihan.binar.pra_project_final.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import jihan.binar.pra_project_final.adapter.NewsAdapter
import jihan.binar.pra_project_final.adapter.ProductAdapter
import jihan.binar.pra_project_final.adapter.ProductSecondAdapter
import jihan.binar.pra_project_final.databinding.FragmentHomeBinding
import jihan.binar.pra_project_final.viewmodel.HomeViewModel


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    private val homeViewModel:HomeViewModel by viewModels()
    private lateinit var productAdapter:ProductAdapter
    private lateinit var productSecondAdapter:ProductSecondAdapter
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getSliderItem()
        imageSlider()
        newProduct()
        secondProduct()
        newsUpdate()

    }

    private fun secondProduct() {
       homeViewModel.getProductSecond("2")
        homeViewModel.isLoadingSecond.observe(requireActivity()){
            showLoadingSecondProduct(it)
        }
        homeViewModel.itemProductSecond.observe(requireActivity()){
            binding.rvSecondProduct.apply {
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                productSecondAdapter= ProductSecondAdapter(it)
                adapter = productSecondAdapter
            }
        }
    }

    private fun newProduct() {
        homeViewModel.getProduct("1")
        homeViewModel.isLoading.observe(requireActivity()){
            showLoadingNewProduct(it)
        }
        homeViewModel.itemProduct.observe(requireActivity()){
            binding.rvNewProduct.apply {
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                productAdapter = ProductAdapter(it)
                adapter = productAdapter
            }
        }
    }

    private fun newsUpdate() {
        homeViewModel.getNews()
        homeViewModel.isLoading.observe(requireActivity()){
            showLoadingNewsUpdate(it)
        }
        homeViewModel.itemNews.observe(requireActivity()){
            binding.rvNews.apply {
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                newsAdapter = NewsAdapter(it)
                adapter = newsAdapter
            }
        }
    }

    private fun showLoadingNewsUpdate(isLoading:Boolean){
        if (isLoading){
            binding.shimmerNewsLayout.visibility = View.VISIBLE
            binding.rvNews.visibility = View.GONE
            binding.shimmerNewsLayout.startShimmer()
        } else {
            binding.shimmerNewsLayout.visibility = View.GONE
            binding.rvNews.visibility = View.VISIBLE
            binding.shimmerNewsLayout.stopShimmer()
        }
    }

    private fun showLoadingNewProduct(isLoading:Boolean){
        if (isLoading){
            binding.shimmerFrameLayout.visibility = View.VISIBLE
            binding.rvNewProduct.visibility = View.GONE
            binding.shimmerFrameLayout.startShimmer()
        } else {
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.rvNewProduct.visibility = View.VISIBLE
            binding.shimmerFrameLayout.stopShimmer()
        }
    }

    private fun showLoadingSecondProduct(isLoading:Boolean){
        if (isLoading){
            binding.shimmerSecondLayout.visibility = View.VISIBLE
            binding.rvSecondProduct.visibility = View.GONE
            binding.shimmerSecondLayout.startShimmer()
        } else {
            binding.shimmerSecondLayout.visibility = View.GONE
            binding.rvSecondProduct.visibility = View.VISIBLE
            binding.shimmerSecondLayout.stopShimmer()
        }
    }

    private fun imageSlider() {
        val imageSlider = binding.imageSlider
        val sliderItems = ArrayList<SlideModel>()

        homeViewModel.itemSlider.observe(requireActivity()){
            it.map { sliderItems.add(SlideModel(it.image)) }
            imageSlider.setImageList(sliderItems, ScaleTypes.CENTER_CROP)
        }
    }


}