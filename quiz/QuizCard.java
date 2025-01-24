package quiz;

public class QuizCard {
    private String question;
    private String answer;

    public QuizCard(String q, String a) {
        setQuestion(q);
        setAnswer(a);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String q) {
        question = q;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String a) {
        answer = a;
    }
}
