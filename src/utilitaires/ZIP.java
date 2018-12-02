package utilitaires;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
 
public class ZIP {
 
    private File    zipfile, tozip;
 
    public ZIP(File zip, File tozip) {
        this.zipfile = zip;
        this.tozip = tozip;
    }
 
    public ZIP(String toUnzip) {
        this(new File(toUnzip), null);
    }
 
    public ZIP(String zip, String tozip) {
        this(new File(zip), new File(tozip));
    }
 
    public void uncompress(String dest) throws IOException {
        uncompress(zipfile, new File(dest));
    }
 
    public static File uncompress(File zipfile, File dest) throws IOException {
        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipfile));
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) { 
            if (ze.isDirectory()) {
                File dir = new File(dest.getAbsolutePath() + File.separatorChar
                        + ze.getName());
                if (!dir.exists())
                    dir.mkdir();
            } else {
                File f = new File(dest.getAbsolutePath() + File.separator
                        + ze.getName());
                if (!f.getParentFile().exists())
                    f.getParentFile().mkdirs();
                FileOutputStream out = new FileOutputStream(dest.getAbsolutePath() + File.separator + ze.getName());
                byte[] buff = new byte[1024];
                int read;
                while ((read = zin.read(buff)) > -1)
                    out.write(buff, 0, read);
                out.flush();
                out.close();
            }
        }
        zin.closeEntry();
        zin.close();
        return dest;
    }
    public static File uncompressjar(File zipfile, File dest) throws IOException {
        JarInputStream zin = new JarInputStream(new FileInputStream(zipfile));
        JarEntry ze; 
        while ((ze = zin.getNextJarEntry()) != null) {
            if (ze.isDirectory()) {
                File dir = new File(dest.getAbsolutePath() + File.separatorChar + ze.getName());
                if (!dir.exists())
                    dir.mkdir();
            } else {
                File f = new File(dest.getAbsolutePath() + File.separator + ze.getName());
                if (!f.getParentFile().exists())
                    f.getParentFile().mkdirs();
                FileOutputStream out = new FileOutputStream(dest.getAbsolutePath() + File.separator + ze.getName());
                byte[] buff = new byte[1024];
                int read;
                while ((read = zin.read(buff)) > -1)
                    out.write(buff, 0, read);
                out.flush();
                out.close();
            }
        }
        zin.closeEntry();
        zin.close();
        return dest;
    }
    private void compressDir(File dir, ZipOutputStream zout) throws IOException {
        String[] list = dir.list();
        if (list == null || list.length == 0)
            throw new NullPointerException(dir.getAbsolutePath() + "n'existe pas ou est vide");
        for (String f : list) {
            // System.out.println(f);
            File toZip = new File(dir, f);
            if (toZip.isDirectory()) {
                compressDir(toZip, zout);
                continue;
            }
            ZipEntry ze = new ZipEntry(toZip.getAbsolutePath().substring(tozip.getAbsolutePath().length() + 1));
            zout.putNextEntry(ze);
            byte[] buff = new byte[1024];
            FileInputStream fin = new FileInputStream(toZip);
            int read = fin.read(buff);
            while (read > -1) {
                zout.write(buff, 0, read);
                zout.flush();
                read = fin.read(buff);
            }
            zout.closeEntry();
            fin.close();
        }
    }
    private void compressDirJar(File dir, JarOutputStream jout) throws IOException {
        String[] list = dir.list();
        if (list == null || list.length == 0)
            throw new NullPointerException(dir.getAbsolutePath() + "n'existe pas ou est vide");
        for (String f : list) {
            // System.out.println(f);
            File toZip = new File(dir, f);
            if (toZip.isDirectory()) {
                compressDirJar(toZip, jout);
                continue;
            }
            JarEntry ze = new JarEntry(toZip.getAbsolutePath().substring(tozip.getAbsolutePath().length() + 1));
            jout.putNextEntry(ze);
            byte[] buff = new byte[1024];
            FileInputStream fin = new FileInputStream(toZip);
            int read = fin.read(buff);
            while (read > -1) {
                jout.write(buff, 0, read);
                jout.flush();
                read = fin.read(buff);
            }
            jout.closeEntry();
            fin.close();
        }
    }
    public boolean compress() throws IOException {
        if (!zipfile.exists())
            zipfile.createNewFile();
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipfile));
        compressDir(tozip, zos);
        zos.closeEntry();
        zos.close();
        return true;
    }
    public boolean compressJar() throws IOException {
 
        if (!zipfile.exists())
            zipfile.createNewFile();
        JarOutputStream jout = new JarOutputStream(new FileOutputStream(zipfile));
        compressDirJar(tozip, jout);
        jout.closeEntry();
        jout.close();
        return true;
    }
}