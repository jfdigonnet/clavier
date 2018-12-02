package prog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LectureFichier {
	
	private String t[] = null;
	 
	public String[] getT() {
		return t;
	}

	public void setT(String[] t) {
		this.t = t;
	}

	public void litFichier(String fichier) {
		String tableauCaractere[] = new String[100];
		try{
			InputStream flux=new FileInputStream(fichier); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne = "";
			int i = 0;
			while ((ligne=buff.readLine())!=null){
				System.out.println(ligne);
				tableauCaractere[i] = ligne;
				i++;
			}
			buff.close(); 
			setT(tableauCaractere);
		}	
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
}
