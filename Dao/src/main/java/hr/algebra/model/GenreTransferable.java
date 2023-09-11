/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author s_vre
 */
public class GenreTransferable implements Transferable {

    public static DataFlavor GENRE_FLAVOR = new DataFlavor(Genre.class, "Genre");
    private static DataFlavor[] SUPPORTED_FLAVORS = {GENRE_FLAVOR};
    private final Genre genre;

    public GenreTransferable(Genre genre) {
        this.genre = genre;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return GENRE_FLAVOR.equals(flavor);
                
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (isDataFlavorSupported(flavor)) {
            return genre;
        }        
        throw new UnsupportedFlavorException(flavor); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
