/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hr.algebra;

import hr.algebra.model.Role;
import hr.algebra.view.ActorPanel;
import hr.algebra.view.DirectorPanel;
import hr.algebra.view.GenrePanel;
import hr.algebra.view.GenreToMoviePanel;
import hr.algebra.view.MoviePanel;
import hr.algebra.view.SignInDialog;
import hr.algebra.view.UploadPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author s_vre
 */
public class MoviesManager extends javax.swing.JFrame {

    private static CardLayout cardLayout;
    private static JPanel contentPane;
    private static UploadPanel uploadPanel;
    private static MoviePanel moviePanel;
    private static GenrePanel genrePanel;
    private static ActorPanel actorPanel;
    private static DirectorPanel directorPanel;
    private static GenreToMoviePanel genreToMoviePanel;
    private static JMenuBar menuBar;
    private static JMenu homeMenu;
    private static JMenu logoutMenu;
    private static int role;

    /**
     * Creates new form MoviesManager
     * @param roleId
     */
    public MoviesManager(int roleId) throws Exception {
        initComponents();

        role = roleId;
        cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);
        initMenuAndPanel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MoviesManager");
        setName("movieManagerFrame"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1003, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MoviesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MoviesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MoviesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MoviesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MoviesManager moviesManager;
                try {
                    moviesManager = new MoviesManager(role);
                    moviesManager.pack();
                    moviesManager.setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(MoviesManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void initMenuAndPanel() throws Exception {

        if (role == Role.ADMIN.getId()) {
            initAdminMenuBar();
            initAdminPanels();
        } else {
            initUserMenuBar();
            initUserPanels();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public void initAdminMenuBar() {

        cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);
        menuBar = new JMenuBar();

        homeMenu = new JMenu("Home");
        logoutMenu = new JMenu("Logout");

        homeMenu.setMnemonic(KeyEvent.VK_H);
        logoutMenu.setMnemonic(KeyEvent.VK_L);

        JMenuItem pageSetupItem = new JMenuItem("Page Setup");
        JMenuItem printItem = new JMenuItem("Print");
        JMenuItem exitItem = new JMenuItem("Exit");

        pageSetupItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        homeMenu.add(pageSetupItem);
        homeMenu.add(printItem);
        homeMenu.add(exitItem);

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        logoutMenu.add(logoutItem);

        menuBar.add(homeMenu);
        menuBar.add(logoutMenu);

        setJMenuBar(menuBar);

        pageSetupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrinterJob printerJob = PrinterJob.getPrinterJob();
                printerJob.pageDialog(printerJob.defaultPage());
            }
        });

        printItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrinterJob printerJob = PrinterJob.getPrinterJob();
                printerJob.printDialog();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(
                        null, 
                        "Exit application?", 
                        "Exit", 
                        JOptionPane.OK_CANCEL_OPTION, 
                        JOptionPane.ERROR_MESSAGE) == JOptionPane.OK_OPTION) {
                    dispose();
                }
            }
        });

        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zatvori MoviesManager
                dispose();

                // Otvori SignInDialog
                SignInDialog signInDialog = new SignInDialog(new javax.swing.JFrame(), true);
                signInDialog.setVisible(true);
            }
        });

    }

    public void initUserMenuBar() {

        menuBar = new JMenuBar();

        homeMenu = new JMenu("Home");
        logoutMenu = new JMenu("Logout");

        homeMenu.setMnemonic(KeyEvent.VK_H);
        logoutMenu.setMnemonic(KeyEvent.VK_L);

        JMenuItem moviesItem = new JMenuItem("Movies");
        JMenuItem actorItem = new JMenuItem("Actors");
        JMenuItem directorItem = new JMenuItem("Directors");
        JMenuItem genreItem = new JMenuItem("Genres");
        JMenuItem genreToMovieItem = new JMenuItem("GenreToMovie");

        homeMenu.add(moviesItem);
        homeMenu.add(actorItem);
        homeMenu.add(directorItem);
        homeMenu.add(genreItem);
        homeMenu.add(genreToMovieItem);

        moviesItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        actorItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        directorItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        genreItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        genreToMovieItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutMenu.add(logoutItem);

        logoutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        menuBar.add(homeMenu);
        menuBar.add(logoutMenu);

        setJMenuBar(menuBar);

        moviesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "Movies");
            }
        });

        actorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cardLayout.show(contentPane, "Actors");
            }
        });
        directorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "Directors");
            }
        });

        genreItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "Genres");
            }
        });

        genreToMovieItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "GenreToMovie");
            }
        });
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zatvori MoviesManager
                dispose();

                // Otvori SignInDialog
                SignInDialog signInDialog = new SignInDialog(new javax.swing.JFrame(), true);
                signInDialog.setVisible(true);
            }
        });
    }

    private void initAdminPanels() {

        uploadPanel = new UploadPanel();

        contentPane.add(uploadPanel, "Upload");
        add(contentPane);

        cardLayout.show(contentPane, "Upload");

        setLayout(new BorderLayout());
        add(contentPane, BorderLayout.CENTER);
    }

    private void initUserPanels() throws Exception {

        moviePanel = new MoviePanel();
        genrePanel = new GenrePanel();
        actorPanel = new ActorPanel();
        directorPanel = new DirectorPanel();
        genreToMoviePanel = new GenreToMoviePanel();

        contentPane.add(moviePanel, "Movies");
        contentPane.add(genrePanel, "Genres");
        contentPane.add(actorPanel, "Actors");
        contentPane.add(directorPanel, "Directors");
        contentPane.add(genreToMoviePanel, "GenreToMovie");

        add(contentPane);

        cardLayout.show(contentPane, "Movies");

        setLayout(new BorderLayout());
        add(contentPane, BorderLayout.CENTER);
    }
}
