package Scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory {
	public static void getAllFiles(File dir,List<File> fileList) {
		try {
			File[] files=dir.listFiles();
			for(File file:files) {
				fileList.add(file);
				if(file.isDirectory()) {
					getAllFiles(file, fileList);
				}
				else {
					System.out.println("file :"+file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeZipFile(File directoryToZip,List<File> fileList) {
		try {
			FileOutputStream fos=new FileOutputStream(directoryToZip.getName()+".zip");
			ZipOutputStream zos=new ZipOutputStream(fos);
			for(File file: fileList) {
				if(!file.isDirectory()) {
					addToZip(directoryToZip, file, zos);
				}
				zos.close();
				fos.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();			
		}
	}
	public static void addToZip(File directoryToZip,File file,ZipOutputStream zos) throws IOException {
		FileInputStream fis=new FileInputStream(file);
		String zipFilePath=file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length()+1,file.getCanonicalPath().length());
		System.out.println("Writing '" + zipFilePath + "' to zip file");
		ZipEntry zipEntry=new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);
		byte[] bytes=new byte[1024];
		int length;
		while((length=fis.read(bytes))>=0) {
			zos.write(bytes, 0,length);
		}
		zos.closeEntry();
		fis.close();
	}
}
