import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Metoda predstavlja generički niz za kojeg se gradi sufiksno polje.
 * @author Tomislav Šebrek
 *
 * @param <T>
 */

public class UlazniNiz<T extends Comparable<T>> {
	
	private List<T> niz;
	private int n;
	private SAISKonstante[] t;
	private List<Integer> P;
	private Set<Integer> LMSSet;
	private Map<Integer, Integer> duljineLMS;
	
	/**
	 * Konstruktor klase. Prima generičku listu za koju se gradi polje.
	 * @param niz generička lista
	 */
	
	public UlazniNiz(List<T> niz) {
		this.niz = new ArrayList<>(niz);
		n = niz.size();
		t = new SAISKonstante[n];
		P = new ArrayList<>();
		duljineLMS = new HashMap<>();
		LMSSet = new HashSet<>();
	}
	
	/**
	 * Metoda koja u linearnoj složenosti određuje vrste tipova znakova.
	 */
	
	public void odrediVrsteZnakova() {
		t[n-1] = SAISKonstante.S_TIP;
		
		for(int pozicija = n-2; pozicija >= 0; pozicija--) {
			if(niz.get(pozicija).compareTo(niz.get(pozicija+1)) < 0) {
				t[pozicija] = SAISKonstante.S_TIP;
			}
			else if(niz.get(pozicija).compareTo(niz.get(pozicija+1)) > 0) {
				t[pozicija] = SAISKonstante.L_TIP;
			}
			else {
				t[pozicija] = t[pozicija+1];
			}
		}
	}
	
	/**
	 * Metoda vraća vrstu znaka (L ili S) na nekoj poziciji.
	 * @param pozicija pozicija koju ispitujemo
	 * @return L ili S tip ovisno o ishodu
	 */
	
	public SAISKonstante dohvatiVrstuZnaka(int pozicija) {
		return t[pozicija];
	}
	
	/**
	 * Metoda određuje indekse LMS znakova.
	 */
	
	public void odrediLMSZnakove() {
		for(int i = 1; i < n; i++) {
			if(t[i] == SAISKonstante.S_TIP 
					&& t[i-1] == SAISKonstante.L_TIP) {
				P.add(i);
			}
		}
		
		for(int i = 0; i < dohvatiBrojLMSova()-1; i++) {
			duljineLMS.put(P.get(i), P.get(i+1)-P.get(i));
		}
		duljineLMS.put(P.get(dohvatiBrojLMSova()-1), 0);
		
		LMSSet.addAll(P);
	}
	
	/**
	 * Metoda dohvaća indeks LMS-a listi LMS-ova.
	 * @param pozicija redni broj LMS-a koji se dohvaća
	 * @return indeks u originalnom polju
	 */
	
	public int dohvatiLMS(int pozicija) {
		return P.get(pozicija);
	}
	
	/**
	 * Metoda vraća broj LMS-ova
	 * @return broj LMS-ova
	 */
	
	public int dohvatiBrojLMSova() {
		return P.size();
	}
	
	/**
	 * Metoda dohvaća znak na nekoj poziciji.
	 * @param pozicija tražena pozicija
	 * @return znak na traženoj poziciji
	 */
	
	public T dohvatiZnak(int pozicija) {
		return niz.get(pozicija);
	}
	
	/**
	 * Metoda vraća veličinu ulaznog niza.
	 * @return veličina niza
	 */
	
	public int dobvatiBrojZnakova() {
		return n;
	}
	
	/**
	 * Metoda vraća informaciju nalazi li se na pozicji LMS ili ne.
	 * @param pozicija indeks koji se ispituje
	 * @return true ili false ovisno o ishodu
	 */
	
	public boolean pozicijaJeLMS(int pozicija) {
		return LMSSet.contains(pozicija);
	}
	
	/**
	 * Metoda ispituje podudaranje 2 LMS-podniza.
	 * @param poz1 indeks početka prvog podniza
	 * @param poz2 indeks početka drugog podniza
	 * @return true ili false ovisno o ishodu
	 */
	
	public boolean provjeriIdenticnostLMSova(int poz1, int poz2) {
		int p1 = duljineLMS.get(poz1);
		int p2 = duljineLMS.get(poz2);
		
		if(p1 != p2) {
			return false;
		}
		
		boolean prvaProvjera = true;
		while(true) {
			T el1 = dohvatiZnak(poz1);
			T el2 = dohvatiZnak(poz2);
			
			if(!el1.equals(el2)) {
				return false;
			}
			
			if(prvaProvjera) {
				prvaProvjera = false;
			}
			else {
				if(pozicijaJeLMS(poz1) && pozicijaJeLMS(poz2)) {
					return true;
				}
				if(pozicijaJeLMS(poz1)) {
					return false;
				}
				if(pozicijaJeLMS(poz2)) {
					return false;
				}
			}
			poz1++;
			poz2++;
		}
	}
}