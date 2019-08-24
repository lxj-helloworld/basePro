package com.example.xiaojin20135.basemodule.files;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;


import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.ConstantUtil;
import com.example.xiaojin20135.basemodule.util.TimeMethods;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class AppExternalFileWriter {
    private static final String canNotWriteFile = "Can not write file: ";
    private static final String canNotCreateDirectory = "Can not create directory: ";
    private final File externalStorageDirectory;
    private final File externalCacheDirectory;
    private Context context;
    private File appDirectory;
    private File appCacheDirectory;

    private static final String TAG = "AppExternalFileWriter";


    /**
     * 构造函数
     * @param context : Context
     */
    public AppExternalFileWriter(Context context) {
        this.context = context;
        externalStorageDirectory = Environment.getExternalStorageDirectory();
        externalCacheDirectory = context.getExternalCacheDir();

    }

    /**
     * 创建文件
     * @param fileName
     * @param inCache
     * @return
     * @throws AppExternalFileWriter.ExternalFileWriterException
     */
    private File createFile(String fileName, boolean inCache) throws AppExternalFileWriter.ExternalFileWriterException {
        return createFile(fileName, getAppDirectory(inCache));
    }

    /**
     * 创建指定文件名的文件
     * @param fileName : Desired name of the file
     * @param parent   parent of the file
     * @return : File with desired name
     */
    private File createFile(String fileName, File parent) throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(true)) {
            try {
                if (parent.isDirectory()) {
                    File detailFile = new File(parent, fileName);
                    if (!detailFile.exists())
                        detailFile.createNewFile();
//                    else {
//                        String messege = "File already there ";
//                        throwException(messege);
//                    }
                    return detailFile;
                } else {
                    throwException(parent + " should be a directory");
                }
            } catch (IOException e) {
                e.printStackTrace();
                String errorMessege = "IOException " + e;
                throwException(errorMessege);
            } catch (Exception e) {
                e.printStackTrace();
                String errorMessege = "Exception " + e;
                throwException(errorMessege);
            }
        }
        return null;
    }

    /**
     * 创建文件夹
     */
    public void createAppDirectory() throws AppExternalFileWriter.ExternalFileWriterException {
        String directoryName = context.getString(R.string.topscomm_file);
        Log.d(TAG,"directoryName = " + directoryName);
        if (isExternalStorageAvailable(false)) {
            appDirectory = new File(Environment.getExternalStorageDirectory().toString(), directoryName);
            Log.d(TAG,"Environment.getExternalStorageDirectory().toString() = " + Environment.getExternalStorageDirectory().toString());
            createDirectory(appDirectory);
            appCacheDirectory = new File(externalCacheDirectory, directoryName);
            createDirectory(appCacheDirectory);
//            Log.d(TAG,"文件夹创建成功！");
        }
    }

    /**
     * 获取当前空闲空间大小
     * @return
     */
    private double getAvailableSpace() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdAvailSize = (double) stat.getAvailableBlocks() * (double) stat.getBlockSize();
        return sdAvailSize;
    }

    /**
     *
     * @param isForFile
     * @return
     * @throws ExternalFileWriterException
     */
    private boolean isExternalStorageAvailable(boolean isForFile) throws ExternalFileWriterException {
        String errorStarter = (isForFile) ? canNotWriteFile : canNotCreateDirectory;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else if (storageState.equals(Environment.MEDIA_BAD_REMOVAL)) {
            throwException(errorStarter + "Media was removed before it was unmounted.");
        } else if (storageState.equals(Environment.MEDIA_CHECKING)) {
            throwException(errorStarter + "Media is present and being disk-checked, "
                    + "Please wait and try after some time");
        } else if (storageState.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            throwException(errorStarter + "Presented Media is read only");
        } else if (storageState.equals(Environment.MEDIA_NOFS)) {
            throwException(errorStarter + "Blank or unsupported file media");
        } else if (storageState.equals(Environment.MEDIA_SHARED)) {
            throwException(errorStarter + "Media is shared with USB mass storage");
        } else if (storageState.equals(Environment.MEDIA_REMOVED)) {
            throwException(errorStarter + "Media is not present");
        } else if (storageState.equals(Environment.MEDIA_UNMOUNTABLE)) {
            throwException(errorStarter + "Media is present but cannot be mounted");
        } else if (storageState.equals(Environment.MEDIA_UNMOUNTED)) {
            throwException(errorStarter + "Media is present but not mounted");
        }
        return false;
    }

    private void throwException(String errorMessege) throws ExternalFileWriterException {
        throw new ExternalFileWriterException(errorMessege);
    }

    private File createDirectory(File directory) throws AppExternalFileWriter.ExternalFileWriterException {
        if (!directory.exists() || !directory.isDirectory()) {
            if (directory.mkdirs()) {
                String messege = "directory " + directory + " created : Path "  + directory.getPath();
            } else {
                if (directory.exists()) {
                    if (directory.isDirectory()) {
                        String messege = "directory " + directory + " Already exists : Path "  + directory.getPath();
                    } else {
                        String messege = directory + "should be a directory but found a file : Path " + directory.getPath();
                        throwException(messege);
                    }
                }
            }
        }
        return directory;
    }

    /**
     * 写文件
     * @param file : File where data is to be written.
     * @param data String which you want to write a file. If size of this is
     *             greater than size available, it will show error.
     */
    private void writeDataToFile(File file, String data, boolean append) throws AppExternalFileWriter.ExternalFileWriterException {
        byte[] stringBuffer = data.getBytes();
        writeDataToFile(file, stringBuffer,append);
    }

    /**
     * 写文件
     * @param file : File where data is to be written.
     * @param data byte array which you want to write a file. If size of this is
     *             greater than size available, it will show error.
     */
    private void writeDataToFile(File file, byte[] data, boolean append) throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(true)) {
            if (file.isDirectory()) {
                throwException(file + " 不是一个文件，不能写入");
            } else {
                if (file != null && data != null) {
                    double dataSize = data.length;
                    double remainingSize = getAvailableSpace();
                    if (dataSize >= remainingSize) {
                        throwException("没有有足够的空间");
                    } else {
                        try {
                            FileOutputStream out = null;
                            out = new FileOutputStream(file,append);
                            out.write(data);
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }



    private File getAppDirectory(boolean inCache) {
        return (inCache) ? this.appCacheDirectory : this.appDirectory;
    }

    /**
     * Creates subdirectory in application directory
     *
     * @param directoryName name of subdirectory
     * @return File object of created subdirectory
     * @throws
     */
    public File createSubDirectory(String directoryName, boolean inCache)
            throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(false)) {
            getAppDirectory();
            File subDirectory = new File(getAppDirectory(inCache), directoryName);
            return createDirectory(subDirectory);
        } else
            return null;
    }

    /**
     * Checks whether directory with given name exists in AppDirectory
     *
     * @param directoryName : Name of the directory to check.
     * @return true if a directory with "directoryName" exists, false otherwise
     */
    public boolean isDirectoryExists(String directoryName, boolean checkInCache) {
        File parentDirectory = (checkInCache) ? appCacheDirectory : appDirectory;
        return isDirectoryExists(directoryName, parentDirectory);
    }

    /**
     * Check whether file with given name exists in parentDirectory or not.
     *
     * @param fileName        : Name of the file to check.
     * @param parentDirectory : Parent directory where directory with "fileName" should be present
     * @return true if a file  with "fileName" exists, false otherwise
     */
    public boolean isFileExists(String fileName, File parentDirectory) {
        Log.d (TAG,"parentDirectory = " + parentDirectory.getPath ());
        File directoryToCheck = new File(parentDirectory, fileName);
        return directoryToCheck.exists() && directoryToCheck.isFile();
    }

    /**
     * Checks whether file with given name exists in AppDirectory
     *
     * @param fileName : Name of the file to check.
     * @return true if a file with "directoryName" exists, false otherwise
     */
    public boolean isFileExists(String fileName, boolean checkInCache) {
        File parentDirectory = (checkInCache) ? appCacheDirectory : appDirectory;
        return isFileExists(fileName, parentDirectory);
    }

    /**
     * Check whether directory with given name exists in parentDirectory or not.
     *
     * @param directoryName   : Name of the directory to check.
     * @param parentDirectory : Parent directory where directory with "directoryName" should be present
     * @return true if a directory with "directoryName" exists, false otherwise
     */
    public boolean isDirectoryExists(String directoryName, File parentDirectory) {
        File directoryToCheck = new File(parentDirectory, directoryName);
        return directoryToCheck.exists() && directoryToCheck.isDirectory();
    }

    /**
     * Creates subdirectory in parent directory
     *
     * @param parent        : Parent directory where directory with "directoryName" should be created
     * @param directoryName name of subdirectory
     * @return File object of created subdirectory
     * @throws
     */
    public File createSubDirectory(File parent, String directoryName)
            throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(false)) {

            getAppDirectory();

            if (!parent.isDirectory())
                throwException(parent.getName() + " Must be a directory ");

            File subDirectory = new File(parent, directoryName);

            return createDirectory(subDirectory);
        } else
            return null;
    }

    /**
     * Deletes given directory with all its subdirectories and its files.
     *
     * @param directory : Directory to delete
     */
    public void deleteDirectory(File directory) {
        if (directory != null) {
            if (directory.isDirectory())
                for (File child : directory.listFiles()) {

                    if (child != null) {
                        if (child.isDirectory())
                            deleteDirectory(child);
                        else
                            child.delete();
                    }
                }

            directory.delete();
        }
//		return false;
    }

    /**
     * Get created app directory
     *
     * @return File object of created AppDirectory
     */
    public File getAppDirectory() throws AppExternalFileWriter.ExternalFileWriterException {
        if (appDirectory == null) {
            createAppDirectory();
        }
        return appDirectory;
    }

    /**
     * get External Cache directory
     *
     * @return File object of External Cache directory
     */
    public File getExternalCacheDirectory() {
        return externalCacheDirectory;
    }

    /**
     * Get external storage directory
     *
     * @return File object of external storage directory
     */
    public File getExternalStorageDirectory() {
        return externalStorageDirectory;
    }

    /**
     * Write data in file of a parent directory
     *
     * @param parent   parent directory
     * @param fileName desired filename
     * @param data     data
     * @throws
     */
    public void writeDataToFile(File parent, String fileName, byte[] data, boolean append)
            throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory();
            File file = createFile(fileName, parent);
            writeDataToFile(file, data,append);
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     *
     * @param fileName name of the file
     * @param data     data to write
     * @throws AppExternalFileWriter.ExternalFileWriterException if external storage is not available or free space is
     *                                                                                                 less than size of the data
     */
    public boolean writeDataToFile(String fileName, String data, boolean inCache, boolean append, boolean toZipFile)
            throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory();

            File file = createFile(fileName, inCache);

            writeDataToFile(file, data,append);

            if(toZipFile){
                writeDataToZipFile(file,inCache,fileName);
            }
            return true;
        }else{
            return false;
        }
    }


    private boolean writeDataToZipFile(File file, boolean inCache, String fileName){
        boolean result = true;
        //创建目标文件，
        Log.d(TAG,"getAppDirectory(fasle)" + getAppDirectory(inCache) );
        String destName = getAppDirectory(inCache) + "/" + fileName + ".zip";
        Log.d(TAG,"destName = " + destName);
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        //设置解压密码
        if(ConstantUtil.SET_ZIP_PSW){
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            zipParameters.setPassword(ConstantUtil.ZIP_PSW);
        }
        try {
            ZipFile zipFile = new ZipFile(destName);
            zipFile.addFile(file,zipParameters);
        }catch (ZipException e){
            e.printStackTrace();
            Log.d(TAG,"e.getMessage = " + e.getMessage());
            result = false;
        }
        //删除被压缩文件
        file.delete();
        return result;
    }


    /**
     * 文件压缩
     * @param file  将要被压缩的文件
     * @param deleteSourceFle  压缩后是否删除源文件
     * @param isCache
     * @param fileName 文件名
     * @return
     */
    public String makeFileToZip(File file,boolean deleteSourceFle,boolean isCache,String fileName){
        //创建目标文件，
        try {
            getAppDirectory();
        } catch (ExternalFileWriterException e) {
            e.printStackTrace();
        }

        String destName = getAppDirectory(isCache) + "/" + fileName + ".zip";
        Log.d(TAG,"destName = " + destName);
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        //设置解压密码
        if(ConstantUtil.SET_ZIP_PSW){
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            zipParameters.setPassword(ConstantUtil.ZIP_PSW);
        }
        try {
            ZipFile zipFile = new ZipFile(destName);
            zipFile.addFile(file,zipParameters);
        }catch (ZipException e){
            e.printStackTrace();
            Log.d(TAG,"e.getMessage = " + e.getMessage());
            destName = "";
        }
        if(deleteSourceFle){
            //删除被压缩文件
            file.delete();
        }
        return destName;
    }


    /**
     * 文件压缩
     * @param file
     * @return
     */
    public String makeFileToZip(File file){
        return makeFileToZip(file,false,false,TimeMethods.TIME_METHODS.getTime());
    }



    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     *
     * @param fileName name of the file
     * @param data     data to write
     * @throws AppExternalFileWriter.ExternalFileWriterException if external storage is not available or free space is
     *                                                                                                 less than size of the data
     */
    public void writeDataToFile(String fileName, byte[] data, boolean inCache, boolean append)
            throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory();

            File file = createFile(fileName, inCache);

            writeDataToFile(file, data,append);
        }
    }

    /**
     * Write data in file of a parent directory
     *
     * @param parent   parent directory
     * @param fileName desired filename
     * @param data     data
     * @throws AppExternalFileWriter.ExternalFileWriterException if external storage is not available or free space is
     *                                                                                                 less than size of the data
     */
    public void writeDataToFile(File parent, String fileName, String data, boolean append)
            throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory();

            File file = createFile(fileName, parent);

            writeDataToFile(file, data,append);
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     * <p>
     * Name of the file will be the timestamp.extension
     * </p>
     *
     * @param extension extension of the file, pass null if you don't want to have
     *                  extension.
     * @param data      data to write
     * @param inCache   Pass true if you want to write data in External Cache. false if you want to write data in external directory.
     * @throws AppExternalFileWriter.ExternalFileWriterException if external storage is not available or free space is
     *                                                                                                 less than size of the data
     */
    public void writeDataToTimeStampedFile(String extension, String data, boolean inCache, boolean append)
            throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory();

            String fileExtension = (TextUtils.isEmpty(extension)) ? "" : "." + extension;
            String fileName = System.currentTimeMillis() + fileExtension;

            File file = createFile(fileName, getAppDirectory(inCache));

            writeDataToFile(file, data,append);
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     * <p>
     * Name of the file will be the timestamp.extension
     * </p>
     *
     * @param parent    parent directory path
     * @param extension extension of the file, pass null if you don't want to have
     *                  extension.
     * @param data      data to write
     * @throws AppExternalFileWriter.ExternalFileWriterException if external storage is not available or free space is
     *                                                                                                 less than size of the data
     */
    public void writeDataToTimeStampedFile(File parent, String extension, String data, boolean append)
            throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory();

            String fileExtension = (TextUtils.isEmpty(extension)) ? "" : "." + extension;
            String fileName = System.currentTimeMillis() + fileExtension;

            File file = createFile(fileName, parent);

            writeDataToFile(file, data,append);
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     * <p>
     * Name of the file will be the timestamp.extension
     * </p>
     *
     * @param extension extension of the file, pass null if you don't want to have
     *                  extension.
     * @param data      data to write
     * @throws AppExternalFileWriter.ExternalFileWriterException if external storage is not available or free space is
     *                                                                                                 less than size of the data
     */
    public void writeDataToTimeStampedFile(String extension, byte[] data, boolean inCache, boolean append)
            throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory();

            String fileExtension = (TextUtils.isEmpty(extension)) ? "" : "." + extension;
            String fileName = System.currentTimeMillis() + fileExtension;

            File file = createFile(fileName, getAppDirectory(inCache));

            writeDataToFile(file, data,append);
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     * <p>
     * Name of the file will be the timestamp.extension
     * </p>
     *
     * @param parent    parent directory path
     * @param extension extension of the file, pass null if you don't want to have
     *                  extension.
     * @param data      data to write
     * @throws AppExternalFileWriter.ExternalFileWriterException if external storage is not available or free space is
     *                                                                                                 less than size of the data
     */
    public void writeDataToTimeStampedFile(File parent, String extension, byte[] data, boolean append)
            throws AppExternalFileWriter.ExternalFileWriterException {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory();

            String fileExtension = (TextUtils.isEmpty(extension)) ? "" : "." + extension;
            String fileName = System.currentTimeMillis() + fileExtension;

            File file = createFile(fileName, parent);

            writeDataToFile(file, data,append);
        }
    }


    public byte[] toByteArray(String filename) throws IOException {
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Exception to report back developer about media state or storage state if
     * writing is not
     * possible
     */
    public class ExternalFileWriterException
            extends Exception {

        public ExternalFileWriterException(String messege) {
            super(messege);
        }

    }
}


