package com.chdryra.android.reviewer;

import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.chdryra.android.reviewer.GVSocialPlatformList.GVSocialPlatform;

public class FragmentReviewShare extends FragmentReviewGrid<GVSocialPlatformList.GVSocialPlatform> {
	public final static String TAG_STRING = "com.chdryra.android.reviewer.tag_string";
	private GVSocialPlatformList mSocialList;
	private Button mPublishButton;
	private Drawable mDefault;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initSocialSharingList();
		
		setGridViewData(mSocialList);
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setDismissOnDone(false);
		setBannerButtonText(getResources().getString(R.string.button_social));
		setIsEditable(false);
		setBackgroundImageAlpha(0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		getBannerButton().setClickable(false);
		View divider = inflater.inflate(R.layout.horizontal_divider, container, false);
		mPublishButton = (Button)inflater.inflate(R.layout.review_banner_button, container, false);
		mPublishButton.setText(getResources().getString(R.string.button_publish));
		mPublishButton.getLayoutParams().height = LayoutParams.MATCH_PARENT;
		mPublishButton.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				
			}
		});
		
		getGridView().getLayoutParams().height = LayoutParams.WRAP_CONTENT;
		getLayout().addView(mPublishButton);
		getLayout().addView(divider);
		
		return v;
	}
	
	private void initSocialSharingList() {
		mSocialList = Administrator.get(getActivity()).getSocialPlatformList(true);
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		GVSocialPlatform platform = (GVSocialPlatform)parent.getItemAtPosition(position);
		if(mDefault == null && !platform.isChosen())
			mDefault = v.getBackground();
		
		platform.press();
		
		if(platform.isChosen())
			v.setActivated(true);
		else
			v.setActivated(false);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
	}
}
