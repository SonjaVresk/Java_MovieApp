/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;


/**
 *
 * @author s_vre
 */
public enum Role {
    
    ADMIN(1),
    USER(2);
    
    private final int id;

    private Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public static Role from(int id) {
        for (Role value : values()) {
            if (value.id == id) {
                return value;
            }
        }
        throw new RuntimeException("Wrong user type.");
    }
}
