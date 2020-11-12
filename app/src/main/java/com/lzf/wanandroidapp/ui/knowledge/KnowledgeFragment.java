package com.lzf.wanandroidapp.ui.knowledge;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.lzf.wanandroidapp.R;
import com.lzf.wanandroidapp.base.BaseFragment;
import com.lzf.wanandroidapp.base.Constant;
import com.lzf.wanandroidapp.entity.Article;
import com.lzf.wanandroidapp.entity.Tree;
import com.lzf.wanandroidapp.ui.adapter.ArticleAdapter;
import com.lzf.wanandroidapp.ui.h5.ContentActivity;
import com.lzf.wanandroidapp.ui.home.HomeAdapter;
import com.lzf.wanandroidapp.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class KnowledgeFragment extends Fragment {
    String TAG = "KnowledgeFragment";
    private KnowledgeViewModel knowledgeViewModel;
    private RecyclerView rvFirst;
    private RecyclerView rvSecond;
    private RecyclerView rvArticle;
    //private SwipeRefreshLayout refresh;
    private View filterLayout;
    private ImageView filter;
    private TextView filterName;

    private boolean expand = false;

    private final TreeAdapter firstAdapter = new TreeAdapter(new ArrayList<Tree>());
    private final TreeAdapter secondAdapter = new TreeAdapter(new ArrayList<Tree>());
    private final ArticleAdapter articleAdapter = new ArticleAdapter(new ArrayList<Article>());
    private int curPage = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_knowledge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFirst = view.findViewById(R.id.rv_first);
        rvSecond = view.findViewById(R.id.rv_second);
        rvArticle = view.findViewById(R.id.rv_article);
        filterLayout = view.findViewById(R.id.filter_layout);
        filter = view.findViewById(R.id.iv_filter);
        filterName = view.findViewById(R.id.filter_name);
        initView();
        loaderData();
    }

    public void initView() {

        filter.setOnClickListener(v -> {
            showFilter();
        });
        rvFirst.setLayoutManager(new FlexboxLayoutManager(requireActivity()));
        rvFirst.setAdapter(firstAdapter);
        firstAdapter.setAction(tree -> {
            secondAdapter.setList(tree.getChildren());
            curPage = 0;
            showFilter();
            knowledgeViewModel.getArticleList(curPage, tree.getChildren().get(0).getId());
            return null;
        });
        rvSecond.setLayoutManager(new FlexboxLayoutManager(requireActivity()));
        rvSecond.setAdapter(secondAdapter);
        secondAdapter.setAction(tree -> {
            curPage = 0;
            showFilter();
            knowledgeViewModel.getArticleList(curPage, tree.getId());
            return null;
        });
        rvArticle.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvArticle.setAdapter(articleAdapter);
        rvArticle.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvArticle.setItemAnimator(null);

        articleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Article article = (Article) adapter.getItem(position);
                Intent intent = new Intent(
                        requireActivity(), ContentActivity.class);
                intent.putExtra(Constant.CONTENT_URL_KEY, article.getLink());
                intent.putExtra(Constant.CONTENT_TITLE_KEY, article.getTitle());
                intent.putExtra(Constant.CONTENT_ID_KEY, article.getId());
                startActivity(intent);
            }
        });

    }

    private void showFilter() {
        expand = !expand;
        if (expand) {
            int height = filterLayout.getMeasuredHeight();
            filterLayout.setVisibility(View.VISIBLE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(filterLayout, "alpha", 0f, 1f);
            ObjectAnimator translationYAnimator1 = ObjectAnimator.ofFloat(filterLayout, "translationY", (float) -height, 0f);
            objectAnimator.setDuration(300L);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimator, translationYAnimator1);
            animatorSet.start();
            filter.setRotation(180f);
        } else {
            int height = filterLayout.getMeasuredHeight();
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(filterLayout, "alpha", 1f, 0f);
            ObjectAnimator translationYAnimator1 = ObjectAnimator.ofFloat(filterLayout, "translationY", 0f, (float) -height);
            objectAnimator.setDuration(300L);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimator, translationYAnimator1);
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    filterLayout.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animatorSet.start();
            filter.setRotation(0f);
        }
    }

    public void loaderData() {
        knowledgeViewModel = ViewModelProviders.of(this).get(KnowledgeViewModel.class);
        knowledgeViewModel.getTree().observe(this, trees -> {
            if (trees != null && !trees.isEmpty()) {
                firstAdapter.setList(trees);
                secondAdapter.setList(trees.get(0).getChildren());
                knowledgeViewModel.getArticleList(curPage, trees.get(0).getChildren().get(0).getId());
            }
        });
        knowledgeViewModel.getArticle().observe(this, articleBaseListResponseBody -> {
            if (articleBaseListResponseBody.getDatas() != null) {
                articleAdapter.setList(articleBaseListResponseBody.getDatas());
            }
        });
        knowledgeViewModel.getTreeList();
    }
}