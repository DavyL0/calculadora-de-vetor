package vetcalc.calculadoravetor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vetcalc.calculadoravetor.model.calculo.Numeros;

public class CalculatorController {
    private Numeros numeros = new Numeros();
    @FXML
    private TextField valorX_A, valorY_A, valorZ_A, valorX_B, valorY_B, valorZ_B, valorX_Resultado,
            valorY_Resultado, valorZ_Resultado, anguloResultado, escalarResultado;
    @FXML
    private Label operacaoDescricaoLabel, resultadoLabel, resultadoDescricaoLabel;
    @FXML
    private ChoiceBox<String> operacoesBox;
    @FXML
    private HBox representacaoBox;
    @FXML
    private VBox resultadoBox, vetorBBox, vetorABox;
    @FXML
    private RadioButton doisButton, tresButton;
    @FXML
    private ToggleGroup dimensaoGroup, representacaoGroup;

    public enum Operacoes {
        MODULO("Módulo", "Calcula o módulo de um vetor", "Módulo do vetor"),
        ADICAO("Adição", "Realiza adição entre o vetor a e b", "a + b"),
        SUBTRACAO("Subtração", "Realiza subtração entre o vetor a e b", "a - b"),
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
        doisButton.setOnAction(e -> setDimensao(false));
        tresButton.setOnAction(e -> setDimensao(true));
    }

    private void definirVisibilidadeInicial() {
        //torna os campos específicos de algumas operaçoes invisiveis
        setResultadosVisiveis(false, anguloResultado, escalarResultado);
        alterarVisibilidadeZ(false);

        //a caixa de selecionar as operaçoes fica indisponivel enquanto um dos radiobuttons nao for selecionado
        desabilitarCampos(true);
    }

    private void configurarChoiceBox() {
        //adiciona as operações à caixa de seleção
        for (Operacoes operacao : Operacoes.values()) {
            operacoesBox.getItems().add(operacao.nome);
        }
        //listener para atualizar a descrição da operação selecionada
        operacoesBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            alterarResumoOperacoes(newValue);
        });
    }

    private void setDimensao(boolean isTresD) {
        // Ajusta a visibilidade dos componentes com base na dimensão selecionada
        alterarVisibilidadeZ(isTresD);

        desabilitarCampos(false);
        operacoesBox.getItems().remove(Operacoes.MODULO.nome);
        if (isTresD) {
            operacoesBox.getItems().remove(Operacoes.MODULO.nome);
        }
        if(!isTresD) {
            operacoesBox.getItems().add(Operacoes.MODULO.nome);
        }

        if (isTresD && !operacoesBox.getItems().contains(Operacoes.PRODUTO_VETORIAL.nome)) {
            operacoesBox.getItems().add(Operacoes.PRODUTO_VETORIAL.nome);
        } else {
            operacoesBox.getItems().remove(Operacoes.PRODUTO_VETORIAL.nome);
        }
        String dimensao;
        if (isTresD) {
            dimensao = "3d";
        } else {
            dimensao = "2d";
        }
        numeros.setDimensao(dimensao);

        //verifica se a operação selecionada possui o Z e atualiza a visibilidade
        verificarVisibilidadeZ();
    }

    private void desabilitarCampos(boolean desativado) {
        operacoesBox.setDisable(desativado);
        valorX_A.setDisable(desativado);
        valorY_A.setDisable(desativado);
        valorX_B.setDisable(desativado);
        valorY_B.setDisable(desativado);
    }

    private void alterarResumoOperacoes(String newValue) {
        //oculta todos os campos de resultado inicialmente
        setResultadosVisiveis(false, anguloResultado, escalarResultado, valorX_Resultado, valorY_Resultado, valorZ_Resultado);

        // Atualiza a descrição da operação e mostra os resultados apropriados
        for (Operacoes operacao : Operacoes.values()) {
            if (operacao.nome.equals(newValue)) {
                operacaoDescricaoLabel.setText(operacao.descricao);
                resultadoDescricaoLabel.setText(operacao.resultado);
                if (operacao == Operacoes.PRODUTO_ESCALAR) {
                    escalarResultado.setVisible(true);
                    escalarResultado.setManaged(true);
                } else if (operacao == Operacoes.ANGULO) {
                    anguloResultado.setVisible(true);
                    anguloResultado.setManaged(true);
                } else {
                    mostrarResultados();
                }
                break;
            }
        }

        verificarVisibilidadeZ();
    }

    private void mostrarResultados() {
        setResultadosVisiveis(true, valorX_Resultado, valorY_Resultado, valorZ_Resultado);
    }

    private void verificarVisibilidadeZ() {
        //verifica se a operação selecionada possui o Z e atualiza a visibilidade
        boolean is3D = tresButton.isSelected();
        String operacaoSelecionada = operacoesBox.getValue();
        boolean precisaCampoZ = false;
        if (operacaoSelecionada != null) {
            precisaCampoZ = is3D && (operacaoSelecionada.equals(Operacoes.ADICAO.nome) ||
                    operacaoSelecionada.equals(Operacoes.SUBTRACAO.nome) ||
                    operacaoSelecionada.equals(Operacoes.PRODUTO_VETORIAL.nome));
        }
        alterarVisibilidadeZ(precisaCampoZ);
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