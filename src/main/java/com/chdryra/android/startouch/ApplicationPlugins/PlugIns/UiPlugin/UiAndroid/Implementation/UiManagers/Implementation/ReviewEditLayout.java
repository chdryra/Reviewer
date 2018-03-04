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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.NullDataReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.Styles;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Interfaces.ReviewViewContainerLayout;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
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

public class ReviewEditLayout implements ReviewViewContainerLayout {
    private static final int LAYOUT = R.layout.fragment_review_edit;
    private static final int SUBJECT = R.id.subject_text;
    private static final int RATING = R.id.review_rating;
    private static final int BANNER = R.id.banner_button;
    private static final int GRID = R.id.gridview_data;
    private static final int COVER = R.id.background_image;
    private static final int CONTEXT_VIEW = R.id.contextual_view;
    private static final int CONTEXT_BUTTON = R.id.wide_button;

    private static final TitleDecorator CONTEXT_DECORATOR = Styles.TitleDecorators.DONE_BUTTON;
    private static final TitleDecorator BANNER_DECORATOR = Styles.TitleDecorators.OPTION_BUTTON;

    private View mView;

    private MenuUi mMenu;
    private SubjectUi<?> mSubjectUi;
    private RatingBarUi mRatingUi;
    private RecyclerViewUi<?> mGridUi;

    private DataBinder<String> mSubject;
    private DataBinder<Float> mRating;
    private DataBinder<Bitmap> mCover;
    private DataBinder<?> mGridData;
    private DataBinder<String> mBanner;
    private DataBinder<String> mContextual;

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
        mSubject = bindSubject(adapter.getSubjectReference(), actions.getSubjectAction(), params.getSubjectParams());
        mRating = bindRating(adapter.getRatingReference(), actions.getRatingBarAction(), params.getRatingBarParams());
        mBanner = bindBanner(actions.getBannerButtonAction(), params.getBannerButtonParams());
        mGridData = bindGridView(adapter.getGridDataReference(), actions.getGridItemAction(), params.getGridViewParams(), calculator);
        mCover = bindCover(adapter.getCoverReference());
        mContextual = newContextUi(actions.getContextualAction(), params.getContextViewParams());
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
        mRatingUi.update(rating);
    }

    @Override
    public void bind() {
        mSubject.bind();
        mRating.bind();
        mGridData.bind();
        mBanner.bind();
        mCover.bind();
        mContextual.bind();
    }

    @Override
    public void unbind() {
        mSubject.unbind();
        mRating.unbind();
        mGridData.unbind();
        mBanner.unbind();
        mCover.unbind();
        mContextual.unbind();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return false;
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return false;
    }

    @NonNull
    private DataBinder<String> newContextUi(@Nullable ButtonAction<?> action, ReviewViewParams.ContextView params) {
        ContextUi ui = new ContextUi(mView.findViewById(CONTEXT_VIEW), CONTEXT_BUTTON,
                action, params, CONTEXT_DECORATOR);
        return new DataBinder<>(ui, action != null ? action.getTitle() : new NullDataReference<String>());
    }

    @NonNull
    private DataBinder<Bitmap> bindCover(DataReference<Bitmap> cover) {
        CoverUi ui = new CoverUi((ImageView) mView.findViewById(COVER), mGridUi);
        return new DataBinder<>(ui, cover);
    }

    @NonNull
    private MenuUi newMenuUi(MenuAction<?> action) {
        return new MenuUi(action);
    }

    @NonNull
    private <T extends GvData> DataBinder<GvDataList<T>> bindGridView
            (DataReference<GvDataList<T>> data,
             GridItemAction<T> action,
             ReviewViewParams.GridView params,
             CellDimensionsCalculator calculator) {
        RecyclerViewUi<T> ui = new RecyclerViewUi<>((RecyclerView) mView.findViewById(GRID),
                action, params, calculator);
        mGridUi = ui;
        return new DataBinder<>(ui, data);
    }


    @NonNull
    private DataBinder<String> bindBanner(ButtonAction<?> action, ReviewViewParams.BannerButton params) {
        BannerButtonUi ui = new BannerButtonUi((Button) mView.findViewById(BANNER),
                action, params.getAlpha(), BANNER_DECORATOR);
        return new DataBinder<>(ui, action.getTitle());
    }

    @NonNull
    private DataBinder<Float> bindRating(DataReference<Float> rating, RatingBarAction<?> action, ReviewViewParams.RatingBar params) {
        mRatingUi = new RatingBarRvUi((FrameLayout) mView.findViewById(RATING), action, params);
        return new DataBinder<>(mRatingUi, rating);
    }

    @NonNull
    private DataBinder<String> bindSubject(DataReference<String> subject,
                                           SubjectAction<?> action,
                                           ReviewViewParams.Subject params) {
        mSubjectUi = new SubjectEditUi((EditText) mView.findViewById(SUBJECT),
                action, params);
        return new DataBinder<>(mSubjectUi, subject);
    }
}
