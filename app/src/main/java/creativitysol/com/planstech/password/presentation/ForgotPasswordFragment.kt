package creativitysol.com.planstech.password.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import creativitysol.com.planstech.R
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.util.Constants
import kotlinx.android.synthetic.main.fragment_forget_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.UnknownHostException

class ForgotPasswordFragment : Fragment() {

    private val forgotPasswordViewModel by viewModel<ForgotPasswordViewModel>()

    @Email(message = "enter valid email")
    lateinit var edtEmail: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_forget_password, container, false)
        edtEmail = root.findViewById(R.id.edt_email)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val validator = Validator(this)

        validator.setValidationListener(object : Validator.ValidationListener {
            override fun onValidationFailed(errors: MutableList<ValidationError>?) {
                for (error in errors!!) {
                    (error.view as EditText).error =
                        error.getCollatedErrorMessage(activity)
                }
            }

            override fun onValidationSucceeded() {
                forgotPasswordViewModel.forgotPassword(edtEmail.text.toString())
            }
        })

        forgotPasswordViewModel.verificationCode.observe(viewLifecycleOwner, {
            val bundle = bundleOf(
                Constants.VERIFICATION_CODE to it,
                Constants.EMAIL to edtEmail.text.toString()
            )

            val fragment = ResetPasswordFragment().apply { arguments = bundle }

            (activity as MainActivity).apply {
                fragmentStack.push(fragment)
            }
        })

        forgotPasswordViewModel.error.observe(viewLifecycleOwner, {
            val error = when (it) {
                is UnknownHostException -> getString(R.string.no_interet_connection)
                else -> getString(R.string.general_issue)
            }
            Snackbar.make(btn_reset_password, error, Snackbar.LENGTH_SHORT).show()
        })

        btn_reset_password.setOnClickListener {
            validator.validate()
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("استعادة كلمة المرور")
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.currentState == Lifecycle.State.DESTROYED
    }
}
