import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import BackPropagation.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


       
         
        // REDE NEURAL ( Trabalho )

        /*
        {
            1.0, 1.0, 1.0, 0.0, 0.0, 
            0.0, 0.0, 0.0, 1.0, 0.0, 
            0.0, 0.0, 1.0, 0.0, 0.0, 
            0.0, 1.0, 0.0, 0.0, 0.0, 
            1.0, 1.0, 1.0, 1.0, 0.0
        },

            { 0.0, 1.0, 0.0, 0.0, 0.0 }, 
            { 1.0, 1.0, 0.0, 0.0, 0.0 }, 
            { 0.0, 1.0, 0.0, 0.0, 0.0 }, 
            { 0.0, 1.0, 0.0, 0.0, 0.0 }, 
            { 1.0, 1.0, 1.0, 0.0, 0.0 }

                ==NOSSOS NÚMEROS==

            { 1.0, 0.0, 1.0, 0.0, 0.0 }, 
            { 1.0, 0.0, 1.0, 0.0, 0.0 }, 
            { 1.0, 1.0, 1.0, 1.0, 0.0 }, 
            { 0.0, 0.0, 1.0, 0.0, 0.0 }, 
            { 0.0, 0.0, 1.0, 0.0, 0.0 }

            { 0.0, 1.0, 1.0, 0.0, 0.0 }, 
            { 1.0, 0.0, 0.0, 1.0, 0.0 }, 
            { 0.0, 1.0, 1.0, 0.0, 0.0 }, 
            { 1.0, 0.0, 0.0, 1.0, 0.0 }, 
            { 0.0, 1.0, 1.0, 0.0, 0.0 }

        */

public class Controller implements Initializable{

    @FXML
    private RadioMenuItem tabelaNot;

    @FXML
    private ImageView imgSecundaria;

    @FXML
    private TextField epocas;

    @FXML
    private TextField taxaAprend;

    @FXML
    private ImageView imgPrimaria;

    @FXML
    private RadioMenuItem relu;

    @FXML
    private RadioMenuItem tabelaOr;

    @FXML
    private TextField tolerancia;

    @FXML
    private RadioMenuItem leakyRelu;

    @FXML
    private RadioMenuItem tanh;

    @FXML
    private RadioMenuItem tabelaXor;

    @FXML
    private RadioMenuItem sigmoide;

    @FXML
    private TextField resultEsperado;

    @FXML
    private RadioMenuItem tabelaAnd;
    
    private RedeNeural rna1;//rede neural da iamgem primaria
    private RedeNeural rna2;//rede neural da iamgem secundaria
    
    private Double[] saidas;
    private int iteracoes;
    private Double erroMinimo;
    private Double aprendizado;

    @FXML
    void abrirPrimaria(ActionEvent event){
        JFileChooser fileChooser = new JFileChooser();
        
        if(fileChooser.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            
            try {
                imgPrimaria.setImage( SwingFXUtils.toFXImage(ImageIO.read(file), null) );
                imgPrimaria.setFitWidth( (double)SwingFXUtils.toFXImage(ImageIO.read(file), null).getWidth() );
                imgPrimaria.setFitHeight( (double)SwingFXUtils.toFXImage(ImageIO.read(file), null).getHeight() );
                imgPrimaria.setLayoutX( 10 );
                imgPrimaria.setLayoutY( 10 );
                
            } catch (IOException ea) {
                //TODO: handle exception
                ea.getStackTrace();
            }
        }
    }

    @FXML
    void abrirSecundaria(ActionEvent event){
    	JFileChooser fileChooser = new JFileChooser();
        
        if(fileChooser.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            
            try {
                imgSecundaria.setImage( SwingFXUtils.toFXImage(ImageIO.read(file), null) );
                imgSecundaria.setFitWidth( (double)SwingFXUtils.toFXImage(ImageIO.read(file), null).getWidth() );
                imgSecundaria.setFitHeight( (double)SwingFXUtils.toFXImage(ImageIO.read(file), null).getHeight() );
                imgSecundaria.setLayoutX( imgPrimaria.getFitWidth() + 20);
                imgSecundaria.setLayoutY( 10 );
                
            } catch (IOException ea) {
                //TODO: handle exception
                ea.getStackTrace();
            }
        }
    }
    

    @FXML
    void gerarRNA(ActionEvent event) {
    	//rna1 = new RedeNeural();
    	//rna2 = new RedeNeural();
    }

    @FXML
    void taxaAprend(ActionEvent event) {
    	aprendizado = Double.valueOf( taxaAprend.getText() );
    }

    @FXML
    void tolerancia(ActionEvent event) {
    	erroMinimo = Double.valueOf( tolerancia.getText() ) / 100;
    }

    @FXML
    void epocas(ActionEvent event) {
    	iteracoes = Integer.valueOf(epocas.getText());
    }

    @FXML
    void resultEsperado(ActionEvent event) {
    	String [] tmp = resultEsperado.getText().split( "," );
    	
    	for(int i = 0; i < tmp.length; i++) {
    		saidas[i] = Double.valueOf( tmp[i] );
    	}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*Double[][]dados_entrada = new Double[][] {
            {1.0, 1.0},
            {1.0, 0.0},
            {0.0, 1.0},
            {0.0, 0.0},
        };

        Double[]dados_desejados = new Double[] {
            0.0, 1.0, 1.0, 0.0
       };
        
        RedeNeural rede = new RedeNeural(8, 12, 4, FuncaoAtivacao.SIGMOIDE, 0.4, 100, 0.1);

        rede.setDados(dados_entrada, dados_desejados);

        rede.treinar();*/
    }
    
    @FXML
    void sair(ActionEvent event) {
    	Main.close();
    }

}
