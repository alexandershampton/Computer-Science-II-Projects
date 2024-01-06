//Alexander Shampton
//CS-1181L-07
//Due: 3/13/22

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MyPanel extends JPanel {
    // coordinates for P
    private int pXCoord = 476;
    private int pYCoord = 150;

    // the change in coordinates for P
    private int deltaPX;
    private int deltaPY;

    // width and height for P
    private int pWidth = 20;
    private int pHeight = 20;

    // pipe coordinates
    private int pipeXCoord = 954;
    private int pipeYCoord = 200;

    // the change in pipe coordinates
    private int deltaPipeX = 2;

    // width for both the pipes
    private int pipeWidth = 50;

    // variables that track the score and if the game is over or not
    private boolean gameOverVar;
    private int score;
    
    // Changes the deltaPY to a negative 2, which brings the P up
    public void goUp() {
        deltaPY = -2;
    }

    // Changes the deltaPY to 1, which brings the P down
    public void goDown() {
        deltaPY = 1;
    }

    // Updates the postion of the pipe and P
    public void updatePostition() {

        // Checks if game is over and if over, it changes how the P moves
        Random random = new Random();
        if (gameOver()) {
            // when the P hits the ground its deltax is set to the pipe's
            if (pYCoord >= 380) {
                deltaPY = 0;
                pYCoord = 380;
                // Checks which way the pipe is going so the P can get pushed by it
                if (deltaPipeX / 2 == 1) {
                    deltaPX = 2;
                } else {
                    deltaPX = -2;
                }

                // Checks if the P gets hit on the inside of the pipe, and sets its position to
                // the top of the bottom pipe
            } else if ((pipeYCoord <= pYCoord + pHeight)
                    && ((pXCoord < pipeXCoord + pipeWidth) && (pXCoord + pWidth > pipeXCoord))) {
                deltaPY = 0;
                pYCoord = pipeYCoord - pHeight;
            }
        }

        // Makes the P not go out of the top of the screen, if user tries, it bounces
        // back
        if (pYCoord < 0 || pYCoord > super.getSize().getHeight()) {
            deltaPY = -deltaPY;
        }

        // When the P has hit something and ended the game, the P will continue to go
        // off the screen
        if (pXCoord < 0 || pXCoord > super.getSize().getWidth()) {
            if (pXCoord <= 0) {
                deltaPX = 2;
            } else {
                deltaPX = -2;
            }
        }

        // When the pipe goes off the screen, it bounces back with different yCoords
        if (pipeXCoord < -49 || pipeXCoord > super.getSize().getWidth()) {
            deltaPipeX = -deltaPipeX;
            pipeYCoord = (random.nextInt(300) + 80);
        }

        // Moves the P either down or up, depending on the value of deltaPY
        pYCoord += deltaPY;

        // Moves the pipe left or right, depending on the value of deltaPipeX
        pipeXCoord -= deltaPipeX;

        // Moves the P left or right when game is over, depending on the value of
        // deltaPX
        pXCoord -= deltaPX;
    }

    // Score goes up everytime the P goes between and through the pipes
    public void scoreCounter() {
        if (((pYCoord > pipeYCoord - 80) && (pYCoord + pHeight < pipeYCoord)) &&((pXCoord==pipeXCoord+pipeWidth) && (deltaPipeX/2 == 1) || (pXCoord+pWidth==pipeXCoord) && (deltaPipeX/2 == -1))&& !gameOver()) {
            score += 1;
        }
    }

    // Checks if game is over returns true if over
    public boolean gameOver() {
        // checks the cordinates of the pipe and P and see if they intersect
        if (pYCoord >= 380 || ((pYCoord <= pipeYCoord - 80) || (pYCoord + pHeight >= pipeYCoord))
                && ((pXCoord <= pipeXCoord + pipeWidth) && (pXCoord + pWidth >= pipeXCoord))) {
            gameOverVar = true;
            deltaPY = 2;
            deltaPX = deltaPipeX;
            return (true);

            // helps with the game over string
        } else if (gameOverVar) {
            return (true);

        } else {
            gameOverVar = false;
            return (false);
        }
    }

    // paints and creates all shapes
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // BackGround
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 1000, 500);

        // Sun
        g.setColor(Color.ORANGE);
        g.fillOval(900, 0, 100, 100);

        // Score Tracker
        g.setColor(Color.WHITE);
        g.setFont(new Font("SERIF", Font.BOLD, 100));
        g.drawString("Score: " + score, 300, 100);

        // Bottom Pipe
        g.setColor(Color.GREEN);
        g.fillRect(pipeXCoord, pipeYCoord, pipeWidth, 1000);

        // Top Pipe
        g.setColor(Color.GREEN);
        g.fillRect(pipeXCoord, pipeYCoord - 580, pipeWidth, 500);

        // Dirt
        g.setColor(new Color(210, 105, 30));
        g.fillRect(0, 400, 1000, 100);

        // Grass
        g.setColor(Color.GREEN);
        g.fillRect(0, 400, 1000, 20);

        // P Icon
        Graphics2D g2 = (Graphics2D) g;
        Image img1 = Toolkit.getDefaultToolkit().getImage("P.png");
        g2.drawImage(img1, pXCoord, pYCoord, pWidth, pHeight, Color.BLUE, this);

        // When Game is Over, new strings appear on screen
        if (gameOverVar) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("SERIF", Font.BOLD, 50));
            g.drawString("Game Over!", 350, 200);

            // The user is not P if the score is below 10, and is displayed
            if ((score < 10)) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("SERIF", Font.BOLD, 100));
                g.drawString("You Are Not", 160, 290);
                g2.drawImage(img1, 720, 200, 100, 100, Color.BLUE, this);

            // The user is P if the score is 10 or more, and is displayed
            } else if ((score >= 10)) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("SERIF", Font.BOLD, 100));
                g.drawString("You Are Pushin'", 110, 290);
                g2.drawImage(img1, 830, 200, 100, 100, Color.BLUE, this);
            }
        }
    }
}
