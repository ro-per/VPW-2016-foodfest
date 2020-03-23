import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		// VARIABELEN___________________________________________________________
		// GLOBALE VARIABELEN
		int n; // aantal testgevallen (1<=n<=1000)
		int b; // aantal budgetten per n (2<=b<=5)
		int f; // aantal foodtrucks per n (2<=f<=10)
		int r; // aantal prijzen per f (1<=r<=20) stijgende volgorde

		List<Integer> budgetten; // lijst van budgetten
		int[] prijsLijst; // lijst gebruikt voor prijzen per foodtruck
		List<int[]> prijzen; // matrix van alle prijzen = lijst van 'prijsLijst'

		// TIJDELIJKE VARIABELEN
		String[] invoer;

		// BEGIN_PROGRAMMA__________________________________________________________________
		Foodfest FF;
		n = Integer.parseInt(sc.nextLine());
		// OVERLOOP AANTAL TESTGEVALLEN
		for (int x = 0; x < n; x++) {
			FF = new Foodfest();
			budgetten = new ArrayList<>();
			prijzen = new ArrayList<>();
			// INLEZEN BUDGETTEN
			invoer = sc.nextLine().split(" ");
			b = Integer.parseInt(invoer[0]);
			budgetten = new ArrayList<>();
			prijzen = new ArrayList<>();
			for (int y1 = 1; y1 < b + 1; y1++) {// inlezen van budgetten in lijst
				budgetten.add(Integer.parseInt(invoer[y1]));
			}
			// WEGSCHRIJVEN BUDGETTEN
			FF.setBudgetten(budgetten);

			// INLEZEN PRIJZEN FOODTRUCKS
			f = Integer.parseInt(sc.nextLine());
			for (int y2 = 0; y2 < f; y2++) {
				invoer = sc.nextLine().split(" ");
				r = Integer.parseInt(invoer[0]);
				prijsLijst = new int[r];
				for (int z = 0; z < r; z++) { // vul de prijsLijst in
					prijsLijst[z] = Integer.parseInt(invoer[z + 1]);
				}
				// WEGSCHRIJVEN PRIJZEN FOODTRUCKS
				prijzen.add(prijsLijst);
			}

			FF.setPrijzen(prijzen);
			FF.vindOplossing();
			FF.printoplossingen(x + 1);
		}
		sc.close();
	}
}

class Foodfest {
	private List<Integer> budgetten;
	private List<Integer> oplossingen;
	private List<int[]> prijzen;
	private int aantalF;

	public Foodfest() {
		budgetten = new ArrayList<>();
		oplossingen = new ArrayList<>();
	}

	public void setPrijzen(List<int[]> prijzen) {
		this.prijzen = prijzen;
		aantalF = prijzen.size();
	}

	public void setBudgetten(List<Integer> budgetten) {
		this.budgetten = budgetten;
	}

	public void vindOplossing() {
		for (int budget : budgetten) {// OVERLOOP ALLES BUDGETTEN
			if (juistBudget(0, budget)) { // ROEP METHODE OP OM TE CHECKEN
				oplossingen.add(budget); // VOEG CORRECT BUDGET TOE
			}
		}
	}

	boolean juistBudget(int fNr, int budget) {
		int l, r;
		l = prijzen.get(fNr).length; // =aantal prijzen in fNr

		if (budget < 0) { // indien budget negatief, moet er niet verder gezocht worden
			return false;
		}

		if (fNr < aantalF - 1) { // ZOLANG JE NIET IN DE LAATSTE TRUCK KIJKT
			// OVERLOOP PRIJZEN IN fNr, en roep methode terug op
			for (int i = 0; i < l; i++) {
				r = prijzen.get(fNr)[i]; // i-de prijs van foodtruck fNr
				if (juistBudget(fNr + 1, budget - r)) { // fNr+1 -> volgende truck
					return true;
				}
			}
		}
		if (fNr == aantalF - 1) { // JE KIJKT NAAR DE LAATSTE TRUCK
			for (int i = 0; i < l; i++) {
				r = prijzen.get(fNr)[i]; // i-de prijs van foodtruck fNr
				if (budget - r == 0) { // fNr+1 -> volgende truck
					return true;
				}
			}
		}
		return false;
	}

	public void printoplossingen(int i) {
		StringBuilder strBldr = new StringBuilder(Integer.toString(i));
		if (!oplossingen.isEmpty()) {
			for (Integer oplossing : oplossingen) {
				strBldr.append(" ").append(oplossing);
			}
			System.out.println(strBldr);
		} else
			System.out.println(i + " GEEN");
	}

}
