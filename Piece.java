import java.awt.*;

/** This class is a MODEL class; an instance of this class represents
  * a board piece, storing data such as the piece type, the current 
  * position and the color.  The most important method in this class 
  * is act(), which is used to update the state of the model at each 
  * time the user hits a button. This methods requires a JManBoard 
  * parameter so that our actions can take place in the context of 
  * the larger gameboard.
  * Note also that act() is an abstract method.  We cannot tell
  * how a piece is actually going to act until we know what type
  * it is.  This illustrates the importance of an abstract class.
  */
public abstract class Piece {
    
    /** Constant used to identify a Block. */
    public static final int BLOCK= 0;  // piece is a block
    /** Constant used to identify J*man. */
    public static final int JMAN= 1;   // piece is the J*man
    /** Constant used to identify a Walker. */
    public static final int WALKER= 2; // piece is a walker
    /** Constant used to identify a Pillar. */
    public static final int PILLAR= 3; // piece is a pillar

    /* class invariants that define properties of a Piece instance */
    int type;               // 0 - block, 1 - Jman, 2 - walker, 3 - pillar
    int x;                  // x coordinate
    int y;                  // y coordinate
    Color color;            // color of the piece
    boolean acted = false;  // whether the piece has acted in current around
        
    /** Constructor: a Piece whose fields are the default values.  This
      * is a dummy constructor so that the application will compile
      * the first time you try it.  You are free to (and should) remove 
      * this constructor once the other two are working.  
    public Piece() {
    }                                                  */
    
    /** Constructor: a Piece with type t and color c, to be placed on board at
      * the position (x, y)
      * Precondition: t is one of the four constants of this class:
      * BLOCK, JMAN, WALKER, or PILLAR. (x, y) is a nonnegative position, and 
      * c is one of Color.red, Color.green, Color.yellow, and Color.white */
    public Piece(int t, int x, int y, Color c) {
        type = t;
        this.x = x;
        this.y = y;
        color = c;
    }

    /** Yields: this piece's type, either BLOCK, JMAN, WALKER, or PILLAR. */
    public int getType() {
        return type;
    }

    /** Yields: this piece's x location on the board. */
    public int getX() {
        return x;
    }

    /** Set this piece's x location on the board to x. */
    public void setX(int x) {
        this.x = x;
    }

    /** Yields: this piece's y location on the board. */
    public int getY() {
        return y;
    }

    /** Set this piece's y location on the board to y. */
    public void setY(int y) {
        this.y = y;
    }

    /** Yields: this piece's color. */
    public Color getColor() {
        return color;
    }

    /** Set this piece's color to c.
      * Precondition: c is Color.white and this is a block, OR
      * c is Color.red, Color.green, or Color.yellow. */
    public void setColor(Color c) {
        color = c;
    }

    /** Yields: the color of this piece, as a capitalized word (e.g. "Red").
      * Yields: the empty string if it is not one of objects Color.red,
      * Color.green, Color.yellow, and Color.white          */
    public String getColorWord() {
        if (color == Color.red) return "Red";
        else if (color == Color.green) return "Green";
        else if (color == Color.yellow) return "Yellow";
        else if (color == Color.white) return "White";
        else return "";
    }

    /** Yields: "This piece has already acted this round". */
    public boolean hasActed() {
        return acted;
    }

    /** Set the state of this piece to the value of acted.*/
    public void setActed(boolean acted) {
        this.acted = acted;
    }

    /** Make this piece take one action on the provided board.
      * What a piece does on its action is defined in the instructions. 
      * Precondition: The provided board includes this piece at position (x,y).    
      */
    public abstract void act(JManBoard board);
    
}