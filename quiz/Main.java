package quiz;

public class Main {
    public static void main(String[] args) {
        QuizCardBuilder builder = new QuizCardBuilder();
        builder.go();

        QuizCardPlayer player = new QuizCardPlayer();
        player.go();
    }
}
