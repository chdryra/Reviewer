/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment;

/**
 * Dialog for adding URLs (disabled): Allows user to enter a URL or can forward request to a
 * browser window where user can search then specify a URL.
 *
 * @see FragmentEditUrlBrowser
 */
public class DialogUrl extends DialogCancelActionDoneFragment implements LaunchableUi {
    private static final ActionType RESULT_BROWSE = ActionType.OTHER;

    private static final String TAG = "DialogURLFragment";

    private ReviewBuilder.DataBuilder<GvUrlList.GvUrl> mBuilder;
    private ClearableEditText                          mUrlEditText;

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    protected View createDialogUi() {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_url, null);

        mUrlEditText = (ClearableEditText) v.findViewById(R.id.url_edit_text);
        if (mBuilder.getGridData().size() == 1) {
            mUrlEditText.setText(((GvUrlList.GvUrl) mBuilder.getGridData().getItem(0))
                    .toShortenedString());
        }

        setKeyboardDoDoneOnEditText(mUrlEditText);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO make type safe
        mBuilder = (ReviewBuilder.DataBuilder<GvUrlList.GvUrl>) Administrator.get(getActivity())
                .getReviewBuilder().getDataBuilder(GvDataList.GvType.URLS);

        setActionButtonAction(RESULT_BROWSE);
        setActionButtonText(getResources().getString(R.string.button_browse));
        dismissDialogOnActionClick();

        setDialogTitle(getResources().getString(R.string.dialog_url_title));
    }

    @Override
    protected void onDoneButtonClick() {
        String urlString = mUrlEditText.getText().toString();
        if (urlString.length() > 0) {
            try {
                mBuilder.add(new GvUrlList.GvUrl(urlString));
            } catch (Exception e) {
                Log.i(TAG, "Malformed URL or incorrect syntax: " + urlString, e);
                Toast.makeText(getActivity(), getResources().getString(R.string.toast_bad_url),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
