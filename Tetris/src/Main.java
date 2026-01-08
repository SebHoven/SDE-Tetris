import singleton.GameManager;

public class Main {
    public static void main(String[] args) {
        GameManager game1 = GameManager.getInstance();
        game1.addScore(100);

        GameManager game2 = GameManager.getInstance();
        System.out.println(game2.getScore());  // Outputs: 100

        System.out.println(game1 == game2);  // Outputs: true
    }
}
