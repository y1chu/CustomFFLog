package Calculation;

public class Fight {

    private String nameOfFight;
    private boolean isClear;
    private String timeLength;
    private int damagePercent;

    public Fight(String nameOfFight, boolean isClear, String timeLength, int damagePercent) {
        this.nameOfFight = nameOfFight;
        this.isClear = isClear;
        this.timeLength = timeLength;
        this.damagePercent = damagePercent;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public String getNameOfFight() {
        return nameOfFight;
    }

    public int getDamagePercent() {
        return damagePercent;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setNameOfFight(String nameOfFight) {
        this.nameOfFight = nameOfFight;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    public void setDamagePercent(int damagePercent) {
        this.damagePercent = damagePercent;
    }

    @Override
    public String toString() {
        return "Calculation.Fight{" +
                "nameOfFight='" + nameOfFight + '\'' +
                ", isClear=" + isClear +
                ", timeLength='" + timeLength + '\'' +
                ", damagePercent=" + damagePercent +
                '}';
    }
}
