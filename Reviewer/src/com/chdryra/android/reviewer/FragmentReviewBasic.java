package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class FragmentReviewBasic extends SherlockFragment {

	private static final int DELETE_CONFIRM = 0;

	private boolean mDismissOnDone = true;
	private boolean mDismissOnDelete = false;
	private boolean mDeleteConfirmation = true;
	private String mDeleteWhat;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case DELETE_CONFIRM:
			if (ActivityResultCode.OK.equals(resultCode))
				doDeleteSelected();
			break;
		default:
			break;
		}
	}

	protected void sendResult(ActivityResultCode resultCode) {
		getActivity().setResult(resultCode.get());
	}

	protected void setDeleteWhatTitle(String deleteWhat) {
		mDeleteWhat = deleteWhat;
	}

	protected void setDeleteConfirmation(boolean deleteConfirmation) {
		mDeleteConfirmation = deleteConfirmation;
	}

	protected boolean hasDataToDelete() {
		return false;
	}
	
	protected void onDeleteSelected() {
	}

	protected void onDoneSelected() {
	}

	protected void onUpSelected() {
	}

	protected void setDismissOnDone(boolean dismiss) {
		mDismissOnDone = dismiss;
	}
	
	protected void setDismissOnDelete(boolean dismiss) {
		mDismissOnDelete = dismiss;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_delete_done, menu);
	}

	private void showDeleteConfirmDialog() {
		DialogDeleteConfirmFragment.showDeleteConfirmDialog(mDeleteWhat,
				FragmentReviewBasic.this, DELETE_CONFIRM, getFragmentManager());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			doUpSelected();
			return true;

		case R.id.menu_item_delete:
			if (hasDataToDelete() && mDeleteConfirmation)
				showDeleteConfirmDialog();
			else
				doDeleteSelected();
			return true;

		case R.id.menu_item_done:
			doDoneSelected();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void doDeleteSelected() {
		if (hasDataToDelete()) {
			onDeleteSelected();
			sendResult(ActivityResultCode.DELETE);
			if(mDismissOnDelete)
				getActivity().finish();
		}
	}
	
	private void doDoneSelected() {
		onDoneSelected();
		sendResult(ActivityResultCode.DONE);
		if(mDismissOnDone)
			getActivity().finish();
	}
	
	private void doUpSelected() {
		onUpSelected();
		sendResult(ActivityResultCode.UP);
		getActivity().finish();
	}
}
