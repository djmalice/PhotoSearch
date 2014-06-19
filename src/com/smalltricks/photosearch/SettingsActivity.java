package com.smalltricks.photosearch;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	Spinner spImageSize;
	Spinner spImageType;
	Spinner spColorFilter;
	EditText etSiteFilter;
	Button btSaveSettings;
	SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		etSiteFilter = (EditText)findViewById(R.id.etSiteFilter);
		
		spImageSize = (Spinner) findViewById(R.id.spImageSize);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> imageSizeAdapter = ArrayAdapter
				.createFromResource(this, R.array.saImageSize,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		imageSizeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spImageSize.setAdapter(imageSizeAdapter);
		spImageSize.setSelection((int)pref.getLong("imageSizeId",0));
		// Set onItemSelected Callback
		spImageSize.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
							  }
			 
			  @Override
			  public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			  }

		});

		
		
		spImageType = (Spinner) findViewById(R.id.spImageType);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> imageTypeAdapter = ArrayAdapter
				.createFromResource(this, R.array.saImageType,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		imageTypeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spImageType.setAdapter(imageTypeAdapter);
		spImageType.setSelection((int)pref.getLong("imageTypeId",0));
		spImageType.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				
			  }
			 
			  @Override
			  public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			  }

		});
		
		
		
		
		
		
		
		
		spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> colorFilterAdapter = ArrayAdapter
				.createFromResource(this, R.array.saColorFilter,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		colorFilterAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spColorFilter.setAdapter(colorFilterAdapter);
		spColorFilter.setSelection((int)pref.getLong("colorFilterId",0));
		spColorFilter.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				
			  }
			 
			  @Override
			  public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			  }

		});
		
		etSiteFilter.setText(pref.getString("siteFilter", ""));
		
		btSaveSettings = (Button)findViewById(R.id.btSaveSettings);
		btSaveSettings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				    
					Editor edit = pref.edit();
					
					String imageSize =String.valueOf(spImageSize.getSelectedItem());
					long imageSizeId = spImageSize.getSelectedItemId();
					if(imageSize.equals(new String("default"))) {
						edit.putString("imageSize","" );
						edit.putLong("imageSizeId",imageSizeId);
					}
					else {
						edit.putString("imageSize",imageSize );
						edit.putLong("imageSizeId",imageSizeId);
					}
					
					String imageType =String.valueOf(spImageType.getSelectedItem());
					long imageTypeId = spImageType.getSelectedItemId();
					if(imageType.equals(new String("default"))){
						edit.putString("imageType","" );
						edit.putLong("imageTypeId",imageTypeId);
					} else {
						edit.putString("imageType",imageType );
						edit.putLong("imageTypeId",imageTypeId);
					}
					
					String colorFilter =String.valueOf(spColorFilter.getSelectedItem());
					long colorFilterId = spColorFilter.getSelectedItemId();
					if(colorFilter.equals(new String("default"))) {
						edit.putString("colorFilter","" );
						edit.putLong("colorFilterId",colorFilterId);
					} else {
						edit.putString("colorFilter",colorFilter);
						edit.putLong("colorFilterId",colorFilterId);
					}
					
					edit.putString("siteFilter", etSiteFilter.getText().toString());
					
					edit.commit();
			}
		});
		
		
		Toast.makeText(this, "Save your changes",Toast.LENGTH_SHORT).show();

		
		
		
	}
}
