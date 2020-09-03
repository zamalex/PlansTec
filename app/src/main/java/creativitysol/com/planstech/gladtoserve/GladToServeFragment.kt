package creativitysol.com.planstech.gladtoserve

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.jaiselrahman.hintspinner.HintSpinnerAdapter
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import creativitysol.com.planstech.R
import creativitysol.com.planstech.gladtoserve.model.Services
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_glad_to_serve.view.*

/**
 * A simple [Fragment] subclass.
 */
class GladToServeFragment : Fragment() {

    lateinit var v: View

    @NotEmpty
    lateinit var sname: EditText

    @NotEmpty
    lateinit var smail: EditText

    @NotEmpty
    lateinit var sphone: EditText

    @NotEmpty
    lateinit var smsg: EditText

    lateinit var validator: Validator

    lateinit var viewModel: ContactViewModel

    lateinit var services: Services

    var arr:Array<String> = arrayOf()
    var arrayList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_glad_to_serve, container, false)

        var log: LoginModel = Paper.book().read("login", LoginModel())

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

       // if (!log.data.token.isEmpty())
            viewModel.getServices("Bearer " + log.data.token)


        sname = v.findViewById(R.id.sname)
        smail = v.findViewById(R.id.smail)
        sphone = v.findViewById(R.id.sphone)
        smsg = v.findViewById(R.id.smsg)

        validator = Validator(this)

        v.ssend.setOnClickListener {
            validator.validate()
        }


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

                jsonObject.addProperty("service_type_id", services.data[v.spinner.selectedItemPosition-1].id)
                jsonObject.addProperty("name", sname.text.toString())
                jsonObject.addProperty("email_address", smail.text.toString())
                jsonObject.addProperty("mobile", sphone.text.toString())
                jsonObject.addProperty("message", smsg.text.toString())

                if (!log.data.token.isEmpty()) {
                    (requireActivity() as MainActivity).showProgress(true)

                    viewModel.send(jsonObject, "Bearer " + log.data.token)
                }
            }
        })





        v.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0)
                    Toast.makeText(requireActivity(), arrayList[position - 1], Toast.LENGTH_LONG).show()
            }
        }


        viewModel.services.observe(requireActivity(), Observer {
            (requireActivity() as MainActivity).showProgress(false)

            if (it.statusCode == 200){
                services = it


                for (i in 0 until it.data.size){
                   arrayList.add(it.data[i].title)
                }

                println("ssss ${arrayList.size}")

                v.spinner.adapter = HintSpinnerAdapter(requireActivity(), arrayList.toArray(), "نوع الخدمة")

            }

        })
        // Inflate the layout for this fragment
        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("نسعد لخدمتكم")
    }
}
