package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewPerspective {
    private ReviewViewAdapter mAdapter;
    private ReviewViewParams mParams;
    private ReviewViewActionCollection mActions;
    private ReviewViewModifier mModifier;

    public interface ReviewViewModifier {
        //abstract methods
        //abstract
        View modify(FragmentReviewView parent, View v, LayoutInflater inflater,
                    ViewGroup container, Bundle savedInstanceState);
    }

    //Constructors
    public ReviewViewPerspective(ReviewViewAdapter adapter,
                                 ReviewViewParams params,
                                 ReviewViewActionCollection actions) {
        this(adapter, params, actions, null);
    }

    public ReviewViewPerspective(ReviewViewAdapter adapter,
                                 ReviewViewParams params,
                                 ReviewViewActionCollection actions,
                                 ReviewViewModifier modifier) {
        mAdapter = adapter;
        mParams = params;
        mActions = actions;
        mModifier = modifier;
    }

    //public methods
    public ReviewViewAdapter getAdapter() {
        return mAdapter;
    }

    public ReviewViewParams getParams() {
        return mParams;
    }

    public ReviewViewActionCollection getActions() {
        return mActions;
    }

    public ReviewViewModifier getModifier() {
        return mModifier;
    }
}

