//-----------------------------------------------------------
//File:   SerializationTest.java
//Desc:   This test the save and load methods in the Game model class.
//----------------------------------------------------------- 
package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class SerializationTest {

    private String filename = "serialization.dat";

    @Test
    public void testGame_SaveAndLoad_successful() throws Exception {
        Game world = Game.instance();
        Player player = world.getPlayer();
        Game.instance().setCurrentLevel(new Level());
        Level level = world.getCurrentLevel();


        player.centerPoint().setXY(100, 200);
        player.setHealth(9);
        world.setDifficulty(DifficultyType.MEDIUM);
        WanderingEnemy enemy = new WanderingEnemy();
        enemy.setId(10);
        enemy.centerPoint().setXY(300, 250);
        enemy.setDirection(EntityDirection.RIGHT);
        assertEquals(EntityDirection.RIGHT, enemy.getDirection());
        WanderingEnemy enemy2 = new WanderingEnemy();
        enemy2.setId(2);
        enemy2.setState(EnemyState.DEAD);
        enemy2.setHealth(0);
        //enemy2.setDirection(EntityDirection.RIGHT);

        level.addEntity(enemy); level.addEntity(enemy2);
        level.setLevelName("Beginner");
        level.setHeight(1500);
        level.setWidth(2000);

        Block block = new Block();
        block.centerPoint.setXY(20, 100);
        block.setHeight(100);
        block.setWidth(500);
        block.setId(5);
        level.addBlock(block);
        player.setState(PlayerState.RUNNING);
        world.save(filename);

        // changing the player/ enemies to see if the load is really changing it back as before
        player.setHealth(2);
        player.centerPoint.setXY(50, 25);
        enemy = new WanderingEnemy();
        enemy.centerPoint.setXY(33, 33);
        enemy2 = new WanderingEnemy();
        world.setDifficulty(DifficultyType.EASY);
        block.setHeight(10);
        block.setWidth(50);
        block.centerPoint.setXY(200, 10);

        world.load(filename);
        
        //check player
        assertEquals(100, player.centerPoint().getX(), 0);
        assertEquals(200, player.centerPoint().getY(), 0);
        assertEquals(9, player.getHealth());

        //check game properties
        assertEquals(PlayerState.RUNNING, player.getState());
        assertEquals(DifficultyType.MEDIUM, world.getDifficulty());
        assertEquals(GameState.LEVEL_PLAYING, world.getState());

        //check game level
        assertEquals(1500, level.getHeight());
        assertEquals(2000, level.getWidth());
        assertEquals(2, level.getEntites().size());
        assertEquals("Beginner", level.getLevelName());
        
        // check enemies
        Enemy enemy0 = (Enemy) level.findEntity(10);
        Enemy enemyTwo = (Enemy) level.findEntity(2);
        assertEquals(2, enemyTwo.getId());
        assertEquals(enemy0.centerPoint.getX(), 300, 0);
        assertEquals(enemy0.centerPoint.getY(), 250, 0);
        assertEquals(EntityDirection.RIGHT, enemy0.direction);
        assertEquals(EntityDirection.LEFT, enemyTwo.direction);
        assertEquals(enemyTwo.getHealth(), 0);
        assertEquals(enemyTwo.getState(), EnemyState.DEAD);

        // check boxes
        Block checkedBlock = level.findBlock(5);
        assertEquals(500, checkedBlock.getWidth());
        assertEquals(100, checkedBlock.getHeight());
        assertEquals(20, checkedBlock.centerPoint().getIntX());
        assertEquals(100, checkedBlock.centerPoint().getIntY());
        block.centerPoint.setXY(20, 100);
        block.setHeight(100);
        block.setWidth(500);

    }
}
