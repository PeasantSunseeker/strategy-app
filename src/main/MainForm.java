package main;

import javax.swing.*;

/**
 * Created by Broderick on 12/30/2016.
 */
public class MainForm {
    private JLabel Title;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel Main;
    private JTable MainTable;

    private void createUIComponents() {
        // TODO: Make table columns dynamic and better
        String[] columnNames = {"<html>Time<br>&nbsp;", "<html>Sun<br>Angle", "<html>Solar<br>Power", "<html>Aerodynamic<br>Drag", "<html>Rolling<br>Resistance", "<html>Total<br>Power", "<html>Battery<br>Coulombs?", "<html>Battery<br>Capacity", "<html>Battery<br>Charge", "<html>Total<br>Change", "<html>Distance<br>Driven"};
        Object[][] data = Data.getData();
        MainTable = new JTable(data, columnNames);
        //MainTable.setFillsViewportHeight(true);
        Data.computeByDistance();
    }
}
