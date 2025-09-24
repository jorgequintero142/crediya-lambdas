package co.com.crediya.endeudamiento;

public enum DesicionEnum {
    APROBADA("Aprobada",2),
    REVISION("Revision Manual",3),
    RECHAZADA("Rechazada",4);

    private final String estado;
    private final int codigo;
    DesicionEnum(String estado , int codigo) {
        this.estado = estado;
        this.codigo = codigo;
    }
    public String getEstado() {
        return estado;
    }

    public int getCodigo() {
        return codigo;
    }
}
