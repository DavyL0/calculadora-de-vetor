package vetcalc.calculadoravetor.model.calculo;

import vetcalc.calculadoravetor.controller.CalculatorController;

/**
 * @author Murilo Nunes, Davy Lopes, Hartur Sales, Pedro Henrique, Bruno Martins
 * @date 31/10/2024
 * @brief Class Main
 */

public class Operation {
    private CalculatorController.Operacoes operacoes;
    private Numeros numeros = new Numeros();

    private double a = numeros.getNumXA() * numeros.getNumXB();
    double b = (numeros.getNumYA() * numeros.getNumYB());

    // Mtd para calcular o módulo de um vetor
    public double calcularModulo(Numeros vetor) {
        //System.out.println("Modulo foi chamado");
        return Math.sqrt(Math.pow(vetor.getNumXA(), 2) + Math.pow(vetor.getNumYA(), 2) + (vetor.getNumZA() != 0 ? Math.pow(vetor.getNumZA(), 2) : 0));
    }

    // Mtd para calcular o produto escalar entre dois vetores
    public double produtoEscalar(Numeros vetor1, Numeros vetor2) {
        //System.out.println("Produto escalar entre 2 vetores foi chamado");
        return vetor1.getNumXA() * vetor2.getNumXB() + vetor1.getNumYA() * vetor2.getNumYB() + vetor1.getNumZA() * vetor2.getNumZB();
    }

    // Mtd para calcular o ângulo entre dois vetores em graus
    public double calcularAnguloEntreVetores(Numeros vetor1, Numeros vetor2) {
        //System.out.println("Calculando o ângulo entre dois vetores");

        double escalar = produtoEscalar(vetor1, vetor2);
        double moduloA = calcularModulo(vetor1);
        double moduloB = calcularModulo(vetor2);

        //só pra descobrir o problema
        if (moduloA == 0) {
            System.out.println("Modulo A = 0, ângulo indefinido.");
            return Double.NaN;
        }
        if (moduloB == 0) {
            System.out.println("Modulo B = 0, ângulo indefinido.");
            return Double.NaN;
        }

        //deixei mais parcelado e garanti o arredondamento
        double cosTheta = escalar / (moduloA * moduloB);
        cosTheta = Math.max(-1.0, Math.min(1.0, cosTheta));

        return Math.toDegrees(Math.acos(cosTheta));
    }


    // Mtd para calcular o produto vetorial entre dois vetores 3D
    public Numeros produtoVetorial(Numeros vetor1, Numeros vetor2) {
        //System.out.println("Produto vetorial pra 2 3d foi chamado");
        double x = vetor1.getNumYA() * vetor2.getNumZB() - vetor1.getNumZA() * vetor2.getNumYB();
        double y = vetor1.getNumZA() * vetor2.getNumXB() - vetor1.getNumXA() * vetor2.getNumZB();
        double z = vetor1.getNumXA() * vetor2.getNumYB() - vetor1.getNumYA() * vetor2.getNumXB();

        Numeros resultado = new Numeros();
        resultado.setNumXA(x);
        resultado.setNumYA(y);
        resultado.setNumZA(z);
        return resultado;
    }
}
