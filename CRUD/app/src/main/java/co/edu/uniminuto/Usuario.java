package co.edu.uniminuto;

public class Usuario {

    private int documneto;
    private String usuario;
    private String nombres;
    private String apellidos;
    private String contra;

    public int getDocumneto() {
        return documneto;
    }

    public void setDocumneto(int documneto) {
        this.documneto = documneto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "documneto=" + documneto +
                ", usuario='" + usuario + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", contra='" + contra + '\'' +
                '}';
    }
}


