package com.konloch.jackie;


import java.util.HashMap;
import java.util.Map;

/**
 * Based off https://groups.google.com/g/comp.ai.philosophy/c/Rv5GPrqZId4
 *
 * @author Konloch
 * @since 7/7/2024
 */
public class Jackie
{
	private final Map<String, String> stimulusResponseMap = new HashMap<>();
	private final Map<String, String> phoneticMap = new HashMap<>();
	
	public void train(String stimulus, String response)
	{
		stimulusResponseMap.put(stimulus, response);
		String phoneticKey = soundex(stimulus);
		phoneticMap.put(phoneticKey, response);
	}
	
	public String respond(String stimulus)
	{
		if (stimulusResponseMap.containsKey(stimulus))
			return stimulusResponseMap.get(stimulus);
		
		String phoneticKey = soundex(stimulus);
		return phoneticMap.getOrDefault(phoneticKey, "I don't understand.");
	}
	
	private String soundex(String s)
	{
		char[] x = s.toUpperCase().toCharArray();
		char firstLetter = x[0];
		
		//convert letters to numeric code
		for (int i = 1; i < x.length; i++)
		{
			switch (x[i])
			{
				case 'B': case 'F': case 'P': case 'V': x[i] = '1'; break;
				case 'C': case 'G': case 'J': case 'K': case 'Q': case 'S': case 'X': case 'Z': x[i] = '2'; break;
				case 'D': case 'T': x[i] = '3'; break;
				case 'L': x[i] = '4'; break;
				case 'M': case 'N': x[i] = '5'; break;
				case 'R': x[i] = '6'; break;
				default: x[i] = '0'; break;
			}
		}
		
		StringBuilder output = new StringBuilder(String.valueOf(firstLetter));
		for (int i = 1; i < x.length; i++)
		{
			if (x[i] != x[i - 1] && x[i] != '0')
				output.append(x[i]);
		}
		
		output.append("0000");
		return output.substring(0, 4);
	}
	
	public static void main(String[] args)
	{
		Jackie jackie = new Jackie();
		jackie.train("What is your name?", "Jackie");
		jackie.train("What is your favorite color?", "Red");
		
		System.out.println(jackie.respond("What is your name?"));
		System.out.println(jackie.respond("What is your favorite color?"));
		System.out.println(jackie.respond("Do you like movies?"));
	}
}