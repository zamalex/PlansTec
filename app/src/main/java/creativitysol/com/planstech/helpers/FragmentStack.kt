package creativitysol.com.planstech.helpers

import android.R
import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.*

class FragmentStack(
    private val activity: Activity,
    private val manager: FragmentManager,
    private val containerId: Int
) {

    /**
     * Returns the number of fragments in the stack.
     *
     * @return the number of fragments in the stack.
     */
    fun size(): Int {
        return fragments.size
    }

    /**
     * Pushes a fragment to the top of the stack. keeping the other fragments in the stack
     */
    fun push(fragment: Fragment?) {
        val top = peek()
        if (top != null) {
            manager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out
                )
                .remove(top)
                .add(containerId, fragment!!, indexToTag(manager.backStackEntryCount + 1))
                .addToBackStack(null)
                .commit()
        } else {
            manager.beginTransaction()
                .add(containerId, fragment!!, indexToTag(0))
                .commit()
        }
        manager.executePendingTransactions()
    }

    /**
     * Pops the top item if the stack.
     * If the fragment implements [OnBackPressedHandlingFragment], calls [OnBackPressedHandlingFragment.onBackPressed] instead.
     * If [OnBackPressedHandlingFragment.onBackPressed] returns false the fragment gets popped.
     *
     * @return true if a fragment has been popped or if [OnBackPressedHandlingFragment.onBackPressed] returned true;
     */
    fun back(): Boolean {
        val top = peek()
        if (top is OnBackPressedHandlingFragment) {
            if ((top as OnBackPressedHandlingFragment).onBackPressed()) return true
        }
        return pop()
    }

    /**
     * Pops the topmost fragment from the stack.
     * The lowest fragment can't be popped, it can only be replaced.
     *
     * @return false if the stack can't pop or true if a top fragment has been popped.
     */
    fun pop(): Boolean {
        if (manager.backStackEntryCount == 0) return false
        manager.popBackStack()
        return true
    }

    fun showFragment(fragment: Fragment?) {
        val fragments = fragments
        for (f in fragments) {
            if (f != null) {
                manager.beginTransaction().hide(f)
            }
        }
        manager.beginTransaction().show(fragment!!)
    }

    /**
     * Replaces stack contents with just one fragment and remove the others.
     *
     * @param fragment
     */
    fun replace(fragment: Fragment?) {
        manager.popBackStackImmediate(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        manager.beginTransaction()
            .replace(containerId, fragment!!, indexToTag(0))
            .commit()
        manager.executePendingTransactions()
    }

    /**
     * Returns the topmost fragment in the stack.
     */
    fun peek(): Fragment? {
        return manager.findFragmentById(containerId)
    }

    /**
     * Returns a back fragment if the fragment is of given class.
     * If such fragment does not exist and activity implements the given class then the activity will be returned.
     *
     * @param fragment     a fragment to search from.
     * @param callbackType a class of type for callback to search.
     * @param <T>          a type of callback.
     * @return a back fragment or activity.
    </T> */
    fun <T> findCallback(
        fragment: Fragment,
        callbackType: Class<T>
    ): T? {
        val back = getBackFragment(fragment)
        if (back != null && callbackType.isAssignableFrom(back.javaClass)) return back as T
        return if (callbackType.isAssignableFrom(activity.javaClass)) activity as T else null
    }

    fun removeFragment(fragment: Fragment) {
        var i = 0
        val fragments = fragments
        for (f in fragments) {
            if (f == fragment) {
                i++
            }
        }
        if (i > 1) {
            val manager = manager
            val trans = manager.beginTransaction()
            trans.remove(fragment)
            trans.commit()
            manager.popBackStack()
        }
    }

    fun getBackFragment(fragment: Fragment): Fragment? {
        val fragments = fragments
        for (f in fragments.indices.reversed()) {
            if (fragments[f] === fragment && f > 0) return fragments[f - 1]
        }
        return null
    }

    fun restartFragment(fragment: Fragment?) {
        val transaction = manager.beginTransaction()
        transaction.detach(fragment!!).attach(fragment)
    }

    val fragments: List<Fragment>
        get() {
            val fragments: MutableList<Fragment> =
                ArrayList(manager.backStackEntryCount + 1)
            for (i in 0 until manager.backStackEntryCount + 1) {
                val fragment = manager.findFragmentByTag(indexToTag(i))
                if (fragment != null) fragments.add(fragment)
            }
            return fragments
        }

    private fun indexToTag(index: Int): String {
        return Integer.toString(index)
    }

    interface OnBackPressedHandlingFragment {
        fun onBackPressed(): Boolean
    }

}