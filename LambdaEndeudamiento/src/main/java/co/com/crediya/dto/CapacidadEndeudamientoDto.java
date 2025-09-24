package co.com.crediya.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CapacidadEndeudamientoDto {

    private BigDecimal salario;
    private BigDecimal sumatoriaDeudaMensual;
    private BigDecimal monto;
    private int plazo;
    private BigDecimal tasaInteres;
    private String email;
    private String nombre;
    private int idSolicitud;

}
