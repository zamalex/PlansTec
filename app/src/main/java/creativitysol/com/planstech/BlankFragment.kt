package creativitysol.com.planstech

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.radiobutton.MaterialRadioButton


class BlankFragment : Fragment() {
    lateinit var v: View
    lateinit var colorStat: ColorStateList
    lateinit var linearLayout: LinearLayout
    var arrayList: ArrayList<View> = ArrayList();
    lateinit var typeface: Typeface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_blank, container, false)


        colorStat = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(Color.parseColor("#AAB5BC"), Color.parseColor("#FFFFFF"))
        )


        linearLayout = v.findViewById(R.id.parents)
        typeface =
            Typeface.createFromAsset(requireActivity().assets, "fonts/tajwalmedium.ttf")


        initDynamicLayout()
        // Inflate the layout for this fragment
        return v
    }

    fun initDynamicLayout() {

        for (i in 0 until 2) {
            var radioGroup: RadioGroup = RadioGroup(requireActivity())
            var textView:TextView = TextView(requireActivity())
            textView.text = "سوال ؤقم ${i}"
            radioGroup.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            for (ii in 0 until 4) {
                var materialRadioButton: MaterialRadioButton =
                    MaterialRadioButton(requireActivity())


                materialRadioButton.setText("نشاط تجاري وخدمي "+ii)
                materialRadioButton.setBackgroundResource(R.drawable.tst)

                materialRadioButton.setTextColor(
                    ContextCompat.getColorStateList(
                        requireActivity(),
                        R.color.ccc
                    )
                );





                materialRadioButton.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(16, 16, 16, 16)
                }


                materialRadioButton.textAlignment = View.TEXT_ALIGNMENT_VIEW_START

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    materialRadioButton.buttonTintList = colorStat
                } else {
                    materialRadioButton.setHighlightColor(getResources().getColor(R.color.buttontint_selectorcolor));

                }
                materialRadioButton.typeface = typeface

                materialRadioButton.id = ViewCompat.generateViewId()

                radioGroup.addView(materialRadioButton)

            }

            linearLayout.addView(textView)
            linearLayout.addView(radioGroup)
            arrayList.add(radioGroup)

        }

        var button = Button(requireActivity())
        button.text = "click me"
        button.setOnClickListener {
            for (i in 0 until arrayList.size){
                if (arrayList[i] is RadioGroup){
                    var radioButton = v.findViewById<MaterialRadioButton>((arrayList[i] as RadioGroup).checkedRadioButtonId)

                    Log.e("answer ${i} is:",radioButton.text.toString()+" and index is "+(arrayList[i] as RadioGroup).indexOfChild(radioButton))
                }
            }
        }
        var editText:EditText = EditText(requireActivity());


        editText.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(16, 16, 16, 16)
            height = 150f.toDips().toInt()

        }
        editText.setBackgroundResource(R.drawable.edit_border)
        linearLayout.addView(button)
        linearLayout.addView(editText)


    }

    fun Float.toDips() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics);
}
