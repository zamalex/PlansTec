package creativitysol.com.planstech.coursedetails

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.navigationInfoParameters
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.smarteist.autoimageslider.SliderAnimations
import creativitysol.com.planstech.R
import creativitysol.com.planstech.databinding.FragmentSingleCourseBinding
import creativitysol.com.planstech.favorites.data.model.TrainingBody
import creativitysol.com.planstech.favorites.presentation.viewmodel.AddToFavouritesViewModel
import creativitysol.com.planstech.login.LoginFragment
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.payment.PaymentOptionsFragment
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_single_course.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class SingleCourseFragment : Fragment() ,SliderAdapterExample.imageInterface{

    lateinit var binding: FragmentSingleCourseBinding
    lateinit var v: View
    lateinit var viewModel: TrainingViewModel

    private lateinit var trainingId: String
    private val addToFavouritesViewModel by viewModel<AddToFavouritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_single_course, container, false
        )

        v = binding.root


        // checkPermission()
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(TrainingViewModel::class.java)


        binding.model = viewModel.course

        if (arguments != null) {
            (requireActivity() as MainActivity).showProgress(true)
            arguments!!.getString("id").let {
                viewModel.getcourse(it!!)
                trainingId = it
            }
        }

        viewModel.course.observe(requireActivity(), Observer {

            if (isAdded) {
                (requireActivity() as MainActivity).showProgress(false)

                (activity as MainActivity).setTitle(it.data.title)

                if (it.data.fav.equals("1"))
                    v.img_add_remove_fav.setImageResource(R.drawable.saved)
                else v.img_add_remove_fav.setImageResource(R.drawable.unsaved)


                if (it.data.isSubscribe){
                    v.button.visibility = View.GONE
                }
                else
                    v.button.visibility = View.VISIBLE

                var aray: ArrayList<String> = ArrayList()
                Log.e("imgs",it.data.imagesGallery.size.toString())

                for (i in 0 until it.data.imagesGallery.size) {
                    aray.add(it.data.imagesGallery[i].image)
                    Log.e("img"+i,it.data.imagesGallery[i].image)
                }

                var adapter: SliderAdapterExample = SliderAdapterExample(requireActivity(),this)
                v.flipper_layout.setSliderAdapter(adapter)
                adapter.renewItems(aray)

                v.flipper_layout.startAutoCycle();
                v.flipper_layout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                v.flipper_layout.indicatorSelectedColor = Color.WHITE;
                v.flipper_layout.indicatorUnselectedColor = Color.GRAY;
            }
        })

        v.img_add_remove_fav.setOnClickListener {
            addToFavouritesViewModel.addToFavourites(
                TrainingBody(
                    type = "training", trainingId = trainingId.toInt()
                )
            )
        }
        addToFavouritesViewModel.trainingResults.observe(viewLifecycleOwner, Observer {
            Snackbar.make(v.img_add_remove_fav, it.message, Snackbar.LENGTH_SHORT).show()
            if (it.data.fav.equals("1"))
                v.img_add_remove_fav.setImageResource(R.drawable.saved)
            else v.img_add_remove_fav.setImageResource(R.drawable.unsaved)
        })

        addToFavouritesViewModel.error.observe(viewLifecycleOwner, Observer{
            Snackbar.make(v.img_add_remove_fav, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
        })



        v.button.setOnClickListener {
            val log: LoginModel = Paper.book().read("login", LoginModel())

            if (log.data.token.isEmpty())
                (requireActivity() as MainActivity).fragmentStack.push(LoginFragment())
            else
                (requireActivity() as MainActivity).fragmentStack.push(PaymentOptionsFragment().apply { arguments = Bundle().apply { putString("type","course")
                putInt("id",this@SingleCourseFragment.viewModel.course.value?.data?.id!!.toInt())} })

        }

        v.share.setOnClickListener {

            if (viewModel.course.value?.data?.id == 0)
                return@setOnClickListener


            var cid = viewModel.course.value?.data?.id
            var aid = 0
            val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync {
                longLink =
                    Uri.parse("https://creativitysol.page.link/?link=https://www.planstec.com/?cid%3D${cid}%26aid%3D${aid}&apn=creativitysol.com.planstech")

                navigationInfoParameters {
                    forcedRedirectEnabled = true
                }

            }.addOnSuccessListener { result ->
                // Short link created
                val shortLink = result.shortLink
                val flowchartLink = result.previewLink
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                var shareMessage = "\nLet me recommend you this application\n\n${shortLink}"
                shareMessage =
                    """
                        ${shareMessage}
                        
                        
                        """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            }.addOnFailureListener {
                // Error
                // ...
            }


        }

        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("عنوان الدورة")
    }

    private fun downloadFile(fileName: String, desc: String, url: String) {
        // fileName -> fileName with extension
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
    }

    fun checkPermission() {


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
                            "plansfile2.pdf",
                            "desc",
                            "https://www.cplusplus.com/files/tutorial.pdf")
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

    override fun onImgClicked(img: String?) {
        ((activity as MainActivity)).fragmentStack.push(ImgFragment().apply {
            arguments = Bundle().apply {
                putString("img",img)
            }
        })
    }

}
