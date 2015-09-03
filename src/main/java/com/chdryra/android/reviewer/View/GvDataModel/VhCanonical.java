package com.chdryra.android.reviewer.View.GvDataModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhCanonical implements ViewHolder {
    ViewHolder mViewHolder;

    public VhCanonical(GvDataType dataType) {
        GvData nullItem = FactoryGvData.newNull(dataType.getDataClass());
        mViewHolder = nullItem.getViewHolder();
    }

    @Override
    public void inflate(Context context, ViewGroup parent) {
        mViewHolder.inflate(context, parent);
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvCanonical canonical = (GvCanonical) data;
        mViewHolder.updateView(canonical.getCanonical());
    }

    @Override
    public View getView() {
        return mViewHolder.getView();
    }
}
