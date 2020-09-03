package creativitysol.com.planstech.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import creativitysol.com.planstech.R
import creativitysol.com.planstech.login.LoginFragment
import creativitysol.com.planstech.main.MainActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_register.view.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    lateinit var v: View

    @NotEmpty(message = "enter valid input")
    lateinit var reg_name: TextInputEditText

    @Email(message = "enter valid email address")
    lateinit var reg_mail: TextInputEditText

    @NotEmpty(message = "enter phone number")
    lateinit var reg_phone: TextInputEditText

    @Password(message = "enter valid input")
    lateinit var reg_pass: TextInputEditText

    @NotEmpty(message = "enter valid input")
    lateinit var reg_city: TextInputEditText

    @NotEmpty(message = "enter valid input")
    lateinit var reg_loc: TextInputEditText


    lateinit var validator: Validator

    lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_register, container, false)

        reg_name = v.findViewById(R.id.register_name)
        reg_mail = v.findViewById(R.id.register_mail)
        reg_phone = v.findViewById(R.id.register_phone)
        reg_pass = v.findViewById(R.id.register_pass)
        reg_city = v.findViewById(R.id.register_city)
        reg_loc = v.findViewById(R.id.register_loc)

        validator = Validator(this)


        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

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

                jsonObject.addProperty("name",reg_name.text.toString())
                jsonObject.addProperty("email",reg_mail.text.toString())
                jsonObject.addProperty("password",reg_pass.text.toString())
                jsonObject.addProperty("city",reg_city.text.toString())
                jsonObject.addProperty("district",reg_loc.text.toString())
                jsonObject.addProperty("confirm_password",reg_pass.text.toString())

                (requireActivity() as MainActivity).showProgress(true)

                viewModel.register(jsonObject)
            }
        })

        viewModel.result.observe(requireActivity(), Observer {
            (requireActivity() as MainActivity).showProgress(false)

            if (it.statusCode==200){
                Toast.makeText(requireActivity(), "success", Toast.LENGTH_LONG).show()

                Paper.book().write("user", it);
                (requireActivity() as MainActivity).fragmentStack.replace(LoginFragment())
            }
            else
                Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_LONG).show()
        })

        v.go_login.setOnClickListener {
            (activity as MainActivity).fragmentStack.push(LoginFragment())
        }


        v.register_btn.setOnClickListener {
            validator.validate()
        }
        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("انشاء حساب")
    }
}
