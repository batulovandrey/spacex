package com.example.butul0ve.spacex

import android.os.Environment
import com.example.butul0ve.spacex.bean.Flight
import com.example.butul0ve.spacex.bean.FlightResponse
import com.example.butul0ve.spacex.bean.Links
import com.example.butul0ve.spacex.bean.Rocket
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.net.URL

/**
 * Created by butul0ve on 20.01.18.
 */

class ExecutionThread(private val mCustomCallback: CustomCallback) : Thread() {

    override fun run() {
        mCustomCallback.showProgressBar()
        mCustomCallback.hideButtonTryAgain()
        try {
            val inputStream = BufferedInputStream(URL(JSON_URL).openStream())
            val url = URL(JSON_URL)
            val connection = url.openConnection()
            connection.connect()
            inputStream.bufferedReader().use { File(FILE_PATH).writeText(it.readLine()) }
            val text = File(FILE_PATH).readText()
            val flightResponse = parseJson(text)
            val flights = flightResponse?.flights
            mCustomCallback.setFlights(flights)
        } catch (ex : IOException) {
            mCustomCallback.showToast(R.string.error)
            mCustomCallback.showButtonTryAgain()
        }
        mCustomCallback.hideProgressBar()
    }

    private fun parseJson(text: String): FlightResponse? {
        var flightResponse: FlightResponse? = null
        try {
            val list = ArrayList<Flight>()
            val jsonArray = JSONArray(text)
            (0 until jsonArray.length())
                    .map { jsonArray.getJSONObject(it) }
                    .forEach {
                        list.add(Flight(it.optInt("flight_number"),
                                Rocket(it.optJSONObject("rocket").optString("rocket_id"),
                                        it.optJSONObject("rocket").optString("rocket_name")),
                                it.optString("launch_date_unix"),
                                Links(it.optJSONObject("links").optString("mission_patch"),
                                        it.optJSONObject("links").optString("article_link"),
                                        it.optJSONObject("links").optString("video_link")),
                                it.optString("details")))
                    }
            flightResponse = FlightResponse(list)
        } catch (ex: JSONException) {
            ex.printStackTrace()
        }
        return flightResponse
    }

    companion object {
        val JSON_URL = "https://api.spacexdata.com/v2/launches?launch_year=2017"
        val FILE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/temp.json"
    }

    interface CustomCallback {

        fun setFlights(flights: List<Flight>?)

        fun showProgressBar()

        fun hideProgressBar()

        fun showToast(message: Int)

        fun showButtonTryAgain()

        fun hideButtonTryAgain()
    }
}