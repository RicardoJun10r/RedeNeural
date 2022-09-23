package BackPropagation;

/**
 * PASSO-A-PASSO
 * 
 * 1° Iniciar a rede neural, colocando o número de entradas, número de neurônios da camada oculta e número de neurônios da camada de saida;
 * 
 * 2° Iniciar os pesos com números aleatórios, utilizando a biblioteca RANDOM;
 * 
 * 3° Iniciar os dados que irá receber os dados de entrada, para serem processados e iniciar os dados que irá receber os dados desejados da saida;
 * 
 * 4° Treinar a rede neural:
 *      
 *      1° Vetor de saida igual a zero; saida = 0;
 *      2° Esse vetor de saida será o somatório das entradas pelo seus respectivos pesos; saida += (entrada * pesos);
 *      3° Esse vetor após o somátorio irá somar mais o "bias" do respectivo neurônio da camada oculta; saida += bias
 *      4° Por fim vetor passará pela função de ativação; resposta = sigmoide(saida); --> { sigmoide = 1 / (1 + Math.exp(-saida)); }
 * 
 * 5° Agora passará para as funções de correções de pesos ( Rede Neural ):
 *      1° os pesos irá receber a função de custo: pesos = (saida - desejado) * entrada;
 *      2° o bias será ajustado, pelo somatório da diferença entre a saida e dados desejados: bias += (saida - desejado);
 * 
 * 5° Agora passará para as funções de correções de pesos ( USANDO BackPropagation ):
 *      1° feed-forward;
 *      2° algoritmo de backpropag
 */

 import java.util.Random;


 // Esta Rede Neural foi feita para fins de teste

public class Teste {
    

    private double[][] entrada_dados;

    private double[] pesos;
    private double bias;

    private Integer epocas;


    public Teste(double[][] entrada_dados, Integer epocas)
    {
        this.entrada_dados = entrada_dados;

        this.epocas = epocas;

        this.pesos = new double[this.entrada_dados[0].length];

        iniciarPesos();
    }

    private void iniciarPesos()
    {
        Random random = new Random();

        for(int i = 0; i < this.pesos.length; i++)
        {
            this.pesos[i] = random.nextDouble();
        }
    }

    private double run(double[]entrada)
    {
        double soma = 0.0;
        for(int i = 0; i < pesos.length; i++)
        {
            soma += (entrada[i] * this.pesos[i]);
        }
        soma += this.bias;

        return sigmoide(soma);
    }

    private double sigmoide(double soma)
    {
        return (1/(1+Math.exp(-soma)));
    }

    public boolean treinar(double[]dados_desejados)
    {

        int contador = 0;

        do
        {

            for(int i = 0; i < this.entrada_dados.length; i++)
            {

                double saida = run(this.entrada_dados[i]);

                for(int j = 0; j < this.pesos.length; j++)
                {
                    this.pesos[j] += (dados_desejados[i] - saida) * this.entrada_dados[i][j];
                    System.out.println(
                        "Desejado = " + dados_desejados[i] +
                        " Saida = " + (saida > 0.5 ? 1 : 0)
                    );
                }

                this.bias += (dados_desejados[i] - saida);

            }
            System.out.println("Epoca = " + contador);
            contador++;
        }while(contador <= this.epocas);


        return true;
    }

}
