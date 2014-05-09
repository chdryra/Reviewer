package com.chdryra.android.reviewer;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class FragmentReviewCreate  extends FragmentReviewGrid {
	public final static String TAG_EDIT_STRING = "com.chdryra.android.reviewer.tag_edit_string";
	
	private final static String DIALOG_TAG_ADD_TAG = "TagAddDialog";
	private final static String DIALOG_TAG_EDIT_TAG = "TagEditDialog";
	
	public final static int TAG_ADD = 10;
	public final static int TAG_EDIT = 11;
	
	private ControllerReviewNode mController;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		if(mController == null)
			mController = Controller.addNewReviewInProgress();
	}
	
	@Override
	protected void initUI() {
		initSubjectUI();
		initRatingBarUI();
		initAddTagUI();
		initTagsUI();
	}
	
	private void initSubjectUI() {
		getSubjectView().addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().length() > 0)
					mController.setTitle(s.toString());
			}
		});
		
	}
	
	private void initRatingBarUI() {
		getTotalRatingBar().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
					mController.setRating(rating);
			}
		});
	}

	private void initAddTagUI() {
		getAddDataButton().setText(getResources().getString(R.string.button_add_tag));
		getAddDataButton().setTextColor(getSubjectView().getTextColors().getDefaultColor());
		getAddDataButton().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTagDialog();
			}
		});
	}
	
	private void initTagsUI(){
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		GridViewCellAdapter adapter = new GridViewCellAdapter(getActivity(), new GVDStrings(mController.getTags()), 
				R.layout.grid_view_cell_text_view, getGridCellWidth(), getGridCellHeight(), getSubjectView().getTextColors().getDefaultColor());
		getGridView().setAdapter(adapter);
		getGridView().setColumnWidth(getGridCellWidth());
		getGridView().setNumColumns(getNumberColumns());
		getGridView().setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            showTagDialog((String)parent.getItemAtPosition(position));
	        }
	    });
	};
	
	@Override
	protected void updateUI() {
		updateSubjectTextUI();
		updateRatingBarUI();
		updateTagsUI();
	}

	private void updateSubjectTextUI() {
		getSubjectView().setText(mController.getTitle());
	}
	
	private void updateRatingBarUI() {
		getTotalRatingBar().setRating(mController.getRating());
	}
	
	private void updateTagsUI() {
		ArrayList<String> tags = mController.getTags();
		Collections.sort(tags);
		((GridViewCellAdapter)getGridView().getAdapter()).setData(new GVDStrings(tags));
	}
	
	private void showTagDialog() {
		showDialog(new DialogTagAddFragment(), TAG_ADD, DIALOG_TAG_ADD_TAG, Controller.pack(mController));
	}
	
	private void showTagDialog(String tag) {
		Bundle args = Controller.pack(mController);
		args.putString(TAG_EDIT_STRING, tag);
		showDialog(new DialogTagEditFragment(), TAG_EDIT, DIALOG_TAG_EDIT_TAG, args);
	}
	
	private void showDialog(SherlockDialogFragment dialog, int requestCode, String tag, Bundle args) {
		dialog.setTargetFragment(FragmentReviewCreate.this, requestCode);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), tag);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_done:
			if(mController.getTags().size() == 0) {
				Toast.makeText(getActivity(), R.string.toast_enter_tag, Toast.LENGTH_SHORT).show();
				return true;
			}
			Intent i = new Intent(getActivity(), ActivityReviewEdit.class);
			Controller.pack(mController, i);
			startActivity(i);
			return true;
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
				Intent j = NavUtils.getParentActivityIntent(getSherlockActivity());
				NavUtils.navigateUpTo(getActivity(), j);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		updateTagsUI();
	}
	
	@Override
	protected void deleteData() {
		mController.removeTags();
	}

	@Override
	protected boolean hasData() {
		return mController.hasTags();
	}

	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.dialog_delete_tags_title);
	}

}
