package co.com.crediya.aprobacion;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Amortizacion {
      private final BigDecimal DIVIDIR_INTERES = BigDecimal.valueOf(12);
    public String obtenerPlanPago(BigDecimal monto, BigDecimal tasaInteres, int plazo) {


        System.out.println("Calculando plan de pagos");
        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
        tasaInteres = tasaInteres.divide(DIVIDIR_INTERES,mc);
        BigDecimal unoMasI = BigDecimal.ONE.add(tasaInteres, mc);
        BigDecimal potenciaPositiva = unoMasI.pow(plazo, mc);
        BigDecimal potenciaNegativa = BigDecimal.ONE.divide(potenciaPositiva, mc);
        BigDecimal cuota = monto.multiply(tasaInteres, mc)
                .divide(BigDecimal.ONE.subtract(potenciaNegativa, mc), mc);
        cuota = cuota.setScale(2, RoundingMode.HALF_UP);

        BigDecimal saldo = monto;
        StringBuilder sb = new StringBuilder();
        sb.append("Plan de pagos:\n");
        for (int mes = 1; mes <= plazo; mes++) {
            BigDecimal interes = saldo.multiply(tasaInteres, mc).setScale(2, RoundingMode.HALF_UP);
            BigDecimal abonoCapital = cuota.subtract(interes).setScale(2, RoundingMode.HALF_UP);
            saldo = saldo.subtract(abonoCapital).setScale(2, RoundingMode.HALF_UP);


            sb.append("Mes ").append(mes)
                    .append(" | Cuota: ").append(cuota)
                    .append(" | Interés: ").append(interes)
                    .append(" | Capital: ").append(abonoCapital)
                    .append(" | Saldo: ").append(saldo)
                    .append("\n");
        }
        return sb.toString();
    }
}
