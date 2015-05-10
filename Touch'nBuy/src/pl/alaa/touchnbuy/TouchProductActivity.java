package pl.alaa.touchnbuy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import nxr.tpad.lib.TPad;
import nxr.tpad.lib.TPadImpl;
import nxr.tpad.lib.views.FrictionMapView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class TouchProductActivity extends Activity {

	// Custom Haptic Rendering view defined in TPadLib
	FrictionMapView fricView;
	
	// TPad object defined in TPadLib
	TPad mTpad;

	private ImageView imageView;

	private View decorView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_product);
		
		
		Resources resources = getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		if (resourceId > 0) {
			int a = resources.getDimensionPixelSize(resourceId);
		    Log.i("NAVIGATION BAR HEIGHT", "" + a);
		}
		
		
		decorView = getWindow().getDecorView();
		// Hide both the navigation bar and the status bar.
		// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
		// a general rule, you should design your app to hide the status bar whenever you
		// hide the navigation bar.
//		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//		              | View.SYSTEM_UI_FLAG_FULLSCREEN;
//		decorView.setSystemUiVisibility(uiOptions);
		
		Intent intent = getIntent();
		
		Uri url = intent.getData();
		int imageId = 1;
		if (url != null) {
			List<String> params = url.getPathSegments();
			String first = params.get(0); 
//			String path = url.getPath();
	
			imageId = Integer.parseInt(first);
		}
		
		try {
			int hapticId = 0;
			int backgroundId = 0;
			
			switch (imageId) {
			case 1:
				hapticId = R.drawable.rybki_1;
				backgroundId = R.drawable.rybki_1_org;
				break;
				
			case 2:
				hapticId = R.drawable.congresia2;
				backgroundId = R.drawable.congersja_org;
				break;
				
			case 3:
				hapticId = R.drawable.wave_01_inverse;
				backgroundId = R.drawable.wave_01_org;
				break;

			case 4:
				hapticId = R.drawable.plytka_3_v2;
				backgroundId = R.drawable.plytka_3_org;
				break;

			case 5:
				hapticId = R.drawable.img_1_zoom_1;
				backgroundId = R.drawable.img_1_zoom_1_org;
				break;
				
			default:
//				finish();
			}
			
			initHaptic(hapticId, backgroundId);

		} catch (Exception e) {
			finish();
		}

	}


	private void initHaptic(int hapticImageRes, int backgroundImageRes) {
//		if (hapticImageRes > 0 && backgroundImageRes > 0) {
//			finish();
//		}

		
		mTpad = new TPadImpl(this);
		

		fricView = (FrictionMapView) findViewById(R.id.view1);

		fricView.setTpad(mTpad);

		Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), hapticImageRes);

		fricView.setDataBitmap(defaultBitmap);
//		defaultBitmap.recycle();
//		fricView.setDisplayShowing(false);

		
		
		
		
		Bitmap displayBitmap = BitmapFactory.decodeResource(getResources(), backgroundImageRes);
		
//		displayBitmap = getBitmapFromURL("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==");
		
		fricView.setDisplayBitmap(displayBitmap);

		fricView.setDataDisplayed(false);
		fricView.setBackgroundColor(Color.TRANSPARENT);

	
		
		

		imageView = (ImageView) findViewById(R.id.view2);
		imageView.setImageResource(backgroundImageRes);

//		imageView.setImageBitmap(displayBitmap);

		imageView.setVisibility(View.GONE);
		
	}
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        Log.e("XXX", "", e);
	        return null;
	    }
	}
	
//	@Override
//	public void onBackPressed() {
//		if (imageView.getVisibility() == View.GONE) {
//			imageView.setVisibility(View.VISIBLE);
//		} else {
//			imageView.setVisibility(View.GONE);
//		}
//
////		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////		              | View.SYSTEM_UI_FLAG_FULLSCREEN;
////		decorView.setSystemUiVisibility(uiOptions);
//
//	};

	@Override
	protected void onDestroy() {
		if (mTpad != null) 
			mTpad.disconnectTPad();
		super.onDestroy();
	}
}
