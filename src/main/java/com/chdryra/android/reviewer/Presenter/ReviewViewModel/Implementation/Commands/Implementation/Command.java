/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;

import android.support.annotation.Nullable;

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

    abstract void execute();

    public void execute(int requestCode, @Nullable ExecutionListener listener) {
        mRequestCode = requestCode;
        mListener = listener;
        execute();
    }

    int getRequestCode() {
        return mRequestCode;
    }

    void onExecutionComplete() {
        if(mListener != null) mListener.onCommandExecuted(mRequestCode);
    }
}
