package creativitysol.com.planstech.consultation_request_questions.presentation

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import creativitysol.com.planstech.R
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.consultation_request_questions.data.model.RequestConsultationBody
import creativitysol.com.planstech.consultation_request_questions.data.model.TimesModel
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.datepicker_dialog.*
import kotlinx.android.synthetic.main.datepicker_dialog.cncl
import kotlinx.android.synthetic.main.datepicker_dialog.pick
import kotlinx.android.synthetic.main.fragment_request_consultation.*
import kotlinx.android.synthetic.main.fragment_request_consultation.view.*
import kotlinx.android.synthetic.main.timepicker_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Response

class ConsultationRequestFragment : Fragment() {

    var selectetSlot = 0
    private lateinit var dateDialog: Dialog
    private lateinit var timeDialog: Dialog
    private var activityId = 1
    private var isProjectId = 1
    var haveMoneyId = 1
    var methodId = 1
    var typeId = 1
    var consultTime = ""
    var consultDate = ""
var timesModel : TimesModel = TimesModel()
    private val requestConsultationViewModel by viewModel<RequestConsultationViewModel>()

    lateinit var v: View
    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_request_consultation, container, false)

        val menu = PopupMenu(requireActivity(), v.btn_time)




        dateDialog = Dialog(requireActivity())
        dateDialog.setContentView(R.layout.datepicker_dialog)
        dateDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dateDialog.setCancelable(false)
        dateDialog.pick.setOnClickListener {
            val sDay = if(dateDialog.picker.dayOfMonth.toString().length==1)"0${dateDialog.picker.dayOfMonth}" else dateDialog.picker.dayOfMonth.toString()
            val sMonth = if((1 + dateDialog.picker.month).toString().length==1)"0${(1 + dateDialog.picker.month)}" else (1 + dateDialog.picker.month).toString()
            consultDate =
                "${dateDialog.picker.year}-${sMonth}-${sDay}"
            btn_date.text = consultDate
            dateDialog.dismiss()

            (activity as MainActivity).loading.show()
            Retrofit.Api.getAvailableTimes(consultDate,"Bearer ${(Paper.book().read("login",
                LoginModel()
            )as LoginModel).data.token}").enqueue(object : retrofit2.Callback<TimesModel>{
                override fun onFailure(call: Call<TimesModel>, t: Throwable) {
                    (activity as MainActivity).loading.dismiss()
                    selectetSlot = 0
                }

                override fun onResponse(call: Call<TimesModel>, response: Response<TimesModel>) {
                    (activity as MainActivity).loading.dismiss()
                    selectetSlot = 0
                    if (response.isSuccessful&&response.body()!=null){
                        timesModel = response.body()!!


                    }

                }
            })
        }

        dateDialog.cncl.setOnClickListener {
            dateDialog.dismiss()
        }

        v.btn_date.setOnClickListener {
            dateDialog.show()
        }


        timeDialog = Dialog(requireActivity())
        timeDialog.setContentView(R.layout.timepicker_dialog)
        timeDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        timeDialog.setCancelable(false)

        timeDialog.pick.setOnClickListener {
            consultTime =
                "${timeDialog.tpicker.hour}:${timeDialog.tpicker.minute}"
            btn_time.text = consultTime
            timeDialog.dismiss()
        }

        timeDialog.cncl.setOnClickListener {
            timeDialog.dismiss()
        }

        v.btn_time.setOnClickListener {
           // timeDialog.show()
            if (timesModel.slots.isNullOrEmpty()){
                Snackbar.make(
                    btn_date_send_consultation, "No available time",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            menu.menu.clear()


            timesModel.slots.forEachIndexed { index, slot ->
                menu.menu.add(0,slot.id,index,"${slot.from} to ${slot.to}")

            }
            menu.show()

            menu.setOnMenuItemClickListener { menuItem ->
                //Toast.makeText(requireActivity(),menuItem.itemId.toString(),Toast.LENGTH_SHORT).show()
                v.btn_time.setText(menuItem.title)
                selectetSlot = menuItem.itemId
                true
            }
        }



        timeDialog.cncl.setOnClickListener {
            timeDialog.dismiss()

        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rg_project_activity.setOnCheckedChangeListener { _, i ->
            activityId = i + 1
        }

        rg_project_is.setOnCheckedChangeListener { _, i ->
            isProjectId = i + 1
        }

        rg_project_have_money.setOnCheckedChangeListener { _, i ->
            haveMoneyId = i + 1
        }

        rg_consult_method.setOnCheckedChangeListener { _, i ->
            methodId = i + 1
        }

        rg_consult_type.setOnCheckedChangeListener { _, i ->
            typeId = i + 1
        }

        btn_date_send_consultation.setOnClickListener {
            if (!isValidUI())
                return@setOnClickListener
            (activity as MainActivity).loading.show()

            requestConsultationViewModel.requestConsultation(
                RequestConsultationBody(
                    activity = activityId, notes = edt_notes.text.toString(),
                    isProject = isProjectId, hasMoney = haveMoneyId,
                    answer = edt_money_answer.text.toString(), method = methodId,
                    type = typeId, date = consultDate, time = "00:00:00",slot = selectetSlot
                )
            )
        }

        requestConsultationViewModel.consultationStatus.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).loading.dismiss()

            if (it) {
                Toast.makeText(requireContext(), "Success, Consultation has been sent", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).onBackPressed()
            }
        })

        requestConsultationViewModel.error.observe(viewLifecycleOwner,
            Observer{
                (activity as MainActivity).loading.dismiss()

                Snackbar.make(
                btn_date_send_consultation, "${it.localizedMessage}",
                Snackbar.LENGTH_SHORT
            ).show()
        })
    }

    private fun isValidUI(): Boolean {
        return when {
            TextUtils.isEmpty(edt_notes.text) -> {
                edt_notes.error = "Enter notes"
                Snackbar.make(btn_time, "Missing notes area", Snackbar.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(consultDate) -> {
                Snackbar.make(btn_time, "Select date", Snackbar.LENGTH_SHORT).show()
                false
            }
           /* TextUtils.isEmpty(consultTime) -> {
                Snackbar.make(btn_time, "Select time", Snackbar.LENGTH_SHORT).show()
                false
            }*/
            selectetSlot==0 -> {
                Snackbar.make(btn_time, "Select time", Snackbar.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.reqcons))
    }
}
