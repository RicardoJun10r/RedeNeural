package BackPropagation;

import java.util.Random;


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
 *      2° algoritmo de backpropagation
 */

 /*

  * Número de neurônios na primeira camada = número de variaveis de entrada;

  * Número de neurônios na segunda camada = média geometrica entre os números da camada de entrada e saída;

  * Número de neurônios na terceira camada = número da quantidade de possíveis respostas;


  Calculo dos deltas = wi(n + 1) = wi(n) + ( c * g * xi);
  c = taxa de aprendizagem;
  g = (saida_desejada - saida_obtida);

  */


public class RedeNeural {
    
    //#region VARIÁVEIS

    // TAMANHO DAS CAMADAS
    private Integer numero_camada_entrada;
    private Integer numero_camada_oculta;
    private Integer numero_camada_saida;

    // CAMADAS
    private Double[] camada_entrada;
    private Double[] camada_oculta;
    private Double[] camada_saida;

    // PESOS
    private Double[][] pesos_entrada_para_oculta;
    private Double[][] pesos_oculta_para_saida;

    
    // VALORES ADICIONAIS
    private FuncaoAtivacao funcaoAtivacao;              // Define qual função de ativação será usada
    private Integer epoche;                            // Define a taxa de aprendizagem e o erro minimo toleravel
    private Double taxa_aprendizagem;
    private Double erro_minimo;

    private Double[] erro_da_rede;

    // DADOS DE ENTRADA E SAIDA
    private Double[][] dados_para_treino;             // Define os dados que serão inicializados e processados
    private Double[] dados_desejados;                // comparados aos dados esperados

    // VIES
    private Double[] vies1;                         
    private Double[] vies2;

    //#endregion

    //#region INICIALIZAÇÃO

    // CONSTRUTOR DA REDE NEURAL
    public RedeNeural(
        Integer numero_camada_entrada, 

        Integer numero_camada_oculta, 

        Integer numero_camada_saida,

        FuncaoAtivacao funcaoAtivacao,

        Double taxa_aprendizagem,

        Integer iteracoes,

        Double erro_minimo
    )
    {
        //---------------------CAMADAS----------------------

        this.numero_camada_entrada = numero_camada_entrada;
        this.numero_camada_oculta = numero_camada_oculta;
        this.numero_camada_saida = numero_camada_saida;


        this.camada_entrada = new Double[numero_camada_entrada];
        this.camada_oculta = new Double[numero_camada_oculta];
        this.camada_saida = new Double[numero_camada_saida];

        //-------------------------------------------------
        
        this.funcaoAtivacao = funcaoAtivacao;

        this.taxa_aprendizagem = taxa_aprendizagem;
        this.erro_minimo = erro_minimo;

        this.epoche = iteracoes;

        //----------------------PESOS----------------------

        this.pesos_entrada_para_oculta = new Double[numero_camada_oculta][numero_camada_entrada];
        this.pesos_oculta_para_saida = new Double[numero_camada_saida][numero_camada_oculta];

        //----------------------VIES-----------------------

        this.vies1 = new Double[numero_camada_oculta];
        this.vies2 = new Double[numero_camada_saida];

        //------------------INICIAR-PESOS------------------
        iniciarPesos();
    }

    private void iniciarPesos()
    {
        Random r = new Random();
        // PESOS CAMADA ENTRADA PARA OCULTA
        for(int i = 0; i < this.numero_camada_oculta; i++)
        {
            for(int j = 0; j < this.numero_camada_entrada; j++)
            {
                this.pesos_entrada_para_oculta[i][j] = r.nextDouble();
            }
            
        }

        // PESOS CAMADA OCULTA PARA SAIDA
        for(int i = 0; i < this.numero_camada_saida; i++)
        {
            for(int j = 0; j < this.numero_camada_oculta; j++)
            {
                this.pesos_oculta_para_saida[i][j] = r.nextDouble();
            }
            
        }
        
        // VIESES DAS CAMADAS ( OPCIONAL )
        // for(int i = 0; i < this.camada_oculta.length; i++)
        // {
        //     this.vies1[i] = r.nextDouble();
        // }

        // for(int i = 0; i < this.camada_saida.length; i++)
        // {
        //     this.vies2[i] = r.nextDouble();
        // }
    }

    /**
     * Método para
     * @param dados_para_treino são os dados de entrada, que serão processados
     * @param dados_desejados são os dados que o usuário espera receber
     */
    public void setDados(Double[][] dados_para_treino, Double[] dados_desejados)
    {
        this.dados_para_treino = dados_para_treino;
        this.dados_desejados = dados_desejados;

        // INICIANDO A CAMADA ENTRADA COM OS DADOS RECEBIDOS
        int contador = 0;
        for(int i = 0; i < dados_para_treino.length; i++)
        {
            for(int j = 0; j < dados_para_treino[0].length; j++)
            {
                this.camada_entrada[contador] = dados_para_treino[i][j];
                contador++;
            }
        }
    }

    //#endregion

    //#region REDE NEURAL

    /**
     * Método PRINCIPAL que irá fazer a rede neural funcionar
     */
    public void treinar()
    {
        int contador = 0;
        do {

            this.feedForward();

            this.backpropagation();

            System.out.println("Epoche: " + contador);
            for(int i = 0; i < this.numero_camada_saida; i++)
            {
                System.out.println("Resultado esperado: " + this.dados_desejados[i] + " | Resultado da rede: " + this.camada_saida[i]);
            }
            
            contador++;

        } while (contador <= this.epoche);
    }

    /**
     * O algoritmo FEEDFORWAR garante que os dados percorram apenas um sentido, da entrada para saída
     */
    private void feedForward()
    {
        // INICIANDO CAMADA OCULTA
        for(int i = 0; i < this.camada_oculta.length; i++)
        {
            this.camada_oculta[i] = 0.0;
        }

        // INICIANDO CAMADA SAIDA
        for(int i = 0; i < this.camada_saida.length; i++)
        {
            this.camada_saida[i] = 0.0;
        }

        // REALIZANDO O CALCULO DA CAMADA OCULTA
        this.somatorio1();

        // REALIZANDO O CALCULO DA CAMADA SAIDA
        this.somatorio2();
    }

    /**
     * O somatório que irá calcular os valores das camadas ocultas
     * Cada camada oculta é igual ao somatório dos valores de entrada pelo seus respectivos pesos
     */
    private void somatorio1()
    {
        // SOMATÓRIO
        for(int i = 0; i < this.numero_camada_oculta; i++)
        {
            for(int j = 0; j < this.numero_camada_entrada; j++)
            {
                this.camada_oculta[i] += (this.camada_entrada[j]*this.pesos_entrada_para_oculta[i][j]);
            }

            // FUNÇÃO DE ATIVAÇÃO
            switch (this.funcaoAtivacao) {
                case SIGMOIDE:
                    this.camada_oculta[i] = this.sigmoide(this.camada_oculta[i]);
                    break;
                case TANH:
                    this.camada_oculta[i] = this.tanh(this.camada_oculta[i]);
                    break;
                case LEAKYRELU:
                    this.camada_oculta[i] = this.leakyrelu(this.camada_oculta[i]);
                    break;
                case RELU:
                    this.camada_oculta[i] = this.relu(this.camada_oculta[i]);
                    break;
                default:
                    this.camada_oculta[i] = 0.0;
                    System.out.println("ERRO");
                    break;
            }
        }
    }

    /**
     * O somatório que irá calcular os valores das camadas de saída
     * Cada camada de saída é igual ao somatório dos valores da camada oculta pelo seus respectivos pesos
     */
    private void somatorio2()
    {
        // SOMATÓRIO
        for(int i = 0; i < this.numero_camada_saida; i++)
        {
            for(int j = 0; j < this.numero_camada_oculta; j++)
            {
                this.camada_saida[i] += (this.camada_oculta[j]*this.pesos_oculta_para_saida[i][j]);
            }

            // FUNÇÃO DE ATIVAÇÃO
            switch (this.funcaoAtivacao) {
                case SIGMOIDE:
                    this.camada_saida[i] = this.sigmoide(this.camada_saida[i]);
                    break;
                case TANH:
                    this.camada_saida[i] = this.tanh(this.camada_saida[i]);
                    break;
                case LEAKYRELU:
                    this.camada_saida[i] = this.leakyrelu(this.camada_saida[i]);
                    break;
                case RELU:
                    this.camada_saida[i] = this.relu(this.camada_saida[i]);
                    break;
                default:
                    this.camada_saida[i] = 0.0;
                    System.out.println("ERRO");
                    break;
            }
        }
    }

    private void calcularErro(Double[]numero)
    {
        Double[]erro_da_rede = new Double[numero.length];

        for(int i = 0; i  < numero.length; i++)
            erro_da_rede[i] = 0.0;

        for(int i = 0; i < numero.length; i++)
            erro_da_rede[i] = Math.pow(numero[i], 2)/2;

        this.erro_da_rede = erro_da_rede;
    }

    private void backpropagation()
    {
        Double [] erroSaida = new Double[this.numero_camada_saida];

        for(int i = 0; i < this.numero_camada_saida; i++)
            erroSaida[i] = 0.0;

        for(int i = 0; i < this.numero_camada_saida; i++)
        {
            switch(this.funcaoAtivacao)
            {
                case SIGMOIDE:
                    erroSaida[i] = (this.dados_desejados[i] - this.camada_saida[i])*this.derivadaSigmoide(this.camada_saida[i]);
                    break;
                case TANH:
                    erroSaida[i] = (this.dados_desejados[i] - this.camada_saida[i])*this.derivadaTahn(this.camada_saida[i]);
                    break;
                case LEAKYRELU:
                    erroSaida[i] = (this.dados_desejados[i] - this.camada_saida[i])*this.derivadaLeakyrelu(this.camada_saida[i]);
                    break;
                case RELU:
                    erroSaida[i] = (this.dados_desejados[i] - this.camada_saida[i])*this.derivadaRelu(this.camada_saida[i]);
                    break;
                default:
                    erroSaida[i] = 0.0;
                    System.out.println("ERRO NO BACKPROPAGATION");
                    break;
            }
        }

        calcularErro(erroSaida);
        
        this.ajustarPesosSaida(erroSaida);

        Double[] erroOculta = new Double[this.numero_camada_oculta+1];
        for(int i = 0; i < this.numero_camada_oculta; i++)
            erroOculta[i] = 0.0;

        for(int i = 0; i < this.numero_camada_saida; i++)
        {
            for(int j = 0; j < this.numero_camada_oculta; j++)
            {
                switch(this.funcaoAtivacao)
                {
                case SIGMOIDE:
                    erroOculta[j] = this.derivadaSigmoide(this.camada_oculta[j]) * erroSaida[i] * this.camada_oculta[j];
                    break;
                case TANH:
                    erroOculta[j] = this.derivadaTahn(this.camada_oculta[j]) * erroSaida[i] * this.camada_oculta[j];
                    break;
                case LEAKYRELU:
                    erroOculta[j] = this.derivadaLeakyrelu(this.camada_oculta[j]) * erroSaida[i] * this.camada_oculta[j];
                    break;
                case RELU:
                    erroOculta[j] = this.derivadaRelu(this.camada_oculta[j]) * erroSaida[i] * this.camada_oculta[j];
                    break;
                default:
                    erroSaida[i] = 0.0;
                    System.out.println("ERRO NO BACKPROPAGATION");
                    break;
                }
            }
        }

        this.ajustarPesosOculta(erroOculta);

    }

    private void ajustarPesosSaida(Double[] erro)
    {
        for(int i = 0; i < this.numero_camada_saida; i++)
        {
            for(int j = 0; j < this.numero_camada_oculta; j++)
            {
                this.pesos_oculta_para_saida[i][j] = this.pesos_oculta_para_saida[i][j] + this.taxa_aprendizagem*erro[i]*this.camada_saida[i];
            }
        }
    }

    private void ajustarPesosOculta(Double[]erro)
    {
        for(int i = 0; i < this.numero_camada_oculta; i++)
        {
            for(int j = 0; j < this.numero_camada_entrada; j++)
            {
                this.pesos_entrada_para_oculta[i][j] = this.pesos_entrada_para_oculta[i][j] + this.taxa_aprendizagem*erro[i]*this.camada_oculta[i];
            }
        }
    }

    //#endregion

    //#region MÉTODOS DAS FUNÇÕES DE ATIVAÇÃO

    private Double sigmoide(Double soma)
    {
        return (1/(1+Math.exp(-soma)));
    }

    private Double derivadaSigmoide(Double numero)
    {
        return this.sigmoide(numero) * (1 - this.sigmoide(numero));
    }

    private Double tanh(Double soma)
    {
        return (2/(1 + Math.exp(-2*soma))-1);
    }

    private Double derivadaTahn(Double x) {
		return 1- Math.pow(this.tanh(x), 2);
	}

    private Double leakyrelu(Double x) {
		return x > 0 ? x : 0.01 * x;
	}

    private Double derivadaLeakyrelu(Double x) {
		return x > 0 ? 1 : 0.01;
	}

    private Double relu(Double x) {
		return x > 0 ? x : 0;
	}

    private Double derivadaRelu(Double x) {
		return x > 0 ? 1 : 0.0;
	}

    //#endregion

    //#region MÉTODOS ADICIONAIS, a fim de testes

    /**
     * Função que mostrará todos os valores, para testes
     */
    public void mostrar()
    {
        int cont = 1;
        System.out.println("=========================================");
        System.out.println("Entrada");
        for(int i = 0; i < this.dados_para_treino.length; i++)
        {
            for(int j = 0; j < this.dados_para_treino[0].length; j++)
            {
                System.out.println(cont + "° " + this.dados_para_treino[i][j] + " ");
                cont++;
            }
            System.out.println();
        }
        cont = 1;
        System.out.println("=========================================");
        System.out.println("Dados desejados");
        for(int i = 0; i < this.dados_desejados.length; i++)
        {
            System.out.println(cont + "° " +this.dados_desejados[i] + " ");
            cont++;
        }
        cont = 1;
        System.out.println();
        System.out.println("=========================================");
        System.out.println("pesos da camada entrada para oculta");
        for(int i = 0; i < this.numero_camada_oculta; i++)
        {
            for(int j = 0; j < this.numero_camada_entrada; j++)
            {
                System.out.println(cont + "° " +this.pesos_entrada_para_oculta[i][j] + " ");
                cont++;
            }
        }
        cont = 1;
        System.out.println();
        System.out.println("=========================================");
        System.out.println("pesos da camada oculta para saida");
        for(int i = 0; i < this.numero_camada_saida; i++)
        {
            for(int j = 0; j < this.numero_camada_oculta; j++)
            {
                System.out.println(cont + "° " +this.pesos_oculta_para_saida[i][j] + " ");
                cont++;
            }
        }
        System.out.println();
        System.out.println("=========================================");
        System.out.println();
        System.out.println("Primeiro resultado");
        cont = 1;
        for(int i = 0; i < this.camada_oculta.length; i++)
        {
            System.out.println(cont + "° " +this.camada_oculta[i] + " ");
            cont++;
        }
        System.out.println();
        System.out.println("=========================================");
        System.out.println();
        System.out.println("Segundo resultado");
        cont = 1;
        for(int i = 0; i < this.camada_saida.length; i++)
        {
            System.out.println(cont + "° " +this.camada_saida[i] + " ");
            cont++;
        }
        // System.out.println();
        // System.out.println("=========================================");
        // System.out.println();
        // System.out.println("Camada entrada");
        // cont = 1;
        // for(int i = 0; i < this.camada_entrada.length; i++)
        // {
        //     System.out.println(cont + "° " +this.camada_entrada[i] + " ");
        //     cont++;
        // }
    }

    //#endregion

}
