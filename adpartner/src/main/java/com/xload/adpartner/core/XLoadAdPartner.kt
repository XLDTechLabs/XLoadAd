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

    fun init(
        DEV_KEY: String,
        application: Application,
        isDevelopment: Boolean,
        listener: XLoadAdPartnerListener
    ) {
        val appsFlyerListener = object : AppsFlyerConversionListener {
            override fun onAppOpenAttribution(conversionData: MutableMap<String, String>?) {
                if (isDevelopment) {
                    conversionData.let {
                        for (attrName in it!!.keys) {
                            Log.d(
                                LOG,
                                "attribute: " + attrName + " = " + conversionData?.get(attrName)
                            )
                        }
                    }
                }
            }

            override fun onConversionDataSuccess(conversionData: MutableMap<String, Any>?) {
                listener.convertionDataSuccess()
                if (isDevelopment) {
                    conversionData.let {
                        for (attrName in it!!.keys) {
                            Log.d(
                                LOG,
                                "attribute: " + attrName + " = " + conversionData?.get(attrName)
                            )
                        }
                    }
                }
            }

            override fun onConversionDataFail(errMessage: String?) {
                listener.convertionDataError("Error Message[ConversionDataFail]: ${errMessage}")
                if (isDevelopment) {
                    Log.d(LOG, "onversionDataFail message: ${errMessage}")
                }
            }

            override fun onAttributionFailure(errMessage: String?) {
                listener.convertionDataError("Error Message[AttributionFailure]: ${errMessage}")
                if (isDevelopment) {
                    Log.d(LOG, "AttributionFailure message: ${errMessage}")
                }
            }
        }

        AppsFlyerLib.getInstance().init(DEV_KEY, appsFlyerListener, application)
        AppsFlyerLib.getInstance().startTracking(application)
    }

}