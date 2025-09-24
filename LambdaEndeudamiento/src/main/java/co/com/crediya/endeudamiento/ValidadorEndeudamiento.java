package co.com.crediya.endeudamiento;

import co.com.crediya.dto.CapacidadEndeudamientoDto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ValidadorEndeudamiento {

    private final static BigDecimal PORCENTAJE_SALARIO = BigDecimal.valueOf(0.35);
    private final static BigDecimal PORCENTAJE_INTERES = BigDecimal.valueOf(12);
    private final static BigDecimal MULTIPLICA_SALARIO_VALIDAR = BigDecimal.valueOf(5);
    private final static int ESCALA = 5;

    public CapacidadEndeudamientoDto obtenerCapacidadEndeudamiento(String strCapacidadEndeudamiento) {
        CapacidadEndeudamiento capacidadEndeudamiento = new CapacidadEndeudamiento(strCapacidadEndeudamiento);
        try {
            return capacidadEndeudamiento.generarCapacidadEndeudamiento();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public DesicionEnum validaCapacidadEndeudamiento(CapacidadEndeudamientoDto capacidadEndeudamiento) {
        BigDecimal capacidadEndeudamientoActual = obtenerCapacidadEndeudamientoActual(capacidadEndeudamiento);
        BigDecimal valorCuotaNueva = obtenerValorCuotaNueva(capacidadEndeudamiento);
        if (valorCuotaNueva.compareTo(capacidadEndeudamientoActual) > 0 ) {
            return DesicionEnum.RECHAZADA;
        }
        return validarRevisionManual(capacidadEndeudamiento) ? DesicionEnum.REVISION :
                DesicionEnum.APROBADA;
    }

    private boolean validarRevisionManual(CapacidadEndeudamientoDto capacidadEndeudamiento) {
        BigDecimal valorRevisar = capacidadEndeudamiento.getSalario().multiply(MULTIPLICA_SALARIO_VALIDAR);
        return capacidadEndeudamiento.getMonto().compareTo(valorRevisar) > 0;
    }

    private BigDecimal obtenerCapacidadEndeudamientoActual(CapacidadEndeudamientoDto capacidadEndeudamiento) {
        BigDecimal totalDeuda = capacidadEndeudamiento.getSumatoriaDeudaMensual();
        BigDecimal salario = capacidadEndeudamiento.getSalario();
        BigDecimal capacidadEndeudamientoMensual = salario.multiply(PORCENTAJE_SALARIO);
        return capacidadEndeudamientoMensual.subtract(totalDeuda);
    }

    private BigDecimal obtenerValorCuotaNueva(CapacidadEndeudamientoDto capacidadEndeudamientoDto) {

        BigDecimal tasaInteres = capacidadEndeudamientoDto.getTasaInteres().divide(PORCENTAJE_INTERES, ESCALA, RoundingMode.HALF_DOWN);
        BigDecimal monto = capacidadEndeudamientoDto.getMonto();
        int plazo = capacidadEndeudamientoDto.getPlazo();

        BigDecimal cuotaPrestamoNuevoA = monto.multiply(tasaInteres);
        BigDecimal interesA = BigDecimal.ONE.add(tasaInteres);
        BigDecimal cuotaPrestamoNuevoB = interesA.pow(plazo, MathContext.DECIMAL128);
        BigDecimal cuotaPrestamoNuevoC = cuotaPrestamoNuevoA.multiply(cuotaPrestamoNuevoB);

        BigDecimal interesD = BigDecimal.ONE.add(tasaInteres);
        BigDecimal cuotaPrestamoNuevoD = interesD.pow(plazo, MathContext.DECIMAL128);
        BigDecimal cuotaPrestamoNuevoE = cuotaPrestamoNuevoD.subtract(BigDecimal.ONE);
        return cuotaPrestamoNuevoC.divide(cuotaPrestamoNuevoE, ESCALA, RoundingMode.HALF_DOWN);
    }
}
