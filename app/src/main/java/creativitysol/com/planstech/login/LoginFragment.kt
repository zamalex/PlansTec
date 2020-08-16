package creativitysol.com.planstech.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import creativitysol.com.planstech.R
import creativitysol.com.planstech.password.SendCodeFragment
import creativitysol.com.planstech.main.MainActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_login.view.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    lateinit var v:View
    @Email(message = "enter valid email address")
    lateinit var log_mail: TextInputEditText



    @Password(message = "enter valid input")
    lateinit var log_pass: TextInputEditText


    lateinit var validator: Validator

    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_login, container, false)

        log_mail = v.findViewById(R.id.login_mail)
        log_pass = v.findViewById(R.id.login_pass)

        validator = Validator(this)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        validator.setValidationListener(object : Validator.ValidationListener {
            override fun onValidationFailed(errors: MutableList<ValidationError>?) {
                for (error in errors!!) {
                    val view = error.view
                    val message = error.getCollatedErrorMessage(activity)
                    (view as EditText).error = message


                }
            }

            override fun onValidationSucceeded() {
                //Toast.makeText(requireActivity(), "success", Toast.LENGTH_LONG).show()
                var jsonObject = JsonObject()
                jsonObject.addProperty("email",log_mail.text.toString())
                jsonObject.addProperty("password",log_pass.text.toString())

                viewModel.login(jsonObject)
            }
        })


        viewModel.result.observe(requireActivity(), Observer {

            if (it!=null&&!it.accessToken.isEmpty()){
                Paper.book().write("login", it);

            }

        })
        v.go_forgot.setOnClickListener {
            (activity as MainActivity).fragmentStack.push(SendCodeFragment())
        }

        v.login_btn.setOnClickListener {
            validator.validate()
        }
        // Inflate the layout for this fragment
        return v
    }
    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("تسجيل دخول")
    }
}
