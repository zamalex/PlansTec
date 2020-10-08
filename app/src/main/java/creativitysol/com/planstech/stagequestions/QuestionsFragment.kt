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
import com.google.gson.JsonObject
import creativitysol.com.planstech.R
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.main.MainActivity
import creativitysol.com.planstech.stagequestions.model.QuestionsModel
import io.paperdb.Paper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody


class QuestionsFragment : Fragment() {
    val loginModel by lazy {
        Paper.book().read("login", LoginModel())
    }
    var map: HashMap<String, RequestBody> = HashMap()
    var mID:Int = 1
    var jsonObject = JsonObject()
    lateinit var viewModel: QuestionsViewModel
    lateinit var v: View
    lateinit var colorStat: ColorStateList
    lateinit var linearLayout: LinearLayout
    var arrayList: ArrayList<Question> = ArrayList();

    var hashmap : ArrayList<String> = ArrayList()
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

        mID = 1//arguments!!.getString( "id", null )
        linearLayout = v.findViewById(R.id.parents)
        typeface =
            Typeface.createFromAsset(requireActivity().assets, "fonts/tajwalmedium.ttf")

        typefaceBold =
            Typeface.createFromAsset(requireActivity().assets, "fonts/tajwalbold.ttf")



        viewModel.getStagesOfPackage(
            "Bearer ${loginModel.data.token}","${mID}")//arguments!!.getString( "id", null )

        viewModel.questionsResopnse.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (it.success){
                    initDynamicLayout(it)

                }
            }
        })


        viewModel.submit.observe(viewLifecycleOwner, Observer {
            if (isAdded){
                (activity as MainActivity).showProgress(false)
                if (it!=null){
                    Toast.makeText(requireActivity(),"submitted successfully",Toast.LENGTH_SHORT).show()

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

                for (ii in 0 until questionsModel.data[i].answers.size) {
                    var materialRadioButton: MaterialRadioButton =
                        MaterialRadioButton(requireActivity())


                    materialRadioButton.setText(questionsModel.data[i].answers[ii].value)
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
                    materialRadioButton.tag = questionsModel.data[i].answers[ii].id
                    materialRadioButton.id = ViewCompat.generateViewId()

                    radioGroup.addView(materialRadioButton)

                }

                linearLayout.addView(textView)
                linearLayout.addView(radioGroup)
                arrayList.add(Question(radioGroup,questionsModel.data[i].questionId))
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

                arrayList.add(Question(editText,questionsModel.data[i].questionId))
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
                if (arrayList[i].view is RadioGroup) {
                    var radioButton =
                        v.findViewById<MaterialRadioButton>((arrayList[i].view as RadioGroup).checkedRadioButtonId)

                    if (radioButton!=null){
                        var s:String = "answers[${arrayList[i].id}][answer] = ${radioButton.text.toString()} and tag is ${radioButton.tag}"
                        var ss:String = "answers[${i}][id] = ${arrayList[i].id.toString()}"
                        hashmap.add("${s}\n")
                        map.put(
                            "answers[${arrayList[i].id}][answer]",
                            RequestBody.create(("text/plain".toMediaTypeOrNull()), radioButton.tag.toString())
                        )
                     //   jsonObject.addProperty("answers[${arrayList[i].id}][answer]",radioButton.tag.toString())

                    }else{
                       // map.clear()

                      //  return@setOnClickListener

                    }


                }
                else if  (arrayList[i].view is EditText) {
                   if ((arrayList[i].view as EditText).text.isEmpty()){
                       map.clear()
                       return@setOnClickListener
                   }

                    var s:String = "answers[${arrayList[i].id}][answer] = ${(arrayList[i].view as EditText).text.toString()}"
               //     var ss:String = "answers[${i}][id] = ${arrayList[i].id.toString()}"
                    hashmap.add("${s}\n")
                  //  jsonObject.addProperty("answers[${arrayList[i].id}][answer]",(arrayList[i].view as EditText).text.toString())
                    map.put(
                        "answers[${arrayList[i].id}][answer]",
                        RequestBody.create(("text/plain".toMediaTypeOrNull()), (arrayList[i].view as EditText).text.toString())
                    )
                }
            }


            ((activity as MainActivity)).showProgress(true)
            viewModel.submit("Bearer ${loginModel.data.token}","${mID}",map)

            for (s in hashmap){
                println(s)
                println("---------------------------------------------")
            }

        }


         linearLayout.addView(button)

    }

    fun Float.toDips() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics);
}
