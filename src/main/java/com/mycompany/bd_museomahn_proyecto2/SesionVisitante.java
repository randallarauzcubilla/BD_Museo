package com.mycompany.bd_museomahn_proyecto2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Randall AC
 */
public class SesionVisitante {
    private static List<Integer> salasAutorizadasIds = new ArrayList<>();

    public static void setSalasAutorizadas(List<Integer> ids) {
        salasAutorizadasIds = ids;
    }

    public static boolean isSalaAutorizada(int idSala) {
        return salasAutorizadasIds.contains(idSala);
    }
    
    public static void limpiarSesion() {
        salasAutorizadasIds = null;
    }
}
