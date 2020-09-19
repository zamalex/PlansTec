package creativitysol.com.planstech.conschat.presentation

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import creativitysol.com.planstech.R
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.conschat.data.model.ChatHistory
import creativitysol.com.planstech.conschat.data.model.SenderBody
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_consultations_chat.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class ConsultationChatFragment : Fragment() {

    private val consultationChatViewModel by viewModel<ConsultationChatViewModel>()
    private val mutableChatItems = mutableListOf<ChatHistory>()
    private lateinit var chatHistoryAdapter: ChatHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_consultations_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        consultationChatViewModel.requestChatHistory()

        btn_send_chat_message.setOnClickListener {
            if (TextUtils.isEmpty(edt_chat_message.text))
                consultationChatViewModel.sendChatMessage(
                    SenderBody(
                        chatHistoryAdapter.getReceiverId()!!,
                        edt_chat_message.text.toString()
                    )
                )
        }

        consultationChatViewModel.getChatHistory.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty())
                btn_send_chat_message.isEnabled = false
            else {
                btn_send_chat_message.isEnabled = true
                mutableChatItems.clear()
                mutableChatItems.addAll(it)
                rec_chat_history.apply {
                    chatHistoryAdapter = ChatHistoryAdapter(mutableChatItems)
                    adapter = chatHistoryAdapter
                }
                chatHistoryAdapter.notifyDataSetChanged()
                rec_chat_history.scrollToPosition(mutableChatItems.size - 1)
            }
        })

        consultationChatViewModel.getStatusSending.observe(viewLifecycleOwner, {
            if (it) {
                edt_chat_message.text.clear()
                consultationChatViewModel.requestChatHistory()
            }
        })

        consultationChatViewModel.errorSending.observe(viewLifecycleOwner, {
            Snackbar.make(rec_chat_history, "Message Sent Failed :(", Snackbar.LENGTH_SHORT).show()
        })

        consultationChatViewModel.errorChatHistory.observe(viewLifecycleOwner, {
            val error = if (it is UnknownHostException)
                getString(R.string.no_interet_connection)
            else getString(R.string.general_issue)
            Snackbar.make(rec_chat_history, error, Snackbar.LENGTH_SHORT).show()
        })

        scheduleRequestingHistory()
    }

    private fun scheduleRequestingHistory(): Disposable {
        return Observable.interval(
            15L, TimeUnit.SECONDS
        )
            .subscribeOn(get<Executors>().getIOThread())
            .subscribe {
                consultationChatViewModel.requestChatHistory()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!scheduleRequestingHistory().isDisposed)
            scheduleRequestingHistory().dispose()
    }
}
