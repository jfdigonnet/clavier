package metier;

import java.text.DecimalFormat;
import java.util.ArrayList;

import prog.fenetrePrincipale;

public class Seance {

	// Variables globales
	// Ensemble des id des traductions
	private ArrayList<Integer> liste;
	// GBVersF Donne le sens 0: GB vers F 1: F vers GB
	// No de la traduction en cours pour les deux sens 
	private int noTraducEnCours = -1;
	// Nombre de mots vu au cours de la seance
	private int vusseance = 0;
	private int cochesseance = 0;
	private int start = 0;
	private fenetrePrincipale application;
	// Mesure du temps passé
	long startTime = 0;
	long elapsedTime = 0;
	
	public Seance(fenetrePrincipale fenetrePrincipale) {
		startTime = System.currentTimeMillis();
		application = fenetrePrincipale;
		setStart( getNoTraducEnCours() );
	}
	public String duree() {
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		DecimalFormat df = new DecimalFormat ( ) ;
		df.setMaximumFractionDigits ( 0 ) ; //arrondi à 2 chiffres apres la virgules
		df.setDecimalSeparatorAlwaysShown ( false ) ; 
		return df.format( elapsedTime / 1000 );
	}
	public void affichePremier() {
		noTraducEnCours = 0;
	}
	public void afficheSuivant() throws Exception {
	}
	public ArrayList<Integer> getListe() {
		return liste;
	}
	public void setListe(ArrayList<Integer> liste) {
		this.liste = liste;
	}
	public int getNoTraducEnCours() {
		return noTraducEnCours;
	}
	public void setNoTraducEnCours(int noTraducEnCours) {
		this.noTraducEnCours = noTraducEnCours;
	}
	public void incNoTraducEnCours() {
		this.noTraducEnCours = noTraducEnCours + 1;
	}
	public void decNoTraducEnCours() {
		this.noTraducEnCours = noTraducEnCours - 1;
	}
	public int getVusseance() {
		return vusseance;
	}
	public void incVusseance() {
		this.vusseance = vusseance + 1;
	}
	public void setVusseance(int vusseance) {
		this.vusseance = vusseance;
	}
	public int getCochesseance() {
		return cochesseance;
	}
	public void setCochesseance(int cochesseance) {
		this.cochesseance = cochesseance;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
}
