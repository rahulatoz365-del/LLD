package managers;

import strategies.SequentialStrategy;
import strategies.RandomStrategy;
import strategies.CustomStrategy;
import strategies.PlayStrategy;
import enums.PlayerStrategy;

public class StrategyManager {
    private static StrategyManager instance = null;
    private SequentialStrategy sequentialStrategy;
    private RandomStrategy randomStrategy;
    private CustomStrategy customStrategy;

    private StrategyManager() {
        sequentialStrategy = new SequentialStrategy();
        randomStrategy = new RandomStrategy();
        customStrategy = new CustomStrategy();
    }

    public static synchronized StrategyManager getInstance() {
        if (instance == null) {
            instance = new StrategyManager();
        }
        return instance;
    }

    public PlayStrategy getStrategy(PlayerStrategy type) {
        if (type == PlayerStrategy.SEQUENTIAL) {
            return sequentialStrategy;
        } else if (type == PlayerStrategy.RANDOM) {
            return randomStrategy;
        } else {
            return customStrategy;
        }
    }
}