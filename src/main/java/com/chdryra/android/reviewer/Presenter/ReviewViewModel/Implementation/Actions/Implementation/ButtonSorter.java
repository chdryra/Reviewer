/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.NamedComparator;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.AsyncSortable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;

/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ButtonSorter<T extends GvData> extends ButtonSelector<T> {
    private final ComparatorCollection<? super T> mComparators;
    private NamedComparator<? super T> mCurrentComparator;
    private boolean mLocked;

    public ButtonSorter(OptionsSelector selector,
                        ComparatorCollection<? super T> comparators) {
        this(comparators.getDefault().getName(), selector, comparators);
    }

    public ButtonSorter(String title, OptionsSelector selector,
                        ComparatorCollection<? super T> comparators) {
        super(title, selector);
        mComparators = comparators;

        mCurrentComparator = mComparators.next();
        for(NamedComparator<? super T> comparator : mComparators.asList()) {
            addOption(new ComparatorCommand(comparator));
        }
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        sort(mCurrentComparator);
    }

    private void sort(final NamedComparator<? super T> comparator) {
        mLocked = true;
        mCurrentComparator = comparator;
        setTitle(Strings.Buttons.SORTING);
        getAdapter().sort(comparator, new AsyncSortable.OnSortedCallback() {
            @Override
            public void onSorted(CallbackMessage message) {
                if(message.isOk()) mCurrentComparator = comparator;
                setTitle(mCurrentComparator.getName());
                mLocked = false;
            }
        });
    }

    @Override
    protected void launchSelector() {
        if(!mLocked) super.launchSelector(mCurrentComparator.getName());
    }

    private class ComparatorCommand extends Command {
        private NamedComparator<? super T> mComparator;

        private ComparatorCommand(NamedComparator<? super T> comparator) {
            super(comparator.getName());
            mComparator = comparator;
        }

        @Override
        public void execute() {
            sort(mComparators.moveToComparator(mComparator.getName()));
        }
    }
}
