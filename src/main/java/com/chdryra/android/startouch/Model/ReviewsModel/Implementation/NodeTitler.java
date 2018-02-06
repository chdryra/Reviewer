/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;

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

    protected void bindToReference(ReferenceBinder<T> binder) {
        mReference.bindToValue(binder);
    }

    protected abstract void fireForBinder();

    protected TitleBinder getBinder() {
        return mBinder;
    }

    public static class AuthorsTree extends NodeTitler<NamedAuthor> implements ReferenceBinder<NamedAuthor>{
        private static final String SEPARATOR = "'s ";

        private String mTitle;
        private String mStem;

        public AuthorsTree(DataReference<NamedAuthor> reference, String stem) {
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
        public void onReferenceValue(NamedAuthor value) {
            mTitle = value.getName() + SEPARATOR + mStem;
            fireForBinder();
        }

        @Override
        public void onInvalidated(DataReference<NamedAuthor> reference) {
            mTitle = mStem;
            fireForBinder();
        }
    }
}
