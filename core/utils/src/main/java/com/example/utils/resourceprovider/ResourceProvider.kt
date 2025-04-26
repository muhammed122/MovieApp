package com.example.utils.resourceprovider

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.example.utils.enums.Languages
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject


class ResourceProvider @Inject constructor(@ApplicationContext private val context: Context) :
    IResourceProvider {

    override fun getColor(@ColorRes resId: Int) = ContextCompat.getColor(context, resId)

    override fun getDrawable(@DrawableRes resId: Int) =
        checkNotNull(ContextCompat.getDrawable(context, resId))

    override fun getText(@StringRes resId: Int, vararg values: Any) =
        context.getString(resId, *values)

   override fun getSpannableText(
        @StringRes resId: Int,
        values: String,
        @ColorRes color: Int
    ): Spannable {
        val string = context.resources.getString(resId,values)
        val spannable = SpannableString(string)
        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(context, color))
        val startIndex = string.indexOf(values)
        spannable.setSpan(
            colorSpan,
            startIndex,
            startIndex + values.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return spannable
    }

    override fun getLocale(): Locale =
        context.resources.configuration.locales.get(0)

    override fun isArabicLocale() = getLocale().language == Languages.ARABIC.value

    override fun getSystemResources(): Resources = context.resources
    @SuppressLint("HardwareIds")
    override fun getDeviceId(): String=
       Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

}