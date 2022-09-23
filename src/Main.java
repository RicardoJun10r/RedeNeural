
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

import BackPropagation.*;

public class Main{

    public static void main(String[] args) {
        
        // REDE NEURAL ( Teste )

        // double[][] dados_entrada2 = {
        //     new double[]{ 0, 0 },
        //     new double[]{ 0, 1 },
        //     new double[]{ 1, 0 },
        //     new double[]{ 1, 1 }
        // };

        // double[] dados_desejados2 = new double[] { 0, 1, 1, 1 };

        // Teste teste = new Teste(
        //     dados_entrada2,
        //     100
        // );

        // teste.treinar(dados_desejados2);

        //       input 2
        //     {1},{1},{1},{0},
        //     {0},{0},{0},{1},
        //     {0},{0},{1},{0},
        //     {0},{1},{0},{0},
        //     {1},{1},{1},{1},
         
        // REDE NEURAL ( Trabalho )

        Double[][]dados_entrada = {
                {0.0, 1.0, 0.0, 0.0, 0.0},
                {1.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0, 0.0},
                {1.0, 1.0, 1.0, 0.0, 0.0},
        };

        Double[]dados_desejados = {
            1.0, 2.0
       };

        // double[]dados_desejados = { Números segundo os nossos nomes
        //      4, 8
        // };
        
        RedeNeural rede = new RedeNeural(25, 7, 2, FuncaoAtivacao.SIGMOIDE, 0.4, 10, 0.1);

        rede.setDados(dados_entrada, dados_desejados);

        rede.treinar();

        rede.mostrar();

    }
}