/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class HorizontalGridUiTouchable<T extends GvData> extends HorizontalGridUi<T>{
    private static final float CLICK_THRESHOLD = 10;

    public HorizontalGridUiTouchable(Context context,
                                     RecyclerView view,
                                     ViewHolderFactory<?> vhFactory,
                                     ValueGetter<GvDataList<T>> getter,
                                     int span,
                                     CellDimensionsCalculator.Dimensions dims,
                                     ApplicationInstance app,
                                     Review review) {
        super(context, view, vhFactory, getter, span, dims);
        getView().addOnItemTouchListener(new Listener(app, review));
    }

    private boolean isAClick(float startX, float endX, float startY, float endY) {
        return !(Math.abs(startX - endX) > CLICK_THRESHOLD
                || Math.abs(startY - endY) > CLICK_THRESHOLD);
    }

    private void launchViewScreen(ApplicationInstance app, Review review) {
        UiSuite ui = app.getUi();
        TagsManager tagsManager = app.getRepository().getTagsManager();
        GvDataType<T> dataType = getValue().getGvDataType();
        ReviewView<?> reviewView = ui.newDataView(review, tagsManager, dataType);
        ui.getLauncher().launch(reviewView, new UiLauncherArgs(RequestCodeGenerator.getCode(HorizontalGridUiTouchable.class, dataType.getDataName())));
    }

    private class Listener implements RecyclerView.OnItemTouchListener {
        private final ApplicationInstance mApp;
        private final Review mReview;

        private float mStartX = 0f;
        private float mStartY = 0f;

        public Listener(ApplicationInstance app, Review review) {
            mApp = app;
            mReview = review;
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mStartX = e.getX();
                    mStartY = e.getY();
                    break;
                case MotionEvent.ACTION_UP: {
                    float endX = e.getX();
                    float endY = e.getY();
                    if (isAClick(mStartX, endX, mStartY, endY)) launchViewScreen(mApp, mReview);
                    break;
                }
            }
            return false;

        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
