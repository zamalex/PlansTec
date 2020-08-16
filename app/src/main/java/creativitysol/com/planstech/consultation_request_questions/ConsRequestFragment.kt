package creativitysol.com.planstech.consultation_request_questions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.datepicker_dialog.*
import kotlinx.android.synthetic.main.datepicker_dialog.cncl
import kotlinx.android.synthetic.main.datepicker_dialog.pick
import kotlinx.android.synthetic.main.fragment_cons_request.view.*

/**
 * A simple [Fragment] subclass.
 */
class ConsRequestFragment : Fragment() {

    lateinit var dateDialog: Dialog
    lateinit var timeDilaog: Dialog

    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_cons_request, container, false)

        dateDialog = Dialog(requireActivity())

        dateDialog.setContentView(R.layout.datepicker_dialog)
        dateDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        timeDilaog = Dialog(requireActivity())

        timeDilaog.setContentView(R.layout.timepicker_dialog)
        timeDilaog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        timeDilaog.setCancelable(false)


        dateDialog.setCancelable(false)
        dateDialog.pick.setOnClickListener {
            Toast.makeText(
                requireActivity(),
                dateDialog.picker.dayOfMonth.toString() + " " + (1 + dateDialog.picker.month) + " " + dateDialog.picker.year,
                Toast.LENGTH_LONG
            ).show()
        }

        dateDialog.cncl.setOnClickListener {
            dateDialog.dismiss()
        }

        v.date_of_cons.setOnClickListener {
            dateDialog.show()
        }



        v.pick_cons_time.setOnClickListener {
          //  timeDilaog.tpicker.setTime(CmtpTime12(10, 10, CmtpTime12.PmAm.PM))

            timeDilaog.show()
        }



        timeDilaog.cncl.setOnClickListener {
            timeDilaog.dismiss()
        }


        // Inflate the layout for this fragment
        return v
    }
    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("طلب استشارة")
    }
}
