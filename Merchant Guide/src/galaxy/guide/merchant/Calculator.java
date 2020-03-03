package galaxy.guide.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {

	Map<String, Character> intergalacticNumeral;
	Map<Character, Integer> numbers;
	Map<String, Double> costOfGoods;
	List<String> response;

	public Calculator() {
		this.numbers = new HashMap<>();
		numbers.put('I',1);
		numbers.put('V',5);
		numbers.put('X',10);
		numbers.put('L',50);
		numbers.put('C',100);
		numbers.put('D',500);
		numbers.put('M',1000);
		this.intergalacticNumeral = new HashMap<String, Character>();
		this.costOfGoods = new HashMap<String, Double>();
		this.response = new ArrayList<String>();
	}

	public void convertToGalaxyMeasures(String symbol, char value) throws NotValidFormatException{
		if(numbers.get(value) == null) {
			throw new NotValidFormatException("Format is not valid, roman value must be one of I, V, X, L, C, D or M");
		}
		this.intergalacticNumeral.put(symbol, value);
	}

	public void convertAmount(String amountToCalculate, String good, String credits) throws NotValidFormatException {
		double amount = getGalaxyAmount(amountToCalculate);
		double costForOne = Integer.valueOf(credits) / amount;
		this.costOfGoods.put(good, costForOne);
	}

	public double getGalaxyAmount(String amountToCalculate) throws NotValidFormatException {
		String[] result = amountToCalculate.split("\\s");
		return getGalaxyAmount(result, 0);
	}

	public double getGalaxyAmount(String[] result, int offset) throws NotValidFormatException {
		String str = "";
		int hi = result.length - offset; 
		for(int i = 0; i < hi; i++) {
			if(intergalacticNumeral.get(result[i]) == null) {
				throw new NotValidFormatException("Are you sure you've told me everyting rigth? I don't know what " + result[i] + " means");
			} else {
				str+=String.valueOf(intergalacticNumeral.get(result[i]));
			}
		}
		return getAmount(str);
	}

	private int getAmount(String s) {
		int res = 0;
		for (int i = 0; i < s.length(); i++){
			if((i + 1) < s.length() && numbers.get(s.charAt(i)) < numbers.get(s.charAt(i + 1))){
				res += numbers.get(s.charAt(i + 1)) - numbers.get(s.charAt(i)); 
				i++;
			} else {
				res += numbers.get(s.charAt(i));         
			}
		}
		return res;
	}

	public void calculateTotalPrice(String query) throws NotValidFormatException {
		String[] result = query.split("\\s");
			this.response.add(query + " is " + String.valueOf(getGalaxyAmount(result, 0)));
	}

	public void calculateTotalPriceOfGood(String query) throws NotValidFormatException {
		String[] result = query.split("\\s");
		int hi = result.length - 1;
		if(costOfGoods.containsKey(result[hi])) {
			double totalPrice = getGalaxyAmount(result, 1) * costOfGoods.get(result[hi]);
			this.response.add(query + " is " + totalPrice + " Credits");
		}
	}

}
