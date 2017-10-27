import java.util.Arrays;

public class SignQuestion {
	private final int PART_ONE_ANSWERS_NEEDED = 1;
	private final int PART_ONE_ANSWER_ITERATIONS = 12;
	private final int PART_TWO_ANSWERS_NEEDED = 3;
	private final int PART_TWO_ANSWER_ITERATIONS = 4;
	
	private Sign[] signAnswers; 
	private boolean[] answerUsed;
	private int questionPart;
	private String question;
	private boolean questionGenerated;
	
	public SignQuestion(int questionPart) {
		signAnswers = new Sign[StarValue.TOTAL_SIGN_NUM];
		answerUsed = new boolean[StarValue.TOTAL_SIGN_NUM];
		Arrays.fill(answerUsed, false);
		this.questionPart = questionPart;
		generateQuestion();
		generateAnswers(); //More efficient way to do this
	}
	
	private void generateQuestion() {
		generateAnswers();
		if (questionPart == 1) {
			question = "Do you believe that the following applies to you?";
			questionGenerated = true;
			}
		else if (questionPart == 2) {
			question = "Which of the following applies most to you?";
			questionGenerated = true;
		}
		else {
			System.out.println("A grave error has been commited. The question part is " + questionPart + " when it should be something else. I must go.");
			System.exit(0);
		}
	}
	
	private void generateAnswers() {
		for (int i = 0; i < signAnswers.length; i++) {
			signAnswers[i] = new Sign(questionPart, i);
			signAnswers[i].getText();
		}
	}
	
	public int getPart() {
		return questionPart;
	}
	
	public String getQuestion() {
		if(!questionGenerated) {
			return null;
		}
		else {
			return question;
		}
	}
	
	public Sign getRandomSign() {
		int randomInt;
		Sign randomAnswer = null;
		boolean randomAnswerSelected = false;
		if(questionGenerated) {
			while(!randomAnswerSelected) {
				randomInt = (int)(Math.round((Math.random()*(signAnswers.length-1))));
				if(!answerUsed[randomInt]) {
					randomAnswer = signAnswers[randomInt];
					randomAnswerSelected = true;
					answerUsed[randomInt] = true;
				}
			}
		}
		return randomAnswer;
	}
	
	public String getAnswer(StarValue s) {
		int pos = s.getPos();
		return signAnswers[pos].getText();
	}
	
	public boolean allAnswersUsed() {
		for(int i = 0; i < answerUsed.length; i++) {
			if(!answerUsed[i]) {
				return false;
			}
		}
		return true;
	}
	
	public int determineAnswersNeededPerIterations() {
		if(questionPart == 1) {
			return PART_ONE_ANSWERS_NEEDED;
		}
		else if (questionPart == 2) {
			return PART_TWO_ANSWERS_NEEDED;
		}
		else {
			System.out.println("A grave error has been commited. The question part is " + questionPart + " when it should be something else. I must go.");
			System.exit(0);
		}
		return 0; //bad
	}
	
	public int determineAnswerIterations() {
		if(questionPart == 1) {
			return PART_ONE_ANSWER_ITERATIONS;
		}
		else if (questionPart == 2) {
			return PART_TWO_ANSWER_ITERATIONS;
		}
		else {
			System.out.println("A grave error has been commited. The question part is " + questionPart + " when it should be something else. I must go.");
			System.exit(0);
		}
		return 0; //bad
	}
}