package utilitaires;

import java.io.File;

public class constantes {

	public static String titreAppli = "Apprentissage du clavier";
	public static String repDonnees = "./dataclavier";
	public static String repMP3 = repDonnees + File.separator + "mp3" + File.separator;
	public static String nomProgramme = "Apprentissage du clavier";
	public static String versionProgramme = "0.0.1";
	public static String dateProgramme = "01/01/2013";
	
	public static String getVersionProgramme() {
		return versionProgramme;
	}
	public static String getDateProgramme() {
		return dateProgramme;
	}
	public static String getTitreAppli() {
		return titreAppli;
	}
	public static String getRepDonnees() {
		return System.getProperty("user.dir") + File.separator + repDonnees;
	}
	public static void setRepDonnees(String repDonnees) {
		if (repDonnees.endsWith(File.separator))
			constantes.repDonnees = repDonnees;
		else
			constantes.repDonnees = repDonnees + File.separator;
	}
	public static String getRepMP3() {
		return System.getProperty("user.dir") + File.separator + repMP3;
	}
}
