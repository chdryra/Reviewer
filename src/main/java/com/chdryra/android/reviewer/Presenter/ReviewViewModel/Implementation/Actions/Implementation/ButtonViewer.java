/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import com.chdryra.android.mygenerallibrary.Collections.CollectionIdable;
import com.chdryra.android.mygenerallibrary.Collections.CollectionIdableImpl;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;

/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ButtonViewer<T extends GvData> extends ButtonSelector<T> {
    private NamedReviewView<?> mCurrentView;
    private final CollectionIdable<String, NamedReviewView<?>> mViews;
    private boolean mInitialised = false;

    public ButtonViewer(String title,
                        OptionsSelector selector) {
        super(title, selector);
        mViews = new CollectionIdableImpl<>();
    }

    public void addView(NamedReviewView<?> view) {
        mViews.add(view);
        addOption(new SwitchViewCommand(view));
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        if(!mInitialised) {
            NamedReviewView<T> defaultView = new NamedReviewView<>(getButtonTitle(), getReviewView());
            mViews.add(defaultView);
            addOption(0, new SwitchViewCommand(defaultView));
            mCurrentView = defaultView;
            mInitialised = true;
        }
    }

    private class SwitchViewCommand extends Command {
        private SwitchViewCommand(NamedReviewView<?> view) {
            super(view.getId());
        }

        @Override
        public void execute() {
            NamedReviewView<?> view = mViews.get(getName());
            if(view != null) {
                mCurrentView = view;
                getReviewView().switchContainerTo(mCurrentView.getView());
            }
        }
    }
}
