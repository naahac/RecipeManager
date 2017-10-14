package com.naahac.tvaproject.ui.screens.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.naahac.tvaproject.R;
import com.naahac.tvaproject.ui.base.BaseFragment;
import com.naahac.tvaproject.ui.screens.main.my_recipes.MyRecipesFragment;
import com.naahac.tvaproject.ui.screens.main.profile.ProfileFragment;
import com.naahac.tvaproject.ui.screens.main.search.SearchFragment;

/**
 * Created by Natanael on 25. 04. 2017.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[];
    private Context context;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles = new String[] { context.getString(R.string.fragment_my_recipes_title),
                context.getString(R.string.fragment_search_title),
                context.getString(R.string.fragment_profile_title)};
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new MyRecipesFragment();
                break;
            case 1:
                fragment = new SearchFragment();
                break;
            case 2:
                fragment = new ProfileFragment();
                break;
            default:
                fragment = new BaseFragment();
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
