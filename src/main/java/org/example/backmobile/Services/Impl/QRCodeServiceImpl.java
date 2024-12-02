package org.example.backmobile.Services.Impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Service;
import prog.dependancy.Services.QRCodeService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class QRCodeServiceImpl implements QRCodeService {


    @Override
    public String generateQRCode(String matricule) {
        try {
            // Generate the QR code as a BitMatrix
            BitMatrix matrix = new MultiFormatWriter().encode(matricule, BarcodeFormat.QR_CODE, 200, 200);

            // Convert BitMatrix to BufferedImage
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);

            // Write BufferedImage to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", outputStream);

            // Convert to Base64 and return as a string
            byte[] qrCodeBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(qrCodeBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error generating QR code", e);
        }
    }
}
