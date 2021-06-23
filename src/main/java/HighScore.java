import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScore {

    private List<Integer> scoreList = new ArrayList<>();

    public void addScore(int score){
        scoreList.add(score);
    }

    public List<Integer> getScoreList() {
        Collections.sort(scoreList);
        Collections.reverse(scoreList);
        return scoreList;
    }
}
