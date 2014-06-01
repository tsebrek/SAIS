import java.util.ArrayList;
import java.util.List;

/**
 * Generalna klasa za kreiranje sufiksnog polja.
 * @author Tomislav Šebrek
 *
 */

public class SAIS {

	/**
	 * Statička metoda za kreiranje sufiksnog polja. Prima niz (uključujući stožer) kojeg treba sortirati.
	 * Korisnik je dužan na kraj dodati znak koji je leksikografski manji od svih ostalih u nizu.
	 * @param niz niz za kojeg treba izgraditi sufiksno polje
	 * @return traženo sufiksno polje
	 */
	
	public static SufiksnoPolje<Character> stvoriSufiksnoPolje(String niz) {
		List<Character> lista = new ArrayList<>();
		for(int i = 0; i < niz.length(); i++) {
			lista.add(niz.charAt(i));
		}
		
		SAISAlgoritam alg = new SAISAlgoritam(lista);
		SufiksnoPolje<Character> SA = alg.provediAlgoritam();
		return SA;
	}
}
