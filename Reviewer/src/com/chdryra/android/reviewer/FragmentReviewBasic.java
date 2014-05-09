package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public abstract class FragmentReviewBasic extends SherlockFragment{

	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;
	private static final int DELETE_CONFIRM = DialogBasicFragment.DELETE_CONFIRM;
	private boolean mDeleteConfirmed = false;

	protected abstract void deleteData();
	protected abstract boolean hasData();
	protected abstract String getDeleteConfirmationTitle();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch (requestCode) {
	        case DELETE_CONFIRM:
				if(resultCode == Activity.RESULT_OK) {
					mDeleteConfirmed = true;
					sendResult(RESULT_DELETE);
				}
				break;			
			default:
				break;
		    }
	}
	
	protected void sendResult(int resultCode) {
		if (resultCode == RESULT_DELETE && hasData()) {
			if(mDeleteConfirmed)
				deleteData();
			else {
				DialogBasicFragment.showDeleteConfirmDialog(getDeleteConfirmationTitle(), FragmentReviewBasic.this, DELETE_CONFIRM, getFragmentManager());
				return;
			}
		}

		getActivity().setResult(resultCode);
		getActivity().finish();	
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);		
		inflater.inflate(R.menu.menu_delete_done, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				sendResult(Activity.RESULT_CANCELED);
				return true;
		
			case R.id.menu_item_delete:
				sendResult(RESULT_DELETE);
				return true;
			
			case R.id.menu_item_done:
				sendResult(Activity.RESULT_OK);
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}

}
