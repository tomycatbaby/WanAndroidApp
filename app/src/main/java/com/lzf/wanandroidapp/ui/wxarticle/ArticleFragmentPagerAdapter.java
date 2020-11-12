package com.lzf.wanandroidapp.ui.wxarticle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lzf.wanandroidapp.entity.Tree;

import java.util.List;

public class ArticleFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Tree> chapters;


    public ArticleFragmentPagerAdapter(FragmentManager fm, List<Tree> list) {
        super(fm);
        chapters = list;
    }

    @Override
    public Fragment getItem(int position) {
        return ArticleFragment.newInstance(chapters.get(position).getId());
    }

    @Override
    public int getCount() {
        return chapters.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return chapters.get(position).getName();
    }
}
