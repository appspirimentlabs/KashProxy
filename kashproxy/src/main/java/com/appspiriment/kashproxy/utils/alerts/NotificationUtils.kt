package com.appspiriment.kashproxy.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.appspiriment.kashproxy.ui.main.KashProxyActivity
import com.appspiriment.kashproxy.R
import java.util.HashSet


/*******************
 * Class   :  NotificationUtils
 * Author  :
 * Created :  9/6/21
 *******************
 * Purpose :  Notification Utils
 *******************
 * Rework Details:
 * 1) {Author} :  {Date} : {Details}
 *******************/
internal class NotificationUtils(val context: Context) {

    companion object {
        const val NOTIFICATION_CHANNEL_NAME = "kashproxy_notification"
        const val NOTIFICATION_PERSISTANT_ID = 108897
        const val NOTIFICATION_NOTMAL_ID = 108899

        private const val BUFFER_SIZE = 10
        private const val INTENT_REQUEST_CODE = 11
        private val apiMappingBuffer = HashMap<String, String>()
        private val apiMappingIdsSet = HashSet<String>()

        fun clearBuffer() {
            synchronized(apiMappingBuffer) {
                apiMappingBuffer.clear()
                apiMappingIdsSet.clear()
            }
        }

    }

    private val mNotificationmanager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_NAME,
                context.getString(R.string.kashproxy_proxy_notification_desc),
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationmanager.createNotificationChannel(notificationChannel)
        }
    }

    private fun canShowNotifications(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mNotificationmanager.areNotificationsEnabled()
        } else {
            true
        }
    }

    internal fun displayKashProxyNotification(show: Boolean) {
        if (show) {
            getKashProxyNotifier(NOTIFICATION_PERSISTANT_ID, true)?.let {
                mNotificationmanager.notify(NOTIFICATION_PERSISTANT_ID, it.build())
            }
        } else {
            mNotificationmanager.cancel(NOTIFICATION_PERSISTANT_ID)
        }
    }

    internal fun displayMappingTx(
        apiMappingPath: String,
        apiResponseType: String
    ) {
        addToBuffer(apiMappingPath, apiResponseType)

        getKashProxyNotifier(NOTIFICATION_NOTMAL_ID)?.apply {
            val inboxStyle = NotificationCompat.InboxStyle()
            synchronized(apiMappingBuffer) {
                var count = 0
                apiMappingBuffer.forEach { mappedTransaction ->
                    if ((mappedTransaction != null) && count < BUFFER_SIZE) {
                        if (count == 0) {
                            setContentText(mappedTransaction.value)
                        }
                        inboxStyle.addLine(mappedTransaction.value)
                    }
                    count++
                }
                setStyle(inboxStyle)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setSubText(apiMappingIdsSet.size.toString())
                } else {
                    setNumber(apiMappingIdsSet.size)
                }
            }
        }?.let{
            mNotificationmanager.notify(NOTIFICATION_NOTMAL_ID, it.build())
        }
    }

    private fun addToBuffer(
        apiMappingPath: String,
        apiResponseType: String
    ) {
        synchronized(apiMappingBuffer) {
            apiMappingIdsSet.add(apiMappingPath)
            apiMappingBuffer.put(
                apiMappingPath,
                "${apiResponseType.uppercase()} : $apiMappingPath"
            )
            if (apiMappingBuffer.keys.size > BUFFER_SIZE) {
                apiMappingBuffer.remove(apiMappingBuffer.keys.last())
            }
        }
    }

    /**
     * **************
     * Method to create a notification
     * **************
     */
    private fun getKashProxyNotifier(
        notificationId: Int,
        persistent: Boolean = false
    ): NotificationCompat.Builder? {
        return if (canShowNotifications()) {

            val mNotificationBuilder =
                NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_NAME)
            mNotificationBuilder.setGroup(NOTIFICATION_CHANNEL_NAME).setGroupSummary(true)

            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_NAME).apply {
                setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        notificationId,
                        KashProxyActivity.getLauncherIntent(context, null),
                        PendingIntent.FLAG_UPDATE_CURRENT or immutableFlag()
                    )
                )
                setLocalOnly(true)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    priority = if(persistent) NotificationManager.IMPORTANCE_HIGH else NotificationManager.IMPORTANCE_LOW
                }
                setSmallIcon(R.drawable.kashproxy_ic_action_transform)
                setContentTitle(
                    context.getString(
                        if(persistent)
                            R.string.kashproxy_proxy_notification
                        else
                            R.string.kashproxy_proxy_map_notification
                    )
                )
                color = ContextCompat.getColor(context,
                    if( persistent) R.color.kashproxy_error_color else R.color.kashproxy_primary_color)
                setOngoing( persistent)
            }
        } else null
    }

    private fun immutableFlag() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_IMMUTABLE
    } else {
        0
    }
}