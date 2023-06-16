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
import com.example.blog.Models.LoginRequest
import com.example.blog.Models.LoginResponse
import com.example.blog.Models.UserRequest
import com.example.blog.Models.UserResponse
import com.example.blog.Retrofit.ApiClient
import com.example.blog.databinding.FragmentLoginBinding
import com.example.blog.databinding.FragmentRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var _binding : FragmentLoginBinding? = null
private val binding get() = _binding!!
class LoginFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setCurrentFragmentTag("fragment10")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.Rprogress.visibility = View.INVISIBLE
        binding.btn.setOnClickListener {
            binding.Rprogress.visibility = View.VISIBLE

            val email = binding.Lemailid.text.toString()
            val pass = binding.Lpass.text.toString()

            val data = LoginRequest(email = email, password = pass)
            val send = ApiClient.userService.loginUser(data)

            send.enqueue(object : Callback<LoginResponse>
            {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        binding.Rprogress.visibility = View.GONE
                        Log.d("test owner",response.body().toString())
                        Toast.makeText(activity,"User logged in successfully", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        isAuthSaved(email,pass)
                    }
                    else if(response.code()==401)
                    {
                        binding.Rprogress.visibility = View.GONE
                        Toast.makeText(activity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        binding.Rprogress.visibility = View.GONE
                        Toast.makeText(activity, "Invalid Request", Toast.LENGTH_SHORT).show()
                        Log.d("test owner", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    binding.Rprogress.visibility = View.GONE
                    Toast.makeText(activity,"Something went wrong", Toast.LENGTH_LONG).show()
                }
            })

        }

        binding.Lregister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
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