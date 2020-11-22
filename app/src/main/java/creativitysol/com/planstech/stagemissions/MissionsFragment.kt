package creativitysol.com.planstech.stagemissions

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
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
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
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
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.lang.IllegalArgumentException
import kotlin.collections.set


class MissionsFragment : Fragment(), MissionsAdapter.MyPickListener, PickiTCallbacks {

    lateinit var v: View
    var pickiT: PickiT? = null
    lateinit var mAdapter: MissionsAdapter
    var selectedID: Int = 0
    var arrayList: ArrayList<String> = ArrayList()
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

        v = inflater.inflate(R.layout.fragment_missions, container, false)
        pickiT = PickiT(activity, this)

        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application).create(
                MissionsViewModel::class.java
            )


        (activity as MainActivity).showProgress(true)
        viewModel.getStagesOfPackage("Bearer ${loginModel.data.token}", arguments!!.getString("id")!!)


        var notAnswered = ArrayList<MissionsModel.Data>()
        viewModel.questionsResopnse.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).showProgress(false)
            if (it != null) {
                if (it.success) {

                    if (!it.data.isNullOrEmpty()){
                        for (m in it.data){
                            if (m.status!=1){
                                notAnswered.add(m)
                            }
                        }
                    }
                    v.missions_rv.apply {
                        mAdapter = MissionsAdapter(
                            requireActivity(), this@MissionsFragment,
                            notAnswered
                        )
                        /*mAdapter = MissionsAdapter(
                            requireActivity(), this@MissionsFragment,
                            it.data as ArrayList<MissionsModel.Data>
                        )*/
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


            for (s in mAdapter.list) {
                println("${s.taskId} ${s.answer}")
                println("--------------------------------------")

                if (s.type.equals("other")) {
                    if (s.selectedFile == null) {
                        Toast.makeText(
                            requireActivity(),
                            "complete file tasks first",
                            Toast.LENGTH_SHORT
                        ).show()
                        parts.clear()
                        map.clear()
                        return@setOnClickListener

                    } else {
                        val reqFile: RequestBody =
                            RequestBody.create(
                                getMimeType(Uri.fromFile(s.selectedFile)).toMediaTypeOrNull(),
                                s.selectedFile!!
                            )

                        parts.add(
                            MultipartBody.Part.createFormData(
                                "answers[${s.taskId}][file]",
                                s.selectedFile!!.getName(),
                                reqFile
                            )
                        )

                        map.put(
                            "answers[${s.taskId}][answer]",
                            RequestBody.create(("text/plain".toMediaTypeOrNull()), "")
                        )

                    }
                } else {
                    if (s.answer == null || s.answer.isEmpty() || !s.isAnswered) {
                        Toast.makeText(
                            requireActivity(),
                            "complete answers tasks first",
                            Toast.LENGTH_SHORT
                        ).show()
                        parts.clear()
                        map.clear()
                        return@setOnClickListener
                    } else {
                        map.put(
                            "answers[${s.taskId}][answer]",
                            RequestBody.create(("text/plain".toMediaTypeOrNull()), s.answer)
                        )
                    }
                }


            }

            print("p size is ${parts.size}")

            for (p in parts) {
                println(p.body.contentType().toString())
            }


            (activity as MainActivity).showProgress(true)

            Retrofit.Api.sendMissions("Bearer ${loginModel.data.token}", parts, arguments!!.getString("id")!!, map)
                .enqueue(object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        (activity as MainActivity).showProgress(false)

                        if (response.isSuccessful) {
                            Toast.makeText(
                                requireActivity(),
                                "submitted successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        (activity as MainActivity).showProgress(false)

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
        if (!s.isEmpty() && s.length > 0) {
            selectedID = pos
            mAdapter.list[pos].isAnswered = true
            mAdapter.list[pos].answer = s

        }

    }

    override fun onMyPick(pos: Int) {
        selectedID = pos
        checkPermission()
    }

    override fun onDownload(taskk: MissionsModel.Data) {
        if (!taskk.file.isEmpty())
            checkPermissionToDownload(taskk.downlaodFileName!!, "desc", taskk.file)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 555) {
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


        mAdapter.list[selectedID].isAnswered = true
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

    private fun downloadFile(fileName: String, desc: String, url: String) {

        try {
            val request = DownloadManager.Request(Uri.parse(url))
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setTitle(fileName)
                .setDescription(desc)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(false)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            val downloadManager =
                requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadID = downloadManager.enqueue(request)
        }catch (e:IllegalArgumentException){
            Toast.makeText(activity,"server error",Toast.LENGTH_SHORT).show()
        }
        // fileName -> fileName with extension

    }

    fun checkPermissionToDownload(fileName: String, desc: String, url: String) {


        Dexter.withContext(activity)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        downloadFile(
                            "${fileName}.pdf",
                            "desc",
                            url
                        )
                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied()) {
                        // Toast.makeText(getActivity(), "قم بالسماح للتطبيق للوصول الى موقعك من خلال الاعدادات", Toast.LENGTH_LONG).show();
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }

}