package creativitysol.com.planstech.tradio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import creativitysol.com.planstech.R
import kotlinx.android.synthetic.main.fragment_t_radio.view.*


class TRadioFragment : Fragment() ,TRVAdapter.radioClick{
  lateinit var v:View

    lateinit var adapter:TRVAdapter

   lateinit var array: Array<Boolean>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v=inflater.inflate(R.layout.fragment_t_radio, container, false)

        array = arrayOf(true,false,false)
      //  adapter= TRVAdapter(requireActivity(),this,array)

       v.trv.apply {
           adapter=this@TRadioFragment.adapter
           layoutManager = LinearLayoutManager(requireActivity())
       }


        // Inflate the layout for this fragment
        return v
    }

    override fun onRadio(position: Int) {
        for (i in 0 until  array.size){
            if (i==position)
                array[i]=true
            else
                array[i]=false

        }
     //   adapter= TRVAdapter(requireActivity(),this,array)
        v.trv.adapter=adapter


    }


}