package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ActivitiesFragments
        .FragmentReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewPerspective<T extends GvData> {
    private ReviewViewAdapter<T> mAdapter;
    private ReviewViewParams mParams;
    private ReviewViewActions mActions;
    private ReviewViewModifier mModifier;

    public interface ReviewViewModifier {
        //abstract
        View modify(FragmentReviewView parent, View v, LayoutInflater inflater,
                    ViewGroup container, Bundle savedInstanceState);
    }

    //Constructors
    public ReviewViewPerspective(ReviewViewAdapter<T> adapter,
                                 ReviewViewParams params,
                                 ReviewViewActions actions) {
        this(adapter, params, actions, null);
    }

    public ReviewViewPerspective(ReviewViewAdapter<T> adapter,
                                 ReviewViewParams params,
                                 ReviewViewActions actions,
                                 ReviewViewModifier modifier) {
        mAdapter = adapter;
        mParams = params;
        mActions = actions;
        mModifier = modifier;
    }

    //public methods
    public ReviewViewAdapter<T> getAdapter() {
        return mAdapter;
    }

    public ReviewViewParams getParams() {
        return mParams;
    }

    public ReviewViewActions<T> getActions() {
        return mActions;
    }

    public ReviewViewModifier getModifier() {
        return mModifier;
    }
}

