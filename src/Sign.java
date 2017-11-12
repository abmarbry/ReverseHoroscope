import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * Represents the individual Sign, mostly in the form of giving their Horoscopes.
 *
 */

public class Sign {
	private final static String[] USED_URLS = {"https://www.astrology.com/horoscope/daily/" , "https://www.tarot.com/daily-horoscope/"};
	private StarValue starSign;
	private int questionPart;
	
	public Sign(int questionPart, int sign) {
		this.questionPart = questionPart;
		starSign = StarValue.values()[sign];
	}
		
	public StarValue getStarValue() {
		return starSign;
	}
	
	public String getText() { //The text is not this will only happen once. 
		return generateText();
	}
	
	private String generateText() { //temp public
		try {
			String url = USED_URLS[questionPart-1] + starSign.getUrlName();
			if(questionPart == 1) {
				url+= ".html";
			}
			Document doc = Jsoup.connect(url).get();
			String dailyHoroscope = doc.select("p").first().text();
			return dailyHoroscope;
		}
		catch(IOException i) {
			i.printStackTrace();
			System.exit(0);
			return null;
		}
	}

}