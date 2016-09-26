/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class Command {
    private int mRequestCode;
    private ExecutionListener mListener;

    public interface ExecutionListener {
        void onCommandExecuted(int requestCode);
    }

    public abstract void execute();

    Command(int requestCode, ExecutionListener listener) {
        mRequestCode = requestCode;
        mListener = listener;
    }

    int getRequestCode() {
        return mRequestCode;
    }

    void onExecutionComplete() {
        mListener.onCommandExecuted(mRequestCode);
    }
}
