package creativitysol.com.planstech

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import com.google.android.material.radiobutton.MaterialRadioButton


class BlankFragment : Fragment() {
    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_blank, container, false)



            val colorStat : ColorStateList = ColorStateList(arrayOf(intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)), intArrayOf(Color.parseColor("#AAB5BC"),Color.parseColor("#FFFFFF")))


        val linearLayout: LinearLayout = v.findViewById(R.id.parents)


        var materialRadioButton: MaterialRadioButton = MaterialRadioButton(requireActivity())
        var materialRadioButton2: MaterialRadioButton = MaterialRadioButton(requireActivity())
        var radioGroup: RadioGroup = RadioGroup(requireActivity())


        materialRadioButton.setText("نشاط تجاري وخدمي")
        materialRadioButton.setBackgroundResource(R.drawable.tst)

        materialRadioButton.setTextColor(
            ContextCompat.getColorStateList(
                requireActivity(),
                R.color.ccc
            )
        );
        materialRadioButton2.setText("نشاط تجاري وخدمي")
        materialRadioButton2.setBackgroundResource(R.drawable.tst)

        materialRadioButton2.setTextColor(
            ContextCompat.getColorStateList(
                requireActivity(),
                R.color.ccc
            )
        );

        radioGroup.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        materialRadioButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(16,16,16,16)
        }

        materialRadioButton2.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(16,16,16,16)
        }



        var typeface:Typeface = Typeface.createFromAsset(requireActivity().assets,"fonts/tajwalmedium.ttf")




        materialRadioButton.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        materialRadioButton2.textAlignment = View.TEXT_ALIGNMENT_VIEW_START

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            materialRadioButton.buttonTintList = colorStat
            materialRadioButton2.buttonTintList = colorStat
        } else{
            materialRadioButton.setHighlightColor(getResources().getColor(R.color.buttontint_selectorcolor));
            materialRadioButton2.setHighlightColor(getResources().getColor(R.color.buttontint_selectorcolor));

        }


        materialRadioButton.typeface = typeface
        materialRadioButton2.typeface = typeface


        radioGroup.addView(materialRadioButton)
        radioGroup.addView(materialRadioButton2)
        linearLayout.addView(radioGroup)


        // Inflate the layout for this fragment
        return v
    }

}
