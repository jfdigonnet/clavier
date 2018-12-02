package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import prog.fenetrePrincipale;

import metier.Seance;

public class actionQuitter implements ActionListener {

	private fenetrePrincipale application;
	private Seance seance;

	/***********************************************************
	 * On quitte l'application
	 ***********************************************************/
	public actionQuitter(fenetrePrincipale app, Seance sc) {
		application = app;
		seance = sc;
	}
	public void actionPerformed(ActionEvent e) {
		application.sauvePosLecture();
		System.out.println( seance.duree() );
		System.exit(0);
	}
}
