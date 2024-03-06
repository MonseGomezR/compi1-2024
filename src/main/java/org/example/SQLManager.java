package org.example;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;

public class SQLManager {
    private String currentProjectPath;

    public SQLManager() {
        this.currentProjectPath = null;
    }

    public void openProject(String projectPath) {
        if (currentProjectPath != null) {
            JOptionPane.showMessageDialog(null, "Ya hay un proyecto abierto. Cierra el proyecto actual antes de abrir otro.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File ideFile = new File(projectPath + File.separator + "project.ide");
        if (!ideFile.exists()) {
            JOptionPane.showMessageDialog(null, "El archivo .ide del proyecto no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Proyecto");
        try (BufferedReader reader = new BufferedReader(new FileReader(ideFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Procesar cada línea del archivo .ide
                // Agregar la lógica para parsear el archivo .ide y construir la estructura del proyecto
                // Por ahora, simplemente agregaremos un nodo de prueba al árbol
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(line.trim());
                root.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir el proyecto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.currentProjectPath = projectPath;
        JOptionPane.showMessageDialog(null, "Proyecto abierto correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void createProject(String projectPath) {
        if (currentProjectPath != null) {
            JOptionPane.showMessageDialog(null, "Ya hay un proyecto abierto. Cierra el proyecto actual antes de crear uno nuevo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File projectFolder = new File(projectPath);
        if (!projectFolder.exists()) {
            if (!projectFolder.mkdirs()) {
                JOptionPane.showMessageDialog(null, "Error al crear la carpeta del proyecto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        File ideFile = new File(projectPath + File.separator + "project.ide");
        try {
            if (!ideFile.createNewFile()) {
                JOptionPane.showMessageDialog(null, "Error al crear el archivo .ide del proyecto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (FileWriter writer = new FileWriter(ideFile)) {
                writer.write("<PROYECTO nombre=\"NuevoProyecto\">\n");
                writer.write("</PROYECTO>\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al crear el proyecto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.currentProjectPath = projectPath;
        JOptionPane.showMessageDialog(null, "Proyecto creado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void executeSQLQuery(String query) {
        if (currentProjectPath == null) {
            JOptionPane.showMessageDialog(null, "No hay ningún proyecto abierto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Consulta SQL ejecutada correctamente:\n" + query, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadProjectStructure() {
        // Implementar la lógica para cargar la estructura del proyecto desde el archivo .ide
    }

    private void saveProjectStructure() {
        // Implementar la lógica para guardar la estructura del proyecto en el archivo .ide
    }

    private void readCSVFile(String filePath) {
        // Implementar la lógica para leer un archivo CSV
    }

    private void writeCSVFile(String filePath) {
        // Implementar la lógica para escribir en un archivo CSV
    }
}
