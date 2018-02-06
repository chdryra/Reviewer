/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import com.chdryra.android.mygenerallibrary.Collections.CollectionIdable;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 11/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class NamedReviewView<T extends GvData> implements CollectionIdable.Idable<String>{
    private final String mName;
    private final ReviewView<T> mView;

    public NamedReviewView(String name, ReviewView<T> view) {
        mName = name;
        mView = view;
    }

    @Override
    public String getId() {
        return mName;
    }

    public ReviewView<T> getView() {
        return mView;
    }
}
