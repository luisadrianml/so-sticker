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

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Toast.makeText(MainActivity.this, getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
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

    private Boolean isValidData(EditText code, EditText name) {
        Boolean result = !code.getText().toString().isEmpty() && !name.getText().toString().isEmpty();
        return result;
    }
}
