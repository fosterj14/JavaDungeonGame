package cs1181.project2.foster;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

public class Enemy {
    
    private int health;
    private int expWorth;
    private int goldValue;
    private String name;
    private Image image;
    private int dmg;
    
    public  Enemy () {
        this.name = "";
        this.health = 0;
        this.expWorth = 0;
        this.goldValue = 0;
        this.image = null;
        this.dmg = 0;
    }
    
    public Enemy(String name, int health, int expWorth, int goldValue, Image image, int dmg) {
        this.name = name;
        this.health = health;
        this.expWorth = expWorth;
        this.goldValue = goldValue;
        this.image = image;
        this.dmg = dmg;
    }
    
    public Enemy(Enemy other) {
        this.name = other.name;
        this.health = other.health;
        this.expWorth = other.expWorth;
        this.goldValue = other.goldValue;
        this.image = other.image;
        this.dmg = other.dmg;
    }
    
    /**
     * Creates an Opponent object of a random type Enemy
     * @param enemyList List of all enemy types
     * @return Enemy opponent
     */
    public Enemy createOpponent(ArrayList<Enemy> enemyList) {
        Random rng = new Random();

        Enemy opponent = new Enemy(enemyList.get(rng.nextInt(5)));
        
        this.name = opponent.name;
        this.health = opponent.health;
        this.expWorth = opponent.expWorth;
        this.goldValue = opponent.goldValue;
        this.image = opponent.image;
        this.dmg = opponent.dmg;

        return opponent;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getExpWorth() {
        return expWorth;
    }

    public void setExpWorth(int expWorth) {
        this.expWorth = expWorth;
    }

    public int getGoldValue() {
        return goldValue;
    }

    public void setGoldValue(int goldValue) {
        this.goldValue = goldValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Checks for an enemy hit, if it is 0, it counts as the enemy missing,
     * if it is not, it returns the value of the enemy's attack damage
     * @return Enemy damage value
     */
    public int getDmg() {
        Random rng = new Random();
        int tempDmg;
        if (rng.nextInt(10) == 0) {
            tempDmg = 0;
        } else {
            tempDmg = dmg;
        }
        return tempDmg;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n" + "HP: " + this.health;
    }
    
}