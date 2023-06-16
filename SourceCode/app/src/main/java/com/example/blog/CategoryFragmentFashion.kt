package com.example.blog

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.blog.Adapters.CategoryAdapter
import com.example.blog.Retrofit.ApiClient
import com.example.blog.databinding.FragmentCategoryFashionBinding

import retrofit2.HttpException
import java.io.IOException

private var _binding : FragmentCategoryFashionBinding? = null
private lateinit var CategoryAdapter: CategoryAdapter
private val binding get() = _binding!!

class CategoryFragmentFashion() : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCategoryFashionBinding.inflate(inflater,container,false)
        val view = binding.root

        lifecycleScope.launchWhenCreated {

                binding.progressbar.visibility = View.VISIBLE
                val response = try {
                    ApiClient.userService.getCategoryFashion()
                }catch (e: IOException){
                    Log.e(ContentValues.TAG,"IOException, you might not have Internet Connection")
                    Toast.makeText(activity,"You might not have Internet Connection", Toast.LENGTH_LONG).show()
                    return@launchWhenCreated
                }catch (e: HttpException){
                    Log.e(ContentValues.TAG,"HttpException,unexpected response")
                    Toast.makeText(activity,"Something went wrong", Toast.LENGTH_LONG).show()
                    return@launchWhenCreated
                }
                if(response.isSuccessful && response.body()!=null){
                    binding.progressbar.visibility = View.GONE
                    Log.d("Response_List", response.body()!!.posts.toString())

                    binding.RecyclerViewCategory.apply {
                        adapter = CategoryAdapter(response.body()!!.posts,response.body()!!.name,1)
                        binding.RecyclerViewCategory.adapter = adapter
                    }

                }
                else if(response.isSuccessful && response.body()==null){
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(activity,"No Blogs posted yet", Toast.LENGTH_LONG).show()

                }
                else{
                    binding.progressbar.visibility = View.GONE
                    Log.e(ContentValues.TAG,"Response Not Successful")
                }

        }

        return view
    }


}