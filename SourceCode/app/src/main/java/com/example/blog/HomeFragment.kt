@file:Suppress("DEPRECATION")

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
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.blog.Adapters.CategoryTabAdapter
import com.example.blog.Adapters.PostAdapter
import com.example.blog.Models.PostGetModel
import com.example.blog.Retrofit.ApiClient
import com.example.blog.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.HttpException
import java.io.IOException


private var _binding : FragmentHomeBinding? = null
private val binding get() = _binding!!

class HomeFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setCurrentFragmentTag("fragment1")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root


        val sharedPref = requireActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val username = sharedPref.getString("Email", "admin@gmail.com")
        val password = sharedPref.getString("Password", "admin")

        // Set the Basic Authentication header
        val authString = "$username:$password"
        val authHeader = "Basic " + Base64.encodeToString(authString.toByteArray(), Base64.NO_WRAP)

        lifecycleScope.launchWhenCreated {
            val response2 = try {
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

            if(response2.isSuccessful){
                for(item in response2.body()!!)
                {
                    if(item.email == username)
                    {
                        binding.userName.text = item.user_name
                        binding.userLogo.text = item.user_name[0].toString()
                        isAuthID(item.id.toString())
                    }
                }
            }


            val response = try {
                ApiClient.userService.getPosts()
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

                binding.RecylerViewPosts.apply {
                    adapter = PostAdapter(response.body()!!)
                    binding.RecylerViewPosts.adapter = adapter

                    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                        androidx.appcompat.widget.SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            var updateList : List<PostGetModel>  = ArrayList<PostGetModel>();
                            for (item in response.body()!!)
                            {
                                if(item.title.toLowerCase().contains(newText.toLowerCase()))
                                {
                                    updateList += item;
                                }
                            }

                            if(updateList.isEmpty())
                            {
                                Toast.makeText(activity,"No blog found", Toast.LENGTH_LONG).show()
                            }
                            else {
                                adapter = PostAdapter(updateList)
                                binding.RecylerViewPosts.adapter = adapter
                            }

                            return true
                        }
                    })

                }
            }


            else if(response.isSuccessful && response.body()==null){
                Toast.makeText(activity,"No Blogs posted yet", Toast.LENGTH_LONG).show()

            }
            else{
                Log.e(ContentValues.TAG,"Response Not Successful")
                Toast.makeText(activity,"Something wnt wrong", Toast.LENGTH_LONG).show()
            }
        }

        val viewPageAdapterD = CategoryTabAdapter(requireActivity().supportFragmentManager,lifecycle)
        val viewPagerD : ViewPager2 = binding.viewPagerD
        viewPagerD.adapter = viewPageAdapterD
        val tabsD: TabLayout = binding.TabLayout

        TabLayoutMediator(tabsD,viewPagerD){ tab, position ->
            when(position)
            {
                0 -> {
                    tab.text = "Travel"
                }
                1 -> {
                    tab.text = "Food"
                }
                2 -> {
                    tab.text = "Health"
                }
                3 -> {
                    tab.text = "Fashion"
                }
            }
        }.attach()

        binding.logout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            isAuthDelete()
        }

        return view
    }

    private fun isAuthDelete(){
        val sharedPref = requireActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", false)
        editor.apply()
    }

    private fun isAuthID(id : String){
        val sharedPref = requireActivity().getSharedPreferences("AuthID", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("ID",id)
        editor.apply()
    }


}