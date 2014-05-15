package com.chdryra.android.reviewer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockDialogFragment;

public abstract class DialogThreeButtonFragment extends SherlockDialogFragment {

	protected Button mLeftButton;
	protected Button mMiddleButton;
	protected Button mRightButton;

	private String mLeftButtonText;
	private String mMiddleButtonText;
	private String mRightButtonText;

	private ActivityResultCode mLeftButtonResult;
	private ActivityResultCode mMiddleButtonResult;
	private ActivityResultCode mRightButtonResult;

	private boolean mDismissOnLeftClick = false;
	private boolean mDismissOnMiddleClick = false;
	private boolean mDismissOnRightClick = false;

	private String mDialogTitle;
	private Intent mReturnData = new Intent();
	
	public static enum ActionType {
		CANCEL(ActivityResultCode.CANCEL), 
		DONE(ActivityResultCode.DONE), 
		OTHER(ActivityResultCode.OTHER), 
		EDIT(ActivityResultCode.EDIT), 
		ADD(ActivityResultCode.ADD), 
		DELETE(ActivityResultCode.DELETE), 
		CLEAR(ActivityResultCode.CLEAR), 
		OK(ActivityResultCode.OK);
		
		private final ActivityResultCode mResultCode;

		private ActionType(ActivityResultCode resultCode) {
			this.mResultCode = resultCode;
		}

		private ActivityResultCode getResultCode() {
			return mResultCode;
		}
	}

	protected abstract View createDialogUI();

	protected void onLeftButtonClick() {
		sendResult(mLeftButtonResult);
	};

	protected void onMiddleButtonClick() {
		sendResult(mMiddleButtonResult);
	};

	protected void onRightButtonClick() {
		sendResult(mRightButtonResult);
	};

	public void setLeftButtonText(String leftButtonText) {
		mLeftButtonText = leftButtonText;
	}

	public void setMiddleButtonText(String middleButtonText) {
		mMiddleButtonText = middleButtonText;
	}

	public void setRightButtonText(String rightButtonText) {
		mRightButtonText = rightButtonText;
	}

	public void setLeftButtonAction(ActionType action) {
		mLeftButtonText = getTitleForAction(action);
		mLeftButtonResult = action.getResultCode();
	}

	public void setMiddleButtonAction(ActionType action) {
		mMiddleButtonText = getTitleForAction(action);
		mMiddleButtonResult = action.getResultCode();
	}

	public void setRightButtonAction(ActionType action) {
		mRightButtonText = getTitleForAction(action);
		mRightButtonResult = action.getResultCode();
	}

	private String getTitleForAction(ActionType type) {
		if (type == ActionType.ADD)
			return getResources().getString(R.string.button_add_text);
		if (type == ActionType.CANCEL)
			return getResources().getString(R.string.button_cancel_text);
		if (type == ActionType.DELETE)
			return getResources().getString(R.string.button_delete_text);
		if (type == ActionType.DONE)
			return getResources().getString(R.string.button_done_text);
		if (type == ActionType.EDIT)
			return getResources().getString(R.string.button_edit_text);
		if (type == ActionType.CLEAR)
			return getResources().getString(R.string.button_clear_text);
		if (type == ActionType.OK)
			return getResources().getString(R.string.button_ok_text);
		else
			return null;
	}

	public void setDialogTitle(String dialogTitle) {
		mDialogTitle = dialogTitle;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return buildDialog();
	}

	@Override
	public void onStop() {
		getSherlockActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onStop();
	}

	protected void setDismissDialogOnLeftClick(boolean dismiss) {
		mDismissOnLeftClick = dismiss;
	}

	protected void setDismissDialogOnMiddleClick(boolean dismiss) {
		mDismissOnMiddleClick = dismiss;
	}

	protected void setDismissDialogOnRightClick(boolean dismiss) {
		mDismissOnRightClick = dismiss;
	}

	protected Intent getReturnData() {
		return mReturnData;
	}
	
	protected void sendResult(ActivityResultCode resultCode) {
		if (getTargetFragment() == null)
			return;

		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode.get(), getReturnData());

		if (resultCode.equals(mLeftButtonResult) && mDismissOnLeftClick)
			dismiss();

		if (resultCode.equals(mMiddleButtonResult) && mDismissOnMiddleClick)
			dismiss();

		if (resultCode.equals(mRightButtonResult) && mDismissOnRightClick)
			dismiss();
	}

	protected View getButtons() {
		View buttons = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_button_layout, null);

		mLeftButton = (Button) buttons.findViewById(R.id.button_left);
		mMiddleButton = (Button) buttons.findViewById(R.id.button_middle);
		mRightButton = (Button) buttons.findViewById(R.id.button_right);

		mLeftButton.setText(mLeftButtonText);
		mLeftButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLeftButtonClick();
			}
		});

		mMiddleButton.setText(mMiddleButtonText);
		mMiddleButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onMiddleButtonClick();
			}
		});

		mRightButton.setText(mRightButtonText);
		mRightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRightButtonClick();
			}
		});

		return buttons;
	}

	protected Dialog buildDialog() {
		Dialog dialog = new Dialog(getActivity());

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
		
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(lp);

		//Hacky layout params to get listview dialogUIs to render properly. 
		//Need to set layout weight of 1 on it...
		layout.addView(createDialogUI(), lp1);
		layout.addView(getButtons(), lp);

		dialog.setContentView(layout);
		dialog.setTitle(mDialogTitle);
		dialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		return dialog;
	}
}
