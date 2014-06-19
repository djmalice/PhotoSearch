package com.smalltricks.photosearch;

import android.app.Activity;
import android.os.Bundle;

import com.loopj.android.image.SmartImageView;

public class FullImage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image);
		String url = getIntent().getStringExtra("url");
		SmartImageView ivImage = (SmartImageView)findViewById(R.id.ivResult);
		ivImage.setImageUrl(url);
	}
}
