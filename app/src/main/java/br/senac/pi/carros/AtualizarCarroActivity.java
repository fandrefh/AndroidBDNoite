package br.senac.pi.carros;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.senac.pi.carros.domain.CarrosDB;

public class AtualizarCarroActivity extends AppCompatActivity {

    private CarrosDB carrosDB;
    private SQLiteDatabase database;
    private Cursor cursor;
    private String[] campos = {"nome", "marca", "tipo_carro", "_id"};
    private String idCarro;
    private EditText edtAlterarCarro, edtAlterarMarca;
    private RadioGroup radioGroup;
    private RadioButton rbAtualizarSedan, rbAtualizarHatch;

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
        radioGroup = (RadioGroup) findViewById(R.id.rgAtualizarTipoCarro);
        rbAtualizarSedan = (RadioButton) findViewById(R.id.rbAtualizarSedan);
        rbAtualizarHatch = (RadioButton) findViewById(R.id.rbAtualizarHatch);
        edtAlterarCarro.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        edtAlterarMarca.setText(cursor.getString(cursor.getColumnIndexOrThrow("marca")));
        String tipoCarro = cursor.getString(cursor.getColumnIndexOrThrow("tipo_carro"));
        if (tipoCarro != null && tipoCarro.equalsIgnoreCase("Sedan")) {
            rbAtualizarSedan.setChecked(true);
        } else if (tipoCarro != null && tipoCarro.equalsIgnoreCase("Hatch")){
            rbAtualizarHatch.setChecked(true);
        } else {
            rbAtualizarSedan.setChecked(true);
        }
        findViewById(R.id.btnAtualizarCarro).setOnClickListener(atualizarCarro());
    }

    private Cursor carregaCarro(int idCarro) {
        cursor = database.query("carro", campos, "_id = " + String.valueOf(idCarro), null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    private View.OnClickListener atualizarCarro() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("nome", edtAlterarCarro.getText().toString());
                values.put("marca", edtAlterarMarca.getText().toString());
                int idTipoSelecionado = radioGroup.getCheckedRadioButtonId();
                String tipo;
                if (idTipoSelecionado == R.id.rbAtualizarSedan) {
                    tipo = "Sedan";
                } else {
                    tipo = "Hatch";
                }
                values.put("tipo_carro", tipo);
                long update = database.update("carro", values, "_id = " + String.valueOf(idCarro), null);
                if (update != 0) {
                    Toast.makeText(AtualizarCarroActivity.this, getString(R.string.sucesso_atualizar_carro), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AtualizarCarroActivity.this, ListaCarrosActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AtualizarCarroActivity.this, getString(R.string.erro_atualizar_carro), Toast.LENGTH_LONG).show();
                }
            }
        };
    }

}
