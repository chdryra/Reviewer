/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



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
        .UiManagers.Interfaces.ReviewViewLayout;
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

public class ReviewViewFragmentLayout implements ReviewViewLayout {
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
    private CoverUi mCover;
    private SubjectUi<?> mSubjectUi;
    private RatingUi<?> mRatingUi;
    private RecyclerViewUi<?> mGridUi;

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
    public <T extends GvData> void attachReviewView(ReviewView<T> reviewView,
                                                    CellDimensionsCalculator calculator) {
        ReviewViewActions<T> actions = reviewView.getActions();
        ReviewViewParams params = reviewView.getParams();
        ReviewViewAdapter<T> adapter = reviewView.getAdapter();

        mMenu = newMenuUi(actions.getMenuAction());
        mSubject = bindSubject(adapter, params.getSubjectParams());
        mRating = bindRating(reviewView);
        mSort = bindBanner((Button)mView.findViewById(BANNER),
                actions.getBannerButtonAction(), params.getBannerButtonParams());
        mGridData = bindGridView(adapter.getGridDataReference(),
                actions.getGridItemAction(), params.getGridViewParams(), calculator);
        mCover = newCoverUi();
        mViewSelector = bindContextView(actions.getContextualAction(), params.getContextViewParams());
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
    public void setCover(@Nullable Bitmap cover) {
        //View only
    }

    @Override
    public void bind() {
        mSubject.bind();
        mRating.bind();
        mGridData.bind();
        mSort.bind();
        mViewSelector.bind();
    }

    @Override
    public void unbind() {
        mSubject.unbind();
        mRating.unbind();
        mGridData.unbind();
        mSort.unbind();
        mViewSelector.unbind();
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
    private CoverUi newCoverUi() {
        return new CoverRvUi((ImageView) mView.findViewById(COVER), mGridUi);
    }

    @NonNull
    private MenuUi newMenuUi(MenuAction<?> action) {
        return new MenuUi(action);
    }

    @NonNull
    private <T extends GvData> DataBinder<GvDataList<T>> bindGridView(DataReference<GvDataList<T>> data,
                                                                      GridItemAction<T> action,
                                                                      ReviewViewParams.GridView params,
                                                                      CellDimensionsCalculator calculator) {
        RecyclerViewUi<T> ui = new RecyclerViewUi<>((RecyclerView) mView.findViewById(GRID),
                action, params, calculator);
        mGridUi = ui;
        return new DataBinder<>(ui, data);
    }

    @NonNull
    private DataBinder<String> bindContextView(@Nullable ButtonAction<?> action,
                                               ReviewViewParams.ContextView params) {
        ContextUi ui = new ContextUi(mView.findViewById(CONTEXT_VIEW), CONTEXT_BUTTON,
                action, params, DECORATOR);
        return new DataBinder<>(ui, action != null ? action.getTitle() : new NullDataReference
                <String>());
    }

    @NonNull
    private DataBinder<String> bindBanner(Button view, ButtonAction<?> action, ReviewViewParams.BannerButton params) {
        BannerButtonUi ui = new BannerButtonUi(view, action, params.getAlpha(), DECORATOR);
        return new DataBinder<>(ui, action.getTitle());
    }

    @NonNull
    private DataBinder<Float> bindRating(ReviewView<?> reviewView) {
        mRatingUi = new RatingTextUi(reviewView, (TextView) mView.findViewById(RATING));
        return new DataBinder<>(mRatingUi, reviewView.getAdapter().getRatingReference());
    }

    @NonNull
    private DataBinder<String> bindSubject(ReviewViewAdapter<?> adapter,
                                          ReviewViewParams.Subject params) {
        mSubjectUi = new SubjectViewUi<>((TextView) mView.findViewById(SUBJECT), params);
        return new DataBinder<>(mSubjectUi, adapter.getSubjectReference());
    }
}
