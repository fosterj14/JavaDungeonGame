package cs1181.project2.foster;

import java.util.Random;

public class Player {

    private int health;
    private final int maxHealth = 100;
    private int mana;
    private final int maxMana = 10;
    private int exp;
    private int gold;
    private int dmg;
    private int magicDmg;
    private int level;
    private int expRequired;
    private final int heal = 50;
    private final int manaCost;
    private int numPotion = 2;
    
    public Player(int health, int mana) {
        this.health = health;
        this.mana = mana;
        exp = 0;
        gold = 0;
        dmg = 5;
        magicDmg = 10;
        level = 1;
        expRequired = 10;
        manaCost = 5;
    }

/**
 * Levels up the player when the appropriate amount of experience is gained.
 * Doubles the amount of experience needed for next level
 */
    public void levelUp() {
        this.level = this.level + 1;
        if (this.getHealth() < 75) {
        this.setHealth(this.getHealth() + 25);
        } else {
            this.setHealth(100);
        }
        this.expRequired = this.expRequired * 2;
    }
    
    public void usePotion() {
        this.setHealth(this.getHealth() + heal);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
/**
 * Determines if a melee attack will hit and returns 0 if it misses and dmg if
 * it hits.
 * @return melee attack damage value
 */
    public int getDmg() {
        Random rng = new Random();
        int tempDmg;
        if (rng.nextInt(10) == 0) {
            tempDmg = 0;
        } else {
            tempDmg = dmg + (level - 1);
        }
        return tempDmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
/**
 * Determines if a magic attack will hit and returns 0 if it misses and magicDmg if
 * it hits.
 * @return magic damage value
 */
    public int getMagicDmg() {
        Random rng = new Random();
        int tempDmg;
        if (rng.nextInt(20) == 0) {
            tempDmg = 0;
        } else {
            tempDmg = magicDmg;
        }
        return tempDmg;
    }

    public void setMagicDmg(int magicDmg) {
        this.magicDmg = magicDmg;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExpRequired() {
        return expRequired;
    }

    public void setExpRequired(int expRequired) {
        this.expRequired = expRequired;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getHeal() {
        return heal;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getNumPotion() {
        return numPotion;
    }

    public void setNumPotion(int numPotion) {
        this.numPotion = numPotion;
    }
    
    @Override
    public String toString() {
        return "HP: " + Integer.toString(this.getHealth()) + "/" + Integer.toString(this.getMaxHealth()) +
                                    "\n" + "Mana: " + Integer.toString(this.getMana()) + "/" + Integer.toString(this.getMaxMana()) +
                                    "\n" + "Gold: " + Integer.toString(this.getGold()) + "\n" + "Level: " + (this.getLevel());
    }

    
    
}
