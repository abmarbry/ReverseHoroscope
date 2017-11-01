
/**
 * 
 * Useful enum for URLs and such.
 *
 */

public enum StarValue{
	ARIES(0, "aries"), TAURUS(1, "taurus"), GEMINI(2, "gemini"), CANCER(3, "cancer"),
	LEO(4, "leo"), VIRGO(5, "virgo"), LIBRA(6, "libra"), SCORPIO(7, "scorpio"),
	SAGITTARIUS(8, "sagittarius"), CAPRICORN(9, "capricorn"), AQUARIUS(10, "aquarius"),
	PISCES(11, "pisces");
	
	public static final int TOTAL_SIGN_NUM = 12;
	private int pos;
	private String urlName;
	private StarValue(int pos, String name) {
		this.pos = pos;
		this.urlName = name;
	}
	public int getPos() {
		return pos;
	}
	
	public String getUrlName() {
		return urlName;
	}
	
	public String getCapsName() {
		return urlName.toUpperCase();
	}
	
	public static String getUrlName(int pos) {
		return StarValue.values()[pos].getUrlName();
	}
	
	public static String getCapsName(int pos) {
		return StarValue.values()[pos].getCapsName();
	}
	
	
}
