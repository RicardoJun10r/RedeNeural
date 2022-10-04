import java.io.IOException;

import BackPropagation.FuncaoAtivacao;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/*
    Implemente uma rede neural artificial do tipo Multi-Layer Perceptron (MLP) que
    utiliza o Backpropagation como algoritmo de aprendizado.

    Regras:
        A rede neural deverá ser capaz de reconhecer dois números, entre 0 e 9.
        Sendo que:

            O primeiro número deve ser a soma da quantidade de letras do primeiro
            nome da primeira pessoa da dupla, no qual as vogais valem 1 e as
            consoantes valem 2.
                Caso o valor seja maior que 9, divida por 3.

            O segundo número deve ser a soma da quantidade de letras do
            primeiro nome da segunda pessoa da dupla, no qual as vogais valem 1
            e as consoantes valem 2.
                Caso o valor seja maior que 9, divida por 3.
            Caso alguma divisão resulte em um número fracionado, o número a ser 
            utilizado deve ser o inteiro imediatamente superior.

        Por exemplo, o número para ser reconhecido em Danniel é: 4.
        Formato da entrada:

        [ ][X][ ][ ] [X][X][X][ ] [X][X][X][ ] [X][ ][X][ ] [X][X][X][X]
        [X][X][ ][ ] [ ][ ][ ][X] [ ][ ][ ][X] [X][ ][X][ ] [X][ ][ ][ ]
        [ ][X][ ][ ] [ ][ ][X][ ] [ ][ ][X][ ] [X][X][X][X] [X][X][X][ ]
        [ ][X][ ][ ] [ ][X][ ][ ] [ ][ ][ ][X] [ ][ ][X][ ] [ ][ ][ ][X]
        [X][X][X][ ] [X][X][X][X] [X][X][X][ ] [ ][ ][X][ ] [X][X][X][ ]

        [ ][X][X][X] [X][X][X][X] [ ][X][X][ ] [ ][X][X][ ] [ ][X][X][ ]
        [X][ ][ ][ ] [ ][ ][ ][X] [X][ ][ ][X] [X][ ][ ][X] [X][ ][ ][X]
        [X][X][X][ ] [ ][ ][X][ ] [ ][X][X][ ] [ ][X][X][X] [X][ ][ ][X]
        [X][ ][ ][X] [ ][X][ ][ ] [X][ ][ ][X] [ ][ ][ ][X] [X][ ][ ][X]
        [ ][X][X][ ] [ ][X][ ][ ] [ ][X][X][ ] [X][X][X][X] [ ][X][X][ ]


        Na saída a rede neural deve apresentar uma das 3 respostas abaixo:
            °Primeiro número;
            °Segundo número;
            °Número não reconhecido;
        Comece utilizando uma taxa de aprendizado de 0,4 e uma tolerância de 10%.
        
        A rede consegue aprender com 100 épocas? E 1000? E 2000?
        
        Varie a quantidade de neurônios na camada oculta e as funções de ativações, teste pelo
        menos 4 configurações diferentes. Qual obteve o melhor resultado?

        Exemplo usando "Danniel"
        --> 3 vogais = 3
        --> 4 consoantes = 8
        resultado = 3 + 8 = 11
        resultado maior que 9
        resultado / 3
        resultado mais ou menos igual a 4
 */

public class Main extends Application{

    private static Stage pS;

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getpS() {
        return pS;
    }

    public static void setpS(Stage pS) {
        Main.pS = pS;
    }

    @Override
    public void start(Stage pS) throws Exception {
        setpS(pS);
        pS.show();
        home();
    }

    public static void home() throws IOException {

        Parent root = FXMLLoader.load(Main.class.getResource("/assets/RNA.fxml"));
        Scene cena = new Scene(root);
        pS.setTitle("RNA");
        pS.setScene(cena);
    }

    public static void close(){
        pS.close();
    } 
}