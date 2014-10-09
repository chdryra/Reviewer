/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: tags. Each grid cell shows a tag.
 * <p/>
 * <p>
 * FragmentReviewGrid functionality:
 * <ul>
 * <li>Subject: disabled</li>
 * <li>RatingBar: disabled</li>
 * <li>Banner button: launches DialogTagAddFragment</li>
 * <li>Grid cell click: launches DialogTagEditFragment</li>
 * <li>Grid cell long click: same as click</li>
 * </ul>
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewTags
 * @see com.chdryra.android.reviewer.DialogTagAddFragment
 * @see com.chdryra.android.reviewer.DialogTagEditFragment
 */
public class FragmentReviewTags extends FragmentReviewGridAddEditDone<GVString> {
    public final static String TAG_STRING = "com.chdryra.android.reviewer.tag_string";
    private GVTagList mTags;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTags = (GVTagList) setAndInitData(GVType.TAGS);
        setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_tags_title));
        setBannerButtonText(getResources().getString(R.string.button_add_tag));
        setAddEditDialogs(DialogTagAddFragment.class, DialogTagEditFragment.class);
    }

    @Override
    protected void addData(int resultCode, Intent data) {
        switch (ActivityResultCode.get(resultCode)) {
            case ADD:
                String tag = (String) data.getSerializableExtra(DialogTagAddFragment.TAG);
                if (tag != null && tag.length() > 0) {
                    mTags.add(tag);
                }
                break;
            default:
        }
    }

    @Override
    protected void editData(int resultCode, Intent data) {
        switch (ActivityResultCode.get(resultCode)) {
            case DONE:
                String oldTag = (String) data.getSerializableExtra(DialogTagEditFragment.TAG_OLD);
                String newTag = (String) data.getSerializableExtra(DialogTagEditFragment.TAG_NEW);
                mTags.remove(oldTag);
                mTags.add(newTag);
                break;
            case DELETE:
                String toDelete = (String) data.getSerializableExtra(DialogTagEditFragment.TAG_OLD);
                mTags.remove(toDelete);
                break;
            default:
        }
    }

    @Override
    protected Bundle packGridCellData(GVString tag, Bundle args) {
        args.putString(TAG_STRING, tag.get());
        return args;
    }
}
