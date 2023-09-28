package co.edu.uniminuto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GestionBD extends SQLiteOpenHelper {

    private static final String DATABASE_USER = "dbUsuarios";
    private static final int VERSION = 1;
    private static final String TABLE_USERS = "usuarios";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS +
            " (USU_DOCUMENTO INTEGER PRIMARY KEY, USU_USUARIO VARCHAR(50) NOT NULL, USU_NOMBRES VARCHAR(150) NOT NULL, " +
            " USU_APELLIDOS VARCHAR(150) NOT NULL, USU_CONTRA VARCHAR(25) NOT NULL)";

    public GestionBD(Context context) {
        super(context, DATABASE_USER, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        Log.i("BASE DE DATOS", "SE CREO LA BASE DE DATOS");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
