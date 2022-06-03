package dnu.fpm.tsptw.data.local

import android.content.Context
import android.content.SharedPreferences
import dnu.fpm.tsptw.data.entity.DataSet

object PreferencesHelper {
    const val DATA_SETS = "dataSets"
    private lateinit var sharedPreferences: SharedPreferences

    @JvmStatic
    fun loadPreferencesHelper(context: Context, name: String?) {
        sharedPreferences =
            context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    @JvmStatic
    fun saveStringSet(key: String, value: Set<String>) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    @JvmStatic
    fun loadStringSet(key: String): MutableSet<String>? {
        val string: Set<String> = setOf()
        return sharedPreferences.getStringSet(key, string)
    }
}