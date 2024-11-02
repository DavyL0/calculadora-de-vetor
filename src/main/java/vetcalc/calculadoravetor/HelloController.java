package vetcalc.calculadoravetor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HelloController {
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

    @FXML
    public void initialize() {
        //torna os campos específicos de algumas operaçoes invisiveis
        anguloResultado.setVisible(false);
        anguloResultado.setManaged(false);
        escalarResultado.setVisible(false);
        escalarResultado.setManaged(false);
        alterarVisibilidadeZ(false);

        //a caixa de selecionar as operaçoes fica indisponivel enquanto um dos radiobuttons nao for selecionado
        operacoesBox.setDisable(true);

        tresButton.setOnAction(e -> {
            tresButtonAction();
        });
        doisButton.setOnAction(e -> {
            doisButtonAction();
        });

        operacaoDescricaoLabel.setText(""); //fica abaixo da choicebox
        resultadoDescricaoLabel.setText(""); //label que descreve o resultado

        operacoesBox.getItems().addAll("Adição", "Subtração", "Produto Escalar", "Ângulo entre os vetores");
        operacoesBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            alterarResumoOperacoes(newValue);
        });

        //os campos do resultado não podem ser editados pelo usuario
        valorX_Resultado.setEditable(false);
        valorY_Resultado.setEditable(false);
        valorZ_Resultado.setEditable(false);
        anguloResultado.setEditable(false);
        escalarResultado.setEditable(false);
    }

    private void doisButtonAction() {
        operacaoDescricaoLabel.setText("");
        resultadoDescricaoLabel.setText("");
        representacaoBox.setVisible(true);
        representacaoBox.setManaged(true);
        alterarVisibilidadeZ(false);
        operacoesBox.setDisable(false);
        operacoesBox.getItems().removeIf(item -> item.equals("Produto Vetorial"));
    }

    private void tresButtonAction() {
        operacaoDescricaoLabel.setText("");
        resultadoDescricaoLabel.setText("");
        representacaoBox.setVisible(false);
        representacaoBox.setManaged(false);
        alterarVisibilidadeZ(true);
        verificarVisibilidadeZ();
        operacoesBox.setDisable(false);
        if (!operacoesBox.getItems().contains("Produto Vetorial")) {
            operacoesBox.getItems().add("Produto Vetorial");
        }
    }

    private void verificarVisibilidadeZ() {
        boolean is3D = tresButton.isSelected();
        String operacaoSelecionada = operacoesBox.getValue();

        boolean precisaCampoZ = false;
        if (operacaoSelecionada != null) {
            precisaCampoZ = is3D && (operacaoSelecionada.equals("Adição") ||
                    operacaoSelecionada.equals("Subtração") ||
                    operacaoSelecionada.equals("Produto Vetorial"));
        }

        alterarVisibilidadeZ(precisaCampoZ);
    }

    private void alterarVisibilidadeZ(boolean visivel) {
        valorZ_A.setVisible(visivel);
        valorZ_A.setManaged(visivel);
        valorZ_B.setVisible(visivel);
        valorZ_B.setManaged(visivel);
        valorZ_Resultado.setVisible(visivel);
        valorZ_Resultado.setManaged(visivel);
    }

    private void alterarResumoOperacoes(String newValue) {
        anguloResultado.setVisible(false);
        anguloResultado.setManaged(false);
        escalarResultado.setVisible(false);
        escalarResultado.setManaged(false);
        valorX_Resultado.setVisible(false);
        valorX_Resultado.setManaged(false);
        valorY_Resultado.setVisible(false);
        valorY_Resultado.setManaged(false);
        valorZ_Resultado.setVisible(false);
        valorZ_Resultado.setManaged(false);

        switch (newValue) {
            case "Adição":
                operacaoDescricaoLabel.setText("Realiza adição entre o vetor a e b");
                resultadoDescricaoLabel.setText("a + b");
                mostrarResultados();
                break;
            case "Subtração":
                operacaoDescricaoLabel.setText("Realiza subtração entre o vetor a e b");
                resultadoDescricaoLabel.setText("a - b");
                mostrarResultados();
                break;
            case "Produto Escalar":
                operacaoDescricaoLabel.setText("Realiza o produto escalar entre o vetor a e b");
                resultadoDescricaoLabel.setText("a . b");
                escalarResultado.setVisible(true);
                escalarResultado.setManaged(true);
                break;
            case "Ângulo entre os vetores":
                operacaoDescricaoLabel.setText("Calcula o ângulo entre o vetor a e b");
                resultadoDescricaoLabel.setText("Ângulo entre a e b");
                anguloResultado.setVisible(true);
                anguloResultado.setManaged(true);
                break;
            case "Produto Vetorial":
                operacaoDescricaoLabel.setText("Realiza o produto vetorial entre o vetor a e b");
                resultadoDescricaoLabel.setText("a x b");
                mostrarResultados();
                break;
        }
        verificarVisibilidadeZ();
    }

    private void mostrarResultados() {
        valorX_Resultado.setVisible(true);
        valorX_Resultado.setManaged(true);
        valorY_Resultado.setVisible(true);
        valorY_Resultado.setManaged(true);
        valorZ_Resultado.setVisible(tresButton.isSelected());
        valorZ_Resultado.setManaged(tresButton.isSelected());
    }
}