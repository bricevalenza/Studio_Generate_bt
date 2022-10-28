import org.apache.commons.codec.binary.Hex;
import studio.core.v1.writer.fs.FsStoryPackWriter;
import studio.driver.model.fs.FsDeviceInfos;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import studio.driver.model.fs.FsDeviceInfos;
import studio.driver.model.fs.FsStoryPackInfos;

class Simple { 

    private static final String DEVICE_METADATA_FILENAME = ".md";
    private static final String PACK_INDEX_FILENAME = ".pi";
    private static final String CONTENT_FOLDER = "BT";
    private static final String NODE_INDEX_FILENAME = "ni";
    private static final String NIGHT_MODE_FILENAME = "nm";
    private static final long FS_MOUNTPOINT_POLL_DELAY = 1000L;
    private static final long FS_MOUNTPOINT_RETRY = 10;
    private static final short DEVICE_METADATA_FORMAT_VERSION_1 = 1;
    private static final short DEVICE_METADATA_FORMAT_VERSION_2 = 2;
    private static final String FS_MOUNTPOINT_PROP = "studio.fs.mountpoint";

    
    public static void main(String args[]) throws IOException
    {  
    
     System.out.println("Hello Java");
  // Generate folder name
     String folderName = CONTENT_FOLDER;
     System.out.println("Uploading pack to folder: " + folderName);

     File destFolder = new File(folderName);
     destFolder.mkdirs();
          
     FsDeviceInfos infos = new FsDeviceInfos();
     try {
    	 System.out.println ("FsDeviceInfos");
    	 
    	 String mdFile = CONTENT_FOLDER + "/" +  DEVICE_METADATA_FILENAME ;
    	 FileInputStream deviceMetadataFis = new FileInputStream(mdFile);
    	 
    	 System.out.println (".md read OK");
    	 
    	 short mdVersion;
		mdVersion = readLittleEndianShort(deviceMetadataFis);
		System.out.println ("Device metadata format version: " + mdVersion);

         // Firmware version
         deviceMetadataFis.skip(4);
         short major = readLittleEndianShort(deviceMetadataFis);
         short minor = readLittleEndianShort(deviceMetadataFis);
         infos.setFirmwareMajor(major);
         infos.setFirmwareMinor(minor);
         //LOGGER.fine("Firmware version: " + major + "." + minor);
         
         System.out.println ("Firmware version: \"" + major + "-" + minor);
         // Serial number
         String serialNumber = null;
         long sn = readBigEndianLong(deviceMetadataFis);
         if (sn != 0L && sn != -1L && sn != -4294967296L) {
             serialNumber = String.format("%014d", sn);
             //LOGGER.fine("Serial Number: " + serialNumber);
             System.out.println ("\"Serial Number: \"" + serialNumber);
         } else {
             //LOGGER.warning("No serial number in SPI");
        	 System.out.println ("No serial number in SPI");
         }
         infos.setSerialNumber(serialNumber);

         // UUID
         deviceMetadataFis.skip(238);
         byte[] uuid = deviceMetadataFis.readNBytes(256);
         infos.setUuid(uuid);
         //LOGGER.fine("UUID: " + Hex.encodeHexString(uuid));
         System.out.println ("\"UUID: \"" + Hex.encodeHexString(uuid));
         deviceMetadataFis.close(); 	 
    	 
    	 
     } catch (IOException e) 
     {
    	 System.out.println ("Failed .md");
     }
    
     try {
         FsStoryPackWriter writer = new FsStoryPackWriter();
         writer.addBootFile(destFolder.toPath(), infos.getUuid());
     } catch (IOException e) 
     {
    	 System.out.println ("Failed to generate device-specific boot file");
     }
    }
    
    private static short readLittleEndianShort(FileInputStream fis) throws IOException {
        byte[] buffer = new byte[2];
        fis.read(buffer);
        ByteBuffer bb = ByteBuffer.wrap(buffer);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getShort();
    }

    private static long readBigEndianLong(FileInputStream fis) throws IOException {
        byte[] buffer = new byte[8];
        fis.read(buffer);
        ByteBuffer bb = ByteBuffer.wrap(buffer);
        bb.order(ByteOrder.BIG_ENDIAN);
        return bb.getLong();
    }
}  
