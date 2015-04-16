package com.unibe.android.stickerGenerator;

import com.unibe.android.stikerGenerator.models.Product;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public List<Integer> lengthAvailableBarcode = new ArrayList<Integer>();
    private String msgError = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lengthAvailableBarcode.add(7);
        lengthAvailableBarcode.add(11);
        lengthAvailableBarcode.add(12);
        lengthAvailableBarcode.add(13);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * @version 1.0
	 * @since 1.0
	 */
	public void onClickGenerate(View v){
		EditText etProductCode = (EditText) findViewById(R.id.etProductCode);
		EditText etProductName= (EditText) findViewById(R.id.etProductName);

        if (isValidData(etProductCode, etProductName)) {
            Product p = new Product(Long.parseLong(etProductCode.getText().toString()),etProductName.getText().toString());
            Intent i = new Intent(this,PreviewActivity.class);
            i.putExtra(PreviewActivity.EXTRA_PRODUCT,p);
            startActivity(i);
        } else {
            Toast.makeText(MainActivity.this, msgError, Toast.LENGTH_SHORT).show();
        }
	}

    /**
     * @version 1.0
     * @since 1.0
     */
    public void onClickAbout(View view) {
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }

    /**
     * Check if the data is valid for two EditTexts
     * @param code EditText for the code of the product
     * @param name EditText for the name of the product
     * @return Boolean with result if the data is valid (not empty) to go through
     */
    private Boolean isValidData(EditText code, EditText name) {
        boolean validData = true;

        if (name.getText() == null || name.getText().toString().isEmpty()){
            validData = false;
            msgError = getString(R.string.msg_product_name_required);
        }
        else if (code.getText() == null || code.getText().toString().isEmpty()){
            validData = false;
            msgError = getString(R.string.msg_product_code_required);
        }
        else{
            String lCode = code.getText() != null ? code.getText().toString() : null;
            if (lCode != null && !lengthAvailableBarcode.contains(lCode.length())){
                validData = false;
                msgError = getString(R.string.msg_product_code_invalidLength);
            }
        }

        return validData;
    }
}
