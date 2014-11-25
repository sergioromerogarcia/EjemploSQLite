package sekth.droid.sqlite.Activities;

import sekth.droid.sqlite.R;
import sekth.droid.sqlite.ddbb.NotasDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NuevaNotaActivity extends Activity {

	public static int resultCode = 10;

	private Button btnAgregar;
	private EditText txtNota;
	private NotasDataSource dataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_nota);

		dataSource = new NotasDataSource(this);
		dataSource.open();

		txtNota = (EditText) findViewById(R.id.txtNota);
		btnAgregar = (Button) findViewById(R.id.btnAgregar);
		btnAgregar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String textoNota = txtNota.getText().toString();

				if (textoNota.length() != 0) {
					dataSource.crearNota(textoNota);
					setResult(RESULT_OK);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"No ha introducido texto", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		dataSource.close();
		super.onPause();
	}

}
