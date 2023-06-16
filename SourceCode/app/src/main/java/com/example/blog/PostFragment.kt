package com.example.blog

import android.R
import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.blog.Models.PostRequest
import com.example.blog.Models.PostResponse
import com.example.blog.Retrofit.ApiClient
import com.example.blog.databinding.FragmentPostBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private var _binding : FragmentPostBinding? = null
private val binding get() = _binding!!

var selectedTopic = "Select Category"

class PostFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setCurrentFragmentTag("fragment2")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topics = listOf("Select Category","Food", "Fashion", "Health", "Travel")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, topics)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.topicSpinner.adapter = adapter

        binding.topicSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedTopic = topics[position]
                // Use the selectedTopic variable as needed
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater,container,false)
        val view = binding.root

        print("\n")
        print(selectedTopic)
        print("\n")

        binding.progressBar.visibility = View.INVISIBLE

        val sharedPref = requireActivity().getSharedPreferences("AuthID", Context.MODE_PRIVATE)
        val userID = sharedPref.getString("ID", "admin@gmail.com")


        binding.PostButton.setOnClickListener {

            binding.progressBar.visibility = View.VISIBLE

            val map = mapOf("Fashion" to "5", "Food" to "4", "Travel" to "3", "Health" to "6")

            val title = binding.TitleData.text.toString()
            val content = binding.ContentData.text.toString()
            val image = binding.ImageData.text.toString()
            val category = map[selectedTopic]
            val author = userID.toString()


            val data = PostRequest(
                title =title, category =category, author = author, content = content, imageUrl = image)
            val send = ApiClient.userService.postBlog(data)



            if(selectedTopic == "Select Category")
            {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(activity,"Please select the category", Toast.LENGTH_LONG).show()
            }
            else{
                send.enqueue(object : Callback<PostResponse>
                {
                    override fun onResponse(
                        call: Call<PostResponse>,
                        response: Response<PostResponse>
                    ) {
                        if(response.isSuccessful)
                        {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(activity,"Blog posted successfully", Toast.LENGTH_LONG).show()
                            binding.TitleData.text = Editable.Factory.getInstance().newEditable("")
                            binding.ContentData.text = Editable.Factory.getInstance().newEditable("")
                            binding.ImageData.text = Editable.Factory.getInstance().newEditable("")
                            binding.topicSpinner.setSelection(0)

                        }
                        else {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(activity, "Invalid Request", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity,"Something went wrong", Toast.LENGTH_LONG).show()
                        binding.TitleData.text = Editable.Factory.getInstance().newEditable("")
                        binding.ContentData.text = Editable.Factory.getInstance().newEditable("")
                        binding.ImageData.text = Editable.Factory.getInstance().newEditable("")
                        binding.topicSpinner.setSelection(0)
                    }
                }
                )
            }

        }

        return view
    }


}