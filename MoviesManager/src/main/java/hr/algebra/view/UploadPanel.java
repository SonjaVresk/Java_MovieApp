/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.view;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.model.Movie;
import hr.algebra.parsers.rss.MovieParser;
import hr.algebra.utilities.MessageUtils;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author s_vre
 */
public class UploadPanel extends javax.swing.JPanel {

    private DefaultListModel<Movie> moviesModel;
    private Repository repository;
    private List<Movie> selectedMovies;
    private Movie instMovie;

    /**
     * Creates new form UploadPanel
     */
    public UploadPanel() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnUpload = new javax.swing.JButton();
        btnDeleteAll = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lsUploadMovies = new javax.swing.JList<>();
        lbUploading = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(241, 201, 201));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        btnUpload.setBackground(new java.awt.Color(172, 121, 121));
        btnUpload.setFont(new java.awt.Font("Chiller", 1, 24)); // NOI18N
        btnUpload.setForeground(new java.awt.Color(255, 255, 255));
        btnUpload.setText("Upload");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        btnDeleteAll.setBackground(new java.awt.Color(121, 74, 74));
        btnDeleteAll.setFont(new java.awt.Font("Chiller", 1, 24)); // NOI18N
        btnDeleteAll.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteAll.setText("Delete all");
        btnDeleteAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAllActionPerformed(evt);
            }
        });

        jScrollPane1.setAutoscrolls(true);

        lsUploadMovies.setBackground(new java.awt.Color(209, 181, 181));
        lsUploadMovies.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(lsUploadMovies);

        lbUploading.setBackground(new java.awt.Color(236, 176, 176));
        lbUploading.setFont(new java.awt.Font("Chiller", 1, 20)); // NOI18N

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Chiller", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(30, 18, 18));
        jLabel1.setText("Movies");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUpload, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeleteAll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lbUploading, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(387, 387, 387))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(lbUploading, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed

        handleUpload(true);
        //Nova dretva:    
        new Thread(() -> {
            try {
                List<Movie> movies = MovieParser.parse();
                repository.createAll(movies);
                loadModel();
            } catch (Exception ex) {
                MessageUtils.showErrorMessage("Unrecoverable error", "Unable to upload data");
                Logger.getLogger(UploadPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_btnUploadActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        //init();
    }//GEN-LAST:event_formComponentShown

    private void btnDeleteAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAllActionPerformed

        if (MessageUtils.showConfirmDialog("Delete", "Delete all data?")) {
            try {
                instMovie = new Movie();
                selectedMovies = repository.selectAll(instMovie);

                for (Movie movie : selectedMovies) {
                    if (movie.getPicturePath() != null) {
                        Files.deleteIfExists(Paths.get(movie.getPicturePath()));
                    }
                }
                repository.deleteAll();
                loadModel();
            } catch (Exception ex) {
                Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error", "Unable to delete movie!");
            }
        }
    }//GEN-LAST:event_btnDeleteAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteAll;
    private javax.swing.JButton btnUpload;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbUploading;
    private javax.swing.JList<Movie> lsUploadMovies;
    // End of variables declaration//GEN-END:variables

    private void init() {
        try {
            repository = RepositoryFactory.getRepository(Movie.class);
            moviesModel = new DefaultListModel<>();
            loadModel();
        } catch (Exception ex) {
            Logger.getLogger(UploadPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Unrecoverable error", "Cannot initiate the form");
            System.exit(1);
        }
    }

    private void loadModel() throws Exception {
        
    //povratak u glavnu dretvu:
        java.awt.EventQueue.invokeLater(() -> {
            List<Movie> movies;
            instMovie = new Movie();
            try {
                movies = repository.selectAll(instMovie);
                moviesModel.clear();
                movies.forEach(moviesModel::addElement);
                lsUploadMovies.setModel(moviesModel);
                handleUpload(false);
            } catch (Exception ex) {
                Logger.getLogger(UploadPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void handleUpload(boolean uploading) {
        lbUploading.setText(uploading ? "Uploading..." : "");
        btnUpload.setEnabled(!uploading);
    }
}
