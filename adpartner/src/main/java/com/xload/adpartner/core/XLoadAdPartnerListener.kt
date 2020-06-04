package com.xload.adpartner.core

/**
 * Created by John Paul Cas on 03/06/2020.
 * jp.cas@xload.io
 */
interface XLoadAdPartnerListener {
    fun convertionDataSuccess()
    fun convertionDataError(err: String)
}