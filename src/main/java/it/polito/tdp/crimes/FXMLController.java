/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Arco;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Arco> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	Arco a=this.boxArco.getValue();
    	if(a==null) {
    		txtResult.appendText("seleziona arco");
    		return;
    	}
    	List<String> percorsoList=this.model.trovaPercorso(a.getV1(), a.getV2());
    	txtResult.appendText("Percorso migliore: \n\n");
    	for (String s: percorsoList) {
			txtResult.appendText(s+"\n");
		}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	String categoria= boxCategoria.getValue();
    	Integer mese=boxMese.getValue();
    	if(categoria==null) {
    		txtResult.appendText("Seleziona categoria");
    		return;
    	}
    	
    	if(mese==null) {
    		txtResult.appendText("Seleziona mese");
    		return;
    	}
    	
    	this.model.creaGrafo(categoria, mese);
    	
    	List<Arco> archi=this.model.getArchi();
    	boxArco.getItems().addAll(archi);
    	txtResult.appendText("ARCHI > PESO MEDIO: \n\n");
    	for(Arco arco:archi) {
    		txtResult.appendText(archi.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(this.model.getCategorie());
    	this.boxMese.getItems().addAll(this.model.getMesi());
    }
}
