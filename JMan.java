import java.awt.*;

/** This class is a MODEL class; an instance of this class represents
  * a J*Man, storing data such as current position and color.
  * However, this class does have one method that provides CONTROLLER
  * functionality -- the method act().  Each board piece needs to act
  * differently, therefore it makes sense to put the code for the
  * action in the class representing the board piece.  Technically
  * this violates the Model-View-Controller separation, but it 
  * illustrates a more important point: MVC is more of a guideline
  * than a rule.  If following the rule exactly makes your program
  * harder to understand, you can relax the rules.       */
public class JMan extends Piece {
    
    /** Constructor: a new J*Man at position (x, y) with given color c.
      * The initial color is Color.red if c = 0, Color.green if c = 1, 
      * and Color.yellow if c = 2.
      * Precondition: (x, y) is a nonnegative coordinate and the value c 
      * is in the range 0..2         */
    public JMan(int x, int y, int c) {
        super(Piece.JMAN, x, y, (c == 0 ? Color.red : (c == 1 ? Color.green : Color.yellow)) );
    }
    
    /** Constructor: a new J*Man at position (x, y) with given color c.
      * Precondition: (x, y) is a nonnegative coordinate and the color c
      * is one of Color.red, Color.green, and Color.yellow.
      */
    public JMan(int x, int y, Color c) {
        super(Piece.JMAN, x, y, c);
    }
    
    /** Yields: representation of this piece */
    public String toString() {
        String color= "";
        return getColorWord() + " J*Man at (" + getX() + ", " + getY() + ")";
    }
    
    /** If possible, move JMan in the direction given by the field
      *  nextManDirection in the provided controller. This should be one of the 
      *  JManApp constants MOVE_UP, MOVE_DOWN, MOVE_LEFT, and MOVE_RIGHT.
      *  Precondition: The board for the provided controller includes this
      *  piece at the position (x,y).    */
    public void act(JManBoard board) {
        int move = board.nextJManDirection();
        int xx = x, yy = y;         // the potential new location after moving

        if (move == 1) yy--;        // move up
        else if (move == 2) yy++;   // move down
        else if (move == 3) xx--;   // move left
        else xx++;                  // move right

        if (!board.isOnBoard(xx, yy)) return;
        Piece p = board.pieceAt(xx, yy);
        if (p == null) {
            // simply move JMan to the empty place without capturing
            board.move(x, y, xx, yy);
        } else if (p.type == Piece.BLOCK) {
            // cannot move into a block
            return;
        } else {
            // check if JMan can capture and then move to Pillar or Walker
            Color pColor = p.color;
            if (canEat(pColor)) {
                board.move(x, y, xx, yy);
                setColor(pColor);
            }
        }

        
    }
    
    /* Yields: "Piece p's color is the color J*Man can currently capture". */
    /** A green J*Man can only capture red pieces
        A red J*Man can only capture yellow pieces
        A yellow J*Man can only capture green pieces */
    private boolean canEat(Color c) {
        if ((color == Color.green && c == Color.red) ||
            (color == Color.red && c == Color.yellow) || 
            (color == Color.yellow && c == Color.green)) return true;
        else return false;
    }
}
