import java.awt.*;

/** This class is a MODEL class; an instance of this class represents
  * the state of the game board, including all of its pieces and
  * their current positions. Instances of this class are the primary
  * reference of the controller and view.  All other models (such as
  * the J*Man or obstacles) are managed by this model.     */
public class JManBoard {
    
    /** Constants that indicate the next direction in which JMan should move */
    /** Jman should move up. */
    public static final int MOVE_UP    = 1;
    /** Jman should move down. */
    public static final int MOVE_DOWN  = 2;
    /** Jman should move left. */
    public static final int MOVE_LEFT  = 3;
    /** Jman should move right. */
    public static final int MOVE_RIGHT = 4;

    /** The next direction in which JMan should move.
      * One of the constants MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT */
    private int nextJManDirection; 
    
    private Piece[][] board;  // 2-d array of Pieces that makes up the game.
    private int height;       // height of the game board in tiles.
    private int width;        // width of the game board in tiles.
    
    private JMan jMan;        // The J*Man piece in this game board.

    /** Default game parameters */
    public static final int DEFAULT_WIDTH   = 20;  // Width of a new game
    public static final int DEFAULT_HEIGHT  = 20;  // Height of a new game
    public static final int DEFAULT_PILLARS = 10;  // # of pillars a new game
    public static final int DEFAULT_WALKERS = 10;  // # of walkers a new game
    public static final int DEFAULT_BLOCKS  = 20;  // # of blocks a new game

    /** Constructor: a default 20 x 20 game board with 10 walkers, 10 
      * pillars,and 20 blocks placed randomly throughout the game board. 
      * J*Man is at position (0, 0), and all other pieces are placed 
      * randomly about the aboard */
    public JManBoard() {
        this(DEFAULT_WIDTH,DEFAULT_HEIGHT,DEFAULT_BLOCKS, DEFAULT_WALKERS, DEFAULT_PILLARS);
    }

    /** Constructor: an h x w game board with 10 walkers, 10 pillars,
      * and 20 blocks placed randomly throughout the game board. 
      * J*Man is at position (0, 0), and all other pieces are placed 
      * randomly about the aboard */
    public JManBoard(int w, int h) {
        this(w,h,DEFAULT_BLOCKS, DEFAULT_WALKERS, DEFAULT_PILLARS);
    }
    
    /** Constructor: an h x w game board with bl blocks, wa walkers, 
      * and pi pillars. 
      * J*Man is at position (0, 0); all other pieces are placed randomly.
      * Precondition: number of pieces specified is <= h*w. */
    public JManBoard(int w, int h, int bl, int wa, int pi) {
        width  = w;
        height = h;
        board = new Piece[w][h];
        placePiece(1, 0, 0);
        initializeBoard(bl, wa, pi);
    }
    
    /** Yields: the width of this board in grid squares */
    public int getWidth() {
        return width;
    }

    /** Yields: the height of this board in grid squares */
    public int getHeight() {
        return height;
    }
    
    /** Yields: "(x, y) is on the board". */
    public boolean isOnBoard(int x, int y) {
        return x > -1 && y > -1 && x < width && y < height;
    }
    
    /** Yields: "(x, y) is on the board and does not contain a piece". */
    public boolean isEmpty(int x, int y) {
        return isOnBoard(x,y) && board[x][y] == null;
    }
    
    /** Yields: the Piece at position (x, y) of the board
      * (null if (x, y) is outside the board or contains null). */
    public Piece pieceAt(int x, int y) {
        if(isOnBoard(x,y)) {
            return board[x][y];
        }
        return null;
    }
    
    /** Yields: the (unique) J*Man piece on this game board.*/
    public JMan getJMan() {
        return jMan;
    }
    
    /** Yields: the next direction in which JMan should move. This is one of
      * the constants MOVE_UP, MOVE_DOWN, MOVE_LEFT, and MOVE_RIGHT. */
    public int nextJManDirection() {
        return nextJManDirection;
    }
    
    /** Change the next direction in which JMan should move to direction d.
      * Precondition: d is one of the constants MOVE_UP, MOVE_DOWN,
      * MOVE_LEFT, and MOVE_RIGHT. */
    public void changeJManDirection(int d) {
        nextJManDirection = d;
    }
    
    /** Move the Piece at (fromX, fromY) to (toX, toY) on the board,
      * changing the position at (fromX, fromY) to null; change the position 
      * in the Piece accordingly. The piece originally in (toX, toY) is 
      * permanently deleted.
      * Precondition:
      * 1. (toX, toY) is on the board.
      * 2. The move is allowed by the game. */
    public void move(int fromX, int fromY, int toX, int toY) {
        board[toX][toY]= board[fromX][fromY];
        board[fromX][fromY]= null;
        board[toX][toY].setX(toX);
        board[toX][toY].setY(toY);
    }

    /** Make every piece on the board act once, with JMan acting first.
      *  When all have acted, reset all their has-acted flags to false.
      * Precondition: the has-acted flag is false for all pieces on the board. */
    public void act() {
        jMan.act(this);
        jMan.setActed(true);
        
        // Make every other piece act.
        for (int i= 0; i < width; i= i+1){
            for (int j= 0; j < height; j= j+1){
                Piece p= board[i][j];
                if (p != null  &&  !p.hasActed()){
                    p.act(this);
                    p.setActed(true);
                }
            }
        }
        
        // Set all the act flags to false.
        for (int i= 0; i < width; i= i+1){
            for (int j= 0; j < height; j= j+1){
                Piece piece = board[i][j];
                if (piece != null){
                    piece.setActed(false);
                }
            }
        }
    }

    /** Put bl block, wa walkers, and pi pillars randomly on the game board
      * Precondition. The board must have enough empty spaces for all of them. */
    private void initializeBoard(int bl, int wa, int pi) {
        // Put the blocks on the board.
        int k= 0;
        // invariant: k blocks have been added.
        while (k < bl) {
            int xx = JManApp.rand(0, width-1);
            int yy = JManApp.rand(0, height-1);
            if(isEmpty(xx, yy)) {
                placePiece(Piece.BLOCK, xx, yy);
                k= k+1;
            }
        }
        
        // Put the walkers on the board.
        k= 0;
        // invariant: k walkers have been added.
        while (k < wa) {
            int xx = JManApp.rand(0, width-1);
            int yy = JManApp.rand(0, height-1);
            if(isEmpty(xx, yy)){
                placePiece(Piece.WALKER, xx, yy);
                k= k+1;
            }
        }
        
        // Put the pillars on the board.
        k= 0;
        // invariant: k pillars have been added.
        while(k < pi) {
            int xx = JManApp.rand(0, width-1);
            int yy = JManApp.rand(0, height-1);
            if(isEmpty(xx, yy)) {
                placePiece(Piece.PILLAR, xx, yy);
                k= k+1;
            }
        }
    }
       
    /** If (x, y) is on the board and empty, create a new piece of type t;
      * put it in location (x, y) of the board.  If the new piece is J*Man,
      * then it stores the piece in the field jMan.
      * Precondition: t is one of the piece constants in class Piece.
      * If t == Piece.JMAN, then jMan is null. */
    public void placePiece(int t, int x, int y){
        if (t == Piece.JMAN) {
            int initialColor = JManApp.rand(0, 2);
            jMan = new JMan(x, y, initialColor);
            board[x][y] = jMan;
        } else if (t == Piece.BLOCK) {
            board[x][y] = new Block(x, y);
        } else if (t == Piece.WALKER) {
            int initialColor = JManApp.rand(0, 2);
            board[x][y] = new Walker(x, y, initialColor);
        } else if (t == Piece.PILLAR) {
            int initialColor = JManApp.rand(0, 2);
            board[x][y] = new Pillar(x, y, initialColor);
        } else {
            // this code block shouldn't be reached
            throw new RuntimeException("Unknown type is being placed!");
        }
    }
}