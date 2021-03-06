/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation;

import android.support.annotation.Nullable;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class Command {
    private int mRequestCode;
    private ExecutionListener mListener;
    private String mName;

    public interface ExecutionListener {
        void onCommandExecuted(int requestCode);
    }

    public abstract void execute();

    public Command(String name) {
        mName = name;
    }

    public Command() {
        mName = "";
    }

    public static Command NoAction(String name) {
        return new Command(name) {
            @Override
            public void execute() {

            }
        };
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void execute(int requestCode, @Nullable ExecutionListener listener) {
        mRequestCode = requestCode;
        mListener = listener;
        execute();
    }

    protected void onExecutionComplete() {
        if (mListener != null) mListener.onCommandExecuted(mRequestCode);
    }

    int getRequestCode() {
        return mRequestCode;
    }
}
