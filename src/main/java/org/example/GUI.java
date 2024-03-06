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
    private SQLManager projectManager;
    public GUI() {
        super("Emulador SQL");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        projectManager = new SQLManager();

        JPanel mainPanel = new JPanel(new BorderLayout());
        // Crear el panel derecho (vacío por ahora)
        JPanel emptyPanel = new JPanel();

        fileTree = new JTree();
        mainPanel.add(new JScrollPane(fileTree), BorderLayout.WEST);

        JButton createProjectButton = new JButton("Crear Proyecto");
        createProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProject();
            }
        });
        mainPanel.add(createProjectButton, BorderLayout.NORTH);


        // Crear el JSplitPane con el árbol de archivos y el panel derecho
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, emptyPanel);
        splitPane.setDividerLocation(200); // Establecer la posición inicial de la barra divisoria
        splitPane.setOneTouchExpandable(true); // Permitir que el usuario colapse el panel

        getContentPane().add(splitPane);
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
                // Procesar cada línea del archivo .ide
                // Aquí necesitarás escribir la lógica para parsear el archivo .ide y construir la estructura del proyecto
                // La estructura del proyecto puede ser una lista de carpetas y archivos representados como nodos del árbol
                // Por ahora, simplemente agregaremos un nodo de prueba al árbol
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
            // También podríamos cargar la estructura del proyecto en el árbol de archivos aquí
        }
    }

    private void createProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String projectPath = fileChooser.getSelectedFile().getAbsolutePath();
            projectManager.createProject(projectPath);
            // También podríamos actualizar la interfaz de usuario con la estructura del proyecto aquí
        }
    }
}

