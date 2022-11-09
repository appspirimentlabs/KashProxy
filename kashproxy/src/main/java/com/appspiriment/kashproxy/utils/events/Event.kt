package com.appspiriment.kashproxy.utils.events

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
internal open class Event<out T>(private val content: T, open val eventType: EventType) {

    enum class EventType {
        SHOW_SNACKBAR,
        SHOW_ALERT_DIALOG,
        START_ACTIVITY,
        SHOW_TOAST,
        DISMISS_KEYBOARD
    }

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
