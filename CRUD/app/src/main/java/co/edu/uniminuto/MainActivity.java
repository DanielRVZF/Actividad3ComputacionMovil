package co.edu.uniminuto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import co.edu.uniminuto.Usuario;

public class MainActivity extends AppCompatActivity {

    // Declarar variables

    private EditText et_documento;
    private EditText et_usuario;
    private EditText et_nombres;
    private EditText et_apellidos;
    private EditText et_contra;
    private EditText et_buscar_documento;
    private ListView listaUsuarios;

    // Declaración de variables
    private int documento;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_documento = findViewById(R.id.et_documento);
        et_usuario = findViewById(R.id.et_usuario);
        et_nombres = findViewById(R.id.et_nombres);
        et_apellidos = findViewById(R.id.et_apellidos);
        et_contra = findViewById(R.id.et_CONTRA);
        et_buscar_documento = findViewById(R.id.et_buscar_documento);
        listaUsuarios = findViewById(R.id.LV_lista);
        listarUsuarios();
    }

    // Metodo para listar usuarios
    private void listarUsuarios() {
        UsuarioDao usuarioDao = new UsuarioDao(this, findViewById(R.id.LV_lista));
        ArrayList<Usuario> userList = usuarioDao.getUserlist();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listaUsuarios.setAdapter(adapter);
    }


    // Se verifica que el dato ingresado en el campo documento corresponda a un valor numerico

    public void setearDatos() {
        String documentoStr = et_documento.getText().toString();

        // Verificar si la entrada es numérica
        if (validarNumero(documentoStr)) { // Se llama el metodo para validar si el campo ingresado es numerico
            documento = Integer.parseInt(documentoStr); // Se transforma la cadena a un entero
        } else {
           // Si el valor no es númerico se muestra al usuairo el error
            mostrarCamposErroneos("El documento debe ser valor númerico");
        }

        nombres = et_nombres.getText().toString(); //Se obtiene el valor ingresado en el campo nombres
        apellidos = et_apellidos.getText().toString(); //Se obtiene el valor ingresado en el campo apellidos
        usuario = et_usuario.getText().toString(); // Se obtiene el valor ingresado en el campo usuario
        contra = et_contra.getText().toString(); // Se obtiene el valor ingresado en el campo contraseña
    }

    // Metodo para validar si el valor ingresado es numerico
    private boolean validarNumero(String str) {
        try {
            Integer.parseInt(str); // Transformar la cadena en un entero
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Metodo para registrar usuarios
    public void accionRegistrar(View v) {
        try {
            setearDatos(); // Se llama el metodo setear datos
            if (validarDocumento(documento) && validarNombre(nombres) && validarContra(contra)) {
                /*
                * validarDocumento(documento)-> se llama a la función validarDocumento, para verificar que el dato ingresado en este campo sea correcto
                *validarNombre(nombres)-> se llama a la función validarNombre, para verificar que el dato ingresado en este campo sea correct
                * validarContra(contra)-> se llama a la función validarContra(contra), para verificar que el dato ingresado en este campo sea correcto
                * */
                UsuarioDao usuarioDao = new UsuarioDao(this, v);
                Usuario usuario1 = new Usuario();
                usuario1.setDocumneto(documento);
                usuario1.setNombres(nombres);
                usuario1.setApellidos(apellidos);
                usuario1.setUsuario(usuario);
                usuario1.setContra(contra);

                usuarioDao.insertUser(usuario1);
                listarUsuarios(); // Llama metodo listar usuarios
            } else {
                // En caso de que los datos ingresados no son correctos se muestra el campo que presenta el error
                if (!validarDocumento(documento)) {
                    mostrarCamposErroneos("Documento");
                } else if (!validarNombre(nombres)) {
                    mostrarCamposErroneos("Nombre");
                } else if (!validarContra(contra)) {
                    mostrarCamposErroneos("Contraseña");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Agrega un registro (log) para identificar el error específico
        }
    }

    // Metodo para buscar al usuario en la base por el numero de documento
    public void accionBuscar(View v) {
        String documentoBuscado = et_buscar_documento.getText().toString(); // obtener el valor ingresado en el campo buscar documento

        if (validarNumero(documentoBuscado)) { // Se valida el valor que ingreso el usuario sea un valor valido
            int numeroDocumento = Integer.parseInt(documentoBuscado); // Transformar el valor a un entero
            UsuarioDao usuarioDao = new UsuarioDao(this, v); // Se crea la instancia de la clase usuarioDao lo que permite interactuar con la base de datos
            Usuario usuarioEncontrado = usuarioDao.buscarUsuarioPorDocumento(numeroDocumento);

            if (usuarioEncontrado != null) {
                mostrarUsuario(usuarioEncontrado); // llamar el metodo que muestra los datos del usuario
            } else {
                mostrarUsuarioNoEncontrado(); // Si no se encuentra se informa al usuario
            }
        } else {
            mostrarCamposErroneos("El documento debe ser un número válido.");
        }
    }

    // Metodo para mostrar datos del usuario
    private void mostrarUsuario(Usuario usuario) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Detalles del Usuario");
        builder.setMessage("Documento: " + usuario.getDocumneto() + "\n" +
                "Nombre: " + usuario.getNombres() + " " + usuario.getApellidos() + "\n" +
                "Usuario: " + usuario.getUsuario());
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Metodo oara mostrar cuando no se ubica el usuario en la base
    private void mostrarUsuarioNoEncontrado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Usuario no encontrado");
        builder.setMessage("No se encontró un usuario con el número de documento ingresado.");
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Metodo para validar el campo documento que los datos sean correctos
    private boolean validarDocumento(int documento) {
        String documentoStr = String.valueOf(documento);
        String regex = "^[1-9]\\d{7}$"; // validar que el numero sea de 8 dígitos positivo
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(documentoStr);
        return matcher.matches();
    }

    // Metodo para validar que el nombre ingresado sea de letras y no numeros
    private boolean validarNombre(String nombre) {
        String regex = "^[A-Za-zÁáÉéÍíÓóÚúüÜñÑ\\s]+$"; // Validar que el nombre este compuesto de letras y no de numeros
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        return matcher.matches();
    }

    private boolean validarContra(String contra) {
        String regex = "^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,}$";// Validar que la contraseña tengo almenos 8 caracteres, un número y un carácter especial
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contra);
        return matcher.matches();
    }

    //Metodo para mostrar kis campos que el usuario introdujo mal los datos
    private void mostrarCamposErroneos(String campoInvalido) {
        String mensajeError = "";

        switch (campoInvalido) {
            case "Documento":
                mensajeError = "El documento debe ser un número válido y mínimo de 8 dígitos.";
                break;
            case "Nombre":
                mensajeError = "El nombre solo debe contener letras.";
                break;
            case "Contraseña":
                mensajeError = "La contraseña debe tener al menos 8 caracteres, un número y un carácter especial.";
                break;
            // Puedes agregar más casos para otros campos si es necesario
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Campo Inválido")
                .setMessage(mensajeError)
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
