package org.example;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GUI extends JFrame {

    private JTree fileTree;
    private JTextArea queryTextArea;
    private JTextArea resultTextArea;
    private SQLManager projectManager;
    File ruta = new File(System.getProperty(("user.dir")) );
    public GUI() {
        super("Emulador SQL");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        projectManager = new SQLManager();

        JPanel mainPanel = new JPanel(new BorderLayout());
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(ruta.getName());
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        buildFileTree(ruta, rootNode);

        fileTree = new JTree();
        fileTree.setModel(treeModel);
        fileTree.expandRow(0);
        fileTree.setRootVisible(true);

        mainPanel.add(new JScrollPane(fileTree), BorderLayout.WEST);

        JPanel queryPanel = new JPanel(new BorderLayout());
        JLabel queryLabel = new JLabel("Consulta SQL:");
        queryTextArea = new JTextArea(5, 40);
        queryPanel.add(queryLabel, BorderLayout.NORTH);
        queryPanel.add(new JScrollPane(queryTextArea), BorderLayout.CENTER);
        mainPanel.add(queryPanel, BorderLayout.CENTER);

        JPanel resultPanel = new JPanel(new BorderLayout());
        JLabel resultLabel = new JLabel("Resultados:");
        resultTextArea = new JTextArea(10, 40);
        resultTextArea.setEditable(false);
        resultPanel.add(resultLabel, BorderLayout.NORTH);
        resultPanel.add(new JScrollPane(resultTextArea), BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        JButton createProjectButton = new JButton("Crear Proyecto");
        createProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProject();
            }
        });
        mainPanel.add(createProjectButton, BorderLayout.NORTH);


        getContentPane().add(mainPanel);
    }

    private void buildFileTree(File ruta, DefaultMutableTreeNode rootNode) {
        if(ruta.isDirectory()) {
            File[] files = ruta.listFiles();
            if(files!=null){
                for(File file : files) {
                    DefaultMutableTreeNode cnode = new DefaultMutableTreeNode(file.getName());
                    rootNode.add(cnode);
                    buildFileTree(file, cnode);
                }
            }
        }
    }

    private void loadProjectStructure() {
        File ideFile = new File("project.ide");
        if (!ideFile.exists()) {
            return;
        }

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Proyecto");
        try (BufferedReader reader = new BufferedReader(new FileReader(ideFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(line.trim());
                root.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileTree.setModel(new DefaultTreeModel(root));
    }

    private void openProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String projectPath = fileChooser.getSelectedFile().getAbsolutePath();
            projectManager.openProject(projectPath);
        }
    }

    private void createProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String projectPath = fileChooser.getSelectedFile().getAbsolutePath();
            projectManager.createProject(projectPath);
        }
    }
}

