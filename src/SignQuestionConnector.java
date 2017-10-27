import java.io.IOException;

public class SignQuestionConnector {
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
	//Bad way to get answers?
	public int getAnswersPerQuestion(int part) {
		return signQuestions[part-1].determineAnswersNeededPerIterations();
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
		return signQuestions[part-1].determineAnswerIterations();
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
