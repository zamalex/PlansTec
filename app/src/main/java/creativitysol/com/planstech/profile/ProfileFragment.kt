package creativitysol.com.planstech.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.squareup.picasso.Picasso
import creativitysol.com.planstech.R
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.register.model.RegisterModel
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class ProfileFragment : Fragment() , PickiTCallbacks {



    var pickiT: PickiT? = null
     var body: MultipartBody.Part? = null

    lateinit var v: View

    var map:HashMap<String, RequestBody> = HashMap<String, RequestBody>()
    val loginModel: LoginModel by lazy {
        Paper.book().read("login", LoginModel())
    }


    val userModel: RegisterModel by lazy {
        Paper.book().read("user", RegisterModel())
    }

    @NotEmpty(message = "enter valid input")
    lateinit var reg_name: TextInputEditText

    @Email(message = "enter valid email address")
    lateinit var reg_mail: TextInputEditText

    @NotEmpty(message = "enter phone number")
    lateinit var reg_phone: TextInputEditText



    @NotEmpty(message = "enter valid input")
    lateinit var reg_city: TextInputEditText

    @NotEmpty(message = "enter valid input")
    lateinit var reg_loc: TextInputEditText
    var registerModel:RegisterModel? = null

    lateinit var validator: Validator

    lateinit var viewModel:ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.fragment_profile, container, false)


        reg_name = v.findViewById(R.id.register_name)
        reg_mail = v.findViewById(R.id.register_mail)
        reg_phone = v.findViewById(R.id.register_phone)
        reg_city = v.findViewById(R.id.register_city)
        reg_loc = v.findViewById(R.id.register_loc)


        reg_name.setText(userModel.data.name)
        reg_mail.setText(userModel.data.email)
        reg_city.setText(userModel.data.city)
        reg_loc.setText(userModel.data.district)


        if (!loginModel.data.token.isEmpty()){
            (activity as MainActivity).showProgress(true)
            creativitysol.com.planstech.api.Retrofit.Api.getProfile("Bearer ${loginModel.data.token}").enqueue(object : retrofit2.Callback<RegisterModel>{
                override fun onResponse(
                    call: Call<RegisterModel>,
                    response: Response<RegisterModel>
                ) {
                    (activity as MainActivity).showProgress(false)
                    registerModel = response.body()
                    if (response.isSuccessful){
                        if (registerModel!=null){
                            Paper.book().write("user", registerModel)
                            reg_name.setText(registerModel!!.data.name)
                            reg_mail.setText(registerModel!!.data.email)
                            reg_city.setText(registerModel!!.data.city)
                            reg_loc.setText(registerModel!!.data.district)
                            reg_phone.setText(registerModel!!.data.phone)

                            registerModel!!.data.let {model->

                                if (model.avatar!=null&&!model.avatar.isEmpty())
                                    Picasso.get().load(model.avatar).fit().centerCrop().into(v.user_img)

                            }
                        }

                    }
                }

                override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                    (activity as MainActivity).showProgress(false)

                }
            })

        }





        validator = Validator(this)

        pickiT = PickiT(activity, this)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

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




                val name: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    reg_name.text.toString())


                val city: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    reg_city.text.toString())

                val district: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    reg_loc.text.toString())

                val mail: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    reg_mail.text.toString())

                val mob: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    reg_phone.text.toString())




                map.put("name", name)
                map.put("city", city)
                map.put("district", district)
                map.put("email", mail)
                map.put("mobile", mob)
                map.put("phone", mob)



                (requireActivity() as MainActivity).showProgress(true)

                viewModel.updateProfile("Bearer ${loginModel.data.token}",body,map)
            }
        })


        v.save_btn.setOnClickListener {
            validator.validate()
        }


        viewModel.updateResponse.observe(viewLifecycleOwner, Observer {
            if (isAdded){
                (requireActivity() as MainActivity).showProgress(false)
                if (it!=null&&it.statusCode==200)
                {
                    if (!it.data.avatar.isNullOrEmpty()){
                        Paper.book().write("pro",it)
                        Picasso.get().load(it.data.avatar).placeholder(R.drawable.menulogo).error(R.drawable.menulogo)
                            .centerCrop().fit()
                            .into(activity!!.profile_img)
                    }

                }

            }
        })

        v.user_img.setOnClickListener {
            checkPermission()
        }

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==555) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    val uri = data.data

                    Picasso.get().load(uri).placeholder(R.drawable.menulogo).error(R.drawable.menulogo)
                        .centerCrop().fit()
                        .into(v.user_img)
                    pickiT!!.getPath(uri, Build.VERSION.SDK_INT)
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    override fun PickiTonStartListener() {}

    override fun PickiTonProgressUpdate(progress: Int) {}

    override fun PickiTonCompleteListener(
        path: String,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        val file = File(path)


        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)



    }

    fun checkPermission() {
        Dexter.withContext(activity)
            .withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        555
                    )
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {}
                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).onSameThread().check()
    }


}