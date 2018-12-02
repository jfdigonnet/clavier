package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import utilitaires.AudioFilePlayer;

public class actionJouer implements ActionListener {

	private String fichier = "";
	
    class MonSwingWorker extends SwingWorker<Integer, String> {

		protected Integer doInBackground() throws Exception {
	        final AudioFilePlayer player = new AudioFilePlayer ();
	    	player.play(fichier);
			return 0;
		}
    }
	public void actionPerformed(String fichier) {
       	this.fichier = fichier;
    	new MonSwingWorker().execute();
	}
    @Override
    public void actionPerformed(ActionEvent e)
    {
            // TODO Raccord de méthode auto-généré
    }
}
