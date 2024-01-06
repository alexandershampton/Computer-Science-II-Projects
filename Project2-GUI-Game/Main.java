//Alexander Shampton
//CS-1181L-07
//Due: 3/13/22

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//imports for the audio file
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

class Main {
    // fields for the audio clip
    Long currentFrame;
    Clip clip;
    String status;
    AudioInputStream audioInputStream;

    // Song: Pushin P By Gunna and Future Featuring Young Thug
    static String filePath = "Pushin P.wav";

    public Main() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // create AudioInputStream object
        // https://www.geeksforgeeks.org/play-audio-file-using-java/
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        // Instructions Frame For Start of game
        JFrame instructions = new JFrame("Instructions");
        JLabel instructionHeaderLabel = new JLabel("                       Instructions");
        instructionHeaderLabel.setFont(new Font("SERIF", Font.BOLD,30));
        JLabel instructionLabel = new JLabel(
                " -To play this game press or hold the spacebar to jump and don't get hit by the poles!");
        JLabel goalLabel = new JLabel(" -Goal: Try to last as long as you can without hitting the poles or ground!");
        JLabel howToBePLabel = new JLabel(" -If you score above 10, you are pushin' P!");
        JButton startButton = new JButton("START GAME");

        // Instuction Panel and Adding components
        JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new GridLayout(5, 1, 10, 1));
        instructionPanel.add(instructionHeaderLabel);
        instructionPanel.add(instructionLabel);
        instructionPanel.add(goalLabel);
        instructionPanel.add(howToBePLabel);
        instructionPanel.add(startButton);
        instructions.add(instructionPanel);
        instructions.setVisible(true);
        instructions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructions.setSize(510, 500);
        instructions.pack();
        instructions.setResizable(false);

        // Listener for startButton, which starts game and music
        startButton.addActionListener(e -> {
            instructions.dispose();
            createGame();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        });
    }

    // Creates the game when called
    public void createGame() {
        JFrame frame = new JFrame("Pushin P");

        MyPanel pushinP = new MyPanel();

        frame.getContentPane().add(pushinP);

        // listener for space bar
        frame.addKeyListener(new KeyListener() {
            // if the key is pressed, p goes up
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 32 && !pushinP.gameOver()) {
                    pushinP.goUp();
                }
            }

            // if the key is released, p goes down
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 32 && !pushinP.gameOver()) {
                    pushinP.goDown();
                }
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        // Timer for the pipes and P
        Timer timer = new Timer(4, e -> {
            pushinP.updatePostition();
            pushinP.repaint();
            pushinP.gameOver();
            pushinP.scoreCounter();
        });
        timer.setRepeats(true);
        timer.start();

        // adding to the frame and configuring the frame
        frame.getContentPane().add(pushinP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // Starts the instructions gui
    public static void main(String args[]) throws Exception {
        new Main();
    }
}