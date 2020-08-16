package creativitysol.com.planstech.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Password
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_reset_password.view.*

/**
 * A simple [Fragment] subclass.
 */
class ResetPasswordFragment : Fragment() {


    @Password(message = "enter valid input")
    lateinit var reset_pass: TextInputEditText

    @Password(message = "enter valid input")
    lateinit var reset_repass: TextInputEditText


    lateinit var v:View

    lateinit var validator: Validator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.fragment_reset_password, container, false)

        reset_pass = v.findViewById(R.id.reset_pass)
        reset_repass = v.findViewById(R.id.reset_repass)

        validator = Validator(this)

        validator.setValidationListener(object : Validator.ValidationListener {
            override fun onValidationFailed(errors: MutableList<ValidationError>?) {
                for (error in errors!!) {
                    val view = error.view
                    val message = error.getCollatedErrorMessage(activity)
                    (view as EditText).error = message


                }
            }

            override fun onValidationSucceeded() {
                Toast.makeText(requireActivity(), "success", Toast.LENGTH_LONG).show()
            }
        })


        v.reset_btn.setOnClickListener {
            validator.validate()
        }
        // Inflate the layout for this fragment
        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("استعادة كلمة المرور")
    }

}
