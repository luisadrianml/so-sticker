package com.unibe.android.stickerGenerator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.Codabar;
import com.onbarcode.barcode.android.Code11;
import com.onbarcode.barcode.android.EAN13;
import com.onbarcode.barcode.android.EAN8;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.ITF14;
import com.onbarcode.barcode.android.UPCA;
import com.unibe.android.stikerGenerator.models.Product;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import java.io.OutputStream;

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
		bmBarcode = Bitmap.createBitmap(700,300, Bitmap.Config.ARGB_8888);

		Canvas canvasBarcode = new Canvas(bmBarcode);
		try {
			generateBarCode(p, canvasBarcode);
			imgBarcode.setImageBitmap(bmBarcode);
            //imgBarcode.setScaleType(ImageView.ScaleType.MATRIX);
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
        int lenCode = p.getId().toString().length();

        if (lenCode == 7){
            EAN8 barcode = new EAN8();

	         /*
               EAN 8 Valid data char set:
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9 (Digits)

               EAN 8 Valid data length: 7 digits only, excluding the last checksum digit
            */
            barcode.setData(Long.toString(p.getId()));


            // Unit of Measure, pixel, cm, or inch
            barcode.setUom(IBarcode.UOM_PIXEL);
            // barcode bar module width (X) in pixel
            barcode.setX(4f);
            // barcode bar module height (Y) in pixel
            barcode.setY(110f);

            // barcode image margins
            barcode.setLeftMargin(10f);
            barcode.setRightMargin(10f);
            barcode.setTopMargin(10f);
            barcode.setBottomMargin(10f);

            // barcode image resolution in dpi
            barcode.setResolution(72);

            // disply barcode encoding data below the barcode
            barcode.setShowText(true);
            // barcode encoding data font style
            barcode.setTextFont(new AndroidFont("Arial", 0, 35));
            // space between barcode and barcode encoding data
            barcode.setTextMargin(6);
            barcode.setTextColor(AndroidColor.black);

            // barcode bar color and background color in Android device
            barcode.setForeColor(AndroidColor.black);
            barcode.setBackColor(AndroidColor.white);

	        /* specify your barcode drawing area*/
            RectF bounds = new RectF(80, 80, 0, 0);
            barcode.drawBarcode(canvas, bounds);
        }
        else if (lenCode == 11){
               //UPC-A
            UPCA barcode = new UPCA();

            /*
               UPC-A Valid data char set:
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9 (Digits)

               UPC-A Valid data length: 11 digits only, excluding the last checksum digit
            */
            barcode.setData(Long.toString(p.getId()));

            // Unit of Measure, pixel, cm, or inch
            barcode.setUom(IBarcode.UOM_PIXEL);
            // barcode bar module width (X) in pixel
            barcode.setX(4f);
            // barcode bar module height (Y) in pixel
            barcode.setY(110f);

            // barcode image margins
            barcode.setLeftMargin(10f);
            barcode.setRightMargin(10f);
            barcode.setTopMargin(10f);
            barcode.setBottomMargin(10f);

            // barcode image resolution in dpi
            barcode.setResolution(72);

            // disply barcode encoding data below the barcode
            barcode.setShowText(true);
            // barcode encoding data font style
            barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 35));
            // space between barcode and barcode encoding data
            barcode.setTextMargin(6);
            barcode.setTextColor(AndroidColor.black);

            // barcode bar color and background color in Android device
            barcode.setForeColor(AndroidColor.black);
            barcode.setBackColor(AndroidColor.white);

            /*specify your barcode drawing area*/
            RectF bounds = new RectF(80, 80, 0, 0);
            barcode.drawBarcode(canvas, bounds);
        }
        else if (lenCode == 12){
            //EAN 13

            EAN13 barcode = new EAN13();

            /*
               EAN 13 Valid data char set:
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9 (Digits)

               EAN 13 Valid data length: 12 digits only, excluding the last checksum digit
            */
            barcode.setData(Long.toString(p.getId()));

            // for EAN13 with supplement data (2 or 5 digits

            // Unit of Measure, pixel, cm, or inch
            barcode.setUom(IBarcode.UOM_PIXEL);
            // barcode bar module width (X) in pixel
            barcode.setX(4f);
            // barcode bar module height (Y) in pixel
            barcode.setY(110f);

            // barcode image margins
            barcode.setLeftMargin(10f);
            barcode.setRightMargin(10f);
            barcode.setTopMargin(10f);
            barcode.setBottomMargin(10f);

            // barcode image resolution in dpi
            barcode.setResolution(72);

            // disply barcode encoding data below the barcode
            barcode.setShowText(true);
            // barcode encoding data font style
            barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 35));
            // space between barcode and barcode encoding data
            barcode.setTextMargin(6);
            barcode.setTextColor(AndroidColor.black);

            // barcode bar color and background color in Android device
            barcode.setForeColor(AndroidColor.black);
            barcode.setBackColor(AndroidColor.white);

	        /*specify your barcode drawing area*/
            RectF bounds = new RectF(80, 80, 0, 0);
            barcode.drawBarcode(canvas, bounds);
        }
        else if (lenCode == 13){
            //ITF14
            ITF14 barcode = new ITF14();

            /*
               ITF 14 Valid data char set:
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9 (Digits)

               ITF 14 Valid data length: 13 digits only, excluding the last checksum digit
            */
            barcode.setData(Long.toString(p.getId()));

            // ITF-14 Wide Narrow bar Ratio
            // Valid value is from 2.0 to 3.0 inclusive.
            barcode.setN(3.0f);

            // ITF-14 bearer bar size vs bar module (X) size ratio
            // Valid values are 0-10 which are a multiple of X.
            barcode.setBearerBarHori(1);
            barcode.setBearerBarVert(1);

            // Unit of Measure, pixel, cm, or inch
            barcode.setUom(IBarcode.UOM_PIXEL);
            // barcode bar module width (X) in pixel
            barcode.setX(4f);
            // barcode bar module height (Y) in pixel
            barcode.setY(110f);

            // barcode image margins
            barcode.setLeftMargin(10f);
            barcode.setRightMargin(10f);
            barcode.setTopMargin(10f);
            barcode.setBottomMargin(10f);

            // barcode image resolution in dpi
            barcode.setResolution(72);

            // disply barcode encoding data below the barcode
            barcode.setShowText(true);
            // barcode encoding data font style
            barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 35));
            // space between barcode and barcode encoding data
            barcode.setTextMargin(6);
            barcode.setTextColor(AndroidColor.black);

            // barcode bar color and background color in Android device
            barcode.setForeColor(AndroidColor.black);
            barcode.setBackColor(AndroidColor.white);

	        /*specify your barcode drawing area */
            RectF bounds = new RectF(80, 80, 0, 0);
            barcode.drawBarcode(canvas, bounds);
        }
    }

	/**
     * Method for print the file generated (when print button is pressed)
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

                //persist barcode image to set to pdf
                String pathBarcode = persistImage(bmBarcode,"barcode_" + p.getId());

                doc.add(Image.getInstance(pathBarcode));


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


            //start:  proccess of printing file with Google Cloud Print
            File file = new File(path_pdf_temp);
            Intent printIntent = new Intent(PreviewActivity.this, PrintDialogActivity.class);
            printIntent.setDataAndType(Uri.fromFile(file), "application/pdf");
            printIntent.putExtra(PrintDialogActivity.PRINT_FLAG_EXTRA, "sticker_"+p.getId());
            startActivity(printIntent);
            //end: process of printing file with Google Cloud Print

        }

	}

    /**
     * Check if the supplied context can render PDF files via some installed application that reacts to a intent
     * with the pdf mime type and viewing action.
     * @param context The Context of the application
     * @return if the android device can display PDF files within others apps
     * @version 1.0
     * @since 1.0
     */
    public static boolean canDisplayPdf(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        if (packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

     /**
     * @author Fernando Perez
     * @version 1.0
     * @since 1.0
     */
    private String persistImage(Bitmap bitmap, String name) {
        File filesDir = getCacheDir();
        File imageFile = new File(filesDir, name + ".png");
        boolean error = false;
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            error = true;
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }

        return !error ? imageFile.getAbsolutePath() : null;
    }

    /**
     * Verify if network is available
     * @return boolean with the respective value about the network available
     * @version 1.0
     * @since 1.0
     */
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
     * Back button is pressed
	 * @version 1.0
	 * @since 1.0
	 */
	public void onClickBack(View view){
		super.onBackPressed();
	}
}
