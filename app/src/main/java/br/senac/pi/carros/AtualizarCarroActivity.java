package br.senac.pi.carros;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.senac.pi.carros.domain.CarrosDB;

public class AtualizarCarroActivity extends AppCompatActivity {

    private CarrosDB carrosDB;
    private SQLiteDatabase database;
    private Cursor cursor;
    private String[] campos = {"nome", "marca", "_id"};
    private String idCarro;
    private EditText edtAlterarCarro, edtAlterarMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_carro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        carrosDB = new CarrosDB(this);
        database = carrosDB.getWritableDatabase();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        idCarro = getIntent().getStringExtra("idCarro");
        cursor = carregaCarro(Integer.parseInt(idCarro));
        edtAlterarCarro = (EditText) findViewById(R.id.edtAlterarCarro);
        edtAlterarMarca = (EditText) findViewById(R.id.edtAlterarMarca);
        edtAlterarCarro.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        edtAlterarMarca.setText(cursor.getString(cursor.getColumnIndexOrThrow("marca")));
    }

    private Cursor carregaCarro(int idCarro) {
        cursor = database.query("carro", campos, "_id = " + String.valueOf(idCarro), null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
