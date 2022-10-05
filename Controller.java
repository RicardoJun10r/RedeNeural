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
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
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
    private TextField qntCamadaOcultaPri;
    
    @FXML
    private TextField qntCamadaOcultaSec;
    
    @FXML
    private MenuItem itemValorOcultaPri;
    
    @FXML
    private MenuItem itemValorOcultaSec;
    
    @FXML
    private TextField qntCamadaSaidaPri;
    
    @FXML
    private TextField qntCamadaSaidaSec;
    
    @FXML
    private MenuItem itemValorSaidaPri;
    
    @FXML
    private MenuItem itemValorSaidaSec;
    
    @FXML
    private CheckMenuItem usarSaidaPadrao;

    @FXML
    private CheckMenuItem usarOcultaPadrao;

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
    private int oculta1;
    private int oculta2;
    private int nSaidas1;
    private int nSaidas2;
    private int entradas1;
    private int entradas2;
    private FuncaoAtivacao funcao;
    private Tabela tabelaUsada;
    
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
    	
    	entradas1 = (int)(imgPrimaria.getFitWidth() * imgPrimaria.getFitHeight());
    	entradas2 = (int)(imgSecundaria.getFitWidth() * imgSecundaria.getFitHeight());
    	
    	rna1 = new RedeNeural(entradas1, oculta1, nSaidas1, funcao, aprendizado, iteracoes, erroMinimo);
    	
    	rna2 = new RedeNeural(entradas2, oculta2, nSaidas2, funcao, aprendizado, iteracoes, erroMinimo);
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
    	saidas = new Double[ tmp.length ];
    	
    	for(int i = 0; i < tmp.length; i++) {
    		saidas[i] = Double.valueOf( tmp[i] );
    	}
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	usarOcultaPadrao.setOnAction( event -> {
        	
        	if( usarOcultaPadrao.isSelected() ) {
        		
        		itemValorOcultaPri.setDisable( true );
        		itemValorOcultaSec.setDisable( true );
        		
        		if( !qntCamadaOcultaPri.getText().isEmpty() && !qntCamadaOcultaSec.getText().isEmpty() ) {
            		oculta1 = Integer.valueOf( qntCamadaOcultaPri.getText() );
            		oculta2 = Integer.valueOf( qntCamadaOcultaSec.getText() );
        		}
        	
        	}
        	else {
        		
        		itemValorOcultaPri.setDisable( false );
        		itemValorOcultaSec.setDisable( false );
        		
        		oculta1 = (int)Math.sqrt(entradas1 + nSaidas1);
            	oculta2 = (int)Math.sqrt(entradas2 + nSaidas2);
            	
        	}
        });
    	
    	usarSaidaPadrao.setOnAction( event -> {
        	
        	if( usarSaidaPadrao.isSelected() ) {
        		
        		itemValorSaidaPri.setDisable( true );
        		itemValorSaidaSec.setDisable( true );
        		
        		if( !qntCamadaSaidaPri.getText().isEmpty() && !qntCamadaSaidaSec.getText().isEmpty()) {
        			nSaidas1 = Integer.valueOf( qntCamadaSaidaPri.getText() );
            		nSaidas2 = Integer.valueOf( qntCamadaSaidaSec.getText() );
        		}
        		
        	}
        	else {
        		
        		itemValorSaidaPri.setDisable( false );
        		itemValorSaidaSec.setDisable( false );
        		
        		if( saidas != null )
        			nSaidas1 = nSaidas2 = saidas.length;
        		
        	}
        });
    	
    	//funcoes de ativacao
    	sigmoide.setOnAction( event -> {
    		sigmoide.setSelected( true );
    		tanh.setSelected( false );
    		relu.setSelected( false );
    		leakyRelu.setSelected( false );
    		funcao = FuncaoAtivacao.SIGMOIDE;
    	});
    	
    	tanh.setOnAction( event -> {
    		sigmoide.setSelected( false );
    		tanh.setSelected( true );
    		relu.setSelected( false );
    		leakyRelu.setSelected( false );
    		funcao = FuncaoAtivacao.TANH;
    	});

    	relu.setOnAction( event -> {
    		sigmoide.setSelected( false );
    		tanh.setSelected( false );
    		relu.setSelected( true );
    		leakyRelu.setSelected( false );
    		funcao = FuncaoAtivacao.RELU;
    	});
    	
    	leakyRelu.setOnAction( event -> {
    		sigmoide.setSelected( false );
    		tanh.setSelected( false );
    		relu.setSelected( false );
    		leakyRelu.setSelected( true );
    		funcao = FuncaoAtivacao.LEAKYRELU;
    	});
    	
    	//tabelas usadas
    	tabelaAnd.setOnAction( event -> {
    		tabelaAnd.setSelected( true );
    		tabelaOr.setSelected( false );
    		tabelaNot.setSelected( false );
    		tabelaXor.setSelected( false );
    		tabelaUsada = Tabela.AND;
    	});
    	
    	tabelaOr.setOnAction( event -> {
    		tabelaAnd.setSelected( false );
    		tabelaOr.setSelected( true );
    		tabelaNot.setSelected( false );
    		tabelaXor.setSelected( false );
    		tabelaUsada = Tabela.OR;
    	});
    	
    	tabelaNot.setOnAction( event -> {
    		tabelaAnd.setSelected( false );
    		tabelaOr.setSelected( false );
    		tabelaNot.setSelected( true );
    		tabelaXor.setSelected( false );
    		tabelaUsada = Tabela.NOT;
    	});
    	
    	tabelaXor.setOnAction( event -> {
    		tabelaAnd.setSelected( false );
    		tabelaOr.setSelected( false );
    		tabelaNot.setSelected( false );
    		tabelaXor.setSelected( true );
    		tabelaUsada = Tabela.XOR;
    	});
    }
    
    @FXML
    void sair(ActionEvent event) {
    	Main.close();
    }

}
