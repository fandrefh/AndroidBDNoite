package br.senac.pi.carros;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import br.senac.pi.carros.domain.CarrosDB;

public class MainActivity extends AppCompatActivity {

    private CarrosDB carrosDB;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        findViewById(R.id.btnCadastrarCarro).setOnClickListener(cadastrarCarro());
    }

    //Salva carro no Banco de Dados da Aplicação
    private View.OnClickListener cadastrarCarro() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = carrosDB.getWritableDatabase();
                ContentValues values = new ContentValues();
                EditText edtNomeCarro = (EditText) findViewById(R.id.edtNomeCarro);
                EditText edtMarcaCarro = (EditText) findViewById(R.id.edtMarcaCarro);
                values.put("nome", edtNomeCarro.getText().toString());
                values.put("marca", edtMarcaCarro.getText().toString());
                long id = database.insert("carro", null, values);
                if (id != 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.cadastro_sucesso), Toast.LENGTH_LONG).show();
                    edtNomeCarro.setText("");
                    edtMarcaCarro.setText("");
                    edtNomeCarro.requestFocus();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.cadastro_erro), Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            database.close();
        } catch (NullPointerException e) {
            Log.i("curso", "Babou geral: " + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ListaCarrosActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
