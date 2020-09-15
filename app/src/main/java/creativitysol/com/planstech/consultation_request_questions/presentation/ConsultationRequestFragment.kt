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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import creativitysol.com.planstech.R
import creativitysol.com.planstech.consultation_request_questions.data.model.RequestConsultationBody
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.datepicker_dialog.*
import kotlinx.android.synthetic.main.datepicker_dialog.cncl
import kotlinx.android.synthetic.main.datepicker_dialog.pick
import kotlinx.android.synthetic.main.fragment_request_consultation.*
import kotlinx.android.synthetic.main.fragment_request_consultation.view.*
import kotlinx.android.synthetic.main.timepicker_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConsultationRequestFragment : Fragment() {

    private lateinit var dateDialog: Dialog
    private lateinit var timeDialog: Dialog
    private var activityId = 1
    private var isProjectId = 1
    var haveMoneyId = 1
    var methodId = 1
    var typeId = 1
    var consultTime = ""
    var consultDate = ""

    private val requestConsultationViewModel by viewModel<RequestConsultationViewModel>()

    lateinit var v: View
    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_request_consultation, container, false)

        dateDialog = Dialog(requireActivity())
        dateDialog.setContentView(R.layout.datepicker_dialog)
        dateDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dateDialog.setCancelable(false)
        dateDialog.pick.setOnClickListener {
            consultDate =
                "${dateDialog.picker.year}-${(1 + dateDialog.picker.month)}-${dateDialog.picker.dayOfMonth}"
            btn_date.text = consultDate
            dateDialog.dismiss()
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
            timeDialog.show()
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
            requestConsultationViewModel.requestConsultation(
                RequestConsultationBody(
                    activity = activityId, notes = edt_notes.text.toString(),
                    isProject = isProjectId, hasMoney = haveMoneyId,
                    answer = edt_money_answer.text.toString(), method = methodId,
                    type = typeId, date = consultDate, time = consultTime
                )
            )
        }

        requestConsultationViewModel.consultationStatus.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(requireContext(), "Success, Consultation has been sent", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).onBackPressed()
            }
        })

        requestConsultationViewModel.error.observe(viewLifecycleOwner, {
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
            TextUtils.isEmpty(consultTime) -> {
                Snackbar.make(btn_time, "Select time", Snackbar.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("طلب استشارة")
    }
}
