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
import com.example.blog.databinding.FragmentCategoryFoodBinding

import retrofit2.HttpException
import java.io.IOException

private var _binding : FragmentCategoryFoodBinding? = null
private val binding get() = _binding!!

class CategoryFragmentFood() : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCategoryFoodBinding.inflate(inflater,container,false)
        val view = binding.root

        lifecycleScope.launchWhenCreated {

            binding.progressbar2.visibility = View.VISIBLE
            val response = try {
                ApiClient.userService.getCategoryFood()
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
                binding.progressbar2.visibility = View.GONE
                Log.d("Response_List", response.body()!!.posts.toString())

                binding.RecyclerViewCategory2.apply {
                    adapter = CategoryAdapter(response.body()!!.posts,response.body()!!.name,1)
                    binding.RecyclerViewCategory2.adapter = adapter
                }

            }
            else if(response.isSuccessful && response.body()==null){
                binding.progressbar2.visibility = View.GONE
                Toast.makeText(activity,"No Blogs posted yet", Toast.LENGTH_LONG).show()

            }
            else{
                binding.progressbar2.visibility = View.GONE
                Log.e(ContentValues.TAG,"Response Not Successful")
            }

        }

        return view
    }


}