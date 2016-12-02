/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.view.View;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.NamedComparator;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.AsyncSortable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;

/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class BannerButtonSorter<T extends GvData> extends BannerButtonActionNone<T> {
    private static final int OPTIONS = RequestCodeGenerator.getCode(BannerButtonSorter.class);

    private final ComparatorCollection<? super T> mComparators;
    private final LaunchOptionsCommand mOptionsCommand;
    private NamedComparator<? super T> mCurrentComparator;
    private boolean mLocked = false;

    public BannerButtonSorter(ComparatorCollection<? super T> comparators,
                              LaunchOptionsCommand command) {
        mComparators = comparators;
        mOptionsCommand = command;
        mCurrentComparator = mComparators.next();
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        sort(mCurrentComparator);
    }

    @Override
    public void onClick(View v) {
        if(!mLocked) sort(mComparators.next());
    }

    @Override
    public boolean onLongClick(View v) {
        CommandsList commands = new CommandsList();
        for(NamedComparator<? super T> comparator : mComparators.asList()) {
            commands.add(new ComparatorCommand(comparator));
        }

        mOptionsCommand.execute(commands, mCurrentComparator.getName());

        return true;
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        if(requestCode == OPTIONS && !mLocked) {
            return mOptionsCommand.onOptionSelected(requestCode, option);
        } else {
            return super.onOptionSelected(requestCode, option);
        }
    }

    private void sort(final NamedComparator<? super T> comparator) {
        mLocked = true;
        setTitle(Strings.Buttons.SORTING);
        getAdapter().sort(comparator, new AsyncSortable.OnSortedCallback() {
            @Override
            public void onSorted(CallbackMessage message) {
                if(!message.isError()) mCurrentComparator = comparator;
                setTitle(mCurrentComparator.getName());
                mLocked = false;
            }
        });
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
