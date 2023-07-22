/*
 * package com.watch.store.service;
 * 
 * import java.io.File; import java.io.FileInputStream; import
 * java.io.FileNotFoundException; import java.io.IOException; import
 * java.io.InputStream; import java.nio.file.Files; import java.nio.file.Paths;
 * import java.util.UUID;
 * 
 * import org.springframework.stereotype.Service; import
 * org.springframework.web.multipart.MultipartFile;
 * 
 * import com.watch.store.exception.BadApiRequest;
 * 
 * @Service public class FileServiceImpl implements FileService{
 * 
 * @Override public String uploadImg(MultipartFile file, String path) throws
 * IOException { //Get file name String
 * originalFileName=file.getOriginalFilename();
 * 
 * //Create Unique name String fileName=UUID.randomUUID().toString(); String
 * extension=originalFileName.substring(originalFileName.lastIndexOf("."));
 * String fileNameWithExtension=fileName+extension; String
 * fullPathWithFileName=path+fileNameWithExtension;
 * 
 * if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpeg"))
 * { //Get the folder path File folder=new File(path);
 * 
 * //Check the folder exist or not //Image folder will be created if not
 * exists!! if(!folder.exists()) { folder.mkdirs(); }
 * 
 * Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName)); return
 * fileNameWithExtension; } else { throw new
 * BadApiRequest("File with this extension "+extension+" not allowed"); } }
 * 
 * @Override public InputStream getResource(String path, String name) throws
 * FileNotFoundException { String fullPath=path+File.separator+name; InputStream
 * inputStream=new FileInputStream(fullPath); return inputStream; }
 * 
 * }
 */