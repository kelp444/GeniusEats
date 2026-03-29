import java.util.ArrayList;
import java.util.Scanner;

public class usersurvey {
	private String diet; // in future: make diet an enum with selectable options
	private ArrayList<String> allergens = new ArrayList<String>();
	private int budget; // only to be 1-3. cannot be any other number. 1: small, 2: medium, 3: large
	
	public usersurvey() {
		diet = "None";
		allergens.clear();
		allergens.add("None");
		budget = 1;
	}
	
	// Nothing should touch these besides saveUserSurvey initially. All of these are reserved for saving everything all at once or going in with editing later on
	
	public void setDiet(String diet) {
		this.diet = diet;
	}
	
	public String getDiet() {
		return diet;
	}
	
	public void setAllergens(ArrayList<String> allergens) {
		this.allergens = allergens;
	}
	
	public ArrayList<String> getAllergens() {
		return allergens;
	}
	
	public void setBudget(int budget) {
		this.budget = budget;
	}

	public int getBudget() {
		return budget;
	}
	
	public void saveUserSurvey() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter what diet you would like. Enter 'none' if you have no preference.");
		String diet = scan.nextLine();
		setDiet(diet);
		System.out.println("Please enter how many allergens you have.");
		int rept = scan.nextInt();
		ArrayList<String> allergyList = new ArrayList<String>();
		for (int i = 0; i < rept; i++) {
			System.out.println("Please enter an allergen. If you would like to back out here, enter 'Done'.");
			String scanVal = scan.nextLine();
			if (scanVal != "Done" && scanVal != "done") {
				allergyList.add(scanVal);
			}
			else {
				i = rept;
			}
		}
		setAllergens(allergyList);
		System.out.println("Enter your budget. 1 for a small budget ($10-30), 2 for a medium budget ($30-60), and 3 for a large budget ($60-100)."); // just an estimate for all 3; change later to real values
		int bugVal = scan.nextInt();
		if (bugVal < 0 || bugVal > 3) {
			System.out.println("Try again. Invalid value.");
			Boolean i = false;
			while (i = false) {
				bugVal = scan.nextInt();
				if (bugVal < 0 || bugVal > 3) {
					System.out.println("Try again. Invalid value.");
				}
				else {
					i = true;
				}
			}
			setBudget(bugVal);
		}
		else {
			setBudget(bugVal);
		}
		
		}
	
}
