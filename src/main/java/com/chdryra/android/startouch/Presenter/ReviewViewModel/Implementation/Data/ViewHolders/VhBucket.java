/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;

/**
 * Created by: Rizwan Choudrey
 * On: 16/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class VhBucket<BucketingValue, Data> extends ViewHolderBasic implements ViewHolder {
    public abstract void updateView(GvBucket<BucketingValue, Data> bucket);

    public VhBucket(int layoutId, int[] viewIds) {
        super(layoutId, viewIds);
    }

    @Override
    public void updateView(ViewHolderData data) {
        try {
            updateView((GvBucket<BucketingValue, Data>) data);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
