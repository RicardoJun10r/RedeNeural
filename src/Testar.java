import BackPropagation.FuncaoAtivacao;
import BackPropagation.RedeNeural;

public class Testar {
    public static void main(String[] args) {
        

        Double [][] input1 = { // 8
            {
                0.0, 1.0, 1.0, 0.0, 0.0, 
                1.0, 0.0, 0.0, 1.0, 0.0, 
                0.0, 1.0, 1.0, 0.0, 0.0, 
                1.0, 0.0, 0.0, 1.0, 0.0, 
                0.0, 1.0, 1.0, 0.0, 0.0
            }
        };

        Double [][] input2 = { // Aleatorio
           {
            1.0, 0.0, 0.0, 1.0, 1.0, 
            0.0, 1.0, 1.0, 0.0, 1.0, 
            1.0, 1.0, 1.0, 1.0, 1.0, 
            0.0, 1.0, 1.0, 1.0, 1.0, 
            1.0, 1.0, 0.0, 0.0, 1.0
           }
        };

        Double [][] input3 = { // 4
            {
                1.0, 0.0, 1.0, 0.0, 0.0, 
                1.0, 0.0, 1.0, 0.0, 0.0, 
                1.0, 1.0, 1.0, 1.0, 0.0, 
                0.0, 0.0, 1.0, 0.0, 0.0, 
                0.0, 0.0, 1.0, 0.0, 0.0
            }
         };

        Double[]output1 = {
            1.0, 0.0, 0.0
        };

        Double[]output2 = {
            0.0, 1.0, 0.0
        };
        

        // CONFIGURAÇÃO PARA VALORES PRÓXIMOS UM DO OUTRO
        //RedeNeural rede1 = new RedeNeural(25, 1, 3, FuncaoAtivacao.SIGMOIDE, 0.2, 10000, 0.1); //valores próximos

        // CONFIGURAÇÃO PARA VALORES DISTANTES UM DO OUTRO
        RedeNeural rede1 = new RedeNeural(25, 1, 3, FuncaoAtivacao.SIGMOIDE, 0.5, 10000, 0.1); // valores diferentes
        
        rede1.setDados(input1, output1);

        rede1.treinar();

        rede1.teste(input1);
        System.out.println("=========================");
        rede1.teste(input2);

    }
}
