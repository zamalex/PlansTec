package creativitysol.com.planstech.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.bank_layout.*
import kotlinx.android.synthetic.main.fragment_payment_options.view.*

/**
 * A simple [Fragment] subclass.
 */
class PaymentOptionsFragment : Fragment() {


    lateinit var bankDialog: BottomSheetDialog
    lateinit var onlineDialog: BottomSheetDialog

    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_payment_options, container, false)

        bankDialog = BottomSheetDialog(requireActivity(),
            R.style.AppBottomSheetDialogTheme
        )

        bankDialog.setCancelable(false)

        bankDialog.setContentView(R.layout.bank_layout)


        onlineDialog = BottomSheetDialog(requireActivity(),
            R.style.AppBottomSheetDialogTheme
        )

        onlineDialog.setCancelable(false)

        onlineDialog.setContentView(R.layout.online_layout)



        v.bank_button.setOnClickListener {

            bankDialog.show()
        }

        v.online_button.setOnClickListener {

            onlineDialog.show()
        }

        bankDialog.exit.setOnClickListener {
            bankDialog.dismiss()
        }


        onlineDialog.exit.setOnClickListener {
            onlineDialog.dismiss()
        }


        return v
    }
    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("طرق الدفع")
    }
}
