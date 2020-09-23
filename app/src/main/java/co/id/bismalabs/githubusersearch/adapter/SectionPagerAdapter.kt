package co.id.bismalabs.githubusersearch.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import co.id.bismalabs.githubusersearch.R
import co.id.bismalabs.githubusersearch.ui.FollowersFragment
import co.id.bismalabs.githubusersearch.ui.FollowingFragment

class SectionPagerAdapter(val name : String, private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitle = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment(name)
            1 -> fragment = FollowingFragment(name)
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitle[position])
    }
}