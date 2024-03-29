package creativitysol.com.planstech.login

import addToFavouritesModule
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import creativitysol.com.planstech.base.BaseApp
import creativitysol.com.planstech.base.di.appModule
import creativitysol.com.planstech.conschat.di.chatModule
import creativitysol.com.planstech.consultation_request_questions.di.requestConsultationModule
import creativitysol.com.planstech.home.HomeFragment
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.main.SplashScreenActivity
import creativitysol.com.planstech.notifications.di.notificationsModule
import creativitysol.com.planstech.password.di.forgotPassModule
import creativitysol.com.planstech.password.di.resetPassModule
import creativitysol.com.planstech.password.presentation.ForgotPasswordFragment
import creativitysol.com.planstech.register.RegisterFragment
import creativitysol.com.planstech.util.Constants
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_login.view.*
import listFavouritesModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    lateinit var v: View

    @NotEmpty(message = "enter valid email address")
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
                jsonObject.addProperty("email", log_mail.text.toString())
                jsonObject.addProperty("password", log_pass.text.toString())

                (requireActivity() as MainActivity).showProgress(true)

                viewModel.login(jsonObject)
            }
        })


        viewModel.result.observe(requireActivity(), Observer {
            if (isAdded) {
                (requireActivity() as MainActivity).showProgress(false)
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()

                if (it != null && !it.data.token.isEmpty()) {
                    Paper.book().write("login", it)

                  //  activity?.application?.onCreate()

                    //(requireActivity() as MainActivity).fragmentStack.replace(HomeFragment())
                   triggerRestart(requireActivity())

                }
            }
        })
        v.go_forgot.setOnClickListener {
            (activity as MainActivity).fragmentStack.push(ForgotPasswordFragment())
        }

        v.login_btn.setOnClickListener {
            validator.validate()
        }

        v.textView22.setOnClickListener {
            (requireActivity() as MainActivity).fragmentStack.push(RegisterFragment())

        }
        // Inflate the layout for this fragment
        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.loginnnn))
    }

    fun triggerRestart(context: Activity) {
        val intent = Intent(context, SplashScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            (context as Activity).finish()
        }
    }


}
