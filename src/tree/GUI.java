package tree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI{
    JFrame frame; //main frame
    JTextArea message, message2;//message display for results
    JButton open, close;//function buttons
    JPanel southPanel,northPanel,eastPanel,westPanel;//panels for frame
    JFileChooser chooser;//file chooser
    File currentFile;//file
    Tree runPro;//run program instance
    JScrollPane scroll, scroll2;//for scroll bars if needed
    JLabel key, code;//lables for results
    JMenuBar menuBar;//menu bar
    JMenu help;//menu selection
    JMenuItem programDiscription;//program description

    
    public GUI(){
        frame = new JFrame();
        message = new JTextArea(20,45);
        message2 = new JTextArea(20,25);
        key = new JLabel("Key");
        code = new JLabel("Code");
        scroll = new JScrollPane(message, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,+
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll2 = new JScrollPane(message2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,+
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        message.setVisible(false);
        message2.setVisible(false);
        menuBar = new JMenuBar();
        help = new JMenu("How to use this program");
        programDiscription = new JMenuItem("Program Description");
        open = new JButton("Open");
        close = new JButton("Close");
        southPanel = new JPanel();
        northPanel = new JPanel();
        eastPanel = new JPanel();
        westPanel = new JPanel();
        chooser = new JFileChooser();

        northPanel.add(menuBar);
        eastPanel.add(code);
        westPanel.add(key);
        message.setEditable(false);
        message2.setEditable(false);
        
        eastPanel.add(scroll);
        westPanel.add(scroll2);
        
        menuBar.add(help);
        help.add(programDiscription);
        
        frame.setBounds(600, 200, 900, 450);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.add(eastPanel, BorderLayout.EAST);
        frame.add(westPanel, BorderLayout.WEST);
        frame.add(northPanel, BorderLayout.NORTH);

        southPanel.add(open);
        southPanel.add(close);
        
        frame.setVisible(true);
        
        buttonFunctions();
    }
    public void buttonFunctions(){
        open.addActionListener((ActionEvent e) -> {
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                try{
                    currentFile = chooser.getSelectedFile();
                    runPro = new Tree(currentFile);
                    showResults();
                    }catch(NullPointerException ioe){
                         JOptionPane.showMessageDialog(null, "Invalid File.");
                    
                }
            }
        });
        close.addActionListener((ActionEvent c) -> {
            System.exit(0);
        });
        programDiscription.addActionListener((ActionEvent h) ->{
            JFrame helpFrame = new JFrame("How to use this program");
            JTextArea howTo = new JTextArea();
            helpFrame.add(howTo);
            howTo.setText("This program works by building a stucture of words with \n"+
                    "their coorisponding code keys, passed in by a correctly formatted \n"+
                    "text document. The program knows when the keys stop and the \n"+
                    "encoded messages begin by detecting an astericks(*). It will then\n go into "+
                    "decoding mode by checking the code against the keys and\n display the "+
                    "decoded message.");
            howTo.setEditable(false);
            helpFrame.setBounds(600, 200, 400, 175);
            helpFrame.setVisible(true);
        });
    }
    public void showResults(){
       message.setText(runPro.getFinalDisplay());
       message2.setText(runPro.getBuildingTheTree());
       message.setVisible(true);
       message2.setVisible(true);
    }
}