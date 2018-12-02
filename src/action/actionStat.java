package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import prog.fenetrePrincipale;

public class actionStat implements ActionListener {

	private fenetrePrincipale application;
	
	/***********************************************************
	 * Afficher les statistiques
	 * On calcule les stats et on les affiche ensuite
	 ***********************************************************/
	public actionStat(fenetrePrincipale app) {
		application = app;
	}
	public void actionPerformed(ActionEvent e) {
	}		
}
