import java.awt.*;

/** This class is a MODEL class; an instance of this class represents
  * a Pillar, storing data such as current position and color.
  * However, this class does have one method that provides CONTROLLER
  * functionality -- the method act().   */
  
public class Pillar extends Piece {

    /** Constructor: a new Pillar at position (x, y) with given color c.
      * The initial color is Color.red if c = 0, Color.green if c = 1, 
      * and Color.yellow if c = 2.
      * Precondition: (x, y) is a nonnegative coordinate and the value c 
      * is in the range 0..2         */
    public Pillar (int x, int y, int c) {
        super(Piece.PILLAR, x, y, (c == 0 ? Color.red : (c == 1 ? Color.green : Color.yellow)) );
    }

    /** Constructor: a new Pillar at position (x, y) with given color c.
      * Precondition: (x, y) is a nonnegative coordinate and the color c
      * is one of Color.red, Color.green, and Color.yellow.
      */
      public Pillar (int x, int y, Color c) {
        super(Piece.PILLAR, x, y, c);
    }

    /** Yields: representation of this piece */
    public String toString() {
        String color= "";
        return getColorWord() + " Pillar at (" + getX() + ", " + getY() + ")";
    }

    /** a pillar has 1/3 probability of choosing a color again,
      * which may be the same as old color      */
    public void act(JManBoard board) {
        int r = JManApp.rand(0, 2);
        if (r == 0) {
            int c = JManApp.rand(0, 2);
            Color color = c == 0 ? Color.red : (c == 1 ? Color.green : Color.yellow);
            setColor(color);
        }
    }

}