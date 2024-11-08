package vetcalc.calculadoravetor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import vetcalc.calculadoravetor.model.calculo.Numeros;

/**
 * @author Murilo Nunes, Davy Lopes Oliveira, Hartur Sales, Pedro Henrique, Bruno Martins
 * @date 31/10/2024
 * @brief Class Main
 */

public class CalculatorController {
    private Numeros numeros = new Numeros();
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
    }

    private void definirVisibilidadeInicial() {
        //torna os campos específicos de algumas operaçoes invisiveis
        setResultadosVisiveis(false, anguloResultado, escalarResultado, moduloResultado, valorX_Resultado, valorY_Resultado);
        alterarVisibilidadeZ(false);

        //a caixa de selecionar as operaçoes fica indisponivel enquanto um dos radiobuttons nao for selecionado
        desabilitarCampos(true);
    }

    private void configurarChoiceBox() {
        //adiciona as operações à caixa de seleção
        operacoesBox.getItems().add(Operacoes.ANGULO.nome);
        operacoesBox.getItems().add(Operacoes.PRODUTO_ESCALAR.nome);
        //listener para atualizar a descrição da operação selecionada
        operacoesBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            alterarResumoOperacoes(newValue);
        });
    }

    private void radioClicado(boolean isTresD) {
        operacoesBox.setValue(null);
        resultadoDescricaoLabel.setText("");
        operacaoDescricaoLabel.setText("");
        // Ajusta a visibilidade dos componentes com base na dimensão selecionada
        alterarVisibilidadeZ(isTresD);

        desabilitarCampos(false);

        if (isTresD) {
            operacoesBox.getItems().remove(Operacoes.MODULO.nome);
            if (!operacoesBox.getItems().contains(Operacoes.PRODUTO_VETORIAL.nome)) {
                operacoesBox.getItems().add(Operacoes.PRODUTO_VETORIAL.nome);
            }
        } else {
            operacoesBox.getItems().remove(Operacoes.PRODUTO_VETORIAL.nome);
            if (!operacoesBox.getItems().contains(Operacoes.MODULO.nome)) {
                operacoesBox.getItems().add(Operacoes.MODULO.nome);
            }
        }

        numeros.setDimensao(isTresD ? "3d" : "2d");

        //verifica se a operação selecionada possui o Z e atualiza a visibilidade
        verificarVisibilidadeZ();
    }

    private void desabilitarCampos(boolean desativado) {
        TextField[] campos = {valorX_A, valorY_A, valorX_B, valorY_B};
        for (TextField campo : campos) {
            campo.setDisable(desativado);
        }
        operacoesBox.setDisable(desativado);
    }

    private void alterarResumoOperacoes(String newValue) {
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
                    break;
                case "Produto Vetorial":
                    configurarOperacao(Operacoes.PRODUTO_VETORIAL);
                    mostrarResultados();
                    break;
            }
        }

        verificarVisibilidadeZ();
    }

    private void configurarOperacao(Operacoes operacao, TextField... camposVisiveis) {
        operacaoDescricaoLabel.setText(operacao.descricao);
        resultadoDescricaoLabel.setText(operacao.resultado);
        setResultadosVisiveis(true, camposVisiveis);
    }

    private void mostrarResultados() {
        setResultadosVisiveis(true, valorX_Resultado, valorY_Resultado, valorZ_Resultado);
    }

    private void verificarVisibilidadeZ() {
        alterarVisibilidadeZ(tresButton.isSelected() && "Produto Vetorial".equals(operacoesBox.getValue()));
    }

    private void alterarVisibilidadeZ(boolean visivel) {
        // Altera a visibilidade dos campos Z
        setResultadosVisiveis(visivel, valorZ_A, valorZ_B, valorZ_Resultado);
    }

    private void setResultadosVisiveis(boolean visible, TextField... fields) {
        // Método auxiliar para definir a visibilidade de múltiplos campos de texto
        for (TextField field : fields) {
            field.setVisible(visible);
            field.setManaged(visible);
        }
    }
}