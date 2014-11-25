package sekth.droid.sqlite.Activities;

import java.util.List;

import sekth.droid.sqlite.R;
import sekth.droid.sqlite.clases.Nota;
import sekth.droid.sqlite.ddbb.NotasDataSource;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NotasActivity extends Activity implements OnItemClickListener {
	private int requestCode = 1;
	private ListView lvNotas;
	private NotasDataSource dataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notas);

		// Instanciamos NotasDataSource para
		// poder realizar acciones con la base de datos
		dataSource = new NotasDataSource(this);
		dataSource.open();

		// Instanciamos los elementos
		lvNotas = (ListView) findViewById(R.id.lvNotas);

		// Cargamos la lista de notas disponibles
		List<Nota> listaNotas = dataSource.getAllNotas();
		ArrayAdapter<Nota> adapter = new ArrayAdapter<Nota>(this,
				android.R.layout.simple_list_item_1, listaNotas);

		// Establecemos el adapter
		lvNotas.setAdapter(adapter);

		// Establecemos un Listener para el evento de pulsación
		lvNotas.setOnItemClickListener(this);

	}

	public void agregarNota(View v) {
		Intent i = new Intent(this, NuevaNotaActivity.class);
		startActivityForResult(i, requestCode);
	}

	@Override
	public void onItemClick(final AdapterView<?> adapterView, View view,
			final int position, long id) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setTitle("Borrar Nota")
				.setMessage("¿Desea borrar esta nota?")
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								Nota nota = (Nota) adapterView
										.getItemAtPosition(position);
								dataSource.borrarNota(nota);
								
								// Refrescamos la lista
								refrescarLista();
							}
						})

				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								return;
							}
						});
		builder.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.d("Result", "Se ejecuta onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == this.requestCode && resultCode == RESULT_OK) {
			// Actualizar el Adapter
			dataSource.open();
			refrescarLista();
		}
	}

	private void refrescarLista() {
		List<Nota> listaNotas = dataSource.getAllNotas();
		ArrayAdapter<Nota> adapter = new ArrayAdapter<Nota>(this,
				android.R.layout.simple_list_item_1, listaNotas);
		lvNotas.setAdapter(adapter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		dataSource.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		dataSource.open();
		super.onResume();
	}
	
}
