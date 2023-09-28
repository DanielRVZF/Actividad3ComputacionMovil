package co.edu.uniminuto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UsuarioDao {
    private GestionBD gestionBD;
    private Context context;
    private View view;

    public UsuarioDao(Context context, View view) {
        this.context = context;
        this.view = view;
        gestionBD = new GestionBD(this.context);
    }

    // Metodo para ingresar usuarios
    public void insertUser(Usuario usuario) {
        try {
            SQLiteDatabase db = gestionBD.getWritableDatabase();
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put("USU_DOCUMENTO", usuario.getDocumneto());
                values.put("USU_USUARIO", usuario.getUsuario());
                values.put("USU_NOMBRES", usuario.getNombres());
                values.put("USU_APELLIDOS", usuario.getApellidos());
                values.put("USU_CONTRA", usuario.getContra());
                long response = db.insert("usuarios", null, values);
                Snackbar.make(this.view, "Se ha registrado el usuario: " + response, Snackbar.LENGTH_LONG).show();
                db.close();
            } else {
                Snackbar.make(this.view, "No se ha registrado el usuario", Snackbar.LENGTH_LONG).show();
            }
        } catch (SQLException sqlException) {
            Log.e("DB", "Error al insertar usuario: " + sqlException.getMessage());
            Snackbar.make(this.view, "Error al registrar el usuario", Snackbar.LENGTH_LONG).show();
        }
    }

    public ArrayList<Usuario> getUserlist() {
        SQLiteDatabase db = gestionBD.getReadableDatabase();
        String query = "SELECT * FROM usuarios";
        ArrayList<Usuario> userList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuario.setDocumneto(cursor.getInt(0));
                usuario.setUsuario(cursor.getString(1));
                usuario.setNombres(cursor.getString(2));
                usuario.setApellidos(cursor.getString(3));
                usuario.setContra(cursor.getString(4));
                userList.add(usuario);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    // Metodo para buscar el usuario por documento
    public Usuario buscarUsuarioPorDocumento(int numeroDocumento) {
        SQLiteDatabase db = gestionBD.getReadableDatabase(); // Obtener una isntancia de la base de datos
        String query = "SELECT * FROM usuarios WHERE USU_DOCUMENTO = ?"; /* Se realiza la consulta SQl,la cual va buscar en la tabla
        usuarios en la columna USU_DOCUMENTO sea el mismo valor que ingresa el usuario para buscar*/
        String[] args = { String.valueOf(numeroDocumento) }; // Se genera una arreglo e cadenas el cual contiene el valor del documento que esta buscando el usuario
        Cursor cursor = db.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            Usuario usuario = new Usuario();
            usuario.setDocumneto(cursor.getInt(0));
            usuario.setUsuario(cursor.getString(1));
            usuario.setNombres(cursor.getString(2));
            usuario.setApellidos(cursor.getString(3));
            usuario.setContra(cursor.getString(4));
            cursor.close();
            db.close();
            return usuario;
        } else {
            cursor.close();
            db.close();
            return null;
        }
    }
}


