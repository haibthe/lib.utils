package com.hb.lib.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.provider.Settings
import android.view.View
import android.widget.LinearLayout
import java.io.IOException
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.experimental.and


/**
 * Created by buihai on 7/15/17.
 */

object Utils {

    private var decimalFormat: DecimalFormat? = null
    private val PATTERN_FORMAT = "###,###,### đ"

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun loadBitmapFromView(v: View): Bitmap {
        if (v.measuredHeight <= 0) {
            v.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            val bmp = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val c = Canvas(bmp)
            v.layout(0, 0, v.measuredWidth, v.measuredHeight)
            v.draw(c)

            return bmp
        }

        val bmp = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        v.layout(v.left, v.top, v.right, v.bottom)
        v.draw(c)

        return bmp

    }

    fun formatDistance(distance: Double): String {
        return if (distance >= 1000) {
            String.format("%.2f km", distance / 1000)
        } else {
            String.format("%.0f m", distance)
        }
    }

    fun formatDecimal(price: Long): String {
        decimalFormat = DecimalFormat.getInstance() as DecimalFormat
        decimalFormat!!.applyPattern(PATTERN_FORMAT)
        return decimalFormat!!.format(price)
    }

    fun getResId(context: Context, name: String, type: String): Int {
        val res = context.resources
        return res.getIdentifier(name, type, context.packageName)
    }

    fun md5(input: String): String {
        try {
            val md = MessageDigest.getInstance("MD5")
            val messageDigest = md.digest(input.toByteArray())
            val number = BigInteger(1, messageDigest)
            var hashtext = number.toString(16)

            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            return hashtext
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {

        val manager = context.assets
        val `is` = manager.open(jsonFileName)

        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()

        return String(buffer, Charset.forName("UTF-8"))
    }

    fun getVersionCode(context: Context): Int {
        var versionCode: Int = 0
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            versionCode = pInfo.versionCode
            return versionCode
        } catch (ex: Exception) {
        }

        return versionCode
    }

    fun getVersionName(context: Context): String {
        var versionName = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = pInfo.versionName
            return versionName
        } catch (e: Exception) {
        }

        return versionName
    }

    fun toHexadecimal(digest: ByteArray, length: Int): String {
        val hash = StringBuilder()

        for (i in 0 until length) {
            val aux = digest[i]
            val b = aux and 0xff.toByte()
            if (Integer.toHexString(b.toInt()).length == 1) hash.append("0")
            hash.append(Integer.toHexString(b.toInt()))
        }
        return hash.toString()
    }

    private val UNITS = arrayOf("K", "M", "B", "T", "P", "E")

    fun suffixNumber(number: Float): String {
        var num = number.toLong()
        val s = java.lang.Long.compare(num, 0)
        val sign = if (s == -1) "-" else ""
        num = Math.abs(num)
        if (num < 1000) {
            return String.format(Locale.getDefault(), "%s%d", sign, num)
        }
        val exp = (Math.log10(num.toDouble()) / 3.0f).toInt()
        return String.format(
            Locale.getDefault(), "%s%.1f%s",
            sign, num / Math.pow(1000.0, exp.toDouble()), UNITS[exp - 1]
        )

    }

    fun extractUrls(text: String): List<String> {
        val containedUrls = ArrayList<String>()
        val urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)"
        val pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
        val urlMatcher = pattern.matcher(text)

        while (urlMatcher.find()) {
            containedUrls.add(
                text.substring(
                    urlMatcher.start(0),
                    urlMatcher.end(0)
                )
            )
        }

        return containedUrls
    }

    fun checkUrls(text: String): Boolean {
        val urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)"
        val pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
        val urlMatcher = pattern.matcher(text)

        return urlMatcher.find()
    }
}
