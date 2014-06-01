import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Glavni {
	
	public static void main(String[] args) throws IOException {
		String niz = "atcgcgct$";
		
	    long t0 = System.currentTimeMillis();
	    SufiksnoPolje<Character> SA = SAIS.stvoriSufiksnoPolje(niz);
		long t1 = System.currentTimeMillis();
		
		System.out.println("Vrijeme: " + (t1-t0)+" ms");
		
		boolean sort = true;
		for(int i = 0; i < SA.dohvatiBrojElemenata()-1; i++) {
			if(niz.substring(SA.dohvatiElement(i)).compareTo(niz.substring(SA.dohvatiElement(i+1))) > 0) {
				sort = false;
				break;
			}
		}
		System.out.println("Sortirano: " + sort);
		
		System.out.println("\nSufiksi:\n");
		for(int i = 0; i < SA.dohvatiBrojElemenata(); i++) {
			System.out.println(niz.substring(SA.dohvatiElement(i)));
		}
	}
}