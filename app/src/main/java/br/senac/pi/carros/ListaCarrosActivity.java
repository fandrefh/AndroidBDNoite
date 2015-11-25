package br.senac.pi.carros;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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
    private String[] campos = {"nome", "marca", "_id"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_carros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        carrosDB = new CarrosDB(this);
        database = carrosDB.getReadableDatabase();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        listarCarros();
        //Click item da lista
        listView.setOnItemClickListener(deletarOuEditarItem());
    }

    private void listarCarros() {
        Cursor cursor = database.query("carro", campos, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            dataSource = new SimpleCursorAdapter(getApplicationContext(), R.layout.item_lista, cursor, campos, new int[] {R.id.txtNomeCarro, R.id.txtMarcaCarro}, 0);
            listView.setAdapter(dataSource);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.zero_registro), Toast.LENGTH_LONG).show();
        }
    }

    private AdapterView.OnItemClickListener deletarOuEditarItem() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final long itemSelecionado = id;
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaCarrosActivity.this);
                builder.setTitle(getString(R.string.titulo_alert));
                builder.setMessage(getString(R.string.menssagem_alert));
                builder.setNegativeButton(getString(R.string.opcao_delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.delete("carro", "_id = " + itemSelecionado, null);
                        finish();
                        Intent intent = new Intent(getApplicationContext(), ListaCarrosActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setPositiveButton(getString(R.string.opcao_editar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ListaCarrosActivity.this, AtualizarCarroActivity.class);
                        intent.putExtra("idCarro", String.valueOf(itemSelecionado));
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
