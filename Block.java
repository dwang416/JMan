import java.awt.*;

/** This class is a MODEL class; an instance of this class represents
  * a Block, storing data such as current position and color.
  * However, this class does have one method that provides CONTROLLER
  * functionality -- the method act().   */
  
public class Block extends Piece {

    /** Constructor: a new Block at position (x, y) 
      * an Block is always colored white         */
    public Block (int x, int y) {
        super(Piece.BLOCK, x, y, Color.white);
    }

    /** Yields: representation of this piece */
    public String toString() {
        String color= "";
        return getColorWord() + " Block at (" + getX() + ", " + getY() + ")";
    }


    /* a block really does nothing */
    public void act(JManBoard board) {
        return;
  }

}