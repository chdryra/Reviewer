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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class FragmentReviewTags  extends FragmentReviewGridAddEditDone<GVString> {
	public final static String TAG_STRING = "com.chdryra.android.reviewer.tag_string";
	private GVTagList mTags;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		mTags = (GVTagList) setAndInitData(GVType.TAGS);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_tags_title));
		setBannerButtonText(getResources().getString(R.string.button_add_tag));
	}

	@Override
	protected void onBannerButtonClick() {
		DialogShower.show(new DialogTagAddFragment(), FragmentReviewTags.this, DATA_ADD, DATA_ADD_TAG, Administrator.get(getActivity()).pack(getController()));
	}

	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = Administrator.get(getActivity()).pack(getController());
		args.putString(TAG_STRING, (parent.getItemAtPosition(position)).toString());
		DialogShower.show(new DialogTagEditFragment(), FragmentReviewTags.this, DATA_EDIT, DATA_EDIT_TAG, args);
	}

	@Override
	protected void addData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case ADD:
			String tag = (String)data.getSerializableExtra(DialogTagAddFragment.TAG);
			if(tag != null && tag.length() > 0)
				mTags.add(tag);
			break;
		default:
			return;
		}
	}
	
	@Override
	protected void editData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			String oldTag = (String)data.getSerializableExtra(DialogTagEditFragment.TAG_OLD);
			String newTag = (String)data.getSerializableExtra(DialogTagEditFragment.TAG_NEW);
			mTags.remove(oldTag);
			mTags.add(newTag);
			break;
		case DELETE:
			String toDelete = (String)data.getSerializableExtra(DialogTagEditFragment.TAG_OLD);
			mTags.remove(toDelete);
			break;
		default:
			return;
		}
	}
}
