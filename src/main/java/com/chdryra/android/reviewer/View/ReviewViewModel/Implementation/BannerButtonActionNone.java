package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;

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
