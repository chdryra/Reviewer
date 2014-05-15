package com.chdryra.android.reviewer;

public enum ActivityResultCode {
	CANCEL(0), DONE(1), OTHER(2), EDIT(3), ADD(4), DELETE(5), CLEAR(6), OK(7), UP(8);

	private final int mValue;

	private ActivityResultCode(int value) {
		this.mValue = value;
	}

	public int get() {
		return mValue;
	}

	public static ActivityResultCode get(int resultCode) {
		ActivityResultCode returnCode = null;
		for (ActivityResultCode code : ActivityResultCode.values()) {
			if (code.equals(resultCode))
			{
				returnCode = code;
				break;
			}
		}

		return returnCode;
	}

	public boolean equals(ActivityResultCode resultCode) {
		return mValue == resultCode.get();
	}

	public boolean equals(int resultCode) {
		return mValue == resultCode;
	}
}
