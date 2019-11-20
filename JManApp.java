import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** This class is a CONTROLLER class; an instances of this class start
  * the game and allocate all of the other necessary objects.  They
  * also communicate between the view and the model via call-back
  * functions (e.g. Listeners).         */
public class JManApp implements ActionListener {
    
    /** Constants that define button functionality in the game */
    /** Command to create a new game. */
    public static final String BUTTON_NEW_GAME = "button_new";
    /** Command to move up. */
    public static final String BUTTON_UP       = "button_up";
    /** Command to move down. */
    public static final String BUTTON_DOWN     = "button_down";
    /** Command to move left. */
    public static final String BUTTON_LEFT     = "button_left";
    /** Command to move right. */
    public static final String BUTTON_RIGHT    = "button_right";
        
    private JManBoard board;  // The game board
    private JManFrame view;   // The application view.
    
    /** Application main. Initializes a new game. */
    public static void main(String[] pars) {
        new JManApp();
    }
    
    /** Constructor: a default 20 x 20 game with 10 walkers, 10 pillars,
      * and 20 blocks placed randomly throughout the game board. 
      * J*Man is at position (0, 0), and all other pieces are placed 
      * randomly about the aboard */
    public JManApp(){
        this(JManBoard.DEFAULT_WIDTH,  JManBoard.DEFAULT_HEIGHT, 
             JManBoard.DEFAULT_BLOCKS, JManBoard.DEFAULT_WALKERS, 
             JManBoard.DEFAULT_PILLARS);
    }
    
    /** Constructor: a game with an h x w game board with bl blocks, wa walkers, 
      * and pi pillars. 
      * J*Man is at position (0, 0), and all other pieces are placed randomly.
      * Precondition: number of pieces specified is <= h*w. */
    public JManApp(int h, int w, int bl, int wa, int pi) {
        // Create a new view and register this as a listener
        view = new JManFrame(w,h);
        
        // Create the game board, put J*Man in (0,0), and
        // put the rest of the pieces randomly on the JManGUI.
        board = new JManBoard(w,h,bl,wa,pi);
        view.addController(this);        
        view.setVisible(true);
    }
    
    public JManBoard getBoard() {
        return board;
    }
    
    /** Process a button push and then repaint the game board.
      * If the button was newGame, open a dialog and ask whether a new game
      * is desired and act accordingly.
      * 
      * If the button was bUp, bDown, bLeft, or bRight, save the direction for
      * processing by JMan in field nextJManDirection, make JMan act, and
      * then make all other pieces act.
      *
      * This method is synchronized to make sure only one button push is being
      * handled at a time. */
    public synchronized void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(BUTTON_NEW_GAME)) {
            if (JOptionPane.showConfirmDialog(view, 
                                              "Start a new game of the current size?",
                                              "New Game?",
                                              JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                System.out.println(e);
                view.removeController(); // This is going to take a while.
                board = new JManBoard();
                view.addController(this);
                view.repaint();          // Repaint the board & exit handler
                return;
            }
        }
        
        /** Set field nextJManDirection to the direction in which JMan should move.
          *  It should be one of the JManBoard constants MOVE_UP, MOVE_DOWN, 
          *  MOVE_LEFT, or MOVE_RIGHT */
        if (e.getActionCommand().equals(BUTTON_UP)) {
            board.changeJManDirection(JManBoard.MOVE_UP);
        } else if (e.getActionCommand().equals(BUTTON_DOWN)) {
            board.changeJManDirection(JManBoard.MOVE_DOWN);
        } else if (e.getActionCommand().equals(BUTTON_LEFT)) {
            board.changeJManDirection(JManBoard.MOVE_LEFT);
        } else if (e.getActionCommand().equals(BUTTON_RIGHT)) {
            board.changeJManDirection(JManBoard.MOVE_RIGHT);
        } else {
            throw new RuntimeException("Unknown button pressed in Application J*Man");
        }
        
        board.act();    // Move the computer controller pieces.
        view.repaint(); // Redisplay for the user.
    }
    
    /** Yields: a random integer in the range lo..hi, with all integers in
      * the range being equally likely. 
      * Precondition lo < hi.               */
    public static int rand(int lo, int hi) {
        return (int)(Math.random()*(hi-lo+1))+lo;
    }
 
}