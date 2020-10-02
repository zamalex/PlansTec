package creativitysol.com.planstech.password.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import creativitysol.com.planstech.R
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.util.Constants
import kotlinx.android.synthetic.main.fragment_forget_password.*
import kotlinx.android.synthetic.main.verify_dialog.*
import okhttp3.Callback
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Response
import java.net.UnknownHostException

class ForgotPasswordFragment : Fragment() {

    private val forgotPasswordViewModel by viewModel<ForgotPasswordViewModel>()

    @Email(message = "enter valid email")
    lateinit var edtEmail: TextInputEditText
    lateinit var dialog: Dialog

    var sMail: String = ""
    var sCode: String = ""

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

        dialog = Dialog(requireActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)


        dialog.setContentView(R.layout.verify_dialog)


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
            /*val bundle = bundleOf(
                Constants.VERIFICATION_CODE to it,
                Constants.EMAIL to edtEmail.text.toString()
            )

            val fragment = ResetPasswordFragment().apply { arguments = bundle }


            (activity as MainActivity).apply {
                fragmentStack.push(fragment)
            }*/
            sMail = edtEmail.text.toString()
            dialog.show()
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


        dialog.done.setOnClickListener {
            if (!dialog.code_et.text.toString().isEmpty()) {
                dialog.dismiss()

                ((requireActivity())as MainActivity).showProgress(true)
                Retrofit.Api.verifyCode(dialog.code_et.text.toString())
                    .enqueue(object : retrofit2.Callback<VerifyResponse> {
                        override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                            ((requireActivity())as MainActivity).showProgress(false)

                        }

                        override fun onResponse(
                            call: Call<VerifyResponse>,
                            response: Response<VerifyResponse>
                        ) {
                            ((requireActivity())as MainActivity).showProgress(false)

                            if (response.isSuccessful) {


                                if (response.body()!!.statusCode == 200) {
                                    val bundle = bundleOf(
                                        Constants.VERIFICATION_CODE to dialog.code_et.text.toString(),
                                        Constants.EMAIL to edtEmail.text.toString()
                                    )

                                    val fragment =
                                        ResetPasswordFragment().apply { arguments = bundle }


                                    (activity as MainActivity).apply {
                                        fragmentStack.push(fragment)
                                    }
                                } else Toast.makeText(
                                    requireActivity(),
                                    "الكود خطأ",
                                    Toast.LENGTH_SHORT
                                ).show()


                            } else Toast.makeText(
                                requireActivity(),
                                "الكود خطأ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }

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
