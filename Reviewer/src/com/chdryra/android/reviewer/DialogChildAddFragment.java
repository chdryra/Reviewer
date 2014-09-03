package com.chdryra.android.reviewer;

import java.text.DecimalFormat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class DialogChildAddFragment extends DialogAddReviewDataFragment{
	public static final String SUBJECT = "com.chdryra.android.reviewer.subject";
	public static final String RATING = "com.chdryra.android.reviewer.rating";
	
	private GVReviewList mChildren;	
	private ClearableEditText mChildNameEditText;
	private RatingBar mChildRatingBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mChildren = (GVReviewList) setAndInitData(GVType.CRITERIA);
		setDialogTitle(getResources().getString(R.string.dialog_add_criteria_title));
	}

	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_criterion, null);
		mChildNameEditText = (ClearableEditText)v.findViewById(R.id.child_name_edit_text);
		mChildRatingBar = (RatingBar)v.findViewById(R.id.child_rating_bar);
		setKeyboardIMEDoAction(mChildNameEditText);
		return v;
	}

	@Override
	protected void OnAddButtonClick() {
		String childName = mChildNameEditText.getText().toString();
		float childRating = mChildRatingBar.getRating();
		
		if(childName == null || childName.length() == 0)
			return;
		
		if(mChildren.contains(childName)) {
			Toast.makeText(getActivity(), childName + ": " + getResources().getString(R.string.toast_exists_criterion), Toast.LENGTH_SHORT).show();
			return;
		}
		
		mChildren.add(childName, childRating);
		
		Intent i = getNewReturnData();
		i.putExtra(SUBJECT, childName);
		i.putExtra(RATING, childRating);
		
		mChildNameEditText.setText(null);		
		mChildRatingBar.setRating(0);
		
		double d = childRating;
		DecimalFormat formatter = new DecimalFormat("0");
		DecimalFormat decimalFormatter = new DecimalFormat("0.0");
		String rating = d % 1L > 0L? decimalFormatter.format(d) : formatter.format(d);

		getDialog().setTitle("+ " + childName + ": " + rating + "/" + "5");
	}
}
