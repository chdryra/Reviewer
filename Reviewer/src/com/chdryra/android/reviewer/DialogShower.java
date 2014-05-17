package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class DialogShower {
	public static void show(SherlockDialogFragment dialog, Fragment targetFragment, int requestCode, String tag, Bundle args) {
		dialog.setTargetFragment(targetFragment, requestCode);
		dialog.setArguments(args);
		dialog.show(targetFragment.getFragmentManager(), tag);
	}
}
