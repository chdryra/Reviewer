/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 13/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataExpandableUi<T extends HasReviewId> extends DataSectionUi<T> {
    private final Context mContext;
    private final ViewHolderFactory<?> mFactory;
    private final DataConverter<T, ? extends ViewHolderData, ?> mConverter;
    private int mDataSize = 0;
    private LinearLayout mLayout;

    public DataExpandableUi(Context context,
                            LinearLayout view,
                            String title,
                            ViewHolderFactory<?> factory,
                            DataConverter<T, ? extends ViewHolderData, ?> converter) {
        super(view, title);
        mContext = context;
        mFactory = factory;
        mConverter = converter;
    }

    @Override
    protected void setEmpty(String label) {
        TextView placeholder = getValueView();
        placeholder.setTypeface(placeholder.getTypeface(), Typeface.ITALIC);
        placeholder.setText(label);
    }

    @Override
    protected void updateView(IdableList<T> data) {
        mDataSize = data.size();
        getValueView().setVisibility(View.GONE);

        if (mLayout != null) {
            mLayout.removeAllViews();
        } else {
            LinearLayout view = getView();
            mLayout = new LinearLayout(view.getContext());
            mLayout.setOrientation(LinearLayout.VERTICAL);
            view.addView(mLayout);
        }

        for (T datum : data) {
            ViewHolder vh = mFactory.newViewHolder();
            vh.inflate(mContext, null);
            vh.updateView(mConverter.convert(datum));
            mLayout.addView(vh.getView());
        }
    }

    @Override
    public void onClick(View v) {
        if (mDataSize > 0) super.onClick(v);
    }
}
