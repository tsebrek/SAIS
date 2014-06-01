import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa koja provodi algoritam SAIS
 * @author Tomislav Šebrek
 *
 * @param <T> generički tip podataka koji se može sortirati koja
 * mora implementirati sučelje Comparable.
 */

public class SAISAlgoritam<T extends Comparable<T>> {
	private List<T> ulaz;
	
	/**
	 * Konstruktor klase. Prima listu za koju treba izgraditi polje.
	 * @param ulaz tražena lista
	 */
	
	public SAISAlgoritam(List<T> ulaz) {
		this.ulaz = new ArrayList<>(ulaz);
	}
	
	
	/**
	 * Metoda koja vraća sufiksno polje. Algoritam provodi ovako:
	 * SA_IS(S) {
			odredi tipove znakova u nizu
			pronađi sve LMS znakove i smjesti ih u polje P
			induciranim sortom sortiraj LMS podnizove
			imenuj svaki LMS podniz i rezultat zapiši i S1
			ako je( svi znakovi S1 su različiti ) {
				direktno izračunaj SA1 iz S
			}
			inače {
				SA1 = SA_IS(S1)
			}
			induciraj SA iz SA1
			vrati SA 
		}

	 * @return traženo sufiksno polje.
	 */
	
	public SufiksnoPolje<T> provediAlgoritam() {
		UlazniNiz<T> niz = new UlazniNiz<>(ulaz);
		niz.odrediVrsteZnakova();
		niz.odrediLMSZnakove();
		
		SufiksnoPolje<T> SA = new SufiksnoPolje<>(ulaz);
		SA.inicijalizirajPolje();
		SA.postaviPokazivaceNaKraj();
		
		// 1.KORAK ALGORITMA
		int n = niz.dohvatiBrojLMSova();
		for(int i = 0; i < n; i++) {
			int pozicija = niz.dohvatiLMS(i);
			T znak = niz.dohvatiZnak(pozicija);
			SA.dodajElement(znak, pozicija);
		}
		
		// 2.KORAK ALGORITMA
		SA.postaviPokazivaceNaPocetak();
		n = niz.dobvatiBrojZnakova();
		for(int i = 0; i < n; i++) {
			int element = SA.dohvatiElement(i);
			if(element == -1 || element == 0) {
				continue;
			}
			
			int pozicija = element - 1;
			if(niz.dohvatiVrstuZnaka(pozicija) == SAISKonstante.S_TIP) {
				continue;
			}
			
			T znak = niz.dohvatiZnak(pozicija);
			SA.dodajElement(znak, pozicija);
		}
		
		// 3.KORAK ALGORITMA
		SA.postaviPokazivaceNaKraj();
		for(int i = n-1; i >= 0; i--) {
			int element = SA.dohvatiElement(i);
			if(element == -1 || element == 0) {
				continue;
			}
			
			int pozicija = element - 1;
			if(niz.dohvatiVrstuZnaka(pozicija) == SAISKonstante.L_TIP) {
				continue;
			}
			
			T znak = niz.dohvatiZnak(pozicija);
			SA.dodajElement(znak, pozicija);
		}
		
		// IMENOVANJE LMS-OVA
		Map<Integer,Integer> mapaImena = new HashMap<>();
		int zadnjiLMS = -1;
		int zadnjeIme = -1;
		boolean imaIdenticnih = false;
		
		for(int i = 0; i < n; i++) {
			int pozicija = SA.dohvatiElement(i);
			if(niz.pozicijaJeLMS(pozicija)) {
				if(mapaImena.size() == 0) {
					mapaImena.put(pozicija, 0);
					zadnjiLMS = pozicija;
					zadnjeIme = 0;
				}
				else {
					if(niz.provjeriIdenticnostLMSova(pozicija, zadnjiLMS)) {
						zadnjiLMS = pozicija;
						imaIdenticnih = true;
					}
					else {
						zadnjiLMS = pozicija;
						zadnjeIme++;
					}
					mapaImena.put(zadnjiLMS, zadnjeIme);
				}
			}
		}
		
		if(!imaIdenticnih) {
			return SA;
		}
		
		List<Integer> S1 = new ArrayList<>();
		int s = niz.dohvatiBrojLMSova();
		for(int i = 0; i < s; i++) {
			int LMS = niz.dohvatiLMS(i);
			S1.add(mapaImena.get(LMS));
		}
		
		SAISAlgoritam<Integer> rekurziviPoziv = new SAISAlgoritam<>(S1);
		SufiksnoPolje<Integer> SA1 = rekurziviPoziv.provediAlgoritam();
			
		// INDUCIRANI SORT POMOCU POMOCNOG SUFIKSNOG POLJA
		
		SA.inicijalizirajPolje();
		SA.postaviPokazivaceNaKraj();
		s = SA1.dohvatiBrojElemenata();
		
		for(int i = s-1; i >= 0; i--) {
			int el = SA1.dohvatiElement(i);
			int poz = niz.dohvatiLMS(el);
			T znak = niz.dohvatiZnak(poz);
			SA.dodajElement(znak, poz);
		}
		

		// 2.KORAK ALGORITMA
		SA.postaviPokazivaceNaPocetak();
		n = niz.dobvatiBrojZnakova();
		for(int i = 0; i < n; i++) {
			int element = SA.dohvatiElement(i);
			if(element == -1 || element == 0) {
				continue;
			}
			
			int pozicija = element - 1;
			if(niz.dohvatiVrstuZnaka(pozicija) == SAISKonstante.S_TIP) {
				continue;
			}
			
			T znak = niz.dohvatiZnak(pozicija);
			SA.dodajElement(znak, pozicija);
		}
		
		// 3.KORAK ALGORITMA
		SA.postaviPokazivaceNaKraj();
		for(int i = n-1; i >= 0; i--) {
			int element = SA.dohvatiElement(i);
			if(element == -1 || element == 0) {
				continue;
			}
			
			int pozicija = element - 1;
			if(niz.dohvatiVrstuZnaka(pozicija) == SAISKonstante.L_TIP) {
				continue;
			}
			
			T znak = niz.dohvatiZnak(pozicija);
			SA.dodajElement(znak, pozicija);
		}
		return SA;
	}
}
