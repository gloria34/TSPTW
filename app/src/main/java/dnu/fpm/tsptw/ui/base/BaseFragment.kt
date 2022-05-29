package dnu.fpm.tsptw.ui.base

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        if (activity != null && activity is OnFragmentChangeListener) {
            (activity as OnFragmentChangeListener).onFragmentChange(this)
        }
    }
}