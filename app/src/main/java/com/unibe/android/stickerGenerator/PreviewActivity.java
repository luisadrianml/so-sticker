package com.unibe.android.stickerGenerator;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.Code11;
import com.onbarcode.barcode.android.IBarcode;
import com.unibe.android.stikerGenerator.models.Product;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PreviewActivity extends Activity {
	
	public static final String EXTRA_PRODUCT = "ext_product";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		
		Intent i = getIntent();
		Product p = (Product) i.getSerializableExtra(EXTRA_PRODUCT);
		
		TextView tvProductCode = (TextView) findViewById(R.id.tvProductCode);
		TextView tvProductName= (TextView) findViewById(R.id.tvProductName);
		
		
		ImageView imgBarcode = (ImageView) findViewById(R.id.imgvBarcode);
		Bitmap bmBarcode = Bitmap.createBitmap(500, 200, Bitmap.Config.ARGB_8888); 
		Canvas canvasBarcode = new Canvas(bmBarcode);
		try {
			generateBarCode(p, canvasBarcode);
			imgBarcode.setImageBitmap(bmBarcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tvProductCode.setText(Long.toString(p.getId()));
		tvProductName.setText(p.getName());
		
	}
	
	/**
	 * Method to generate the bar code
	 * @version 1.0
	 * @since 1.0
	 * */
	private static void generateBarCode(Product p,Canvas canvas) throws Exception
    {
        Code11 barcode = new Code11();

        // barcode data to encode
        barcode.setData(Long.toString(p.getId()));

        // Unit of Measure, pixel, cm, or inch
        barcode.setUom(IBarcode.UOM_PIXEL);
        // barcode bar module width (X) in pixel
        barcode.setX(5f);
        // barcode bar module height (Y) in pixel
        barcode.setY(85f);

        // barcode image margins
        barcode.setLeftMargin(10f);
        barcode.setRightMargin(10f);
        barcode.setTopMargin(10f);
        barcode.setBottomMargin(10f);

        // barcode image resolution in dpi
        barcode.setResolution(72);

        // disply barcode encoding data below the barcode
        barcode.setShowText(false);
        // barcode encoding data font style
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 12));
        // space between barcode and barcode encoding data
        barcode.setTextMargin(6);
        barcode.setTextColor(AndroidColor.black);
        
        // barcode bar color and background color in Android device
        barcode.setForeColor(AndroidColor.black);
        barcode.setBackColor(AndroidColor.white);

        /*
           specify your barcode drawing area
	    */
	    RectF bounds = new RectF(30, 30, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }

	/**
	 * @version 1.0
	 * @since 1.0
	 */
	public void onClickPrint(View view){
		//TODO: finish this module with Google Cloud Print
	}
	
	/**
	 * @version 1.0
	 * @since 1.0
	 */
	public void onClickBack(View view){
		super.onBackPressed();
	}
}
