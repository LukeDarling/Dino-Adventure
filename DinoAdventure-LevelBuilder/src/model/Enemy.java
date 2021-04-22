package model;

import java.io.*;
import javafx.beans.property.*;

public class Enemy extends Entity {

    protected EnemyState state;
    protected EnemyState type;
    protected int tps = 1;

    protected IntegerProperty healthProperty = new SimpleIntegerProperty();

    public Enemy() { }

    public Enemy(double x, double y) {
        centerPoint.xProperty().set(x);
        centerPoint.yProperty().set(y);
    }

    public Enemy(double x, double y, EnemyState type) {
        centerPoint.xProperty().set(x);
        centerPoint.yProperty().set(y);
        this.type = type;
    }

    public int getHealth() {
        return healthProperty.get();
    }

    public void setHealth(int health) {
        healthProperty.set(health);
    }

    public IntegerProperty healthProperty() {
        return healthProperty;
    }

    public EnemyState getState() {
        return state;
    }

    public void setState(EnemyState state) {
        this.state = state;
    }

    public EnemyState getType() {
        return type;
    }

    public void setType(EnemyState type) {
        this.type = type;
    }

    /**
     * gets the type of the enemy and returns a string value of it's type
     * @return String value of enemy type 
     */
    public String getTypeString() {
        if (type == EnemyState.WANDERING) {
            return "WANDERING";
        }
        else if (type == EnemyState.FOLLOWING) {
            return "FOLLOWING";
        }
        else if (type == EnemyState.STANDING) {
            return "STANDING";
        }
        else if (type == EnemyState.JUMPING) {
            return "JUMPING";
        }
        else if (type == EnemyState.FLEEING) {
            return "FLEEING";
        }
        return "";
    }

    // writes the each property to the DataOutputStream passed in the parameters of the enemy to the file to be saved.
    public void serialize(DataOutputStream writer) throws IOException{
            writer.writeDouble(centerPoint().getX());
            writer.writeDouble(centerPoint().getY());
            writer.writeInt(state.ordinal());
            if (super.direction != null){
            writer.writeInt(super.direction.ordinal());
            }
            else{
                writer.writeInt(direction.ordinal());
            }
            writer.writeInt(healthProperty.intValue());
        }

    // reads the DataOutputStream passed in the parameters and sets the Game model accordingly.
    public void deserialize(DataInputStream reader) throws IOException{
        centerPoint().setX(reader.readDouble()); 
        centerPoint().setY(reader.readDouble());
        state = EnemyState.values()[reader.readInt()];
        direction = EntityDirection.values()[reader.readInt()];
        healthProperty = new SimpleIntegerProperty(reader.readInt());
        
    }

    public void setType(String type) {
        this.type = EnemyState.valueOf(type);
    }
    
}
