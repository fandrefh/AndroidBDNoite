package br.senac.pi.carros.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.PrivateKey;

/**
 * Created by Note on 18/11/2015.
 */
public class CarrosDB extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "cursoandroid.db";
    private static final int VERSAO_BANCO = 1;
    private static final String TAG = "curso";

    public CarrosDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Criando a tabela 'carro'.");
        db.execSQL("CREATE TABLE IF NOT EXISTS carro (_id integer primary key autoincrement," +
                "nome text, marca text);");
        Log.i(TAG, "Tabela criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
