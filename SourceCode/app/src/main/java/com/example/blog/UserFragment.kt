package com.example.blog

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.blog.Retrofit.ApiClient
import com.example.blog.databinding.FragmentUserBinding
import retrofit2.HttpException
import java.io.IOException

private var _binding : FragmentUserBinding? = null
private val binding get() = _binding!!
class UserFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setCurrentFragmentTag("fragment3")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserBinding.inflate(inflater,container,false)
        val view = binding.root

        val sharedPref = requireActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val username = sharedPref.getString("Email", "admin@gmail.com")
        val password = sharedPref.getString("Password", "admin")

        // Set the Basic Authentication header
        val authString = "$username:$password"
        val authHeader = "Basic " + Base64.encodeToString(authString.toByteArray(), Base64.NO_WRAP)

        lifecycleScope.launchWhenCreated {
            binding.loadingbar.visibility = View.VISIBLE
            val response = try {
                ApiClient.userService.userData(authHeader = authHeader)
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
                binding.loadingbar.visibility = View.GONE

                for(item in response.body()!!)
                {
                    if(item.email == username)
                    {
                        if(item.blog_posts.isEmpty())
                        {
                            binding.noBlogs.visibility = View.VISIBLE
                            binding.userRecycle.visibility = View.INVISIBLE
                        }
                        else{
                            binding.userRecycle.apply {
                                adapter = com.example.blog.Adapters.CategoryAdapter(item.blog_posts, item.user_name,0)
                                binding.userRecycle.adapter = adapter
                                binding.userName.text = item.user_name
                                binding.userAbout.text = item.about
                                binding.userProfile.text = item.user_name[0].toString()
                            }
                        }
                    }
                }

            }
            else if(response.isSuccessful && response.body()==null){
                binding.loadingbar.visibility = View.GONE
                Toast.makeText(activity,"No Blogs posted yet", Toast.LENGTH_LONG).show()

            }
            else{
                binding.loadingbar.visibility = View.GONE
                Log.e(ContentValues.TAG,response.message()+response.code())
                Toast.makeText(activity,response.message()+response.code(), Toast.LENGTH_LONG).show()
            }
        }



        return view
    }


}