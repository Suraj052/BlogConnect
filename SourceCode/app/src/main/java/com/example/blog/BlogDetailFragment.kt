package com.example.blog

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.blog.databinding.FragmentBlogDetailBinding
import java.text.SimpleDateFormat
import java.util.*


private var _binding : FragmentBlogDetailBinding? = null
private val binding get() = _binding!!

class BlogDetailFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setCurrentFragmentTag("fragment4")
    }

    private val args : BlogDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBlogDetailBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.btitle.text = args.btitle
        binding.bcontent.text = args.bcontent
        binding.bdate.text = convertDateFormat(args.bdate)
        binding.btime.text = convertTimeFormat(args.bdate)
        binding.bauthor.text = args.bauthor
        binding.bcategory.text = args.bcategory
        Glide.with(requireContext())
            .load(args.bimage)
            .into(binding.imageurl)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_blogDetailFragment_to_homeFragment)

        }

        val content = "TITLE\n${args.btitle}\n\nCONTENT\n${args.bcontent}\n\nAUTHOR\n${args.bauthor}"
        binding.shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, content)
            val chooser = Intent.createChooser(shareIntent, "Share the blog")
            startActivity(chooser)
        }

        return view
    }

}


fun convertDateFormat(dateString: String): String {

    val inputFormatWithMillis = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    val inputFormatWithoutMillis = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val format = if (dateString.contains(".")) {
        inputFormatWithMillis
    } else {
        inputFormatWithoutMillis
    }

    val inputFormat = SimpleDateFormat(format, Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date!!)


}

fun convertTimeFormat(dateString: String): String {
    val inputFormatWithMillis = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    val inputFormatWithoutMillis = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val format = if (dateString.contains(".")) {
        inputFormatWithMillis
    } else {
        inputFormatWithoutMillis
    }

    val inputFormat = SimpleDateFormat(format, Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date!!)
}

