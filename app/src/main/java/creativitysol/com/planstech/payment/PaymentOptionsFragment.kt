package creativitysol.com.planstech.payment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import creativitysol.com.planstech.R
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.packages.PlanViewModel
import io.paperdb.Paper
import kotlinx.android.synthetic.main.bank_layout.*
import kotlinx.android.synthetic.main.fragment_payment_options.view.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class PaymentOptionsFragment : Fragment(), PickiTCallbacks {


    lateinit var bankDialog: BottomSheetDialog
    lateinit var onlineDialog: BottomSheetDialog
    var selectedID: Int = 0
    var pickiT: PickiT? = null
    lateinit var body:MultipartBody.Part

    lateinit var v: View

    var map:HashMap<String,RequestBody> = HashMap<String,RequestBody>()

    val loginModel: LoginModel by lazy {
        Paper.book().read("login", LoginModel())
    }

    lateinit var viewModel: PlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_payment_options, container, false)
        pickiT = PickiT(activity, this)

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(PlanViewModel::class.java)

        if (arguments != null)
            selectedID = arguments!!.getInt("id")

        bankDialog = BottomSheetDialog(
            requireActivity(),
            R.style.AppBottomSheetDialogTheme
        )

        bankDialog.setCancelable(false)

        bankDialog.setContentView(R.layout.bank_layout)


        onlineDialog = BottomSheetDialog(
            requireActivity(),
            R.style.AppBottomSheetDialogTheme
        )

        onlineDialog.setCancelable(false)

        onlineDialog.setContentView(R.layout.online_layout)



        v.bank_button.setOnClickListener {

            bankDialog.show()
        }

        v.online_button.setOnClickListener {

            onlineDialog.show()
        }

        bankDialog.exit.setOnClickListener {
            bankDialog.dismiss()
        }


        onlineDialog.exit.setOnClickListener {
            onlineDialog.dismiss()
        }

        bankDialog.trans_img.setOnClickListener {
            checkPermission()
        }


        bankDialog.ssend.setOnClickListener {
            if (bankDialog.trasn_no.text.isEmpty())
                return@setOnClickListener
            if (bankDialog.trans_img.text.isEmpty())
                return@setOnClickListener


            val payment_method: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                "bank")


            val selected: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                selectedID.toString())

            val transaction_code: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                bankDialog.trasn_no.text.toString())


            map.put("payment_method", payment_method)
            map.put("item_id", selected)
            map.put("transaction_code", transaction_code)






            viewModel.subscribeToPackage("Bearer ${loginModel.data.token}",body,map)


            // jsonObject.viewModel.subscribeToPackage("Bearer ${loginModel.data.token}",)

        }


        viewModel.subscribeResponse.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (it.success)
                    Toast.makeText(activity,"subscribed",Toast.LENGTH_SHORT).show()
            }
        })


        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("طرق الدفع")
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==555) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    val uri = data.data
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

        bankDialog.trans_img.setText(file.name)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
         body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        

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
