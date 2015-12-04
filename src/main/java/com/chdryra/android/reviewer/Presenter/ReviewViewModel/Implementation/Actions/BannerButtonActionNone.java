package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonActionNone<T extends GvData> extends ReviewViewActionBasic<T>
        implements BannerButtonAction<T> {
    private String mTitle;

    //Constructors
    public BannerButtonActionNone() {
    }

    public BannerButtonActionNone(String title) {
        mTitle = title;
    }

    //public methods
    @Override
    public String getButtonTitle() {
        return mTitle;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }
}
