package com.chdryra.android.reviewer;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;

public class DialogShower {
	public static void show(DialogFragment dialog, Fragment targetFragment, int requestCode, String tag, Bundle args) {
		dialog.setTargetFragment(targetFragment, requestCode);
		dialog.setArguments(args);
		dialog.show(targetFragment.getFragmentManager(), tag);
	}
}
