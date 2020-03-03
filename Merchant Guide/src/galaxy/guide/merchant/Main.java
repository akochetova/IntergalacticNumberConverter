package galaxy.guide.merchant;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.xml.crypto.Data;

public class Main {

	public static void main(String[] args) throws NotValidFormatException {
		System.out.println("Enter please the file name to convert");
		String fileName = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name;
		Scanner reader = null;
		try {
			String path = System.getProperty("user.dir");
			String separator = System.getProperty("file.separator");
			name = br.readLine();
			if(name != null) {
				fileName = name;
			} else {
				System.out.println("I didn't get that, sorry");
			}
			File file = new File(path + separator + fileName);
			reader = new Scanner(file);
			Calculator calc = new Calculator();
			
			final String REGEX_ONE = ".+\\smeans\\s\\w+";
			final String REGEX_TWO = ".+units\\sof\\s\\w+\\sare\\sworth\\s.+";
			final String REGEX_THREE = "how\\sm[u,a][c,n][h,y](\\sCredits)?\\sis\\s.+";

			String delimiterOne =  "\\smeans\\s"; 
			Pattern patternOne = Pattern.compile(delimiterOne, 
					Pattern.CASE_INSENSITIVE); 
			String delimiterTwo =  "\\sunits\\sof\\s"; 
			Pattern patternTwo = Pattern.compile(delimiterTwo, 
					Pattern.CASE_INSENSITIVE); 
			String delimiterThree =  "\\sare\\sworth\\s"; 
			Pattern patternThree = Pattern.compile(delimiterThree, 
					Pattern.CASE_INSENSITIVE); 
			String delimiterFour =  "\\scredits"; 
			Pattern patternFour = Pattern.compile(delimiterFour, 
					Pattern.CASE_INSENSITIVE); 
			String delimiterFive =  "how\\smuch\\sis\\s"; 
			Pattern patternFive = Pattern.compile(delimiterFive, 
					Pattern.CASE_INSENSITIVE); 
			String delimiterSix =  "how\\smany\\scredits\\sis\\s"; 
			Pattern patternSix = Pattern.compile(delimiterSix, 
					Pattern.CASE_INSENSITIVE); 
			
			boolean flag = true;
			String data = null;
			while (reader.hasNextLine() && flag) {
				data = reader.nextLine();
// #######################  FIRST REGEX TO CONVERT INTERGALACTIC NUMERAL TO ROMAN ##################
				while(data.matches(REGEX_ONE)) {
					String[] result = patternOne.split(data); 
					calc.convertToGalaxyMeasures(result[0], result[1].charAt(0));
					data = reader.nextLine();
				}
// #######################  SECOND REGEX TO GET PRICE OF GOODS  #################################
				while (data.matches(REGEX_TWO)) {
					String[] result = patternTwo.split(data);
					String[] resultTwo = patternThree.split(result[1]);
					String[] resultThree = patternFour.split(resultTwo[1]); 
					calc.convertAmount(result[0], resultTwo[0], resultThree[0]);
					data = reader.nextLine();
				}
// #######################  THIRD REGEX TO ELABORATE QUERIES  #################################				
				while (data.matches(REGEX_THREE)) {
					String[] result = patternFive.split(data);
					if(result.length == 1) {
						result = patternSix.split(data);
						if(result.length > 1) {
							String str = result[1].split("\\?")[0].trim();
							calc.calculateTotalPriceOfGood(str);
						} else {
							throw new NotValidFormatException("Hmm, I didn't get that, are you sure your query is correct?");
						}
					} else if(result.length > 1) {
						String str = result[1].split("\\?")[0].trim();
						calc.calculateTotalPrice(str);
					}   
					data = reader.nextLine();
				}
				flag = false;
			}
			while(data != null) {
				calc.response.add("I have no idea what you are talking about");
				if(reader.hasNext()) {
					data = reader.nextLine();
				} else {
					data = null;
				}
			}	
//	###########################  OUTPUT  ##########################################
			System.out.println("------------------------------------------------");
			for(String output : calc.response) {
				System.out.println(output);
			}
			
		} catch (Exception e) {
			System.out.println("Oops! " + e.getMessage());
		} finally {
			reader.close();
		}
	}
}
