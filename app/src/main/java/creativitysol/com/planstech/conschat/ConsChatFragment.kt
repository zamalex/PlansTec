package creativitysol.com.planstech.conschat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import creativitysol.com.planstech.R
import kotlinx.android.synthetic.main.fragment_cons_chat.view.*

/**
 * A simple [Fragment] subclass.
 */
class ConsChatFragment : Fragment() {

    lateinit var v:View

    lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v=inflater.inflate(R.layout.fragment_cons_chat, container, false)

        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        v.chat_rv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter =
                ChatRVAdapter(
                    requireActivity()
                )
        }
        // Inflate the layout for this fragment
        return v
    }

}
