package creativitysol.com.planstech.stagequestions

import android.view.View

class Question {

    var view:View? = null
    var id:Int? = null


    constructor(view: View?, id: Int?) {
        this.view = view
        this.id = id
    }
}