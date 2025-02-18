package com.lexleontiev.preferences_data_store_example.presentation

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference


abstract class TypedListPreference<T> : ListPreference {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attributeSet, defStyleAttr, defStyleRes)

    private var defaultValue: T? = null
    abstract val fromStringToType: (String) -> T?
    abstract val persistType: (T) -> Boolean
    abstract val getPersistedType: (T) -> T

    override fun persistString(value: String?): Boolean {
        val typeValue = value?.let { fromStringToType(it) } ?: return false
        return persistType(typeValue)
    }

    override fun getPersistedString(defaultReturnValue: String?): String {
        return defaultValue?.let {
            getPersistedType(it).toString()
        } ?: ""
    }

    fun setTypedValues(typedValues: Collection<T>, defaultValue: T?) {
        this.defaultValue = defaultValue
        val entryValues = typedValues.map { it.toString() }.toTypedArray()
        super.setEntryValues(entryValues)
    }
}

class LongListPreference: TypedListPreference<Long> {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attributeSet, defStyleAttr, defStyleRes)

    override val fromStringToType: (String) -> Long? = String::toLongOrNull
    override val persistType: (Long) -> Boolean = ::persistLong
    override val getPersistedType: (Long) -> Long = ::getPersistedLong
}

class IntListPreference: TypedListPreference<Int> {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attributeSet, defStyleAttr, defStyleRes)

    override val fromStringToType: (String) -> Int? = String::toIntOrNull
    override val persistType: (Int) -> Boolean = ::persistInt
    override val getPersistedType: (Int) -> Int = ::getPersistedInt
}

class FloatListPreference: TypedListPreference<Float> {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attributeSet, defStyleAttr, defStyleRes)

    override val fromStringToType: (String) -> Float? = String::toFloatOrNull
    override val persistType: (Float) -> Boolean = ::persistFloat
    override val getPersistedType: (Float) -> Float = ::getPersistedFloat
}