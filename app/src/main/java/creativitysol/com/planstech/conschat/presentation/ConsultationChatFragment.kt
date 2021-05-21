package creativitysol.com.planstech.conschat.presentation

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.ChannelEventListener
import com.pusher.client.channel.PusherEvent
import com.pusher.client.channel.SubscriptionEventListener
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import creativitysol.com.planstech.R
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.conschat.data.model.ChatMessages
import creativitysol.com.planstech.conschat.data.model.SenderBody
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.profile.ProfileResponse
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_consultations_chat.*
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class ConsultationChatFragment : Fragment() {

    var conversation = "0"
    private val consultationChatViewModel by viewModel<ConsultationChatViewModel>()
    private val mutableChatItems = ArrayList<ChatMessages.Data.Message>()
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
        btn_send_chat_message.isEnabled = false
        conv_txt.visibility = View.VISIBLE

        btn_send_chat_message.setOnClickListener {
            if (!TextUtils.isEmpty(edt_chat_message.text))
            /* chatHistoryAdapter.messages.add(ChatMessages.Data.Message().apply { message="jjjjjj"
             createdAt=""
             sender=ChatMessages.Data.Message.Sender().apply { id=1 }})
         chatHistoryAdapter.notifyDataSetChanged()*/
                consultationChatViewModel.sendChatMessage(
                    SenderBody(
                        conversation,
                        edt_chat_message.text.toString()
                    )
                )
        }

        consultationChatViewModel.getChatHistory.observe(viewLifecycleOwner, Observer {

            if (it != null && it.success) {
                connectPusher(it.data.channelId)
                conversation = it.data.conversationId.toString()
                if (it.data.messages.isNullOrEmpty()) {
                    btn_send_chat_message.isEnabled = false
                    conv_txt.visibility = View.VISIBLE

                } else {
                    //Toast.makeText(activity,it.archived.toString(),Toast.LENGTH_SHORT).show()
                    if (!it.data.status.equals("1")) {
                        btn_send_chat_message.isEnabled = false
                        Snackbar.make(rec_chat_history, "This conversation is not active", Snackbar.LENGTH_SHORT).show()

                    } else {
                        btn_send_chat_message.isEnabled = true

                    }
                    conv_txt.visibility = View.INVISIBLE
                    mutableChatItems.clear()
                    mutableChatItems.addAll(it.data.messages)
                    rec_chat_history.apply {
                        chatHistoryAdapter = ChatHistoryAdapter(it)
                        adapter = chatHistoryAdapter

                    }
                    chatHistoryAdapter.addMessages(mutableChatItems)
                    chatHistoryAdapter.notifyDataSetChanged()
                    rec_chat_history.scrollToPosition(mutableChatItems.size - 1)
                }
            }

        })

        consultationChatViewModel.getStatusSending.observe(viewLifecycleOwner, Observer {
            if (it) {
                edt_chat_message.text.clear()
                //consultationChatViewModel.requestChatHistory()
            }
        })

        consultationChatViewModel.errorSending.observe(viewLifecycleOwner, Observer {
            Snackbar.make(rec_chat_history, "Message Sent Failed :(", Snackbar.LENGTH_SHORT).show()
        })

        consultationChatViewModel.errorChatHistory.observe(viewLifecycleOwner, Observer {
            val error = if (it is UnknownHostException)
                getString(R.string.no_interet_connection)
            else "No conversation found"
            Snackbar.make(rec_chat_history, error, Snackbar.LENGTH_SHORT).show()
        })

    }


    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("الاستشارات")
    }

    fun connectPusher(chanel_id: String?) {
        val options = PusherOptions()
        options.setCluster("eu")
        val pusher = Pusher("c0f17cadcc697c1309f2", options) //app key
        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.e("pusher: State", " changed to " + change.currentState)
            }

            override fun onError(
                message: String,
                code: String,
                e: Exception
            ) {
                Log.e("pusher:problem", " connecting! msg:$message")
            }
        }, ConnectionState.ALL)
        val channel =
            pusher.subscribe(chanel_id, object : ChannelEventListener {
                //3e402d6eb0b608b7ada8033eae30c467d9980d67
                override fun onEvent(event: PusherEvent) {
                    println("Received event with data: " + event.data)
                }

                override fun onSubscriptionSucceeded(channelName: String) {
                    println("Subscribed to channel: $channelName")
                } // Other ChannelEventListener methods
            })
        channel.bind("talk-send-message",
            SubscriptionEventListener { event ->
                println("Received event with data: " + event.data)
                if (activity == null) return@SubscriptionEventListener
                activity!!.runOnUiThread {
                    try {
                        val jsonObject = JSONObject(event.data)



                        chatHistoryAdapter.messages.add(ChatMessages.Data.Message().apply {
                            message =  jsonObject.getString("message")
                            createdAt = jsonObject.getString("created_at")
                            sender = ChatMessages.Data.Message.Sender().apply { id = jsonObject.getString("user_id").toInt() }
                        })
                        chatHistoryAdapter.notifyDataSetChanged()
                        if (chatHistoryAdapter.messages.size > 0) {
                            rec_chat_history.scrollToPosition(chatHistoryAdapter.messages.size - 1)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
    }
}
