package creativitysol.com.planstech.follow

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_follow.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FollowFragment : Fragment() {

    private val viewModel: FollowViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application!!)
            .create(FollowViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFollow().observe(viewLifecycleOwner, Observer {
            if (it != null && it.statusCode == 200) {
                var intent: Intent

                it.data.let { i ->
                    twitter.setOnClickListener {
                        if (i.social.twitter.isEmpty())
                            return@setOnClickListener
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(i.social.twitter))
                        requireActivity().startActivity(intent)
                    }
                    youtube.setOnClickListener {
                        if (i.social.youtube.isEmpty())
                            return@setOnClickListener
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(i.social.youtube))
                        requireActivity().startActivity(intent)
                    }
                    website.setOnClickListener {
                        if (i.social.website.isEmpty())
                            return@setOnClickListener
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(i.social.website))
                        requireActivity().startActivity(intent)
                    }
                    instagram.setOnClickListener { }
                    whatsapp.setOnClickListener {
                        if (i.social.whatsapp.isEmpty())
                            return@setOnClickListener
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(i.social.whatsapp))
                        requireActivity().startActivity(intent)
                    }
                    linkedin.setOnClickListener {
                        if (i.social.linkedin.isEmpty())
                            return@setOnClickListener
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(i.social.linkedin))
                        requireActivity().startActivity(intent)
                    }
                    facebook.setOnClickListener {
                        if (i.social.facebook.isEmpty())
                            return@setOnClickListener
                        intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(i.social.facebook))
                        requireActivity().startActivity(intent)
                    }
                    location.setOnClickListener {
                        if (i.map.lat.isEmpty() || i.map.long.isEmpty())
                            return@setOnClickListener
                        val uri: String = java.lang.String.format(
                            Locale.ENGLISH,
                            "geo:%f,%f",
                            -31.083332,
                            150.916672
                        )
                        intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                        requireActivity().startActivity(intent)
                    }
                    location.setText(it.data.address)
                }

            }
        })

    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.followu))
    }
}
