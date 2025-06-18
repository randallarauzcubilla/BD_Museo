package com.mycompany.bd_museomahn_proyecto2;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Randall AC
 */
public class QRUtils {

    public static void generarQRCodeSala(int idSala, String rutaSalida) throws Exception {
        int ancho = 300;
        int alto = 200;
        String formato = "png";
        String contenido = "QR_Sala_" + idSala;

        BitMatrix matriz = new MultiFormatWriter().encode(
                contenido,
                BarcodeFormat.QR_CODE,
                ancho,
                alto
        );

        Path ruta = Paths.get(rutaSalida);
        MatrixToImageWriter.writeToPath(matriz, formato, ruta);
        System.out.println("QR generado en: " + rutaSalida);
    }
}

