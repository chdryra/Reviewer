/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.NullDataReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.Styles;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Interfaces.ReviewViewContainerLayout;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 06/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewViewLayout implements ReviewViewContainerLayout {
    private static final int LAYOUT = R.layout.fragment_review_view;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.middle_button;
    private static final int BANNER = R.id.right_button;
    private static final int GRID = R.id.gridview_data;
    private static final int COVER = R.id.background_image;
    private static final int CONTEXT_VIEW = R.id.view_selector_layout;
    private static final int CONTEXT_BUTTON = R.id.left_button;

    private static final TitleDecorator DECORATOR = Styles.TitleDecorators.OPTION_BUTTON;

    private View mView;

    private MenuUi mMenu;
    private SubjectUi<?> mSubjectUi;
    private RatingUi<?> mRatingUi;
    private RecyclerViewUi<?> mGridUi;

    private DataBinder<Bitmap> mCover;
    private DataBinder<String> mSubject;
    private DataBinder<Float> mRating;
    private DataBinder<String> mSort;
    private DataBinder<String> mViewSelector;
    private DataBinder<?> mGridData;

    @Override
    public View inflateLayout(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(LAYOUT, container, false);
        return mView;
    }

    @Override
    public <T extends GvData> void bindToReviewView(ReviewView<T> reviewView,
                                                    CellDimensionsCalculator calculator) {
        ReviewViewActions<T> actions = reviewView.getActions();
        ReviewViewParams params = reviewView.getParams();
        ReviewViewAdapter<T> adapter = reviewView.getAdapter();

        mMenu = newMenuUi(actions.getMenuAction());
        mSubject = bindSubject((TextView) mView.findViewById(SUBJECT),
                adapter.getSubjectReference(), params.getSubjectParams());
        mRating = bindRating((TextView) mView.findViewById(RATING), reviewView);
        mSort = bindBanner((Button) mView.findViewById(BANNER),
                actions.getBannerButtonAction(), params.getBannerButtonParams());
        mGridData = bindGridView((RecyclerView) mView.findViewById(GRID),
                adapter.getGridDataReference(), actions.getGridItemAction(),
                params.getGridViewParams(), calculator);
        mViewSelector = bindContextView(mView.findViewById(CONTEXT_VIEW),
                actions.getContextualAction(), params.getContextViewParams());
        if (params.manageCover()) {
            mCover = bindCover((ImageView) mView.findViewById(COVER),
                    adapter.getCoverReference());
        }
    }

    @Override
    public void inflateMenu(Menu menu, MenuInflater inflater) {
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        return mMenu.onItemSelected(item);
    }

    @Override
    public String getSubject() {
        return mSubjectUi.getValue();
    }

    @Override
    public float getRating() {
        return mRatingUi.getValue();
    }

    @Override
    public void setRating(float rating) {
        //View only
    }

    @Override
    public void bind() {
        mSubject.bind();
        mRating.bind();
        mGridData.bind();
        mSort.bind();
        mViewSelector.bind();
        if (mCover != null) mCover.bind();
    }

    @Override
    public void unbind() {
        mSubject.unbind();
        mRating.unbind();
        mGridData.unbind();
        mSort.unbind();
        mViewSelector.unbind();
        if (mCover != null) mCover.unbind();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mGridUi.onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return mGridUi.onOptionsCancelled(requestCode);
    }

    @NonNull
    private MenuUi newMenuUi(MenuAction<?> action) {
        return new MenuUi(action);
    }

    @NonNull
    private DataBinder<Bitmap> bindCover(ImageView view, DataReference<Bitmap> cover) {
        CoverUi ui = new CoverUi(view, mGridUi);
        return new DataBinder<>(ui, cover);
    }

    @NonNull
    private <T extends GvData> DataBinder<GvDataList<T>> bindGridView
            (RecyclerView view,
             DataReference<GvDataList<T>> data,
             GridItemAction<T> action,
             ReviewViewParams.GridView params,
             CellDimensionsCalculator calculator) {
        RecyclerViewUi<T> ui = new RecyclerViewUi<>(view, action, params, calculator);
        mGridUi = ui;
        return new DataBinder<>(ui, data);
    }

    @NonNull
    private DataBinder<String> bindContextView(View view,
                                               @Nullable ButtonAction<?> action,
                                               ReviewViewParams.ContextView params) {
        ContextUi ui = new ContextUi(view, CONTEXT_BUTTON, action, params, DECORATOR);
        DataReference<String> data = action != null ? action.getTitle() : new
                NullDataReference<String>();
        return new DataBinder<>(ui, data);
    }

    @NonNull
    private DataBinder<String> bindBanner(Button view,
                                          ButtonAction<?> action,
                                          ReviewViewParams.BannerButton params) {
        BannerButtonUi ui = new BannerButtonUi(view, action, params.getAlpha(), DECORATOR);
        return new DataBinder<>(ui, action.getTitle());
    }

    @NonNull
    private DataBinder<Float> bindRating(TextView view, ReviewView<?> reviewView) {
        mRatingUi = new RatingTextUi(reviewView, view);
        return new DataBinder<>(mRatingUi, reviewView.getAdapter().getRatingReference());
    }

    @NonNull
    private <T extends TextView> DataBinder<String> bindSubject(T view,
                                                                DataReference<String> subject,
                                                                ReviewViewParams.Subject params) {
        mSubjectUi = new SubjectViewUi<>(view, params);
        return new DataBinder<>(mSubjectUi, subject);
    }
}
