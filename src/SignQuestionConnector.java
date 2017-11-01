import java.io.IOException;

/**
 * 
 * Represents the connection between the questions of the different parts and the main program. It begins generation of all of the questions, and provides accessors that
 * main will need.
 *
 */

public class SignQuestionConnector {
	private final int PART_ONE_ANSWERS_NEEDED = 1;
	private final int PART_ONE_ANSWER_ITERATIONS = 12;
	private final int PART_TWO_ANSWERS_NEEDED = 3;
	private final int PART_TWO_ANSWER_ITERATIONS = 4;
	
	private final int NUMBER_OF_PARTS = 2;
	private SignQuestion[] signQuestions;
	
	public SignQuestionConnector() {
		signQuestions = new SignQuestion[NUMBER_OF_PARTS];
		generateAllQuestions();
	}
	
	private void generateAllQuestions() {
		for(int i = 0; i < signQuestions.length; i++) {
			signQuestions[i] = new SignQuestion((i+1));
		}
	}
	
	public String getQuestion(int part) {
		return signQuestions[part-1].getQuestion();
	}
	
	public Sign getRandomSign(int part) {
		return signQuestions[part-1].getRandomSign();
	}
	
	public int getAnswersPerQuestion(int part) {
		if(part == 1) {
			return PART_ONE_ANSWERS_NEEDED;
		}
		else if (part == 2) {
			return PART_TWO_ANSWERS_NEEDED;
		}
		else { //This is impossible
			System.out.println("A grave error has been commited. The question part is " + part + " when it should be something else. I must go.");
			System.exit(0);
			return -1;
		}
	}
	
	public boolean isYesOrNoQuestion(int part) {
		if (part == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getLoopIterationsOfAnswers(int part) {
		if(part == 1) {
			return PART_ONE_ANSWER_ITERATIONS;
		}
		else if (part == 2) {
			return PART_TWO_ANSWER_ITERATIONS;
		}
		else { //This is impossible
			System.out.println("A grave error has been commited. The question part is " + part + " non-existant. I must go.");
			System.exit(0);
		}
		return 0;
	}
	
	public String getPartTitle(int part) throws IOException{
		if(part==1) {
			return "Part I: Individual Relatability\n\n";
		}
		if(part==2) {
			return "Part II: Comparative Relatability\n\n";
		}
		else {
				throw new IOException();
		}
	}
	
}
