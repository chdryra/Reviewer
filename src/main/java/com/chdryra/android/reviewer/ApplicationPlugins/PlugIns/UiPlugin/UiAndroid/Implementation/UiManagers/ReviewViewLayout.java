/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 06/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public interface ReviewViewLayout extends OptionSelectListener{
    View inflateLayout(LayoutInflater inflater, ViewGroup container);

    <T extends GvData> void attachReviewView(ReviewView<T> reviewView, CellDimensionsCalculator calculator);

    void inflateMenu(Menu menu, MenuInflater inflater);

    boolean onMenuItemSelected(MenuItem item);

    String getSubject();

    float getRating();

    void setRating(float rating);

    void setCover(@Nullable Bitmap cover);

    void update(boolean forceSubject);
}
