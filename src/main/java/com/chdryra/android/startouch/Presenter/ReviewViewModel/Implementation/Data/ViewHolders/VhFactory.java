/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;


import com.chdryra.android.corelibrary.Viewholder.ViewHolder;

/**
 * Created by: Rizwan Choudrey
 * On: 31/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class VhFactory<T extends ViewHolder> implements ViewHolderFactory<T> {
    private static final String INSTANTIATION_ERR = "Constructor not found: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";

    private final Class<T> mVhClass;

    public VhFactory(Class<T> vhClass) {
        mVhClass = vhClass;
    }

    @Override
    public T newViewHolder() {
        try {
            return mVhClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + mVhClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + mVhClass.getName(), e);
        }
    }
}
