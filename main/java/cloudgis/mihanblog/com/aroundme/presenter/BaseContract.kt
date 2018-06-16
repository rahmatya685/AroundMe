package com.edsab.pm.presenter

/**
 * Created by rahmati on 2/8/2018.
 */
class BaseContract {

    interface Presenter<in T>{
        fun subscribe()
        fun unSubscribe()
        fun attachView(view : T)
    }

    interface View {
        fun showMessage(msg: String)
        fun showProgress(msg: String)
        fun showError(error:String)
        fun dismissProgress()

    }
}