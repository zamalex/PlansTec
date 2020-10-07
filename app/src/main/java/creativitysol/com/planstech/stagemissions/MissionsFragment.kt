package creativitysol.com.planstech.stagemissions

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import creativitysol.com.planstech.R
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.stagemissions.model.MissionsModel
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_missions.view.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import kotlin.collections.set


class MissionsFragment : Fragment() ,MissionsAdapter.MyPickListener, PickiTCallbacks {

    lateinit var v:View
    var pickiT: PickiT? = null
    lateinit var mAdapter: MissionsAdapter
    var selectedID:Int = 0
    var arrayList:ArrayList<String> = ArrayList()
    var parts: ArrayList<MultipartBody.Part> = ArrayList()
    var map: HashMap<String, RequestBody> = HashMap()

    lateinit var viewModel: MissionsViewModel

    val loginModel by lazy {
        Paper.book().read("login", LoginModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v= inflater.inflate(R.layout.fragment_missions, container, false)
        pickiT = PickiT(activity, this)

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application).create(
            MissionsViewModel::class.java
        )


        (activity as MainActivity).showProgress(true)
        viewModel.getStagesOfPackage("Bearer ${loginModel.data.token}", "1")


        viewModel.questionsResopnse.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).showProgress(false)
            if (it != null) {
                if (it.success) {
                    v.missions_rv.apply {
                        mAdapter = MissionsAdapter(
                            requireActivity(), this@MissionsFragment,
                            it.data as ArrayList<MissionsModel.Data>
                        )
                        layoutManager = LinearLayoutManager(requireActivity())
                        adapter = mAdapter
                        val dividerItemDecoration = DividerItemDecoration(
                            requireActivity(),
                            LinearLayoutManager.VERTICAL
                        )

                        addItemDecoration(dividerItemDecoration)
                    }
                }
            }
        })






        v.confirm_answers.setOnClickListener {
            for (s in mAdapter.list){
                println("${s.task_id} ${s.answer}")
                println("--------------------------------------")

                if (s.type.equals("other")){
                    if (s.selectedFile==null){
                        Toast.makeText(requireActivity(),"complete tasks first",Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                else{
                    if (s.answer==null||s.answer.isEmpty()){
                        Toast.makeText(requireActivity(),"complete tasks first",Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                if (s.type.equals("other")){
                    val reqFile: RequestBody =
                        RequestBody.create(
                            getMimeType(Uri.fromFile(s.selectedFile)).toMediaTypeOrNull(),
                            s.selectedFile!!
                        )
                    parts.add(
                        MultipartBody.Part.createFormData(
                            "answers[${s.task_id}][file]",
                            s.selectedFile!!.getName(),
                            reqFile
                        )
                    )
                }else{
                    map.put(
                        "answers[${s.task_id}][answer]",
                        RequestBody.create(("text/plain".toMediaTypeOrNull()), s.answer)
                    )
                }

            }


            Retrofit.Api.sendMissions("Bearer ${loginModel.data.token}",parts,"1",map).enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                }
            })
        }



        // Inflate the layout for this fragment
        return v
    }

    fun getMimeType(uri: Uri): String {
        var mimeType: String? = null
        mimeType = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val cr = context!!.contentResolver
            cr.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
                uri
                    .toString()
            )
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension.toLowerCase()
            )
        }
        return mimeType!!
    }


    override fun sendTxt(pos: Int, s: String) {
        selectedID = pos
       mAdapter.list[pos].isAnswered=true
        mAdapter.list[pos].answer = s

    }

    override fun onMyPick(pos: Int) {
        selectedID = pos
        checkPermission()
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


        mAdapter.list[selectedID].isAnswered=true
        mAdapter.list[selectedID].selectedFile = file

        mAdapter.notifyDataSetChanged()




    }

    fun checkPermission() {
        Dexter.withContext(activity)
            .withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    val intent = Intent()
                    intent.type = "*/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
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