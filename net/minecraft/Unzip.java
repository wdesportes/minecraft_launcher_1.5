package net.minecraft;

//Importation des packages dont on va se servir
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip{

    /**
     * décompresse le fichier zip dans le répertoire donné
     * @param folder le répertoire où les fichiers seront extraits
     * @param zipfile le fichier zip à décompresser
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void unzip(File zipfile, File folder) throws FileNotFoundException, IOException{

        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(
                        new FileInputStream(zipfile.getCanonicalFile())));

        ZipEntry ze = null;
        try {
            while((ze = zis.getNextEntry()) != null){

                File f = new File(folder.getCanonicalPath(), ze.getName());
                
                if (ze.isDirectory()) {
                    f.mkdirs();
                    continue;
                }
                
                f.getParentFile().mkdirs();
                OutputStream fos = new BufferedOutputStream(
                        new FileOutputStream(f));
                try {
                    try {
                        final byte[] buf = new byte[8192];
                        int bytesRead;
                        while (-1 != (bytesRead = zis.read(buf)))
                            fos.write(buf, 0, bytesRead);
                    }
                    finally {
                        fos.close();
                    }
                }
                catch (final IOException ioe) {
                    f.delete();
                    throw ioe;
                }
            }
        }
        finally {
            zis.close();
            zipfile.delete();
        }
    }
}