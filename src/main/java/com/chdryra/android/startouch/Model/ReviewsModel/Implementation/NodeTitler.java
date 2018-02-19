/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 08/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class NodeTitler<T> {
    private DataReference<T> mReference;
    private TitleBinder mBinder;

    public interface TitleBinder {
        void onTitle(String title);
    }

    public NodeTitler(DataReference<T> reference) {
        mReference = reference;
    }

    public void bind(TitleBinder binder) {
        if(mBinder != null) throw new IllegalStateException("Already bound!");
        mBinder = binder;
        fireForBinder();
    }

    protected void bindToReference(DataReference.ValueSubscriber<T> binder) {
        mReference.subscribe(binder);
    }

    protected abstract void fireForBinder();

    protected TitleBinder getBinder() {
        return mBinder;
    }

    public static class AuthorsTree extends NodeTitler<AuthorName> implements DataReference.ValueSubscriber<AuthorName> {
        private static final String SEPARATOR = "/";

        private String mTitle;
        private String mStem;

        public AuthorsTree(DataReference<AuthorName> reference, String stem) {
            super(reference);
            mStem = stem;
            mTitle = mStem;
            bindToReference(this);
        }

        @Override
        protected void fireForBinder() {
            getBinder().onTitle(mTitle);
        }

        @Override
        public void onReferenceValue(AuthorName value) {
            mTitle = value.getName() + SEPARATOR + mStem;
            fireForBinder();
        }

        @Override
        public void onInvalidated(DataReference<AuthorName> reference) {
            mTitle = mStem;
            fireForBinder();
        }
    }
}
