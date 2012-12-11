package games.chrono.monsters;

import java.awt.Image;
import java.util.List;
import java.util.Set;


public abstract class MagicMonster extends BasicMonster {

    private final static String MP_STAT = "MP";
    private final static String MAX_MP = "maxMP";

    public MagicMonster (Set<String> allowableModes, String condition, Image image,
                        List<String> stats) {
        super(allowableModes, condition, image, stats);
    }

    @Override
    protected String[] getStatLines () {
        String[] oldStats = super.getStatLines();
        String[] stats = new String[oldStats.length + 1];
        System.arraycopy(oldStats, 0, stats, 0, oldStats.length);
        stats[oldStats.length] = String.format("MP: %d/%d", getStat(MP_STAT).intValue(), getStat(MAX_MP).intValue());
        return stats;
    }

    public int getMP() {
        return getStat(MP_STAT).intValue();
    }

    public void setMP(int mp) {
        changeStat(MP_STAT, mp);
    }

    public void changeMP(int changeInMP) {
        changeStat(MP_STAT, getMP() + changeInMP);
    }

}
