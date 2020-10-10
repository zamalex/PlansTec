package creativitysol.com.planstech.terms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.R
import creativitysol.com.planstech.about.DataResponse
import creativitysol.com.planstech.api.Retrofit
import kotlinx.android.synthetic.main.fragment_about.view.*
import kotlinx.android.synthetic.main.fragment_terms.view.*
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class TermsFragment : Fragment() {

    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_terms, container, false)

        ((activity as MainActivity)).showProgress(true)

        Retrofit.Api.terms().enqueue(object : retrofit2.Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if (isAdded) {
                    ((activity as MainActivity)).showProgress(false)
                    if (response.isSuccessful) {
                        v.terms.setText(response.body()!!.data.content)
                    }

                }

            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                ((activity as MainActivity)).showProgress(false)

            }
        })

        return v
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle(getString(R.string.terns))
    }

}
