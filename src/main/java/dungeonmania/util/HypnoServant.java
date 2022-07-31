/* 
package dungeonmania.util;
import dungeonmania.Entities.Moving.Player;
import java.util.List;
import java.util.ArrayList;
import dungeonmania.exceptions.InvalidActionException;

import dungeonmania.Entities.Moving.Mercenary;

public class HypnoServant {
    Mercenary BewitchedMerc;
    int TimeBewitched;

    public HypnoServant(Mercenary victim, int TimeBewitched,Player player) throws InvalidActionException{
        this.BewitchedMerc = victim;
        this.TimeBewitched = TimeBewitched;
        Bewitch(player);
    }

    public void Bewitch(Player player) throws InvalidActionException{
        this.BewitchedMerc.becomeAlly(this.BewitchedMerc, player);
    }

    public void DisenchantingProgress(Player player,List<HypnoServant> unwittingVictims) {
        this.TimeBewitched -= this.TimeBewitched - 1;
        ///  equals function does not work on type int only integer
        if (this.TimeBewitched == 0) {
            this.FreeMerc(player);
            unwittingVictims.remove(this);
        }
    }

    // Remove mercenary as ally
    public void FreeMerc(Player player) {
        this.BewitchedMerc.setAlly(false);
        player.removeAlly();
        this.BewitchedMerc.setInteractable(true);
    }
}

*/