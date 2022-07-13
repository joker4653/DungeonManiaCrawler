package dungeonmania.EnemyBattleStrategy;

public class NoBattlingStrategy extends EnemyBattlingStrategy {

    // DELETE??????????????????????????????????????????????????
    @Override
    public double calculateEnemyHealth(double playerDmg) {
        return super.getHealth();
    }
    
    @Override
    public double attackModifier() {
        return 0;
    }
    
}