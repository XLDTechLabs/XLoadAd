package com.xload.adpartner.core

import android.app.Application
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib

/**
 * Created by John Paul Cas on 03/06/2020.
 * jp.cas@xload.io
 */
object XLoadAdPartner {

    val LOG = XLoadAdPartner::class.java.simpleName

    fun init(DEV_KEY: String, application: Application, listener: XLoadAdPartnerListener) {
        val appsFlyerListener = object : AppsFlyerConversionListener {
            override fun onAppOpenAttribution(conversionData: MutableMap<String, String>?) {
                conversionData.let {
                    for (attrName in it!!.keys) {
                        Log.d(
                            LOG,
                            "attribute: " + attrName + " = " + conversionData?.get(attrName)
                        )
                    }
                }
            }

            override fun onConversionDataSuccess(conversionData: MutableMap<String, Any>?) {
                listener.convertionDataSuccess()
                conversionData.let {
                    for (attrName in it!!.keys) {
                        Log.d(
                            LOG,
                            "attribute: " + attrName + " = " + conversionData?.get(attrName)
                        )
                    }
                }
            }

            override fun onConversionDataFail(errMessage: String?) {
                listener.convertionDataError("Error Message[ConversionDataFail]: ${errMessage}")
            }

            override fun onAttributionFailure(errMessage: String?) {
                listener.convertionDataError("Error Message[AttributionFailure]: ${errMessage}")
            }
        }

        AppsFlyerLib.getInstance().init(DEV_KEY, appsFlyerListener, application)
        AppsFlyerLib.getInstance().startTracking(application)
    }

}