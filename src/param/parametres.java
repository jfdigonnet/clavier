package param;

import java.io.File;
import java.util.prefs.Preferences;

import utilitaires.constantes;

public class parametres {
	
	// Sens de la lecture GB -> F ou F -> GB
	private int sens;
	// Répertoire d'enregistrement des données
	private String repertoire_donnees;
	// Style choisi
	private String style;
	// Tri des mots présentés : Date, par mot étranger ou mot français, etc...
	// Pas de tri par défaut 
	private int typeTri = 0;
	// Passage à la traduction suivante automatique
	private Boolean suivantAuto = false;
	// Délai av	ant de passer au suivant suite à l'affichage d'une traduction
	private int delaisSuivant = 0;
	// Afficher tous les mots ou seulement les mots non conns
	private Boolean afficherTousLesMots = true;
	// Enregistrer la position de lecture
	private Boolean enresPosLecteur = false;
	// Position dans la lecture (GB -> F)
	private Integer PositionTraductionGB = -1;
	// Position dans la lecture (F -> GB)
	private Integer PositionTraductionF = -1;
	// Proxy pour la connexion internet
	private String proxy = "";
	// Jouer le mot en langue 1 lors de l'affichage
	private Boolean joueTDS = false;
	// Temps avant interro suivante 	
	private int TempsAvantInterrogationSuivante = 4;
	// Mémorisé les mots déja interrogés
	private Boolean MemoInterro = false;
	// Temps avant interro suivante 
	
	private static parametres instance = new parametres();
	
	public static parametres getInstance() {
        return instance;
    }

	private parametres() {
		loadParam();
	}
	public String getRepertoire_donnees() {
		return repertoire_donnees;
	}
	/*
	 * 
	 */
	public void setRepertoire_donnees(String repertoire_donnees) {
		this.repertoire_donnees = repertoire_donnees;
	}
	/*
	 * 
	 */
	public String getRepSons() {
        String repOutImg = getRepertoire_donnees();
        if ( repOutImg.substring(0, repOutImg.length()-1) != "/") repOutImg += "/";
        repOutImg += constantes.getRepMP3(); 
        if ( repOutImg.substring(0, repOutImg.length()-1) != "/") repOutImg += "/";
        // On vérifie son existence sinon on le crée
		File rep = new File(repOutImg);
		if (! rep.exists()) {
			rep.mkdirs();
		}
        return repOutImg;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	public void loadParam() {
    	Preferences prefsRoot = Preferences.userRoot();
    	Preferences myPrefs = prefsRoot.node("traduc.preference");
    	repertoire_donnees = myPrefs.get("repertoire_donnees", System.getProperty("user.dir"));
    	typeTri = myPrefs.getInt("typeTri", 0);
    	style = myPrefs.get("style", "");

    	suivantAuto = myPrefs.getBoolean("suivantAuto", false);
    	delaisSuivant = myPrefs.getInt("delaisSuivant", 0);
    	
    	afficherTousLesMots = myPrefs.getBoolean("afficherTousLesMots", false);
    	enresPosLecteur = myPrefs.getBoolean("enresPosLecteur", false);
    	PositionTraductionGB = myPrefs.getInt("PositionTraductionGB", 0);
    	PositionTraductionF = myPrefs.getInt("PositionTraductionF", 0);
    	
    	sens = myPrefs.getInt("Sens", 0);
    	typeTri = myPrefs.getInt("Tri", 0);
    	
    	proxy = myPrefs.get("proxy", "");
    	joueTDS = myPrefs.getBoolean("joueTDS", false);
    	
    	TempsAvantInterrogationSuivante = myPrefs.getInt("TempsAvantInterrogationSuivante", 4);
    	MemoInterro = myPrefs.getBoolean("MemoInterro", false);
	}
	public void sauvePosLecture( int pos) {
		if ( getEnresPosLecteur() ) {
			Preferences prefsRoot = Preferences.userRoot();
	    	Preferences myPrefs = prefsRoot.node("traduc.preference");
	    	if (getSens())
	    		myPrefs.putInt("PositionTraductionGB",  pos);
	    	else
	    		myPrefs.putInt("PositionTraductionF",  pos);
		}
	}
	/*
	 * Enregistrrement des paramètres de l'application
	 * On reçoie la position dans la lecture
	 */
	public void saveParam() {
		Preferences prefsRoot = Preferences.userRoot();
    	Preferences myPrefs = prefsRoot.node("traduc.preference");
    	myPrefs.put("repertoire_donnees", repertoire_donnees);
    	myPrefs.putInt("typeTri",  typeTri);
    	myPrefs.put("style", style);

    	myPrefs.putBoolean("suivantAuto", suivantAuto);
    	myPrefs.putInt("delaisSuivant",  delaisSuivant);
    	
    	myPrefs.putBoolean("afficherTousLesMots", afficherTousLesMots);
    	
    	myPrefs.putBoolean("enresPosLecteur", enresPosLecteur);
    	myPrefs.putInt("PositionTraductionGB",  PositionTraductionGB);    	
    	myPrefs.putInt("PositionTraductionF",  PositionTraductionF);
    	
    	myPrefs.putInt("Sens",  sens);
    	myPrefs.putInt("Tri",  typeTri);
    	
    	myPrefs.put("proxy",  proxy);
    	myPrefs.putBoolean("joueTDS", joueTDS);
    	
    	myPrefs.putInt("TempsAvantInterrogationSuivante", TempsAvantInterrogationSuivante);
    	myPrefs.putBoolean("MemoInterro", MemoInterro);
	}
	/*
	 * Retourne le type de tri :
	 * 	Date de création (Du plus ancien au plus récent)
	 * 	Langue étrangère
	 * 	Langue maternelle
	 * 	Au hasard
	 * 	Date de création (Du plus récent au plus ancien)
	 * cf utilitaires.constantes
	 */
	public int getTypeTri() {
		return typeTri;
	}
	public void setTypeTri(int typeTri) {
		this.typeTri = typeTri;
	}
	public Boolean getSuivantAuto() {
		return suivantAuto;
	}
	public void setSuivantAuto(Boolean suivantAuto) {
		this.suivantAuto = suivantAuto;
	}
	public int getDelaisSuivant() {
		return delaisSuivant;
	}
	public void setDelaisSuivant(Integer delaisSuivant) {
		this.delaisSuivant = delaisSuivant;
	}
	public Boolean getAfficherTousLesMots() {
		return afficherTousLesMots;
	}
	public void setAfficherTousLesMots(Boolean afficherTousLesMots) {
		this.afficherTousLesMots = afficherTousLesMots;
	}
	public void setDelaisSuivant(int delaisSuivant) {
		this.delaisSuivant = delaisSuivant;
	}
	public void setEnresPosLecteur(Boolean enresPosLecteur) {
		this.enresPosLecteur = enresPosLecteur;
	}
	public int getPositionTraduction() {
    	if ( getSens() ) { 
    		return this.PositionTraductionGB;
    	} else {
    		return this.PositionTraductionF;
    	}
	}
	public void setPositionLecture(int pos) {
    	if ( getSens() ) { 
    		PositionTraductionGB = pos;
    	} else {
    		PositionTraductionF = pos;
    	}
	}
	/*
	 * Permet de connaitre le sens de la lecture 
	 * Du F vers le GB 
	 * ou l'inverse
	 */
	public Boolean getSens() {
		return (sens == 0);
	}
	public Boolean getEnresPosLecteur() {
		return enresPosLecteur;
	}
	public String loadParamRep() {
    	Preferences prefsRoot = Preferences.userRoot();
    	Preferences myPrefs = prefsRoot.node("traduc.preference");
    	return myPrefs.get("lastrepert", System.getProperty("user.dir"));
	}
	public void sauveParamRep(String repert) {
    	Preferences prefsRoot = Preferences.userRoot();
    	Preferences myPrefs = prefsRoot.node("traduc.preference");
    	myPrefs.put("lastrepert", repert);
	}
	public String getProxy() {
		return proxy;
	}
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	public Boolean getJoueTDS() {
		return joueTDS;
	}
	public void setJoueTDS(Boolean joueTDS) {
		this.joueTDS = joueTDS;
	}

	public void setSens(int selectedIndex) {
		this.sens = selectedIndex;
	}
	public Integer loadParamDerAtteindre() {
    	Preferences prefsRoot = Preferences.userRoot();
    	Preferences myPrefs = prefsRoot.node("traduc.preference");
    	return myPrefs.getInt("dernier", 0);
	}
	public void sauveParamAtteindre(int no) {
    	Preferences prefsRoot = Preferences.userRoot();
    	Preferences myPrefs = prefsRoot.node("traduc.preference");
    	myPrefs.putInt("dernier", no);
	}

	/**
	 * @return the tempsAvantInterrogationSuivante
	 */
	public int getTempsAvantInterrogationSuivante() {
		return TempsAvantInterrogationSuivante;
	}

	/**
	 * @param tempsAvantInterrogationSuivante the tempsAvantInterrogationSuivante to set
	 */
	public void setTempsAvantInterrogationSuivante(int tempsAvantInterrogationSuivante) {
		TempsAvantInterrogationSuivante = tempsAvantInterrogationSuivante;
	}
	public String loadDerRepSauve() {
    	Preferences prefsRoot = Preferences.userRoot();
    	Preferences myPrefs = prefsRoot.node("traduc.preference");
    	return myPrefs.get("derrepsauve", System.getProperty("user.dir"));
	}
	public void sauveDerRepSauve(String repert) {
    	Preferences prefsRoot = Preferences.userRoot();
    	Preferences myPrefs = prefsRoot.node("traduc.preference");
    	myPrefs.put("derrepsauve", repert);
	}

	/**
	 * @return the memoInterro
	 */
	public Boolean getMemoInterro() {
		return MemoInterro;
	}

	/**
	 * @param memoInterro the memoInterro to set
	 */
	public void setMemoInterro(boolean memoInterro) {
		MemoInterro = memoInterro;
	}
}


