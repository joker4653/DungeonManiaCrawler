package dungeonmania.Battling.EnemyBattleStrategy;

public class NoBattlingStrategy extends EnemyBattlingStrategy {

    @Override
    public double calculateDeltaEnemyHealth(double playerDmg) {
        return 0;
    }
    
    @Override
    public double attackModifier() {
        return 0;
    }
    
}
