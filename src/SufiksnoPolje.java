import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Klasa koja predstavlja sufiksno polje.
 * @author Tomislav Šebrek
 *
 * @param <T>
 */

public class SufiksnoPolje<T extends Comparable<T>> {
	private int SA[];
	private int n;
	private int brojElemenata;
	private boolean pomisanjeUdesno;
	
	private List<T> niz;
	
	private Map<T, Integer> mapaPretinaca;
	private List<T> listaElemenata;
	private Map<T, Integer> pokazivaci;
	
	/**
	 * Konstruktor klase. Prima niz nad kojim se treba kreirati sufiksno polje.
	 * @param niz niz nad kojim se polje treba kreirati.
	 */
	
	public SufiksnoPolje(List<T> niz) {
		this.niz = new ArrayList<>(niz);
		inicijalizirajElemente();
		inicijalizirajPolje();
		odrediRaspodijeluPoPretincima();
	}

	/**
	 * Metoda koja sve elemente polja postavlja na -1.
	 */
	
	public void inicijalizirajPolje() {
		for(int i = 0; i < n; i++) {
			this.SA[i] = -1;
		}
	}
	
	/**
	 * Metoda postavlja pokazivače na početak svakog pretinca.
	 */
	
	public void postaviPokazivaceNaPocetak() {
		int pozicija = 0;
		for(T element : listaElemenata) {
			pokazivaci.put(element, pozicija);
			pozicija += mapaPretinaca.get(element);
		}
		pomisanjeUdesno = true;
	}
	
	/**
	 * Metoda postavlja pokazivače na kraj svakog pretinca.
	 */
	
	public void postaviPokazivaceNaKraj() {
		int pozicija = n-1;
		for(int i = brojElemenata -1; i >= 0; i--) {
			T element = listaElemenata.get(i);
			pokazivaci.put(element, pozicija);
			pozicija -= mapaPretinaca.get(element);
		}
		pomisanjeUdesno = false;
	}

	/**
	 * Metoda dodaje element u traženi pretinac i pomoče pokazivač.
	 * @param element pretinca u koji se dodaje element
	 * @param vrijednost vrijednost koja se dodaje u pretinac
	 */
	
	public void dodajElement(T element, int vrijednost) {
		int pozicija = pokazivaci.get(element);
		SA[pozicija] = vrijednost;
		
		if(pomisanjeUdesno) {
			pokazivaci.put(element, pozicija+1);
		}
		else {
			pokazivaci.put(element, pozicija-1);
		}
	}
	
	/**
	 * Metoda dohvaća element s tražene pozicije.
	 * @param pozicija tražena pozicija
	 * @return element na poziciji
	 */
	
	public int dohvatiElement(int pozicija) {
		return SA[pozicija];
	}
	
	/**
	 * Metoda dohvaća veličinu sufiksnog polja.
	 * @return veličina sufiksnog polja
	 */
	
	public int dohvatiBrojElemenata(){
		return n;
	}
	
	/**
	 * Za zadani ulazni niz kreira se skup elemenata i mape s pretincima.
	 */
	
	private void odrediRaspodijeluPoPretincima() {
		for(T element : niz) {
			if(mapaPretinaca.containsKey(element)) {
				int broj = mapaPretinaca.get(element);
				mapaPretinaca.put(element, broj+1);
			}
			else {
				mapaPretinaca.put(element, 1);
			}
		}
		
		listaElemenata = new ArrayList<>(mapaPretinaca.keySet());
		brojElemenata = listaElemenata.size();
		Collections.sort(listaElemenata);
	}
	
	/**
	 * Na osnovu ulaznog niza inicijaliziraju se potrebni parametri.
	 */
	
	private void inicijalizirajElemente() {
		this.n = niz.size();
		this.SA = new int[n];
		mapaPretinaca = new HashMap<>();
		pokazivaci = new HashMap<>();
	}
}
