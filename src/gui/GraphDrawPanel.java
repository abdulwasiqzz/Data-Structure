package gui;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class GraphDrawPanel extends JPanel
{
    Graph g;
    java.util.List<Edge> highlight=new ArrayList<>();
    boolean isDirected = false;

    public GraphDrawPanel(Graph g){
        this.g=g;
        setBackground(Color.WHITE);
    }

    public void setDirected(boolean directed) {
        this.isDirected = directed;
    }

    public void setHighlight(java.util.List<Edge> h){
        highlight=h;
        repaint();
    }

    protected void paintComponent(Graphics gr)
    {
        super.paintComponent(gr);
        if(g==null)
        {
            return;
        }
        Graphics2D g2=(Graphics2D)gr;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int n=g.V;
        int cx=getWidth()/2;
        int cy=getHeight()/2;

        // INCREASED RADIUS - use 40% of the smaller dimension for better spacing
        int r = (int)(Math.min(getWidth(), getHeight()) * 0.40);

        Point[] p=new Point[n];
        for(int i=0;i<n;i++)
        {
            double a=2*Math.PI*i/n - Math.PI/2; // Start from top
            p[i]=new Point(cx+(int)(r*Math.cos(a)),cy+(int)(r*Math.sin(a)));
        }

        // --- Draw Regular Edges with Bright Green Color ---
        g2.setStroke(new BasicStroke(3.5f)); // Reduced thickness so weights are visible
        g2.setColor(new Color(0, 200, 0));

        // Track drawn edges to avoid duplicates in undirected graphs
        java.util.Set<String> drawnEdges = new java.util.HashSet<>();

        for(Edge e:g.edges)
        {
            // For undirected graphs, only draw each edge once
            String edgeKey = getEdgeKey(e.from, e.to);
            if(!isDirected && drawnEdges.contains(edgeKey)) {
                continue;
            }
            drawnEdges.add(edgeKey);

            if(isDirected) {
                drawArrow(g2, p[e.from].x, p[e.from].y, p[e.to].x, p[e.to].y,
                        new Color(0, 200, 0), 3.5f);
            } else {
                g2.drawLine(p[e.from].x, p[e.from].y, p[e.to].x, p[e.to].y);
            }

            // Draw weight with offset from edge center to avoid overlap
            int mx=(p[e.from].x+p[e.to].x)/2;
            int my=(p[e.from].y+p[e.to].y)/2;

            // Calculate perpendicular offset to move weight away from edge
            double dx = p[e.to].x - p[e.from].x;
            double dy = p[e.to].y - p[e.from].y;
            double len = Math.sqrt(dx*dx + dy*dy);
            if(len > 0) {
                // Perpendicular vector
                double perpX = -dy / len;
                double perpY = dx / len;
                // Offset by 20 pixels
                mx += (int)(perpX * 20);
                my += (int)(perpY * 20);
            }

            g2.setFont(new Font("Arial", Font.BOLD, 18));
            String weightStr = String.valueOf(e.weight);
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(weightStr);
            int textHeight = fm.getHeight();

            // Draw yellow background
            g2.setColor(new Color(255, 255, 0));
            g2.fillRoundRect(mx - textWidth/2 - 5, my - textHeight/2 - 2,
                    textWidth + 10, textHeight + 4, 8, 8);

            // Draw GREEN border
            g2.setColor(new Color(0, 200, 0));
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawRoundRect(mx - textWidth/2 - 5, my - textHeight/2 - 2,
                    textWidth + 10, textHeight + 4, 8, 8);

            // Draw weight text
            g2.setColor(new Color(0, 150, 0));
            g2.drawString(weightStr, mx - textWidth/2, my + textHeight/3);

            // Reset for next edge
            g2.setStroke(new BasicStroke(3.5f));
            g2.setColor(new Color(0, 200, 0));
        }

        // --- Draw Highlighted Edges (Bright Blue) ---
        g2.setStroke(new BasicStroke(6.0f));
        g2.setColor(new Color(0, 120, 255));

        // Track drawn highlighted edges to avoid duplicates in undirected graphs
        java.util.Set<String> drawnHighlightEdges = new java.util.HashSet<>();

        for(Edge e:highlight){
            // For undirected graphs, only draw each edge once
            String edgeKey = getEdgeKey(e.from, e.to);
            if(!isDirected && drawnHighlightEdges.contains(edgeKey)) {
                continue;
            }
            drawnHighlightEdges.add(edgeKey);

            if(isDirected) {
                drawArrow(g2, p[e.from].x, p[e.from].y, p[e.to].x, p[e.to].y,
                        new Color(0, 120, 255), 6.0f);
            } else {
                g2.drawLine(p[e.from].x, p[e.from].y, p[e.to].x, p[e.to].y);
            }
        }

        // --- Draw Vertices (Bright Orange) ---
        for(int i=0;i<n;i++)
        {
            g2.setColor(new Color(255, 140, 0));
            g2.fillOval(p[i].x-20,p[i].y-20,40,40);

            g2.setStroke(new BasicStroke(3.0f));
            g2.setColor(Color.BLACK);
            g2.drawOval(p[i].x-20,p[i].y-20,40,40);

            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.setColor(Color.BLACK);
            String letter = String.valueOf((char)('A' + i));
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(letter);
            int textHeight = fm.getAscent();
            g2.drawString(letter, p[i].x - textWidth/2, p[i].y + textHeight/3);
        }
    }

    // Helper method to create a unique edge key for undirected graphs
    private String getEdgeKey(int from, int to) {
        // For undirected graphs, make sure A-B and B-A have the same key
        int min = Math.min(from, to);
        int max = Math.max(from, to);
        return min + "-" + max;
    }

    // Helper method to draw arrows for directed graphs
    private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2,
                           Color color, float strokeWidth) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(strokeWidth));

        // Calculate direction vector
        double dx = x2 - x1;
        double dy = y2 - y1;
        double len = Math.sqrt(dx*dx + dy*dy);

        if(len == 0) return;

        // Normalize
        dx /= len;
        dy /= len;

        // Shorten line to stop at vertex circle (radius 20)
        int newX2 = x2 - (int)(dx * 22);
        int newY2 = y2 - (int)(dy * 22);

        // Draw main line
        g2.drawLine(x1, y1, newX2, newY2);

        // Draw arrowhead
        double arrowLength = 15;
        double arrowWidth = 8;

        // Calculate arrowhead points
        int arrowTipX = newX2;
        int arrowTipY = newY2;

        int arrowLeft1X = (int)(arrowTipX - arrowLength * dx + arrowWidth * dy);
        int arrowLeft1Y = (int)(arrowTipY - arrowLength * dy - arrowWidth * dx);

        int arrowLeft2X = (int)(arrowTipX - arrowLength * dx - arrowWidth * dy);
        int arrowLeft2Y = (int)(arrowTipY - arrowLength * dy + arrowWidth * dx);

        // Fill arrowhead
        int[] xPoints = {arrowTipX, arrowLeft1X, arrowLeft2X};
        int[] yPoints = {arrowTipY, arrowLeft1Y, arrowLeft2Y};
        g2.fillPolygon(xPoints, yPoints, 3);
    }
}