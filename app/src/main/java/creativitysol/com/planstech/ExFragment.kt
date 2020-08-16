package creativitysol.com.planstech

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.expansionpanel.ExpansionLayout
import kotlinx.android.synthetic.main.fragment_ex.*
import kotlinx.android.synthetic.main.fragment_ex.view.*

/**
 * A simple [Fragment] subclass.
 */
class ExFragment : Fragment() {

    lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_ex, container, false)
        // Inflate the layout for this fragment

    v.expansionLayout.addListener(object :ExpansionLayout.Listener{
        override fun onExpansionChanged(expansionLayout: ExpansionLayout?, expanded: Boolean) {

        }
    })
        return v
    }

}
