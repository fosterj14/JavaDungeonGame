package cs1181.project2.foster;

import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author John Foster
 */
public class CS1181Project2Foster extends Application {

    private boolean attacking; // MC
    private boolean casting;
    public boolean enemy1Click;
    public boolean enemy2Click;
    public boolean enemy3Click;

    @Override
    public void start(Stage primaryStage) {
        Player player = new Player(100, 10);

        // create enemy types here and put into arrayList
        ArrayList<Enemy> enemyList = createEnemies();

        // make enemy buttons
        Button enemy1 = new Button();
        enemy1.setStyle("-fx-background-color: transparent");
        Button enemy2 = new Button();
        enemy2.setStyle("-fx-background-color: transparent");
        Button enemy3 = new Button();
        enemy3.setStyle("-fx-background-color: transparent");

        Enemy opponent1 = new Enemy();
        opponent1.createOpponent(enemyList);
        Enemy opponent2 = new Enemy();
        opponent2.createOpponent(enemyList);
        Enemy opponent3 = new Enemy();
        opponent3.createOpponent(enemyList);
        enemy1.setGraphic(new ImageView(opponent1.getImage()));
        enemy2.setGraphic(new ImageView(opponent2.getImage()));
        enemy3.setGraphic(new ImageView(opponent3.getImage()));

        //create the pane for the enemies
        GridPane enemyDisplay = new GridPane();
        HBox enemyInfo = new HBox();

        // size the enemy buttons
        enemy1.setMinSize(400, 500);
        enemy2.setMinSize(400, 500);
        enemy3.setMinSize(400, 500);

        // text area for enemy info
        TextArea enemy1Info = new TextArea(opponent1.toString());
        enemy1Info.setEditable(false);
        enemy1Info.setMaxWidth(150);
        enemy1Info.setMinHeight(45);
        enemy1Info.setMaxHeight(45);
        TextArea enemy2Info = new TextArea(opponent2.toString());
        enemy2Info.setEditable(false);
        enemy2Info.setMaxWidth(150);
        enemy2Info.setMinHeight(45);
        enemy2Info.setMaxHeight(45);
        TextArea enemy3Info = new TextArea(opponent3.toString());
        enemy3Info.setEditable(false);
        enemy3Info.setMaxWidth(150);
        enemy3Info.setMinHeight(45);
        enemy3Info.setMaxHeight(45);

        // place enemies in enemy display      
        enemyDisplay.add(enemy1, 0, 0);
        enemyDisplay.add(enemy2, 2, 0);
        enemyDisplay.add(enemy3, 4, 0);
        enemyInfo.getChildren().add(enemy1Info);
        enemyInfo.getChildren().add(enemy2Info);
        enemyInfo.getChildren().add(enemy3Info);
        enemyInfo.setPadding(new Insets(10, 0, 0, 130));
        enemyInfo.setSpacing(270);

        // health and mana area
        VBox playerArea = new VBox(10);

        TextArea playerInfo = new TextArea(player.toString());

        // for updating on player actions and events
        TextArea updates = new TextArea("Welcome to Paint Quest! Select your attack and then pick the enemy, or use a potion to heal. "
                + "As you kill enemies, you will gain gold and experience. Experience makes you stronger. Get as much gold as you can and flee "
                + "before you die!");

        playerInfo.setEditable(false);
        playerInfo.setMaxHeight(95);
        playerInfo.setMaxWidth(100);

        updates.setEditable(false);
        updates.setMaxHeight(100);
        updates.setMaxWidth(280);
        updates.setWrapText(true);

        playerArea.getChildren().addAll(playerInfo, updates);

        // alerts for clearing all enemies, fleeing, and player death
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Continue or Flee?");
        alert.setHeaderText("Continue to find more loot or flee for your safety?");
        alert.setContentText("Make your choice");
        ButtonType continueButton = new ButtonType("Continue");
        ButtonType fleeButton = new ButtonType("Flee");
        alert.getButtonTypes().setAll(continueButton, fleeButton);

        Alert fleeing = new Alert(AlertType.INFORMATION);
        fleeing.setTitle("Fleeing");
        fleeing.setHeaderText(null);

        Alert playerDeath = new Alert(AlertType.INFORMATION);
        playerDeath.setTitle(null);
        playerDeath.setHeaderText(null);
        playerDeath.setContentText("You have perished!");

        // player options
        Button attackButton = new Button("Melee Attack");
        attackButton.setMinSize(50, 50);
        Image attackImage = new Image("file:attack.png", 50, 50, true, false);
        ImageView attackPic = new ImageView(attackImage);
        attackButton.setGraphic(attackPic);

        Button castSpell = new Button("Magic Spell");
        castSpell.setMinSize(50, 50);
        Image spellImage = new Image("file:spell.png", 50, 50, true, false);
        ImageView spellPic = new ImageView(spellImage);
        castSpell.setGraphic(spellPic);

        Button potion = new Button("Potion");
        potion.setMinSize(50, 50);
        Image potionPicImage = new Image("file:potion.png", 50, 50, true, false);
        ImageView potionPic = new ImageView(potionPicImage);
        potion.setGraphic(potionPic);
        VBox charOptions = new VBox(10);

        charOptions.getChildren().addAll(attackButton, castSpell, potion);

        // action for when melee attack is selected
        attackButton.setOnAction(e -> {
            attacking = true; // MC
            casting = false;
        });
        // action for when cast spell is selected
        castSpell.setOnAction(e -> {
            casting = true;
            attacking = false;
        });
        // logic for the first enemy button
        enemy1.setOnAction(e2 -> {
            enemy1Click = true;
            enemy2Click = false;
            enemy3Click = false;
            if (!attacking && !casting) {
                return; // MC
            }
            // attack phase, attacked enemy returns its own attack
            // for melee attack
            if (attacking && enemy1Click) {
                int attack = player.getDmg();
                int enemyAttack = opponent1.getDmg();
                if (attack != 0 && enemyAttack != 0) {
                    opponent1.setHealth(opponent1.getHealth() - attack);
                    player.setHealth(player.getHealth() - enemyAttack);
                    enemy1Info.setText(opponent1.toString());
                    playerInfo.setText(player.toString());
                    updates.setText("Hit!" + "\n" + "Enemy hit you!");
                } else if (attack == 0 && enemyAttack != 0) {
                    updates.setText("Attack missed!" + "\n" + "Enemy hit you!");
                    player.setHealth(player.getHealth() - enemyAttack);
                    enemy1Info.setText(opponent1.toString());
                    playerInfo.setText(player.toString());
                } else if (attack != 0 && enemyAttack == 0) {
                    opponent1.setHealth(opponent1.getHealth() - attack);
                    enemy1Info.setText(opponent1.toString());
                    updates.setText("Hit!" + "\n" + "Enemy missed!");
                }
                if (attack == 0 && enemyAttack == 0) {
                    updates.setText("You both missed!");
                }
                attacking = false;
                enemy1Click = false;
            }
            // for magic attack
            if (casting && enemy1Click) {
                int enemyAttack = opponent1.getDmg();
                if (player.getMana() < 5) {
                    updates.setText("No mana left, attack with melee!");
                    casting = false;
                }
                if (player.getMana() >= 5) {
                    int spell = player.getMagicDmg();
                    player.setMana(player.getMana() - player.getManaCost());
                    if (spell != 0 && enemyAttack != 0) {
                        opponent1.setHealth(opponent1.getHealth() - spell);
                        player.setHealth(player.getHealth() - enemyAttack);
                        enemy1Info.setText(opponent1.toString());
                        playerInfo.setText(player.toString());
                        updates.setText("Spell hit!" + "\n" + "Enemy hit you!");
                    } else if (spell == 0 && enemyAttack != 0) {
                        player.setHealth(player.getHealth() - enemyAttack);
                        playerInfo.setText(player.toString());
                        updates.setText("Spell missed!" + "\n" + "Enemy hit you!");
                    } else if (spell != 0 && enemyAttack == 0) {
                        opponent1.setHealth(opponent1.getHealth() - spell);
                        enemy1Info.setText(opponent1.toString());
                        playerInfo.setText(player.toString());
                        updates.setText("Spell hit!" + "\n" + "Enemy missed!");
                    }
                    if (spell == 0 && enemyAttack == 0) {
                        playerInfo.setText(player.toString());
                        updates.setText("You both missed!");
                    }
                    casting = false;
                    enemy1Click = false;
                }
            }
            // enemy death
            if (opponent1.getHealth() <= 0) {
                enemy1.setDisable(true);
                if (player.getMana() < 9) {
                    player.setMana(player.getMana() + 2);
                } else {
                    player.setMana(10);
                }
                player.setExp(player.getExp() + opponent3.getExpWorth());
                if (player.getExp() >= player.getExpRequired()) {
                    player.levelUp();
                }
                player.setGold((player.getGold() + opponent1.getGoldValue()));
                playerInfo.setText(player.toString());
                updates.setText(opponent1.getName() + " is dead!");
                opponent1.setHealth(0);
                enemy1Info.setText(opponent1.toString());
                if (opponent1.getHealth() == 0 && opponent2.getHealth() == 0 && opponent3.getHealth() == 0) {
                    updates.setText(opponent1.getName() + " is dead!" + "\n" + "Round Cleared");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == continueButton) {
                        enemy1.setDisable(false);
                        enemy2.setDisable(false);
                        enemy3.setDisable(false);
                        opponent1.createOpponent(enemyList);
                        opponent2.createOpponent(enemyList);
                        opponent3.createOpponent(enemyList);
                        enemy1.setGraphic(new ImageView(opponent1.getImage()));
                        enemy2.setGraphic(new ImageView(opponent2.getImage()));
                        enemy3.setGraphic(new ImageView(opponent3.getImage()));
                        enemy1Info.setText(opponent1.toString());
                        enemy2Info.setText(opponent2.toString());
                        enemy3Info.setText(opponent3.toString());
                    } else {
                        fleeing.setContentText("You escape with your loot!" + "\n" + "You collected " + player.getGold() + " gold!");
                        fleeing.showAndWait();
                        System.exit(0);
                    }
                }
            }
            // checks for player death
            if (player.getHealth() <= 0) {
                playerInfo.setText(player.toString());
                playerDeath.showAndWait();
                System.exit(0);
            }
        });
        // logic for the second enemy button
        enemy2.setOnAction(e3 -> {
            enemy1Click = false;
            enemy2Click = true;
            enemy3Click = false;
            if (!attacking && !casting) {
                return; // MC
            }
            if (attacking && enemy2Click) {
                // attack phase, attacked enemy returns its own attack
                // for melee attack
                int attack = player.getDmg();
                int enemyAttack = opponent2.getDmg();
                if (attack != 0 && enemyAttack != 0) {
                    opponent2.setHealth(opponent2.getHealth() - attack);
                    player.setHealth(player.getHealth() - enemyAttack);
                    enemy2Info.setText(opponent2.toString());
                    playerInfo.setText(player.toString());
                    updates.setText("Hit!" + "\n" + "Enemy hit you!");
                } else if (attack == 0 && enemyAttack != 0) {
                    updates.setText("Attack missed!" + "\n" + "Enemy hit you!");
                    player.setHealth(player.getHealth() - enemyAttack);
                    enemy2Info.setText(opponent2.toString());
                    playerInfo.setText(player.toString());
                } else if (attack != 0 && enemyAttack == 0) {
                    opponent2.setHealth(opponent2.getHealth() - attack);
                    enemy2Info.setText(opponent2.toString());
                    updates.setText("Hit!" + "\n" + "Enemy missed!");
                }
                if (attack == 0 && enemyAttack == 0) {
                    updates.setText("You both missed!");
                }
                if (attack == 0 && enemyAttack == 0) {
                    updates.setText("You both missed!");
                }
                attacking = false;
                enemy2Click = false;
            }
            // for magic attack
            if (casting && enemy2Click) {
                int enemyAttack = opponent2.getDmg();
                if (player.getMana() < 5) {
                    updates.setText("No mana left, attack with melee!");
                    casting = false;
                }
                if (player.getMana() >= 5) {
                    int spell = player.getMagicDmg();
                    player.setMana(player.getMana() - player.getManaCost());
                    if (spell != 0 && enemyAttack != 0) {
                        opponent2.setHealth(opponent2.getHealth() - spell);
                        player.setHealth(player.getHealth() - enemyAttack);
                        enemy2Info.setText(opponent2.toString());
                        playerInfo.setText(player.toString());
                        updates.setText("Spell hit!" + "\n" + "Enemy hit you!");
                    } else if (spell == 0 && enemyAttack != 0) {
                        player.setHealth(player.getHealth() - enemyAttack);
                        playerInfo.setText(player.toString());
                        updates.setText("Spell missed!" + "\n" + "Enemy hit you!");
                    } else if (spell != 0 && enemyAttack == 0) {
                        opponent2.setHealth(opponent2.getHealth() - spell);
                        enemy2Info.setText(opponent1.toString());
                        playerInfo.setText(player.toString());
                        updates.setText("Spell hit!" + "\n" + "Enemy missed!");
                    }
                    if (spell == 0 && enemyAttack == 0) {
                        playerInfo.setText(player.toString());
                        updates.setText("You both missed!");
                    }
                    casting = false;
                    enemy2Click = false;
                }
            }
            // enemy death
            if (opponent2.getHealth() <= 0) {
                enemy2.setDisable(true);
                if (player.getMana() < 9) {
                    player.setMana(player.getMana() + 2);
                } else {
                    player.setMana(10);
                }
                player.setExp(player.getExp() + opponent3.getExpWorth());
                if (player.getExp() >= player.getExpRequired()) {
                    player.levelUp();
                }
                updates.setText(opponent2.getName() + " is dead!");
                player.setGold(player.getGold() + opponent2.getGoldValue());
                playerInfo.setText(player.toString());
                opponent2.setHealth(0);
                enemy2Info.setText(opponent2.toString());
                if (opponent1.getHealth() == 0 && opponent2.getHealth() == 0 && opponent3.getHealth() == 0) {
                    updates.setText(opponent2.getName() + " is dead!" + "\n" + "Round Cleared");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == continueButton) {
                        enemy1.setDisable(false);
                        enemy2.setDisable(false);
                        enemy3.setDisable(false);
                        opponent1.createOpponent(enemyList);
                        opponent2.createOpponent(enemyList);
                        opponent3.createOpponent(enemyList);
                        enemy1.setGraphic(new ImageView(opponent1.getImage()));
                        enemy2.setGraphic(new ImageView(opponent2.getImage()));
                        enemy3.setGraphic(new ImageView(opponent3.getImage()));
                        enemy1Info.setText(opponent1.toString());
                        enemy2Info.setText(opponent2.toString());
                        enemy3Info.setText(opponent3.toString());
                    } else {
                        fleeing.setContentText("You escape with your loot!" + "\n" + "You collected " + player.getGold() + " gold!");
                        fleeing.showAndWait();
                        System.exit(0);
                    }
                }
            }
            // checks for player death
            if (player.getHealth() <= 0) {
                playerInfo.setText(player.toString());
                playerDeath.showAndWait();
                System.exit(0);
            }
        });
        // logic for the third enemy button
        enemy3.setOnAction(e4 -> {
            enemy1Click = false;
            enemy2Click = false;
            enemy3Click = true;
            if (!attacking && !casting) {
                return; // MC
            }
            // attack phase, attacked enemy returns its own attack
            // for melee attack
            if (attacking && enemy3Click) {
                int attack = player.getDmg();
                int enemyAttack = opponent3.getDmg();
                if (attack != 0 && enemyAttack != 0) {
                    opponent3.setHealth(opponent3.getHealth() - attack);
                    player.setHealth(player.getHealth() - enemyAttack);
                    enemy3Info.setText(opponent3.toString());
                    playerInfo.setText(player.toString());
                    updates.setText("Hit!" + "\n" + "Enemy hit you!");
                } else if (attack == 0 && enemyAttack != 0) {
                    updates.setText("Attack missed!" + "\n" + "Enemy hit you!");
                    player.setHealth(player.getHealth() - enemyAttack);
                    enemy1Info.setText(opponent1.toString());
                    playerInfo.setText(player.toString());
                } else if (attack != 0 && enemyAttack == 0) {
                    opponent3.setHealth(opponent3.getHealth() - attack);
                    enemy3Info.setText(opponent3.toString());
                    updates.setText("Hit!" + "\n" + "Enemy missed!");
                }
                if (attack == 0 && enemyAttack == 0) {
                    updates.setText("You both missed!");
                }
                if (attack == 0 && enemyAttack == 0) {
                    updates.setText("You both missed!");
                }
                attacking = false;
                enemy3Click = false;
            }
            // for magic attack
            if (casting && enemy3Click) {
                int enemyAttack = opponent3.getDmg();
                if (player.getMana() < 5) {
                    updates.setText("No mana left, attack with melee!");
                    casting = false;
                }
                if (player.getMana() >= 5) {
                    int spell = player.getMagicDmg();
                    player.setMana(player.getMana() - player.getManaCost());
                    if (spell != 0 && enemyAttack != 0) {
                        opponent3.setHealth(opponent3.getHealth() - spell);
                        player.setHealth(player.getHealth() - enemyAttack);
                        enemy3Info.setText(opponent3.toString());
                        playerInfo.setText(player.toString());
                        updates.setText("Spell hit!" + "\n" + "Enemy hit you!");
                    } else if (spell == 0 && enemyAttack != 0) {
                        player.setHealth(player.getHealth() - enemyAttack);
                        playerInfo.setText(player.toString());
                        updates.setText("Spell missed!" + "\n" + "Enemy hit you!");
                    } else if (spell != 0 && enemyAttack == 0) {
                        opponent3.setHealth(opponent3.getHealth() - spell);
                        enemy3Info.setText(opponent3.toString());
                        playerInfo.setText(player.toString());
                        updates.setText("Spell hit!" + "\n" + "Enemy missed!");
                    }
                    if (spell == 0 && enemyAttack == 0) {
                        playerInfo.setText(player.toString());
                        updates.setText("You both missed!");
                    }
                    casting = false;
                    enemy3Click = false;
                }
            }
            // enemy death
            if (opponent3.getHealth() <= 0) {
                enemy3.setDisable(true);
                if (player.getMana() < 9) {
                    player.setMana(player.getMana() + 2);
                } else {
                    player.setMana(10);
                }
                player.setExp(player.getExp() + opponent3.getExpWorth());
                if (player.getExp() >= player.getExpRequired()) {
                    player.levelUp();
                }
                playerInfo.setText(player.toString());
                updates.setText(opponent3.getName() + " is dead!");
                player.setGold(player.getGold() + opponent3.getGoldValue());
                opponent3.setHealth(0);
                enemy3Info.setText(opponent3.toString());
                if (opponent1.getHealth() == 0 && opponent2.getHealth() == 0 && opponent3.getHealth() == 0) {
                    updates.setText(opponent3.getName() + " is dead!" + "\n" + "Round Cleared");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == continueButton) {
                        enemy1.setDisable(false);
                        enemy2.setDisable(false);
                        enemy3.setDisable(false);
                        opponent1.createOpponent(enemyList);
                        opponent2.createOpponent(enemyList);
                        opponent3.createOpponent(enemyList);
                        enemy1.setGraphic(new ImageView(opponent1.getImage()));
                        enemy2.setGraphic(new ImageView(opponent2.getImage()));
                        enemy3.setGraphic(new ImageView(opponent3.getImage()));
                        enemy1Info.setText(opponent1.toString());
                        enemy2Info.setText(opponent2.toString());
                        enemy3Info.setText(opponent3.toString());
                    } else {
                        fleeing.setContentText("You escape with your loot!" + "\n" + "You collected " + player.getGold() + " gold!");
                        fleeing.showAndWait();
                        System.exit(0);
                    }
                }
            }
            // checks for player death
            if (player.getHealth() <= 0) {
                playerInfo.setText(player.toString());
                playerDeath.showAndWait();
                System.exit(0);
            }
        });
        // heals with potion
        potion.setOnAction(e -> {
            if (player.getNumPotion() != 0) {
                if (player.getHealth() <= 50) {
                    player.setNumPotion(player.getNumPotion() - 1);
                    player.setHealth(player.getHealth() + player.getHeal());
                    playerInfo.setText(player.toString());
                    updates.setText("You regained " + player.getHeal() + " HP.\n" + "HP: " + Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth()) + "\n"
                            + "Potions remaining: " + player.getNumPotion());
                } else if (player.getHealth() == 100) {
                    updates.setText("You are at full health!");
                } else {
                    player.setNumPotion(player.getNumPotion() - 1);
                    updates.setText("You regained " + (100 - player.getHealth()) + " HP.\n" + "HP: " + Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth())
                            + "\n" + "Potions remaining: " + player.getNumPotion());
                    player.setHealth(100);
                    playerInfo.setText(player.toString());
                }
            } else {
                updates.setText("You are out of potions!");
            }
        });

        //character display
        HBox charDisplay = new HBox(10);
        charDisplay.setPadding(new Insets(10, 0, 10, 500));
        charDisplay.getChildren().addAll(charOptions, playerArea);

        // play area
        VBox root = new VBox();

        root.getChildren().add(enemyDisplay);
        root.getChildren().add(enemyInfo);
        root.getChildren().add(charDisplay);

        Image caveImage = new Image("file:cave.png", 1245, 750, true, false);
        ImageView background = new ImageView(caveImage);
        StackPane backgroundPane = new StackPane();

        backgroundPane.getChildren().addAll(background, root);

        Scene scene = new Scene(backgroundPane, 1245, 750);

        primaryStage.setResizable(false);
        primaryStage.setWidth(1250);
        primaryStage.setHeight(775);
        primaryStage.setTitle("Paint Quest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates and returns an ArrayList of enemies
     *
     * @return ArrayList of enemies
     */
    public static ArrayList<Enemy> createEnemies() {
        ArrayList<Enemy> enemyList = new ArrayList<>();

        Image orcPic = new Image("file:orc.png", 400, 500, true, false);
        Image ogrePic = new Image("file:ogre.png", 400, 500, true, false);
        Image warlockPic = new Image("file:warlock.png", 400, 500, true, false);
        Image skeletonPic = new Image("file:skeleton.png", 400, 500, true, false);
        Image zombiePic = new Image("file:zombie.png", 400, 500, true, false);

        Enemy orc = new Enemy("Orc", 10, 4, 5, orcPic, 5);
        enemyList.add(orc);
        Enemy ogre = new Enemy("Ogre", 15, 10, 8, ogrePic, 10);
        enemyList.add(ogre);
        Enemy warlock = new Enemy("Warlock", 7, 7, 3, warlockPic, 20);
        enemyList.add(warlock);
        Enemy skeleton = new Enemy("Skeleton", 4, 3, 1, skeletonPic, 3);
        enemyList.add(skeleton);
        Enemy zombie = new Enemy("Zombie", 3, 2, 1, zombiePic, 1);
        enemyList.add(zombie);

        return enemyList;
    }
}
