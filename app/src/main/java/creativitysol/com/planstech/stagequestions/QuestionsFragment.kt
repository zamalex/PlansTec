package creativitysol.com.planstech.stagequestions

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.marginLeft
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.radiobutton.MaterialRadioButton
import creativitysol.com.planstech.R
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.stagequestions.model.QuestionsModel
import io.paperdb.Paper


class QuestionsFragment : Fragment() {
    val loginModel by lazy {
        Paper.book().read("login", LoginModel())
    }

    lateinit var viewModel: QuestionsViewModel
    lateinit var v: View
    lateinit var colorStat: ColorStateList
    lateinit var linearLayout: LinearLayout
    var arrayList: ArrayList<View> = ArrayList();
    lateinit var typeface: Typeface
    lateinit var typefaceBold: Typeface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_questions, container, false)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(QuestionsViewModel::class.java)

        colorStat = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(Color.parseColor("#AAB5BC"), Color.parseColor("#FFFFFF"))
        )


        linearLayout = v.findViewById(R.id.parents)
        typeface =
            Typeface.createFromAsset(requireActivity().assets, "fonts/tajwalmedium.ttf")

        typefaceBold =
            Typeface.createFromAsset(requireActivity().assets, "fonts/tajwalbold.ttf")



        viewModel.getStagesOfPackage(
            "Bearer ${loginModel.data.token}",1.toString())// arguments!!.getString( "id", null )

        viewModel.questionsResopnse.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (it.success){
                    initDynamicLayout(it)

                }
            }
        })


        // Inflate the layout for this fragment
        return v
    }

    fun initDynamicLayout(questionsModel: QuestionsModel) {

        for (i in 0 until questionsModel.data.size) {
            var radioGroup: RadioGroup = RadioGroup(requireActivity())
            var textView: TextView = TextView(requireActivity())
            textView.typeface=typefaceBold
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 16, 16, 16)
            }
            textView.text = questionsModel.data[i].title

            if (questionsModel.data[i].type.equals("multichoose")){
                radioGroup.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                for (ii in 0 until 4) {
                    var materialRadioButton: MaterialRadioButton =
                        MaterialRadioButton(requireActivity())


                    materialRadioButton.setText("نشاط تجاري وخدمي " + ii)
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


            else if (questionsModel.data[i].type.equals("written")){
                var editText: EditText = EditText(requireActivity());


                editText.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(16, 16, 16, 16)
                    height = 150f.toDips().toInt()

                }
                editText.setBackgroundResource(R.drawable.edit_border)
                linearLayout.addView(textView)
                linearLayout.addView(editText)
            }


        }

        var button = Button(requireActivity())
        button.text = "تأكيد الاجابات"
        button.typeface = typefaceBold
        button.setBackgroundResource(R.drawable.gradient_button)
        button.gravity = Gravity.CENTER

        button.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(64f.toDips().toInt(), 16, 64f.toDips().toInt(), 16)
            gravity = Gravity.CENTER
            // 150f.toDips().toInt()

        }
        button.setPadding(10,5,10,5)

        button.setTextColor(Color.WHITE)



        button.setOnClickListener {
            for (i in 0 until arrayList.size) {
                if (arrayList[i] is RadioGroup) {
                    var radioButton =
                        v.findViewById<MaterialRadioButton>((arrayList[i] as RadioGroup).checkedRadioButtonId)

                    Log.e(
                        "answer ${i} is:",
                        radioButton.text.toString() + " and index is " + (arrayList[i] as RadioGroup).indexOfChild(
                            radioButton
                        )
                    )
                }
            }
        }


         linearLayout.addView(button)

    }

    fun Float.toDips() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics);
}
