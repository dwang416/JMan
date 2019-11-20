import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** This class is a a VIEW class.  Instances of this class provide the GUI.
  * They display buttons, as well as the current positions and colors of
  * the various board pieces.  However, and instance of this class does
  * not actually do anything on its own.  For the buttons to work and the
  * board pieces to move, we need a CONTROLLER class.     */
public class JManFrame extends JFrame {
    
    /** Constants to determine screen geometry. */
    public static final int TILE_WIDTH  = 16;  // width of a tile in pixels.
    public static final int TILE_HEIGHT = 16;  // height of a tile in pixels.
    
    // The controller that communicates between the view and the model.
    private JManApp controller = null;
    
    // The panel that contains the board of the game. Class JManPanel is an "inner class", defined below.
    private JManPanel panel = null;
    
    // Buttons on the GUI
    private JButton bUp      = new JButton("Up");
    private JButton bDown    = new JButton("Down");
    private JButton bLeft    = new JButton("Left");
    private JButton bRight   = new JButton("Right");
    private JButton bNewGame = new JButton("New Game");
    
    // Box to contain the direction buttons
    private Box buttonBox   = new Box(BoxLayout.X_AXIS);
    
    // The box to contain buttonBox and the instructions
    private Box instructBox = new Box(BoxLayout.Y_AXIS);
    
    private int height = 20; // height of the game board in tiles.
    private int width  = 20; // width of the game board in tiles.
    
    /** Constructor: Initialize the view of aN h x w game board. */
    public JManFrame(int w, int h) {
        super("J*Man!!!");
        this.width  = w;
        this.height = h;
        
        // Set the preferred dimensions of the buttons
        Dimension buttondim = new Dimension(w*TILE_WIDTH/4,27);
        bUp.setPreferredSize(buttondim);
        bDown.setPreferredSize(buttondim);
        bLeft.setPreferredSize(buttondim);
        bRight.setPreferredSize(buttondim);
        bNewGame.setPreferredSize(new Dimension(w*TILE_WIDTH/2,27));
        
        // Add the direction buttons to buttonBox and set the buttonBox alignment
        buttonBox.add(bUp);
        buttonBox.add(bDown);
        buttonBox.add(bLeft);
        buttonBox.add(bRight);
        buttonBox.setAlignmentX(0);
        
        // Set the action commands for each of the buttons
        bUp.setActionCommand(JManApp.BUTTON_UP);
        bDown.setActionCommand(JManApp.BUTTON_DOWN);
        bLeft.setActionCommand(JManApp.BUTTON_LEFT);
        bRight.setActionCommand(JManApp.BUTTON_RIGHT);
        bNewGame.setActionCommand(JManApp.BUTTON_NEW_GAME);
        
        // Set up the game board display.
        panel= new JManPanel();
        
        // Place the direction buttons and the instructions into instructBox and
        // set its alignment.
        instructBox.setAlignmentX(0);
        instructBox.add(buttonBox);
        addInstructions(instructBox);
        
        // Put the board and buttons and instructions into the frame.
        getContentPane().add(bNewGame, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(instructBox, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(5,30);
        setResizable(false);
        setVisible(false);
    }
    
    /** Add to instructBox the rules of the game (a sequence of JLabels). */
    private void addInstructions(Box instructBox) {
        instructBox.add(new JLabel(" Use the four buttons to direct J*Man (the star-", SwingConstants.LEFT));
        instructBox.add(new JLabel(" like piece) to capture the other colored pieces.", SwingConstants.LEFT));
        instructBox.add(new JLabel(" J*Man can capture: ", SwingConstants.LEFT));
        instructBox.add(new JLabel("    a green piece if he is yellow,", SwingConstants.LEFT));
        instructBox.add(new JLabel("    a yellow piece if he is red, ", SwingConstants.LEFT));
        instructBox.add(new JLabel("    a red piece if he is green.", SwingConstants.LEFT));
        instructBox.add(new JLabel(" Walkers (triangles) wander randomly about.", SwingConstants.LEFT));
        instructBox.add(new JLabel(" Pillars (circles) change color occasionally.", SwingConstants.LEFT));
        instructBox.add(new JLabel(" Nothing can enter a block (white square).", SwingConstants.LEFT));
        instructBox.add(new JLabel(" Be careful. With patience, you can always capture ", SwingConstants.LEFT));
        instructBox.add(new JLabel(" a pillar, but capturing all walkers requires ", SwingConstants.LEFT));
        instructBox.add(new JLabel(" thinking ahead. Good Luck!", SwingConstants.LEFT));
    }
    
    /** Add a JManApp to communicate with both user and the Model */
    public void addController(JManApp controller) {
        if (this.controller == null) {
            this.controller = controller;
            bUp.addActionListener(controller);
            bDown.addActionListener(controller);
            bLeft.addActionListener(controller);
            bRight.addActionListener(controller);
            bNewGame.addActionListener(controller);
        } else {
            throw new IllegalStateException("Attempt to add controller when one is already registered with view");
        }
    }
    
    /** Remove the active JManApp.  This method is useful for reseting the game. */
    public void removeController() {
        if (controller != null) {
            bUp.removeActionListener(controller);
            bDown.removeActionListener(controller);
            bLeft.removeActionListener(controller);
            bRight.removeActionListener(controller);
            bNewGame.removeActionListener(controller);
            controller = null;
        } else {
            throw new IllegalStateException("Attempt to remove controller when none is currently registered with view");
        }
    }
    
    /** Instruct the inner panel to repaint.  Used for animation. */
    public void repaint() {
        panel.repaint();
    }
    
    /* Inner class to take care of the graphics. 
     * The beauty of inner classes is that they are a separate class that has access 
     * to all of the fields of the parent class (in this case, width and height are 
     * from the JManFrame class). This is a powerful programming technique, but a bit
     * difficult to master.  We present it here without further comment.
     */
    private class JManPanel extends JPanel{
        /* Constructor: a new panel whose dimensions are given by outer class. */
        public JManPanel() {
            setPreferredSize(new Dimension(TILE_WIDTH*width, TILE_HEIGHT*height));
        }
        
        /* Paint the game board. */
        public void paint(Graphics g) {
            //paint the background
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, TILE_WIDTH*width, TILE_HEIGHT*height);
            
            // Only continue if there is a controller
            if (controller == null) { 
                return;
            }
            
            // Paint each element of the game board
            JManBoard board = controller.getBoard();
            for (int i= 0; i < width; i= i+1) {
                for (int j= 0; j < height; j= j+1) {
                    // tile (i, j) is in pixels (h..h1-1, v..v1-1)
                    int h  = i * TILE_WIDTH;
                    int h1 = (i+1) * TILE_WIDTH;
                    int v  = j * TILE_HEIGHT;
                    int v1 = (j+1) * TILE_HEIGHT;
                    
                    Piece piece = board.pieceAt(i,j);
                    if (piece != null) {
                        g.setColor(piece.getColor());
                        if (piece.getType() == Piece.BLOCK) {
                            // Tile is a block; fill it with a square.
                            g.fillRect(h+1, v, TILE_WIDTH-2, TILE_HEIGHT-2);
                        } else if (piece.getType() == Piece.JMAN){
                            // Fill J*Man's square with J*Man's Asterix Icon.
                            g.drawLine(h+3, v+2, h1-3, v1-2);
                            g.drawLine(h+3, v1-2, h1-3, v+2);
                            g.drawLine(h+1, v+TILE_HEIGHT/2, h1-1, v+TILE_HEIGHT/2);
                            g.drawLine(h+TILE_WIDTH/2, v+1, h+TILE_WIDTH/2, v1-1);
                        } else if (piece.getType() == Piece.WALKER){
                            //Tile is a walker, fill it with an appropriate colored triangle.
                            g.fillPolygon(new int[]{h+1, h1-1, h+TILE_WIDTH/2},
                                          new int[]{v1-2, v1-2, v}, 3);
                        } else if (piece.getType() == Piece.PILLAR){
                            // Tile is a pillar, fill it with an appropriate colored disk.
                            g.fillOval(h+1, v, TILE_WIDTH-2, TILE_HEIGHT-2);
                        }
                    }
                }
            }
        }
    } // End of inner class JManPanel
}