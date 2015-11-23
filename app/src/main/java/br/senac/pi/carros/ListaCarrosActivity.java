package br.senac.pi.carros;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import br.senac.pi.carros.domain.CarrosDB;

public class ListaCarrosActivity extends AppCompatActivity {

    private CarrosDB carrosDB;
    private SQLiteDatabase database;
    private CursorAdapter dataSource;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_carros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carrosDB = new CarrosDB(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.listarCarros).setOnClickListener(listarCarros());
    }

    private View.OnClickListener listarCarros() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = carrosDB.getReadableDatabase();
                String[] campos = {"nome", "marca", "_id"};
                Cursor cursor = database.query("carro", campos, null, null, null, null, null);
                if (cursor.getCount() > 0) {
                    dataSource = new SimpleCursorAdapter(getApplicationContext(), R.layout.item_lista, cursor, campos, new int[] {R.id.txtNomeCarro, R.id.txtMarcaCarro}, 0);
                    listView.setAdapter(dataSource);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.zero_registro), Toast.LENGTH_LONG).show();
                }
            }
        };
    }

}
