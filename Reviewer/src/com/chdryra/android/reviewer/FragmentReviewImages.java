package com.chdryra.android.reviewer;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.FunctionPointer;
import com.chdryra.android.mygenerallibrary.ImageHelper;
import com.chdryra.android.reviewer.GVImageList.GVImage;
import com.google.android.gms.maps.model.LatLng;

public class FragmentReviewImages extends FragmentReviewGridAddEditDone<GVImage> {
	public static final String BITMAP = "com.chdryra.android.reviewer.bitmap";
	public static final String CAPTION = "com.chdryra.android.reviewer.caption";
	public static final String LATLNG = "com.chdryra.android.reviewer.latlng";
	
	private GVImageList mImages;
	private HelperReviewImage mHelperReviewImage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mImages = getController().getImages();
		mHelperReviewImage = HelperReviewImage.getInstance(getController());
		
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_images_title));
		setGridViewData(mImages);
		setGridCellLayout(R.layout.grid_cell_image);
		setGridCellDimension(CellDimension.HALF, CellDimension.HALF);
		setBannerButtonText(getResources().getString(R.string.button_add_image));
		setIsEditable(true);
	}
		
	@Override
	protected void onBannerButtonClick() {
		startActivityForResult(HelperReviewImage.getImageChooserIntents(getActivity(), mHelperReviewImage), DATA_ADD);
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		Bundle args = new Bundle();
		GVImage image = (GVImage)parent.getItemAtPosition(position);
		args.putParcelable(BITMAP, image.getBitmap());
		args.putParcelable(LATLNG, image.getLatLng());
		args.putString(CAPTION, image.getCaption());
		DialogShower.show(new DialogImageEditFragment(), FragmentReviewImages.this, DATA_EDIT, DATA_EDIT_TAG, args);
	}
			
	@Override
	protected void onDoneSelected() {
		getController().setImages(mImages);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case DATA_ADD:
			switch (resultCode) {
			case Activity.RESULT_OK:
				final boolean isCamera;
		        if(data == null)
		            isCamera = true;
		        else {
		        	final String action = data.getAction();
		            if(action == null)
		                isCamera = false;
		            else
		                isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		        }
		        
		        if(!isCamera) {
		        	Uri uri = data == null ? null : data.getData();
		        	String path = ImageHelper.getRealPathFromURI(getSherlockActivity(), uri);
		        	mHelperReviewImage.setImageFilePath(path);
		        }
		     
		        if(mHelperReviewImage.bitmapExists())
		        {
		        	if(isCamera) {
			    		Uri imageUri = Uri.fromFile(new File(mHelperReviewImage.getImageFilePath()));
			        	getSherlockActivity().sendBroadcast(
			        			new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));
		        	}
		        	addData(resultCode, data);
		        }
				break;		
			case Activity.RESULT_CANCELED:
				if(!mHelperReviewImage.bitmapExists())
					mHelperReviewImage.deleteImageFile();
				break;
			default:
				break;
			}
			break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	@Override
	protected void addData(int resultCode, Intent data) {
		switch(resultCode) {
		case Activity.RESULT_OK:
			mHelperReviewImage.addReviewImage(getActivity(), mImages, new FunctionPointer<Void>() {
				@Override
				public void execute(Void data) {
					updateUI();
				}
			});
			break;
		default:
			return;
		}
	}
	
	@Override
	protected void editData(int resultCode, Intent data) {
		switch(ActivityResultCode.get(resultCode)) {
		case DONE:
			String newCaption = (String)data.getSerializableExtra(CAPTION);
			if(newCaption != null)
				mImages.updateCaption((Bitmap)data.getParcelableExtra(BITMAP), (LatLng)data.getParcelableExtra(LATLNG), 
						(String)data.getSerializableExtra(DialogImageEditFragment.OLD_CAPTION), newCaption);
			break;
		case DELETE:
			mImages.remove((Bitmap)data.getParcelableExtra(BITMAP), (LatLng)data.getParcelableExtra(LATLNG), (String)data.getSerializableExtra(CAPTION));
			break;
		default:
			return;
		}
	}
}
