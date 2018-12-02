package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import prog.fenetrePrincipale;

public class actionReinit implements ActionListener {

    private fenetrePrincipale application;
	
	public actionReinit(fenetrePrincipale app) {
		application = app;
	}
	public void actionPerformed(ActionEvent e) {
		application.reInit();

	}

}
