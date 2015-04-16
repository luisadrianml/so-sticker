package com.unibe.android.stickerGenerator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.Code11;
import com.onbarcode.barcode.android.IBarcode;
import com.unibe.android.stikerGenerator.models.Product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PreviewActivity extends Activity {
	
	public static final String EXTRA_PRODUCT = "ext_product";


    private Product p = null;
    private Bitmap bmBarcode = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		
		Intent i = getIntent();
		p = (Product) i.getSerializableExtra(EXTRA_PRODUCT);
		
		TextView tvProductCode = (TextView) findViewById(R.id.tvProductCode);
		TextView tvProductName= (TextView) findViewById(R.id.tvProductName);
		
		
		ImageView imgBarcode = (ImageView) findViewById(R.id.imgvBarcode);
		bmBarcode = Bitmap.createBitmap(500, 200, Bitmap.Config.ARGB_8888);
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
	public void onClickPrint(View view) throws IOException {
        if (!isNetworkAvailable()) {
            Toast.makeText(PreviewActivity.this,
                    getResources().getString(R.string.notNetwork),
                    Toast.LENGTH_SHORT).show();
        } else {
            boolean error = false;

            String path_pdf_temp = getApplicationContext().getCacheDir() + "/sticker.pdf";

            //generate pdf to print
            try{

                Document doc = new Document();
                Paragraph par;
                PdfWriter.getInstance(doc, new FileOutputStream(path_pdf_temp));
                doc.open();



                par = new Paragraph(50);
                par.add(getString(R.string.product_code) + ": " + p.getId());
                doc.add(par);
                par = new Paragraph(50);
                par.add(getString(R.string.product_name) + ": " + p.getName());
                doc.add(par);

                doc.close();

            }catch(Exception e){
                error = true;
            }
            //end: generate pdf to print


            File file = new File(path_pdf_temp);
            Intent printIntent = new Intent(PreviewActivity.this, PrintDialogActivity.class);
            printIntent.setDataAndType(Uri.fromFile(file), "application/pdf");
            printIntent.putExtra(PrintDialogActivity.PRINT_FLAG_EXTRA, "Android print demo");
            startActivity(printIntent);
        }

	}

    public boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "***Available***");
            return true;
        }
        Log.e("Network Testing", "***Not Available***");
        return false;
    }
	
	/**
	 * @version 1.0
	 * @since 1.0
	 */
	public void onClickBack(View view){
		super.onBackPressed();
	}
}
