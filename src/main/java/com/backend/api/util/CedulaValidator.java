package com.backend.api.util;

public class CedulaValidator {

    public static boolean isValidarCedula(String cedula) {
        // 1. Validar longitud
        if (cedula == null || cedula.length() != 10) {
            return false;
        }

        try {
            // 2. Obtener provincia (primeros dos dígitos)
            int provincia = Integer.parseInt(cedula.substring(0, 2));
            if (provincia < 1 || provincia > 24) {
                return false; // Provincia no válida
            }

            // 3. Obtener el dígito verificador a verificar
            int digitoVerificadorCedula = Integer.parseInt(cedula.substring(9, 10));

            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            int suma = 0;
            int valor;

            // 4. Aplicar el algoritmo (multiplicación y suma)
            for (int i = 0; i < 9; i++) {
                valor = Integer.parseInt(cedula.substring(i, i + 1)) * coeficientes[i];
                // Si el valor es mayor a 9, se le resta 9
                suma += (valor >= 10) ? valor - 9 : valor;
            }

            // 5. Calcular el dígito verificador teórico
            int residuo = suma % 10;
            int digitoVerificadorCalculado = (residuo == 0) ? 0 : 10 - residuo;

            // 6. Comparar
            return digitoVerificadorCalculado == digitoVerificadorCedula;

        } catch (NumberFormatException e) {
            return false; // No es un número
        }
    }
}