package gui;
import model.*;
import algorithm.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    Graph g;
    GraphDrawPanel traversal, pathMST;

    JComboBox<String> fBox, tBox, sBox, eBox;
    JTextField vField, wField;
    JCheckBox dir;

    DefaultTableModel model;

    // Labels to display path values
    JLabel pathValueLabel;

    public MainFrame()
    {
        setTitle("Graph Data Structure Visualizer");
        setSize(1400, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JLabel title = new JLabel("Graph Data Structure Visualizer", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        add(title, BorderLayout.NORTH);

        JPanel topSection = new JPanel(new GridLayout(1, 2, 10, 0));
        topSection.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topSection.setPreferredSize(new Dimension(1400, 250));

        JPanel leftForm = new JPanel(new GridBagLayout());
        leftForm.setBorder(BorderFactory.createTitledBorder("Create the Graph"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        dir = new JCheckBox("Is Directed Graph");
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        leftForm.add(dir, c);

        c.gridy++; c.gridwidth = 1;
        c.gridx = 0;
        leftForm.add(new JLabel("Put the Number Of Vertices"), c);

        vField = new JTextField(15);
        JButton gen = new JButton("Generate");
        c.gridy++;
        c.gridx = 0;
        leftForm.add(vField, c);
        c.gridx = 1;
        leftForm.add(gen, c);

        c.gridx = 0; c.gridy++;
        leftForm.add(new JLabel("From Vertex"), c);
        c.gridx = 1;
        leftForm.add(new JLabel("To Vertex"), c);

        fBox = new JComboBox<>();
        tBox = new JComboBox<>();
        c.gridx = 0; c.gridy++;
        leftForm.add(fBox, c);
        c.gridx = 1;
        leftForm.add(tBox, c);

        c.gridx = 0; c.gridy++;
        c.gridwidth = 2;
        leftForm.add(new JLabel("Weight"), c);

        wField = new JTextField(15);
        JButton add = new JButton("Add Edge");
        c.gridy++; c.gridwidth = 1;
        c.gridx = 0;
        leftForm.add(wField, c);
        c.gridx = 1;
        leftForm.add(add, c);

        JPanel rightTable = new JPanel(new BorderLayout(5, 5));

        model = new DefaultTableModel(new String[]
                {"Vertex From", "Vertex To", "Weight"}, 0);

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JButton rem = new JButton("Remove Selected Row");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(rem);
        rem.setPreferredSize(new Dimension(180, 30));

        rightTable.add(scroll, BorderLayout.CENTER);
        rightTable.add(buttonPanel, BorderLayout.EAST);

        topSection.add(leftForm);
        topSection.add(rightTable);

        add(topSection, BorderLayout.NORTH);

        // Center panel with the two graphs side by side with a separator line
        JPanel center = new JPanel(new GridLayout(1, 2, 20, 10));
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        traversal = new GraphDrawPanel(null);
        traversal.setPreferredSize(new Dimension(680, 350));
        JPanel traversalPanel = new JPanel(new BorderLayout());
        JLabel traversalLabel = new JLabel("Graph Traversal (BFS/DFS)", SwingConstants.CENTER);
        traversalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        traversalPanel.add(traversalLabel, BorderLayout.NORTH);
        traversalPanel.add(traversal, BorderLayout.CENTER);
        traversalPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        pathMST = new GraphDrawPanel(null);
        pathMST.setPreferredSize(new Dimension(680, 350));
        JPanel pathMSTPanel = new JPanel(new BorderLayout());
        JLabel pathMSTLabel = new JLabel("Shortest Path & MST (Dijkstra/Prim/Kruskal)", SwingConstants.CENTER);
        pathMSTLabel.setFont(new Font("Arial", Font.BOLD, 14));

        pathValueLabel = new JLabel("Total Cost: -", SwingConstants.CENTER);
        pathValueLabel.setFont(new Font("Arial", Font.BOLD, 12));
        pathValueLabel.setForeground(new Color(0, 100, 0));

        JPanel pathMSTTop = new JPanel(new GridLayout(2, 1));
        pathMSTTop.add(pathMSTLabel);
        pathMSTTop.add(pathValueLabel);

        pathMSTPanel.add(pathMSTTop, BorderLayout.NORTH);
        pathMSTPanel.add(pathMST, BorderLayout.CENTER);
        pathMSTPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        center.add(traversalPanel);
        center.add(pathMSTPanel);

        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(2, 1, 5, 5));
        bottom.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row1.setBorder(BorderFactory.createTitledBorder("Graph Traversal"));

        sBox = new JComboBox<>();
        JButton bfs = new JButton("BFS");
        JButton dfs = new JButton("DFS");

        row1.add(new JLabel("Starting Vertex:"));
        row1.add(sBox);
        row1.add(bfs);
        row1.add(dfs);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row2.setBorder(BorderFactory.createTitledBorder("Shortest Path & Minimum Spanning Tree"));

        eBox = new JComboBox<>();
        JButton dij = new JButton("Dijkstra");
        JButton prim = new JButton("Prim");
        JButton kr = new JButton("Kruskal");

        row2.add(new JLabel("Starting Vertex:"));
        row2.add(sBox);
        row2.add(new JLabel("Ending Vertex:"));
        row2.add(eBox);
        row2.add(dij);
        row2.add(prim);
        row2.add(kr);

        bottom.add(row1);
        bottom.add(row2);

        add(bottom, BorderLayout.SOUTH);


        gen.addActionListener(e -> {
            int v = Integer.parseInt(vField.getText());
            g = new Graph(v, dir.isSelected());

            fBox.removeAllItems();
            tBox.removeAllItems();
            sBox.removeAllItems();
            eBox.removeAllItems();

            for (int i = 0; i < v; i++) {
                String letter = String.valueOf((char)('A' + i));
                fBox.addItem(letter);
                tBox.addItem(letter);
                sBox.addItem(letter);
                eBox.addItem(letter);
            }

            model.setRowCount(0);
            traversal.g = g;
            pathMST.g = g;
            traversal.setDirected(dir.isSelected());
            pathMST.setDirected(dir.isSelected());
            pathValueLabel.setText("Total Cost: -");
            repaint();
        });

        add.addActionListener(e -> {
            int f = letterToIndex((String) fBox.getSelectedItem());
            int t = letterToIndex((String) tBox.getSelectedItem());
            int w = Integer.parseInt(wField.getText());

            g.addEdge(f, t, w);
            model.addRow(new Object[]{fBox.getSelectedItem(), tBox.getSelectedItem(), w});
            repaint();
        });

        rem.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r != -1) {
                // Get the edge data from the selected row
                String fromVertex = (String) model.getValueAt(r, 0);
                String toVertex = (String) model.getValueAt(r, 1);
                int weight = (int) model.getValueAt(r, 2);

                // Convert letters to indices
                int fromIdx = letterToIndex(fromVertex);
                int toIdx = letterToIndex(toVertex);

                // Remove the edge from the graph
                removeEdgeFromGraph(fromIdx, toIdx, weight);

                // Remove the row from table
                model.removeRow(r);

                // Clear highlights and reset path value
                traversal.setHighlight(new ArrayList<>());
                pathMST.setHighlight(new ArrayList<>());
                pathValueLabel.setText("Total Cost: -");

                repaint();
            }
        });

        bfs.addActionListener(e ->
                animate(traversal, BFS.traverse(g, letterToIndex((String) sBox.getSelectedItem()))));

        dfs.addActionListener(e ->
                animate(traversal, DFS.traverse(g, letterToIndex((String) sBox.getSelectedItem()))));

        dij.addActionListener(e -> {
            java.util.List<Edge> path = Dijkstra.path(
                    g,
                    letterToIndex((String) sBox.getSelectedItem()),
                    letterToIndex((String) eBox.getSelectedItem()));
            pathMST.setHighlight(path);
            int totalCost = calculateTotalWeight(path);
            pathValueLabel.setText("Shortest Path Cost: " + totalCost);
        });

        prim.addActionListener(e -> {
            java.util.List<Edge> mst = Prim.mst(g);
            pathMST.setHighlight(mst);
            int totalCost = calculateTotalWeight(mst);
            pathValueLabel.setText("MST Total Cost: " + totalCost);
        });

        kr.addActionListener(e -> {
            java.util.List<Edge> mst = Kruskal.mst(g);
            pathMST.setHighlight(mst);
            int totalCost = calculateTotalWeight(mst);
            pathValueLabel.setText("MST Total Cost: " + totalCost);
        });
    }

    private int letterToIndex(String letter) {
        return letter.charAt(0) - 'A';
    }

    private int calculateTotalWeight(java.util.List<Edge> edges) {
        int total = 0;
        if (edges != null) {
            for (Edge e : edges) {
                total += e.weight;
            }
        }
        return total;
    }

    // Helper method to remove an edge from the graph
    private void removeEdgeFromGraph(int from, int to, int weight) {
        if (g == null || g.edges == null) return;

        // Create a list to hold edges to remove
        java.util.List<Edge> toRemove = new ArrayList<>();

        // Find and mark edges for removal
        for (Edge edge : g.edges) {
            if (edge.from == from && edge.to == to && edge.weight == weight) {
                toRemove.add(edge);
            }
            // For undirected graphs, also remove the reverse edge
            if (!g.edges.isEmpty() && edge.from == to && edge.to == from && edge.weight == weight) {
                toRemove.add(edge);
            }
        }

        // Remove all marked edges
        g.edges.removeAll(toRemove);
    }

    void animate(GraphDrawPanel panel, java.util.List<Edge> steps) {
        new Thread(() -> {
            java.util.List<Edge> cur = new ArrayList<>();
            for (Edge e : steps)
            {
                cur.add(e);
                panel.setHighlight(new ArrayList<>(cur));
                try {
                    Thread.sleep(400);
                } catch (Exception ignored) {}
            }
        }).start();
    }
}