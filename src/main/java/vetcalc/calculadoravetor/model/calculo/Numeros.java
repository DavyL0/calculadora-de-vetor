package vetcalc.calculadoravetor.model.calculo;

/**
 * @author Murilo Nunes, Davy Lopes Oliveira, Hartur Sales, Pedro Henrique, Bruno Martins
 * @date 31/10/2024
 * @brief Class Main
 */

public class Numeros {
    private double numXA;
    private double numXB;
    private double numYA;
    private double numYB;
    private double numZA;
    private double numZB;
    private char operation;
    private double result;
    private String dimensao;

    public Numeros() {
    }

    public Numeros(double numXA, double numXB, double numYA, double numYB, double numZA, double numZB, char operation, double result, String dimensao) {
        this.numXA = numXA;
        this.numXB = numXB;
        this.numYA = numYA;
        this.numYB = numYB;
        this.numZA = numZA;
        this.numZB = numZB;
        this.operation = operation;
        this.result = result;
        this.dimensao = dimensao;
    }

    public double getNumXA() {
        return numXA;
    }

    public void setNumXA(double numXA) {
        this.numXA = numXA;
    }

    public double getNumXB() {
        return numXB;
    }

    public void setNumXB(double numXB) {
        this.numXB = numXB;
    }

    public double getNumYA() {
        return numYA;
    }

    public void setNumYA(double numYA) {
        this.numYA = numYA;
    }

    public double getNumYB() {
        return numYB;
    }

    public void setNumYB(double numYB) {
        this.numYB = numYB;
    }

    public double getNumZA() {
        return numZA;
    }

    public void setNumZA(double numZA) {
        this.numZA = numZA;
    }

    public double getNumZB() {
        return numZB;
    }

    public void setNumZB(double numZB) {
        this.numZB = numZB;
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getDimensao() {
        return dimensao;
    }

    public void setDimensao(String dimensao) {
        this.dimensao = dimensao;
    }

}
