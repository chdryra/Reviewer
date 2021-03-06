/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;


import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.Comparators.ComparatorCollection;
import com.chdryra.android.corelibrary.Comparators.NamedComparator;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.Sortable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CommandList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.OptionsSelector;

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
        this(comparators.getDefault().getId(), selector, comparators);
    }

    public ButtonSorter(String title, OptionsSelector selector,
                        ComparatorCollection<? super T> comparators) {
        super(selector, new CommandList(title), true);
        mComparators = comparators;

        mCurrentComparator = mComparators.iterator().next();
        for (NamedComparator<? super T> comparator : mComparators) {
            addOption(new ComparatorCommand(comparator));
        }
        setCurrentlySelected(mCurrentComparator.getId());
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        sort(mCurrentComparator);
    }

    @Override
    protected void launchSelector() {
        if (!mLocked) super.launchSelector();
    }

    private void sort(final NamedComparator<? super T> comparator) {
        mLocked = true;
        mCurrentComparator = comparator;
        setTitle(Strings.Buttons.SORTING);
        getAdapter().sort(comparator, new Sortable.OnSortedCallback() {
            @Override
            public void onSorted(CallbackMessage message) {
                if (message.isOk()) mCurrentComparator = comparator;
                setTitle(mCurrentComparator.getId());
                mLocked = false;
            }
        });
    }

    private class ComparatorCommand extends Command {
        private ComparatorCommand(NamedComparator<? super T> comparator) {
            super(comparator.getId());
        }

        @Override
        public void execute() {
            NamedComparator<? super T> comparator = mComparators.get(getName());
            if (comparator != null) sort(comparator);
        }
    }
}
