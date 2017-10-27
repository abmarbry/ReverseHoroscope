import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;

public class Profile implements Serializable {

	private static final long serialVersionUID = 297364283964982364L;
	private final int MILLISECONDS_IN_A_DAY = 86400000;
	private int[] signConnection;
	private int mostConnectionPossible;
	private String name;
	private boolean hasUpdatedValuesToday;
	private Calendar firstDayInstantiated;
	private Calendar lastTimeUsed;
	private Calendar today;
	
	Profile(String name) {
		this.name = name;
		signConnection = new int[StarValue.TOTAL_SIGN_NUM];
		mostConnectionPossible = 0;
		firstDayInstantiated = Calendar.getInstance();
		hasUpdatedValuesToday = false;
		lastTimeUsed = null;
	}
	
	public String getName() {
		return name;
	}

	public void addConnection(int part, StarValue s) throws IOException{
		if(part==1) {
			signConnection[s.getPos()]++;
		}
		else if(part == 2) {
			signConnection[s.getPos()]+= 3;
		}
		else {
			throw new IOException();
		}
	}
	
	public double getConnectionPercent(StarValue s) {
		double percent = 100.0*((float) signConnection[s.getPos()]) / ((float) mostConnectionPossible);
		return percent;
	}
	
	public int getConnection(StarValue s) {
		return signConnection[s.getPos()];
	}
	
	public int getMostConnectionPossible() {
		return mostConnectionPossible;
	}
	
	public void addToTotal(int howMuch) {
		mostConnectionPossible+= howMuch;
	}
	
	public void updateProfileDates() {
		lastTimeUsed = today;
	}
	
	public boolean hasUpdatedValuesToday() {
		today = Calendar.getInstance();
		if(lastTimeUsed == null || (today.compareTo(lastTimeUsed) >= MILLISECONDS_IN_A_DAY)) {
			hasUpdatedValuesToday = false;
		}
		else {
			hasUpdatedValuesToday = true;
		}
		return hasUpdatedValuesToday;
	}
	
	public int getDaysProgramUsed() {
		int differenceInDays;
		if(lastTimeUsed!=null) {
			differenceInDays = (int)( (lastTimeUsed.getTimeInMillis() - firstDayInstantiated.getTimeInMillis()) / (MILLISECONDS_IN_A_DAY));
		}
		else {
			differenceInDays = 0;
		}
		return differenceInDays;
	}
}
