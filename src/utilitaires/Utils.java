package utilitaires;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;

public class Utils
{
    public final static String txt = "txt";
    public final static String csv = "csv";
    public final static String TXT = "TXT";
    public final static String CSV = "CSV";
    public final static String zip = "zip";
    public final static String ZIP = "ZIP";
    public final static String mp3 = "mp3";
    public final static String MP3 = "MP3";
	public final static String ogg = "ogg";
	public final static String OGG = "OGG";
 
    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
 
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
 
    /** Returns an ImageIcon, or null if the path was invalid. */
    public static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    static public boolean deleteDirectory(String repSon) { 
        boolean resultat = true; 
        File rep = new File(repSon);
        if( rep.exists() ) { 
                File[] files = rep.listFiles(); 
                for(int i=0; i<files.length; i++) { 
                        if(files[i].isDirectory()) { 
                                resultat &= deleteDirectory(files[i].getAbsolutePath()); 
                        } 
                        else { 
                        	resultat &= files[i].delete(); 
                        } 
                } 
        } 
        resultat &= rep.delete(); 
        return( resultat ); 
    }
    public static Date calToDate(Calendar cal) {
    	return cal.getTime();
    }
    public static Calendar dateToCal(Date date) {
    	Calendar myCal = Calendar.getInstance();
    	myCal.setTime(date);
    	return myCal;
    }
}
