package vetcalc.calculadoravetor.controller;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import vetcalc.calculadoravetor.model.calculo.Numeros;
import vetcalc.calculadoravetor.model.calculo.Operation;

import java.text.DecimalFormat;

/**
 * @author Murilo Nunes, Davy Lopes, Hartur Sales, Pedro Henrique, Bruno Martins
 * @date 31/10/2024
 * @brief Class CalculatorController
 */

public class CalculatorController {
    @FXML
    private TextField valorX_A, valorY_A, valorZ_A, valorX_B, valorY_B, valorZ_B, valorX_Resultado,
            valorY_Resultado, valorZ_Resultado, anguloResultado, escalarResultado, moduloResultado;
    @FXML
    private Label operacaoDescricaoLabel, resultadoLabel, resultadoDescricaoLabel;
    @FXML
    private ChoiceBox<String> operacoesBox;
    @FXML
    private VBox resultadoBox, vetorBBox, vetorABox;
    @FXML
    private RadioButton doisButton, tresButton;
    @FXML
    private ToggleGroup dimensaoGroup, representacaoGroup;
    private boolean isTresD;
    private static final DecimalFormat FORMATO_DECIMAL = new DecimalFormat("#,##0.###");
    private Numeros numeros = new Numeros();

    public enum Operacoes {
        MODULO("Módulo", "Calcula o módulo de um vetor", "Módulo do vetor"),
        PRODUTO_ESCALAR("Produto Escalar", "Realiza o produto escalar entre o vetor a e b", "a . b"),
        ANGULO("Ângulo entre os vetores", "Calcula o ângulo entre o vetor a e b", "Ângulo entre a e b"),
        PRODUTO_VETORIAL("Produto Vetorial", "Realiza o produto vetorial entre o vetor a e b", "a x b");

        final String nome, descricao, resultado;

        Operacoes(String nome, String descricao, String resultado) {
            this.nome = nome;
            this.descricao = descricao;
            this.resultado = resultado;
        }
    }

    @FXML
    public void initialize() {
        definirVisibilidadeInicial(); //configura a visibilidade inicial dos componentes
        configurarChoiceBox(); //preenche a caixa de seleção de operações
        doisButton.setOnAction(e -> radioClicado(false));
        tresButton.setOnAction(e -> radioClicado(true));
        addListeners();
    }

    private void addListeners() {
        //para fiscalizar a mudança nos textfields
        valorX_A.textProperty().addListener((observable, oldValue, newValue) -> calcularResultado());
        valorY_A.textProperty().addListener((observable, oldValue, newValue) -> calcularResultado());
        valorZ_A.textProperty().addListener((observable, oldValue, newValue) -> calcularResultado());
        valorX_B.textProperty().addListener((observable, oldValue, newValue) -> calcularResultado());
        valorY_B.textProperty().addListener((observable, oldValue, newValue) -> calcularResultado());
        valorZ_B.textProperty().addListener((observable, oldValue, newValue) -> calcularResultado());

        operacoesBox.valueProperty().addListener((observable, oldValue, newValue) -> calcularResultado());
    }


    private void definirVisibilidadeInicial() {
        //torna os campos específicos de algumas operaçoes invisiveis
        setResultadosVisiveis(false, anguloResultado, escalarResultado, moduloResultado, valorX_Resultado, valorY_Resultado);
        alterarVisibilidadeZ(false, false);

        //a caixa de selecionar as operaçoes fica indisponivel enquanto um dos radiobuttons nao for selecionado
        desabilitarCampos(true);
    }

    private void configurarChoiceBox() {
        //adiciona as operações à caixa de seleção
        operacoesBox.getItems().add(Operacoes.ANGULO.nome);
        operacoesBox.getItems().add(Operacoes.PRODUTO_ESCALAR.nome);
        operacoesBox.getItems().add(Operacoes.MODULO.nome);
        //listener para atualizar a descrição da operação selecionada
        operacoesBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            alterarResumoOperacoes(newValue);
        });
    }

    private void radioClicado(boolean isTresD) {
        TextField[] textFields = {valorX_A, valorY_A, valorZ_A, valorX_B, valorY_B, valorZ_B, valorX_Resultado,
                valorY_Resultado, valorZ_Resultado, anguloResultado, escalarResultado, moduloResultado};
        for (TextField textField : textFields) {
            textField.clear();
        }
        this.isTresD = isTresD;
        operacoesBox.getSelectionModel().clearSelection();
        resultadoDescricaoLabel.setText("");
        operacaoDescricaoLabel.setText("");
        // Ajusta a visibilidade dos componentes com base na dimensão selecionada
        alterarVisibilidadeZ(isTresD, false);

        desabilitarCampos(false);

        if (isTresD) {
            if (!operacoesBox.getItems().contains(Operacoes.PRODUTO_VETORIAL.nome)) {
                operacoesBox.getItems().add(Operacoes.PRODUTO_VETORIAL.nome);
            }
        } else {
            operacoesBox.getItems().remove(Operacoes.PRODUTO_VETORIAL.nome);
        }

        numeros.setDimensao(isTresD ? "3d" : "2d");

    }

    private void desabilitarCampos(boolean desativado) {
        TextField[] campos = {valorX_A, valorY_A, valorX_B, valorY_B};
        for (TextField campo : campos) {
            campo.setDisable(desativado);
        }
        operacoesBox.setDisable(desativado);
    }

    private void alterarResumoOperacoes(String newValue) {
        desativarVetorB(true);
        setResultadosVisiveis(false, moduloResultado, anguloResultado, escalarResultado, valorX_Resultado, valorY_Resultado, valorZ_Resultado);

        if (newValue != null) {
            switch (newValue) {
                case "Produto Escalar":
                    configurarOperacao(Operacoes.PRODUTO_ESCALAR, escalarResultado);
                    break;
                case "Ângulo entre os vetores":
                    configurarOperacao(Operacoes.ANGULO, anguloResultado);
                    break;
                case "Módulo":
                    configurarOperacao(Operacoes.MODULO, moduloResultado);
                    desativarVetorB(false);
                    break;
                case "Produto Vetorial":
                    configurarOperacao(Operacoes.PRODUTO_VETORIAL, valorX_Resultado, valorY_Resultado, valorZ_Resultado);
                    break;
            }
        }

        alterarVisibilidadeZ(isTresD, "Produto Vetorial".equals(operacoesBox.getValue()));
    }

    private void desativarVetorB(boolean visible) {
        //serve só pra quando a operação for de modulo, pq nao da pra calcular o módulo de dois vetores
        vetorBBox.setManaged(visible);
        vetorBBox.setVisible(visible);
        valorX_B.setManaged(visible);
        valorX_B.setVisible(visible);
        valorY_B.setManaged(visible);
        valorY_B.setVisible(visible);
    }

    private void configurarOperacao(Operacoes operacao, TextField... camposVisiveis) {
        operacaoDescricaoLabel.setText(operacao.descricao);
        resultadoDescricaoLabel.setText(operacao.resultado);
        setResultadosVisiveis(true, camposVisiveis);
    }

    private void alterarVisibilidadeZ(boolean visibilidadeCamposZ, boolean visibilidadeResultadoZ) {
        valorZ_A.setVisible(visibilidadeCamposZ);
        valorZ_A.setManaged(visibilidadeCamposZ);
        valorZ_B.setVisible(visibilidadeCamposZ);
        valorZ_B.setManaged(visibilidadeCamposZ);

        valorZ_Resultado.setVisible(visibilidadeResultadoZ);
        valorZ_Resultado.setManaged(visibilidadeResultadoZ);
    }

    private void setResultadosVisiveis(boolean visible, TextField... fields) {
        // Mtd auxiliar para definir a visibilidade de múltiplos campos de texto
        for (TextField field : fields) {
            field.setVisible(visible);
            field.setManaged(visible);
        }
    }

    public void calcularResultado() {
        resultadoLabel.setStyle("");
        try {
            if (operacoesBox.getValue() == null) {
                resultadoLabel.setText("Selecione uma operação");
                return;
            }

            if (operacoesBox.getValue().equals("Módulo")) {
                if (valorX_A.getText().isEmpty() || valorY_A.getText().isEmpty() || (isTresD && valorZ_A.getText().isEmpty())) {
                    resultadoLabel.setText("Preencha todos os campos");
                    return;
                }
            } else {
                if (valorX_A.getText().isEmpty() || valorY_A.getText().isEmpty() || valorX_B.getText().isEmpty() || valorY_B.getText().isEmpty() ||
                        (isTresD && (valorZ_A.getText().isEmpty() || valorZ_B.getText().isEmpty()))) {
                    resultadoLabel.setText("Preencha todos os campos");
                    return;
                }
            }

            double x1 = Double.parseDouble(valorX_A.getText());
            double y1 = Double.parseDouble(valorY_A.getText());
            double z1 = isTresD ? Double.parseDouble(valorZ_A.getText()) : 0;

            double x2 = 0, y2 = 0, z2 = 0;
            if (!operacoesBox.getValue().equals("Módulo")) {
                x2 = Double.parseDouble(valorX_B.getText());
                y2 = Double.parseDouble(valorY_B.getText());
                z2 = isTresD ? Double.parseDouble(valorZ_B.getText()) : 0;
            }

            Numeros vetor1 = new Numeros();
            vetor1.setNumXA(x1);
            vetor1.setNumYA(y1);
            vetor1.setNumZA(isTresD ? z1 : 0);

            Numeros vetor2 = new Numeros();
            vetor2.setNumXB(x2);
            vetor2.setNumYB(y2);
            vetor2.setNumZB(isTresD ? z2 : 0);
            vetor2.setNumXA(x2);
            vetor2.setNumYA(y2);
            vetor2.setNumZA(isTresD ? z2 : 0);

            Operation operation = new Operation();
            String operacao = operacoesBox.getValue();

            switch (operacao) {
                case "Produto Escalar":
                    double produtoEscalar = operation.produtoEscalar(vetor1, vetor2);
                    escalarResultado.setText(String.valueOf(FORMATO_DECIMAL.format(produtoEscalar)));
                    break;
                case "Ângulo entre os vetores":
                    double angulo = operation.calcularAnguloEntreVetores(vetor1, vetor2);
                    anguloResultado.setText(String.valueOf(FORMATO_DECIMAL.format(angulo)) + "°");
                    break;
                case "Módulo":
                    double modulo = operation.calcularModulo(vetor1);
                    moduloResultado.setText(String.valueOf(FORMATO_DECIMAL.format(modulo)));
                    break;
                case "Produto Vetorial":
                    Numeros produtoVetorial = operation.produtoVetorial(vetor1, vetor2);
                    valorX_Resultado.setText(String.valueOf(FORMATO_DECIMAL.format(produtoVetorial.getNumXA())));
                    valorY_Resultado.setText(String.valueOf(FORMATO_DECIMAL.format(produtoVetorial.getNumYA())));
                    valorZ_Resultado.setText(String.valueOf(FORMATO_DECIMAL.format(produtoVetorial.getNumZA())));
                    break;
                default:
                    resultadoLabel.setText("Operação inválida");
                    break;
            }

        } catch (NumberFormatException e) {
            resultadoLabel.setStyle("-fx-text-fill: red");
            resultadoLabel.setText("Insira números válidos");
        }

    }

}