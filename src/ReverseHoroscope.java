import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class ReverseHoroscope {
	final static int NUMBER_OF_PARTS = 2;
	final static int MAX_PROFILE_NUM = 10;
	static Scanner in = new Scanner(System.in); 

	public static void main(String[] args) {

		try {
			System.out.println("Loading. Please be patient...");
			ArrayList<Profile> profiles = importProfiles();
			Profile selectedProfile; //bad?
			SignQuestionConnector connector = new SignQuestionConnector(); //bad?
			int choice;
			
			while(true) {
				try {
				selectedProfile = null;
				System.out.println("Reverse Horoscope: Discover Your Real Star Sign");
				System.out.println("1) Create New Profile");
				System.out.println("2) Continue with Existing Profile");
				System.out.println("3) View Current Results"); 
				System.out.println("4) Quit");
				choice = in.nextInt();
				switch(choice) {
				case 1: //Create new profile
					System.out.println("What is the name for the new profile?");
					profiles.add(new Profile(in.next()));
					in.nextLine();
					selectedProfile = profiles.get(profiles.size()-1);
					break;
				case 2: //continue with existing profile
					selectedProfile = printMenu(profiles);
					break;
				case 3: //view profile
					selectedProfile = printMenu(profiles);
					if(selectedProfile != null) {
						printResults(selectedProfile);
						selectedProfile = null;
					}
					break;
				case 4: //quit
					exportProfiles(profiles);
					in.close();
					System.exit(0);
				default:
					System.out.println("Please choose a valid option.");
					break;
				}
				if(selectedProfile!=null) {
					if(!selectedProfile.hasUpdatedValuesToday()) {
						run(selectedProfile, connector);
						selectedProfile.updateProfileDates();
					}
					else {
						System.out.println("You have already checked your horoscope today! Select another profile or try again tomorrow.");
					}
				}
			}
			
			catch(InputMismatchException e) {
				System.out.println("Invalid Entry: If you were taking the test, you will have to please try again.");
				in.nextLine();
			}
		}
			
			}
		catch(IOException e) {
			System.out.println("Error: Terminating...");
			System.exit(0);
		}
	}
	
	
	
	
	public static void run(Profile selectedProfile, SignQuestionConnector connector) throws IOException, InputMismatchException {
		Sign[] tempSigns;
		int answersGivenPerQuestion;
		boolean yesOrNoAnswer;
		int subQuestionLoopIterations;
		int choice;
		boolean answered;
		
		for(int partIterator = 1; partIterator <= NUMBER_OF_PARTS; partIterator++) {
			
			answersGivenPerQuestion = connector.getAnswersPerQuestion(partIterator);
			yesOrNoAnswer = connector.isYesOrNoQuestion(partIterator);
			subQuestionLoopIterations = connector.getLoopIterationsOfAnswers(partIterator);
			tempSigns = new Sign[answersGivenPerQuestion];
			
			System.out.println("\n" + connector.getPartTitle(partIterator));
			
			for(int subQuestionIterator = 0; subQuestionIterator < subQuestionLoopIterations; subQuestionIterator++) {
				answered = false;
				
				for(int answerIterator = 0; answerIterator < answersGivenPerQuestion; answerIterator++) {
					tempSigns[answerIterator] = connector.getRandomSign(partIterator);
					if(yesOrNoAnswer) {
						System.out.println("For the following:\n" +"1) Applies\n" + "2) Does Not\n");
					}
					else {
						System.out.printf("Answer " + (answerIterator+1) + ") ");
					}
					System.out.println(tempSigns[answerIterator].getText());
				}
				
				while(!answered) {
					choice = in.nextInt();
					
					if(choice <= answersGivenPerQuestion+1 && choice > 0) {
						answered = true;
						if(yesOrNoAnswer && choice == 1) {
							selectedProfile.addConnection(partIterator, tempSigns[choice-1].getStarValue());
						}
						else if(!yesOrNoAnswer) {
							selectedProfile.addConnection(partIterator, tempSigns[choice-1].getStarValue());
						}
					}
					else {
						System.out.println("Invalid Input. Look at the answers and enter the most fitting integer.");
					}
				}
			}
			selectedProfile.addToTotal(answersGivenPerQuestion);
		}
		printResults(selectedProfile);
	}
	
	static ArrayList<Profile> importProfiles() throws IOException{
		ArrayList<Profile> temp;
		 try {
			 File dataFile = new File("horoscope_data.txt");
				if(!dataFile.exists()) {
					dataFile.createNewFile();
					temp = new ArrayList<Profile>();
					return temp;
				}
	         FileInputStream dataFileIn = new FileInputStream("horoscope_data.txt");
	         ObjectInputStream dataIn = new ObjectInputStream(dataFileIn);
	         temp = (ArrayList<Profile>) dataIn.readObject();
	         dataIn.close();
	         dataFileIn.close();
	         return temp;
	      }
		 catch(IOException i) {
	         return executeErrorProtocol();
	      }
		 catch(ClassNotFoundException c) {
			 return executeErrorProtocol();
		 }
	}
	
	static void exportProfiles(ArrayList<Profile> profiles) {
		try {
			FileOutputStream dataFileOut = new FileOutputStream("horoscope_data.txt");
			ObjectOutputStream dataOut = new ObjectOutputStream(dataFileOut);
			dataOut.writeObject(profiles);
			dataOut.close();
			dataFileOut.close();
		}
		catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	private static Profile printMenu(ArrayList<Profile> profiles) {
		Profile selectedProfile;
		int choice;
		System.out.println("Which profile would you like to select?");
		for(int i = 0; i < profiles.size(); i++) {
			System.out.println((i+1) + ") " + profiles.get(i).getName());
		}	
		System.out.println((profiles.size()+1) + ") Back to Menu");
		choice = in.nextInt();			
		if((choice < profiles.size()+1) && choice > 0) {
			selectedProfile = profiles.get(choice-1);
		}
		else if(choice == profiles.size()+1) {
			selectedProfile = null;
		}
		else {
			System.out.println("Invalid choice. Please try harder.");
			selectedProfile = null;
		}
		return selectedProfile;
	}
	
	private static void printResults(Profile selectedProfile) {
		System.out.println("\nCurrent Astrological Connection: ");
		for(int signIterator = 0; signIterator < StarValue.TOTAL_SIGN_NUM; signIterator++) {
			System.out.printf(StarValue.getCapsName(signIterator) + ": " + "%d/%d, or %.2f %% \n", selectedProfile.getConnection(StarValue.values()[signIterator]),
					selectedProfile.getMostConnectionPossible(), selectedProfile.getConnectionPercent(StarValue.values()[signIterator]));
		}
		System.out.println("\nYou have been using Reverse Horoscope for " + selectedProfile.getDaysProgramUsed() + " days. The more days you take this test, the more accurate the test is"
				+ " to pointing towards a star pattern that you can call your own.\n");
	}
	
	
	static ArrayList<Profile> executeErrorProtocol() {
        System.out.println("\nWarning: There was a problem in getting your Profiles. Reset Profiles, or terminate?");
        System.out.println("1) Reset Profiles");
        System.out.println("2) Terminate");
        int choice = in.nextInt();
        if(choice == 1) {
       	 System.out.println("Resetting data...");
       	 ArrayList<Profile> temp = new ArrayList<Profile>();
       	 return temp;
        }
        else if(choice != 2) {
        	System.out.println("This data is usless. I'm terminating...");
        }
        in.close();
        System.exit(0);
        return null;
	}
}
