/* 
package dungeonmania.util;
import dungeonmania.Entities.Moving.Player;
import dungeonmania.Entities.Moving.Mercenary;

public class HypnoServant {
    Mercenary BewitchedMerc;
    int TimeBewitched;

    public HypnoServant(Mercenary victim, int TimeBewitched) {
        this.BewitchedMerc = victim;
        this.TimeBewitched = TimeBewitched;
    }

    public void Bewitch(Player player) {
        this.BewitchedMerc.becomeAlly(this.BewitchedMerc, player);
    }

    // Remove mercenary as ally
    public void freeMerc(Player player) {
        this.BewitchedMerc.setAlly(true);
        player.removeAlly();
        this.BewitchedMerc.setInteractable(true);
    }
}
*/