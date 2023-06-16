package com.example.blog

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.blog.Models.UserRequest
import com.example.blog.Models.UserResponse
import com.example.blog.Retrofit.ApiClient
import com.example.blog.databinding.FragmentRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private var _binding : FragmentRegisterBinding? = null
private val binding get() = _binding!!
class RegisterFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setCurrentFragmentTag("fragment11")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.Rprogress.visibility = View.INVISIBLE

        binding.btn.setOnClickListener{

            binding.Rprogress.visibility = View.VISIBLE

            val emailValue = binding.Remail.text.toString()
            val passValue = binding.Rpassword.text.toString()
            val user = binding.Rfullname.text.toString()
            val full = binding.Rfullname.text.toString()
            val about = binding.Rabout.text.toString()

            val data = UserRequest(email = emailValue, password = passValue, user_name = user, first_name = full, about = about)
            val send = ApiClient.userService.registerUser(data)

            send.enqueue(object : Callback<UserResponse>
            {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        binding.Rprogress.visibility = View.GONE
                        Log.d("test owner",response.body().toString())
                        Toast.makeText(activity,"User registered successfully", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                        isAuthSaved(emailValue,passValue)
                    }
                    else {
                        binding.Rprogress.visibility = View.GONE
                        Toast.makeText(activity, "Invalid Request", Toast.LENGTH_SHORT).show()
                        Log.d("test owner", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    binding.Rprogress.visibility = View.GONE
                    Toast.makeText(activity,"Something went wrong", Toast.LENGTH_LONG).show()
                }
            })

        }

        binding.Rlogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return view
    }

    private fun isAuthSaved(email : String,pass : String){
        val sharedPref = requireActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.putString("Email",email)
        editor.putString("Password",pass)
        editor.apply()
    }

}