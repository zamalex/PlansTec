package creativitysol.com.planstech.password.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Password
import creativitysol.com.planstech.R
import creativitysol.com.planstech.login.LoginFragment
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.password.data.model.ResetPasswordBody
import creativitysol.com.planstech.util.Constants
import kotlinx.android.synthetic.main.fragment_reset_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ResetPasswordFragment : Fragment() {


    @Password(message = "enter valid input")
    lateinit var reset_pass: TextInputEditText

    @Password(message = "enter valid input")
    lateinit var reset_repass: TextInputEditText

    private val TAG = "ResetPasswordFragment"

    lateinit var v: View

    lateinit var validator: Validator

    private lateinit var verificationCode: String

    private val resetPasswordViewModel by viewModel<ResetPasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_reset_password, container, false)

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
                resetPasswordViewModel.resetPassword(
                    ResetPasswordBody(
                        password = reset_pass.text.toString(),
                        confirmPassword = reset_pass.text.toString(),
                        code = verificationCode
                    )
                )
            }
        })

        resetPasswordViewModel.verificationCode.observe(
            viewLifecycleOwner, {
                when (it.statusCode) {
                    200L -> (activity as MainActivity).fragmentStack.push(LoginFragment())
                    else -> Snackbar.make(
                        reset_pass, "${it.message}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        )

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(Constants.VERIFICATION_CODE).let {
            verificationCode = it!!
            Log.e(TAG, "onViewCreated:  $it")
        }
        arguments?.getString(Constants.EMAIL).let {
            txt_email_msg.text = String.format(getString(R.string.email_text), it)
        }

        btn_reset_password.setOnClickListener {
            validator.validate()
        }

    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("استعادة كلمة المرور")
    }

}
