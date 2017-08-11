/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import com.chdryra.android.mygenerallibrary.Collections.CollectionIdable;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;

/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ButtonViewer<T extends GvData> extends ButtonSelector<T> {
    private final String mDefaultView;
    private final int mDefaultPosition;
    private final CollectionIdable<String, NamedReviewView<?>> mViews;
    private boolean mInitialised = false;

    public ButtonViewer(String title,
                        String defaultView,
                        int defaultPosition,
                        OptionsSelector selector,
                        CollectionIdable<String, NamedReviewView<?>> views) {
        super(title, selector);
        mDefaultView = defaultView;
        mDefaultPosition = defaultPosition;
        mViews = views;
        for(NamedReviewView<?> view : mViews) {
            mViews.add(view);
            addOption(new SwitchViewCommand(view));
        }
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        if(!mInitialised) {
            NamedReviewView<T> defaultView = new NamedReviewView<>(mDefaultView, getReviewView());
            mViews.add(defaultView);
            addOption(mDefaultPosition, new SwitchViewCommand(defaultView));
            mInitialised = true;
        }

        setCurrentlySelected(mDefaultView);
    }

    private class SwitchViewCommand extends Command {
        private SwitchViewCommand(NamedReviewView<?> view) {
            super(view.getId());
        }

        @Override
        public void execute() {
            NamedReviewView<?> view = mViews.get(getName());
            if(view != null) {
                getReviewView().switchContainerTo(view.getView());
            }
        }
    }
}
