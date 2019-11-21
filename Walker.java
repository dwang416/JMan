import java.awt.*;

/** This class is a MODEL class; an instance of this class represents
  * a Walker, storing data such as current position and color.
  * However, this class does have one method that provides CONTROLLER
  * functionality -- the method act().   */
  
public class Walker extends Piece {

    /** Constructor: a new Walker at position (x, y) with given color c.
      * The initial color is Color.red if c = 0, Color.green if c = 1, 
      * and Color.yellow if c = 2.
      * Precondition: (x, y) is a nonnegative coordinate and the value c 
      * is in the range 0..2         */
    public Walker (int x, int y, int c) {
        super(Piece.WALKER, x, y, (c == 0 ? Color.red : (c == 1 ? Color.green : Color.yellow)) );
    }

    /** Constructor: a new Walker at position (x, y) with given color c.
      * Precondition: (x, y) is a nonnegative coordinate and the color c
      * is one of Color.red, Color.green, and Color.yellow.
      */
      public Walker (int x, int y, Color c) {
        super(Piece.WALKER, x, y, c);
    }

    /** Yields: representation of this piece */
    public String toString() {
        String color= "";
        return getColorWord() + " Walker at (" + getX() + ", " + getY() + ")";
    }

    /** a walker has 1/3 probability of walking to a 4-direction neighbour */
    public void act(JManBoard board) {
        int r = JManApp.rand(0, 2);
        if (r == 0) {
            int move = JManApp.rand(1, 4);
            int xx = x, yy = y;         // the potential new location after moving
    
            if (move == 1) yy--;        // move up
            else if (move == 2) yy++;   // move down
            else if (move == 3) xx--;   // move left
            else xx++;                  // move right
    
            if (board.isEmpty(xx, yy)) board.move(x, y, xx, yy);
        }
    }

}