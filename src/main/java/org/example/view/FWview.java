package org.example.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * FWview class represents a view from MVC design pattern.
 * Is responsible for communication with user.
 * @author SuperStudent
 * @version  version1
 */
public class FWview implements ActionListener{



    int count = 0;

    JFrame frame;

    JPanel topPanel;
    JLabel infoLabel;
    JButton saveGraph;

    JPanel bottomPanel;
    JLabel noOfVerticesLabel;
    JButton addVertex;
    JButton removeVertex;

    JButton okButton;

    JButton readFromFileButton;

    JPanel grid;

    ArrayList <ArrayList<Integer>> resultGraph = new ArrayList <>();

    public boolean isActive()
    {
        return frame.isVisible();
    }
    public ArrayList <ArrayList<Integer>> getCreatedGraph()
    {
        return  resultGraph;
    }

    public ArrayList <String> readGraphFromFile(String fileName) {

        ArrayList<String> lines = new ArrayList<>();
        Path fpath = Paths.get(fileName);
        try {
            lines = (ArrayList<String>) Files.readAllLines(fpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj == saveGraph)
        {
            JButton btn;
            JTextField tf;

            for(int i=0; i<count; i++)
            {
                resultGraph.add(new ArrayList<>());
                for(int j=0; j<count; j++)
                {
                    int index = count+2+i+i*count+j;
                    if(i==j) {
                        btn = (JButton) grid.getComponent(index);
                        resultGraph.get(i).add(Integer.parseInt(btn.getText()));
                    }
                    else {
                        tf = (JTextField) grid.getComponent(index);
                        if(!tf.getText().equals(""))
                        {
                            resultGraph.get(i).add(Integer.parseInt(tf.getText()));
                        }
                        else
                        {
                            resultGraph.get(i).add(1000000);
                        }
                    }
                }
            }
            frame.setVisible(false);
            return;
        }
        if(obj == okButton)
        {
            frame.setVisible(false);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            return;
        }

            if(obj == addVertex)
            {
                JButton btn = new JButton(Integer.toString(count+1));
                grid.add(btn,count+1);

                int index;
                for(int i=1; i<count+1; i++)
                {
                    index = (i+1)*(count+1)+i;
                    JTextField text = new JTextField("");
                    text.setHorizontalAlignment(JTextField.CENTER);
                    grid.add(text,index);
                }

                grid.add(new JButton(Integer.toString(count+1)));


                for(int i=1; i<count+1; i++)
                {
                    index = (count+2)*(count+1)+i;
                    JTextField text = new JTextField("");
                    text.setHorizontalAlignment(JTextField.CENTER);
                    grid.add(text,index);
                }
                btn = new JButton("0");
                btn.setBackground(Color.WHITE);
                grid.add(btn);
                count++;
            }

            if(obj == removeVertex)
            {
                int index;
                for(int i=0; i<count+1; i++)
                {
                    index = (count+1)*(count+1)-1-i;
                    grid.remove(index);
                }
                for(int i=0; i<count; i++)
                {
                    index = (count+1)*(count-i)-1;
                    grid.remove(index);
                }
                count--;
            }


            for(int i=0; i<(count+1)*(count+1); i++)
                grid.getComponent(i).setFont(new Font("Arial", Font.PLAIN, 120/count));

            int screenX = 1280;
            int screenY = 720-bottomPanel.getHeight()-topPanel.getHeight();
            int squareSize = (screenY-10*count)/(count+1);
            int marginX = (screenX-squareSize*(count+1))/count;
            int marginY = (screenY-squareSize*(count+1))/count;
            grid.setLayout(new GridLayout(count+1,count+1,marginX, marginY));
            noOfVerticesLabel.setText("Number of vertices: " + count);
        }

    void makeGrid(int size)
    {
        grid = new JPanel();
        grid.setSize(500,500);
        int screenX = 1280;
        int screenY = 720-bottomPanel.getHeight()-topPanel.getHeight();
        int squareSize = (screenY-10*size)/(size+1);
        int marginX = (screenX-squareSize*(size+1))/size;
        int marginY = (screenY-squareSize*(size+1))/size;
        grid.setLayout(new GridLayout(size+1,size+1,marginX, marginY));

        for(int i=0; i<(size+1); i++)
        {
            for(int j=0; j<(size+1); j++)
            {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.PLAIN, 120/size));

                JTextField text = new JTextField("");
                text.setFont(new Font("Arial", Font.PLAIN, 120/size));
                text.setHorizontalAlignment(JTextField.CENTER);
                if(i*j==0 && i+j>0)
                {
                    btn.setText(Integer.toString(((i+1)*(j+1)-1)));
                    grid.add(btn);
                }
                else if (i+j==0)
                {
                    btn.setText("");
                    grid.add(btn);
                }
                else if (i==j)
                {
                    btn.setText("0");
                    btn.setBackground(Color.WHITE);
                    grid.add(btn);
                }
                else
                {
                    grid.add(text);
                }
            }
        }
        frame.getContentPane().add(BorderLayout.CENTER, grid);
    }
    public void GUI(int size)
    {
        count=size;
        //Creating the Frame
        frame = new JFrame("Create a Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);


        //Creating the bottomPanel at bottom and adding components
        bottomPanel = new JPanel();

        noOfVerticesLabel = new JLabel("Number of vertices: "+count);

        addVertex= new JButton("Add a Vertex");
        removeVertex = new JButton("Remove a Vertex");
        saveGraph = new JButton("Save a Graph");
        readFromFileButton = new JButton("Select file");

        addVertex.addActionListener(this);
        removeVertex.addActionListener(this);
        saveGraph.addActionListener(this);
        readFromFileButton.addActionListener(this);

        bottomPanel.add(noOfVerticesLabel); // Components Added using Flow Layout
        bottomPanel.add(addVertex);
        bottomPanel.add(removeVertex);
        bottomPanel.add(saveGraph);
        bottomPanel.add(readFromFileButton);

        topPanel = new JPanel();

        infoLabel = new JLabel("Create a graph.");

        topPanel.add(infoLabel);

        makeGrid(size);

        frame.getContentPane().add(BorderLayout.NORTH, topPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);

        frame.setVisible(true);
    }

    /**
     * Public constructor that prints out a statement about instantation of class.
     */
    public FWview() {
        System.out.println("View starts");

    }

    /**
     * Shows a dialog that informs about passing incorrect number of command line arguments.
     * Asks to enter two parameters instead.
     * @return an integer
     */
    public int wrongArgc()
    {
        String temp = JOptionPane.showInputDialog(null, "Wrong number of command line arguments. Enter the graph's size.",
                "Error", JOptionPane.QUESTION_MESSAGE);

        return Integer.parseInt(temp);
    }
    public boolean resultReady() throws InterruptedException {
        if(this.isActive()) {
            Thread.sleep(1000);
            return resultReady();
        }
        return true;
    }
    public void showGraph(ArrayList <ArrayList<Integer>> solvedGraph)
    {
        grid.removeAll();
        int size = solvedGraph.size();
        JButton btn = new JButton("");
        btn.setFont(new Font("Arial", Font.PLAIN, 120/size));
        grid.add(btn);
        for(int i=0; i<size; i++)
        {
            JButton btn1 = new JButton(Integer.toString(i+1));
            btn1.setFont(new Font("Arial", Font.PLAIN, 120/size));
            grid.add(btn1);
        }
        for(int i=1; i<=size; i++)
        {
            JButton btn2 = new JButton(Integer.toString(i));
            btn2.setFont(new Font("Arial", Font.PLAIN, 120/size));
            grid.add(btn2);
            for(int j=1; j<=size; j++)
            {
                JButton btn3 = new JButton();
                btn3.setFont(new Font("Arial", Font.PLAIN, 120 / size));
                btn3.setBackground(Color.WHITE);
                if(solvedGraph.get(i - 1).get(j - 1)>100000)
                {
                    btn3.setText("INF");
                }
                else
                {
                    btn3.setText(Integer.toString(solvedGraph.get(i - 1).get(j - 1)));
                }
                grid.add(btn3);
            }

        }
            infoLabel.setText("Optimized Graph:");
            bottomPanel.removeAll();
            okButton = new JButton("OK");
            okButton.addActionListener(this);
            bottomPanel.add(okButton);
            frame.setVisible(true);
    }
}
