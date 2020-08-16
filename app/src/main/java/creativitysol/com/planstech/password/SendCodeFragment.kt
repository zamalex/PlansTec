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
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.Password
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import kotlinx.android.synthetic.main.fragment_send_code.view.*

/**
 * A simple [Fragment] subclass.
 */
class SendCodeFragment : Fragment() {

    @Email(message = "enter valid email")
    lateinit var co_mail: TextInputEditText

    lateinit var validator: Validator

    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_send_code, container, false)
        co_mail = v.findViewById(R.id.code_mail)

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
                (activity as MainActivity).fragmentStack.push(ResetPasswordFragment())
            }
        })



        v.go_reset.setOnClickListener {
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
