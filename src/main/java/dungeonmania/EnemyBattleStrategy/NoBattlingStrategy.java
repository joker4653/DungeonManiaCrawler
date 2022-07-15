package dungeonmania.EnemyBattleStrategy;

public class NoBattlingStrategy extends EnemyBattlingStrategy {

    @Override
    public double calculateEnemyHealth(double playerDmg) {
        return super.getHealth();
    }
    
    @Override
    public double attackModifier() {
        return 0;
    }
    
}
