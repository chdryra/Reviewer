package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class FragmentReviewTags  extends FragmentReviewGridAddEditDone<GVString> {
	public final static String TAG_STRING = "com.chdryra.android.reviewer.tag_string";
	private GVTagList mTags;
	private boolean mReviewInProgress = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		if(getController() == null) {
			setController(Controller.addNewReviewInProgress());
			mReviewInProgress = true;
		}
		
		mTags = (GVTagList) setAndInitData(GVType.TAGS);
		
		setDismissOnDone(false);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_tags_title));
		setBannerButtonText(getResources().getString(R.string.button_add_tag));
		setIsEditable(true);
	}

	@Override
	protected void onBannerButtonClick() {
		DialogShower.show(new DialogTagAddFragment(), FragmentReviewTags.this, DATA_ADD, DATA_ADD_TAG, Controller.pack(getController()));
	}

	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = Controller.pack(getController());
		args.putString(TAG_STRING, ((GVString)parent.getItemAtPosition(position)).toString());
		DialogShower.show(new DialogTagEditFragment(), FragmentReviewTags.this, DATA_EDIT, DATA_EDIT_TAG, args);
	}

	@Override
	protected void onUpSelected() {
		if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
			Intent i = NavUtils.getParentActivityIntent(getSherlockActivity());
			if(!mReviewInProgress)
				Controller.pack(getController(), i);
			NavUtils.navigateUpTo(getActivity(), i);
		}
	}
	
	@Override
	protected void onDoneSelected() {
		if(mTags.size() == 0) {
			Toast.makeText(getActivity(), R.string.toast_enter_tag, Toast.LENGTH_SHORT).show();
			return;
		}
		
		super.onDoneSelected();
		
		Intent i = new Intent(getActivity(), ActivityReviewEdit.class);
		Controller.pack(getController(), i);
		startActivity(i);
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
