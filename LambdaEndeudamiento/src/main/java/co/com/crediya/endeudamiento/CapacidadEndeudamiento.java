package co.com.crediya.endeudamiento;

import co.com.crediya.dto.CapacidadEndeudamientoDto;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class CapacidadEndeudamiento {
    private final String strEndeudamiento;

    public CapacidadEndeudamiento(String strEndeudamiento) {
        this.strEndeudamiento = strEndeudamiento;
    }

    CapacidadEndeudamientoDto generarCapacidadEndeudamiento() throws Exception {
        log.info("Leyendo los parametros para crear endeudamiento ", this.strEndeudamiento);
        String[] datosEndeudamiento = this.strEndeudamiento.split(",");
        try {
            return CapacidadEndeudamientoDto
                    .builder()
                    .nombre(datosEndeudamiento[0])
                    .monto(new BigDecimal(datosEndeudamiento[1]))
                    .salario(new BigDecimal(datosEndeudamiento[2]))
                    .plazo(Integer.parseInt(datosEndeudamiento[3]))
                    .tasaInteres(new BigDecimal(datosEndeudamiento[4]))
                    .sumatoriaDeudaMensual(new BigDecimal(datosEndeudamiento[5]))
                    .email(datosEndeudamiento[6])
                    .idSolicitud(Integer.parseInt(datosEndeudamiento[7]))
                    .build();
        } catch (Exception e) {
            log.error("Error consultando los parametros del endeudamiento", e);
            return null;
        }

    }


}
