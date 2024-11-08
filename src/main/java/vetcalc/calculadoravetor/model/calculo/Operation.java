package vetcalc.calculadoravetor.model.calculo;

import vetcalc.calculadoravetor.controller.CalculatorController;

/**
 * @author Murilo Nunes, Davy Lopes Oliveira, Hartur Sales, Pedro Henrique, Bruno Martins
 * @date 31/10/2024
 * @brief Class Main
 */

public class Operation {
    private CalculatorController.Operacoes operacoes;
    private Numeros numeros = new Numeros();

    private double a = numeros.getNumXA() * numeros.getNumXB();
    double b = (numeros.getNumYA() * numeros.getNumYB());


    public double modVet() {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    // Método para calcular o produto escalar entre dois vetores
    public double dotProduct() {
        double xA = numeros.getNumXA();
        double yA = numeros.getNumYA();
        double xB = numeros.getNumXB();
        double yB = numeros.getNumYB();

        return (xA * xB) + (yA * yB);
    }

    // Método para calcular o ângulo entre dois vetores (em graus)
    public double angleBetween() {
        double dotProd = dotProduct();
        double magnitudeA = Math.sqrt(Math.pow(numeros.getNumXA(), 2) + Math.pow(numeros.getNumYA(), 2));
        double magnitudeB = Math.sqrt(Math.pow(numeros.getNumXB(), 2) + Math.pow(numeros.getNumYB(), 2));

        double cosTheta = dotProd / (magnitudeA * magnitudeB);
        return Math.toDegrees(Math.acos(cosTheta));
    }

    // Método para calcular o produto vetorial entre dois vetores em 3D (resultando em um novo vetor 3D)
    public Numeros crossProduct() {
        double xA = numeros.getNumXA();
        double yA = numeros.getNumYA();
        Double zA = numeros.getNumZA(); // Suporta null para vetores 2D
        double xB = numeros.getNumXB();
        double yB = numeros.getNumYB();
        Double zB = numeros.getNumZB(); // Suporta null para vetores 2D

        if (zA == null || zB == null) {
            throw new IllegalArgumentException("Cross product is only defined for 3D vectors.");
        }

        // Calcula o produto vetorial
        double x = yA * zB - zA * yB;
        double y = zA * xB - xA * zB;
        double z = xA * yB - yA * xB;

        // Cria um novo objeto Numeros para armazenar o vetor resultante
        Numeros result = new Numeros();
        result.setNumXA(x);
        result.setNumYA(y);
        result.setNumZA(z);

        return result;
    }

}
