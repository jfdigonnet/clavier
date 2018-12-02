package prog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import action.actionQuitter;
import action.actionReinit;
import prog.LectureFichier;
import utilitaires.MonSwingWorker;
import utilitaires.constantes;
import metier.Seance;

public class fenetrePrincipale extends JFrame implements ActionListener, KeyListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Eléments d'interface
	private JLabel labelBas;
	private JLabel labelClavier;
	private JLabel labelLettre;
	private String ListeLettre[];
	private JButton boutonQuitter;
	private int ligneencours = 0;
	private int caractereencours = 0;
	private String lettreencours;
	private int nbligne;
	JTextField typingArea;
	
	private JTextArea editF;
	private JButton boutonJouer;
	// Utilitaires
	Timer timer;          // Timer affichant le mot suivant
	// les variable de la séance de formation qui s'ouvre
	private Seance seance;
	   
	public fenetrePrincipale() {
		 seance = new Seance(this);
		 try {
			 creeInterface();
			 this.addKeyListener(this);
		 } catch (Exception e) {
			 e.printStackTrace();
			 JOptionPane.showMessageDialog(this,
					 "Erreur de construction de l'interface\n" + e.getLocalizedMessage(), constantes.titreAppli, JOptionPane.ERROR_MESSAGE);
		 }
		 ajouteIcone();
		 chargementFichierLettre();
		 loadParam();
		 affichelettre();
	}
//	public void ajouteActionQuitter() {
//		this.addWindowListener(new WindowAdapter(){
//            public void windowClosing(WindowEvent e){
//                  int reponse = JOptionPane.showConfirmDialog(null,
//                                       "Voulez-vous quitter l'application",
//                                       "Confirmation",
//                                       JOptionPane.YES_NO_OPTION,
//                                       JOptionPane.QUESTION_MESSAGE);
//                  if (reponse==JOptionPane.YES_OPTION){
//                          this.dispose();
//                  }
//            }
//   });
//	}
	/**
	 * 
	 */
	public void loadParam() {
        Preferences prefsRoot = Preferences.userRoot();
        Preferences myPrefs = prefsRoot.node("traduc.preference");

       setLigneencours( myPrefs.getInt("ligneencours", 0) );
       setCaractereencours( myPrefs.getInt("caractereencours", 0) );
       System.out.println(getLigneencours());
       System.out.println(getCaractereencours());
    }
	/**
	 * 
	 * @param pos
	 */
	public void sauvePosLecture() {
            Preferences prefsRoot = Preferences.userRoot();
            Preferences myPrefs = prefsRoot.node("traduc.preference");
            myPrefs.putInt("ligneencours",  getLigneencours());
            myPrefs.putInt("caractereencours",  getCaractereencours() );
            System.out.println(getCaractereencours());
    }
	public void reInit() {
        Preferences prefsRoot = Preferences.userRoot();
        Preferences myPrefs = prefsRoot.node("traduc.preference");
        myPrefs.putInt("ligneencours",  0);
        myPrefs.putInt("caractereencours",  0 );
        setCaractereencours(0);
        setLigneencours(0);        
        System.out.println(getCaractereencours());
        affichelettre();
	}
	private void ajouteIcone() {
        Image imageApp = null;
		try {
			imageApp = ImageIO.read(this.getClass().getResource("ressources/iconeappli.png"));
		} catch (IOException e) {
			// System.out.println("Erreur de lecture de l'icone : " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		 this.setIconImage(imageApp);
	 }
	/**
	 * 
	 */
	private void affichelettre() {
		 // cast char to string
		int lg = ListeLettre[ligneencours].length();
		System.out.println("Longueur de la ligne : " + lg);
		caractereencours++;

//		System.out.println("Caractère en cours : " + getCaractereencours());
//		
//		System.out.println("Soit position dans la ligne : " + getCaractereencours());
		// Si fin de ligne
	  	if (caractereencours == lg ) {
//	  		System.out.println("Fin de ligne atteinte");
	  		ligneencours++;
//	  		System.out.println("Ligne en cours : " + getLigneencours());
//	  		System.out.println("Soit no de ligne : " + getLigneencours() + 1);
	  		caractereencours = 0;
	  	}
	  	// Gestion de la fin de fichier à venir
	  	if (getLigneencours() == getNbligne()) {
	  		joueFichier("termine");
	  		return;
	  	} else {
	  		joueFichier("NOUVELLE");
//	  		String lettre = "" + ListeLettre[ligneencours].charAt(caractereencours - 1);
	  		String lettreA = String.valueOf(ListeLettre[ligneencours].charAt(caractereencours - 1));
		 
	  		if (lettreA.equals(" ")) {
	  			labelLettre.setText( "e" );
		  		lettreencours = " ";
		  		joueFichier("ESPACE");
	  		} else {
	  			labelLettre.setText( lettreA );
		  		lettreencours = lettreA.toUpperCase();
		  		joueFichier(lettreA);
	  		}
	  		
	  		
		}
	  	placeCurseur();
	}
	/**
	 * 
	 */
	private void chargementFichierLettre() {
        LectureFichier litfichier = new LectureFichier();   
        litfichier.litFichier("fichiers/exo/lecon1.txt");
        String ListeC[] = litfichier.getT();
        int nbligne =0;
        for (int i=0; i<ListeC.length; i++) {
            String ligne = ListeC[i];
            if( ligne != null) {
                if (ligne.length() > 0) {
                	nbligne++;
                }
//                    for (int l=0; l<ligne.length(); l++) {
//                        char lettre = ligne.charAt(l);
//                        System.out.println(lettre);
//                    }
//                }
            }
        }
        setNbligne(nbligne);
        setListeLettre(ListeC);
    }

	/**
	 * @throws Exception 
	 * 
	 */
	private void creeInterface() throws Exception {
		Font police = new Font(Font.SANS_SERIF , Font.TRUETYPE_FONT, 14);
		this.setFont(police);

        JPanel panelPrinc = new JPanel();
        panelPrinc.setBorder(new EmptyBorder(5, 5, 5, 5));
        //panelPrinc.setPreferredSize(new Dimension(600,200));
        panelPrinc.setLayout(new BorderLayout());
        getContentPane().add(panelPrinc);

        JPanel panelHaut = new JPanel(new FlowLayout());
        
        JPanel panelBas = new JPanel(new FlowLayout());
        Border myRaisedBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED , new Color( 133, 156, 221 ),
            new Color( 133, 156, 221 ));
        panelBas.setBorder(myRaisedBorder);
        
        JPanel panelLettre = new JPanel();
        //panelLettre.setPreferredSize(new Dimension(600,400));
        panelLettre.setBorder(myRaisedBorder);
        panelLettre.addKeyListener(this);
        
        JPanel panelClavier = new JPanel();
        panelClavier.setPreferredSize(new Dimension(720,270));
        panelClavier.setBorder(myRaisedBorder);
        
		JPanel panelLigneInfo = new JPanel();
		
		setTitle(constantes.titreAppli);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        labelLettre = new JLabel("Lettre");
        labelLettre.setHorizontalAlignment(SwingConstants.CENTER);
        labelLettre.setFont(new Font("Tahoma", Font.BOLD, 150));
        labelLettre.setPreferredSize(new Dimension(100,255));
        labelLettre.addKeyListener(this);
        //labelLettre.setBounds(10, 10, 150, 20);
    	panelLettre.add(labelLettre);

        //JLabel label1 = new JLabel("Saisie");
        //label1.setPreferredSize(new Dimension(50,50));
        //label1.setBounds(10, 60, 150, 20);
        //panelBas.add(label1); //, BorderLayout.WEST);

        typingArea = new JTextField();
        typingArea.setPreferredSize(new Dimension(100,50));
        typingArea.addKeyListener(this);
        typingArea.setFont(new Font("Arial", Font.PLAIN, 25));
        typingArea.addKeyListener(this);
		//panelBas.add(typingArea); //, BorderLayout.CENTER);
        
		labelClavier = new JLabel("Clavier");
        labelClavier.setPreferredSize( new Dimension(700,260));
        labelClavier.setIcon(new ImageIcon("fichiers/clavier.png"));
        labelClavier.addKeyListener(this);
        panelClavier.add(labelClavier);

		labelBas = new JLabel();
		labelBas.setText(constantes.titreAppli);
		Border myRaisedBorder2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED , new Color( 133, 156, 221 ),
				new Color( 133, 156, 221 ));
		labelBas.setBorder(myRaisedBorder2);
		panelLigneInfo.add(labelBas);
        //panelBas.add(panelLigneInfo); //, BorderLayout.SOUTH);
		
        boutonQuitter = new JButton("Quitter");
        boutonQuitter.setPreferredSize(new Dimension(120,30));
        boutonQuitter.addActionListener(new actionQuitter(this, seance));
        boutonQuitter.addKeyListener(this);
        panelBas.add(boutonQuitter); //, BorderLayout.EAST);
        
        JButton boutonRecommencer = new JButton("Réinitialiser");
        boutonRecommencer.addKeyListener(this);
        boutonRecommencer.setPreferredSize(new Dimension(140,30));
        boutonRecommencer.addActionListener(new actionReinit(this));
        panelBas.add(boutonRecommencer); //, BorderLayout.EAST);

        panelHaut.add(panelLettre);
		panelHaut.add(panelClavier);
		
		panelPrinc.add(panelHaut, BorderLayout.NORTH);
		panelPrinc.add(panelBas, BorderLayout.SOUTH);

		//setSize(600,500);
		setLocationRelativeTo(null);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	/**********************************************************
	 * les actions effectuées
	 **********************************************************/
	public void actionPerformed(ActionEvent e) {
		/***********************************************************
		 * Afficher le mot suivant
		 ***********************************************************/
		if (e.getActionCommand().equals("suivant")) {
		}				
		/***********************************************************
		 * Afficher le mot prédédent
		 ***********************************************************/
		if (e.getActionCommand().equals("precedent")) {
		}				
	}
	/**
	 * 
	 */
	class MonAction extends TimerTask {
		public void run() {
			timer.cancel();
		}
	}
	public void keyTyped(KeyEvent e) {
//		System.out.println("keyTyped");
	}
	private void joueFichier(String leFichier) {
		String nomFichier = "fichiers/lettres/" + leFichier.toUpperCase() + ".mp3";
		System.out.println(nomFichier);
		File fichier = new File(nomFichier);
		if (! fichier.exists()) {
			nomFichier = "fichiers/lettres/NONTROUVE.mp3" ;
			System.out.println(fichier.toString() + " non trouvé");
		}
		try {
			 new MonSwingWorker(nomFichier).execute();
		} catch (Exception e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		}
		attendre();
	}
	private String deterNomFichier(int code) {
		String nomfichier;
		switch (code) {
        case 18:  nomfichier = "alt";
                break;
        case 17:  nomfichier = "ctrl";
        		break;
        case 44:  nomfichier = "interrogation";
        	break;
        case 153:  nomfichier = "inferieur";
        	break;
        case 59:  nomfichier = "pointvirgule";
        		break;
        case 513:  nomfichier = "deuxpoints";
        		break;		
        case 517:  nomfichier = "exclamation";
        		break;		
        case 16:  nomfichier = "maj";
        		break;		
        		
        case 16777465:  nomfichier = "pourcent";
		break;		
        case 151:  nomfichier = "etoile";
		break;		
        case 130:  nomfichier = "chapeau";
		break;		
        case 515:  nomfichier = "dollar";
		break;		
        case 16777394:  nomfichier = "petitdeux";
		break;		
        case 150:  nomfichier = "eperluette";
		break;		
        case 16777449:  nomfichier = "eaigu";
		break;		
        case 152:  nomfichier = "doublequote";
		break;		
        case 222:  nomfichier = "quotesimple";
		break;		
        case 519:  nomfichier = "parentheseouverte";
		break;		

        case 45:  nomfichier = "tiretmilieu";
		break;		
        case 16777448:  nomfichier = "egrave";
		break;		
        case 523:  nomfichier = "tiretbas";
		break;		
        case 16777415:  nomfichier = "ccedille";
		break;		
        case 16777440:  nomfichier = "agrave";
		break;		
        case 522:  nomfichier = "parentheseferme";
		break;		

        case 61:  nomfichier = "egale";
		break;		
        case 8:  nomfichier = "retourarriere";
		break;		
        case 10:  nomfichier = "entree";
		break;		
        case 65406:  nomfichier = "altgr";
		break;		
        case 32:  nomfichier = "espace";
		break;		
        default: nomfichier = "inconnu";
    	break;
		}
		return nomfichier;
	}
	/**
	 * 
	 */
	public void keyPressed(KeyEvent event) {
		int code = event.getExtendedKeyCode();
		//System.out.println("Lettre " + event.getExtendedKeyCode());
		// Caractères possiblement ok
		if (((code >= 65) && (code <= 90)) || (code == 32) ) {
			
			int codeattendu = 0;
			if (lettreencours.equals(" ") ) {
				codeattendu = 32;
			} else {
				codeattendu = (int)lettreencours.charAt(0);
			}
			System.out.println("Code attendu : " + codeattendu);
			
			String lettresaisie = "" ;
			if (code == 32) {
				lettresaisie = "espace";
			} else {
				lettresaisie = "" + event.getKeyChar();
				System.out.println(lettresaisie);
			}
			joueFichier(lettresaisie);
			
			if ( codeattendu != code ) {
				joueFichier("ERREUR");
			} else {
				joueFichier("BRAVO");
				affichelettre();
			}
		} else {
			joueFichier("ERREUR");
			
			String nomFichier = deterNomFichier(code);
			joueFichier(nomFichier);
		}
		placeCurseur();
	}
	private void attendre() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void placeCurseur() {
		setFocusable(true); // on le rend focusable
//	  	typingArea.setText("");
//	  	typingArea.setFocusable(true);
//	  	typingArea.repaint();
	}
	public void keyReleased(KeyEvent e) {
//		System.out.println("keyReleased");
	}
	public JTextArea getEditF() {
		return editF ;
	}
	public JButton getBoutonJouer() {
		return boutonJouer;
	}
    /**
     * Accesseur de listeLettre
     *
     * @return listeLettre
     */
    public String[] getListeLettre()
    {
        return ListeLettre;
    }
    /**
     * Mutateur de listeLettre
     *
     * @param listeLettre listeLettre
     */
    public void setListeLettre(String[] listeLettre)
    {
        ListeLettre = listeLettre;
    }
	public int getNbligne() {
		return nbligne;
	}
	public void setNbligne(int nbligne) {
		this.nbligne = nbligne;
	}
	public int getLigneencours() {
		return ligneencours;
	}
	public void setLigneencours(int ligneencours) {
		this.ligneencours = ligneencours;
	}
	public Integer getCaractereencours() {
		return caractereencours;
	}
	public void setCaractereencours(int caractereencours) {
		this.caractereencours = caractereencours;
	}
	public void windowActivated(WindowEvent e) {
	}
	public void windowClosed(WindowEvent e) {
		//System.out.println( e.toString());
		sauvePosLecture();
	}
	public void windowClosing(WindowEvent e) {
		System.out.println("On sort");
		sauvePosLecture();	
	}
	public void windowDeactivated(WindowEvent e) {
		System.out.println("windowDeactivated");
	}
	public void windowDeiconified(WindowEvent e) {
		System.out.println("windowDeiconified");
	}
	public void windowIconified(WindowEvent e) {
		System.out.println("windowIconified");
	}
	public void windowOpened(WindowEvent e) {
		System.out.println("windowOpened");
	}
}
