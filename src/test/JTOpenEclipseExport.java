///////////////////////////////////////////////////////////////////////////////
//
// JTOpen (IBM Toolbox for Java - OSS version)
//
// Filename:  JTOpenEclipseExport
//
// The source code contained herein is licensed under the IBM Public License
// Version 1.0, which has been approved by the Open Source Initiative.
// Copyright (C) 1997-2023 International Business Machines Corporation and
// others.  All rights reserved.
//
///////////////////////////////////////////////////////////////////////////////

package test;

import java.beans.PropertyVetoException;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import com.ibm.as400.access.*;

/**
 * This class is designed to update an IBM i system with the latest updates
 * currently worked on in eclipse.
 * 
 */
public class JTOpenEclipseExport extends Thread {

  private static boolean isWindows;
  private static boolean skipSameLen = false;
  private static boolean refreshAll = false; 
  String as400Name_;
  String userid_;
  String password_;
  private String compileError;

  static {
	  String osName= System.getProperty("os.name"); 
	  if (osName.indexOf("Win") >= 0) {
		  isWindows = true; 
	  }
  }
  public JTOpenEclipseExport(String as400Name, String userid, String password) {
    as400Name_ = as400Name;
    userid_ = userid;
    password_ = password;
  }

  public void run() {
    try {
     compileError = export(as400Name_,userid_,password_); 
    } catch (Exception e) {
      synchronized (System.out) {
        System.out.println("Exception caught exporting to " + as400Name_);
        e.printStackTrace(System.out);
      }
    }
  }

  public static void usage() {
    System.out.println("Usage:  java  test.JTOpenEclipseExport IBMi[+IBMi]* userid password   [REFRESHALL]  ");
    System.out.println("   Updates IBM i systems with the latest test changes in the Eclipse Environment");
    System.out.println("   Uses {user.dir}/lastUpdate.$system as a time marker");
    System.out.println("   If REFRESHALL is selected, the size of the files are checked and different sized files are moved to the system");
  }

  public static String export(String as400Name, String userid, String password) throws Exception {   
    String prefix = as400Name + ":";
    String currentDirectory = System.getProperty("user.dir");
    // Home directory should be the base of the git repository
    String homeDirectory = System.getProperty("user.home");
    String testDirectory = currentDirectory + File.separatorChar + "src";
    File iniDirectoryFile = new File(testDirectory + File.separatorChar + "ini");
    File jdbcDirectoryFile = new File(testDirectory + File.separatorChar + "jdbc");
    File testDirectoryFile = new File(testDirectory + File.separatorChar + "test");
    if (!testDirectoryFile.exists()) {
      System.out.println(prefix + "Error. directory " + testDirectory + File.separatorChar + "test"
          + " does not exist.  user.home is not the git root");
    }
    System.out.println(prefix + "Current directory is " + currentDirectory);
    long startTime = System.currentTimeMillis();
    String lastTimeFilename = homeDirectory + File.separatorChar + "lastUpdate." + as400Name;
    File lastTimeFile = new File(lastTimeFilename);
    if (!lastTimeFile.exists()) {
      lastTimeFile.createNewFile();
      lastTimeFile.setLastModified(startTime - 20 * 365 * 24 * 60 * 60000L);
      System.out.println(prefix + lastTimeFilename + " does not exist.  Creating a new one 20 years old");
    }
    long lastModifiedTime = lastTimeFile.lastModified();
    Timestamp lastModifiedTimestamp = new Timestamp(lastModifiedTime);
    System.out.println(prefix + "Comparing against " + lastTimeFilename + " " + lastModifiedTimestamp.toString());

    Vector<String> fileList = buildFileList(testDirectoryFile, "test", lastModifiedTime);
    Vector<String> iniList = buildFileList(iniDirectoryFile, "ini", lastModifiedTime);
    fileList.addAll(iniList);
    Vector<String> jdbcList = buildFileList(jdbcDirectoryFile, "jdbc", lastModifiedTime);
    fileList.addAll(jdbcList);

    AS400 as400 = new AS400(as400Name, userid, password.toCharArray());
    as400.setGuiAvailable(false);
    transferFiles(as400, testDirectory, fileList);
    System.out.println(prefix + "Files transferred");
    lastTimeFile.setLastModified(startTime);

    String compileError = compileFiles(as400); 
    synchronized (System.out) {
       if (compileError != null) { 
         System.out.println(prefix+"==================================================================================================="); 
         System.out.println(prefix+"COMPILE ERROR ("+compileError+") exporting to "+as400Name+" at "+ (new Timestamp(System.currentTimeMillis()))); 
         System.out.println(prefix+"==================================================================================================="); 
       } else {
         System.out.println(prefix+"==================================================================================================="); 
         System.out.println(prefix+"DONE exporting to "+as400Name+" at "+ (new Timestamp(System.currentTimeMillis()))); 
         System.out.println(prefix+"==================================================================================================="); 
       }
    }
    return compileError; 
  }

  public static void main(String args[]) {
    try {
      System.out.println("Usage:  java  test.JDTOpenEclipseExport IBMi userid password   ");
      System.out.println("   Updates an IBM i system with the latest changes in the Eclipse Environment");

      String as400Name = args[0];
      System.out.println("EXPORTING to " + as400Name);
      String userid = args[1];
      String password = args[2];
      if (args.length > 3) { 
        if ("REFRESHALL".equals(args[3])) { 
          System.out.println("REFRESHING ALL WITH DIFFERENT SIZE"); 
          refreshAll = true; 
          skipSameLen = true; 
        }
      }

      if (as400Name.indexOf('+') < 0) {
         String compileError = export(as400Name, userid, password); 
         if (compileError == null) { 
           System.out.println("Export to "+as400Name+" completed"); 
           
         } else { 
           System.out.println("Export to "+as400Name+" FAILED  with "+compileError); 
         }
      } else {
        String[] systems = as400Name.split("\\+");
        JTOpenEclipseExport[] threads = new JTOpenEclipseExport[systems.length];
        for (int i = 0; i < systems.length; i++) {
          threads[i] = new JTOpenEclipseExport(systems[i], userid, password);
        }
        for (int i = 0; i < systems.length; i++) {
          System.out.println("Starting export for system " + systems[i]);
          threads[i].start();
        }
        String systemList=""; 
        String errorSystemList = ""; 
        int completedCount = 0; 
        boolean[] joined = new boolean[systems.length]; 
        System.out.println("Waiting for exports"); 
        while (completedCount < systems.length) {
          for (int i = 0; i < systems.length; i++) {
            if (!joined[i]) {
              if (!threads[i].isAlive()) {
                threads[i].join();
                if ( threads[i].compileError != null) { 
                  errorSystemList += " " + systems[i];
                }
                systemList += " " + systems[i];
                completedCount++;
                System.out.println(
                    "Export completed for " + (completedCount) + "/" + systems.length + " systems " + systemList);
                joined[i] = true; 
              }
            }
          }
        }
        if (errorSystemList.length() > 0) { 
          System.out.println("Compiles failed on the following systems : "+errorSystemList);
        }
        System.out.println("All exports done");
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      usage();
    }

  }

  protected static String compileFiles(AS400 as400) throws AS400SecurityException, ErrorCompletingRequestException, IOException, InterruptedException, PropertyVetoException {
   String errorOutput = null; 
    String prefix = as400 + ":";
    System.out.println(prefix + "Running compile ");
    CommandCall cc = new CommandCall(as400);
    String compileCommand = "QSYS/QSH CMD('cd /home/jdbctest; test/AllQshCompileScript > /tmp/JTOpenCompile.out 2>&1') ";
    cc.run(compileCommand);
    IFSFile ifsFile = new IFSFile(as400, "/tmp/JTOpenCompile.out");
    IFSFileReader reader = new IFSFileReader(ifsFile);
    BufferedReader bufferedReader = new BufferedReader(reader);
    String line = bufferedReader.readLine();
    while (line != null) {
      System.out.println(prefix + line);
     if (line.indexOf("COMPILE ERROR") >= 0) { 
       errorOutput= "COMPILE ERROR"; 
     }
      line = bufferedReader.readLine();
    }
    bufferedReader.close();
   
   return errorOutput; 
  }

  static int bufferCleanup(byte[] buffer, int length, boolean binary) {
    if (!binary) {
      /* Remove any CR in the file */
      int toPosition = 0;
      for (int i = 0; i < length; i++) {
        boolean skipCR = false;
        if (buffer[i] < 0x20) {
          switch (buffer[i]) {
          case 0x0a:
          case 0x09:
            break;
          case 0x0D:
            skipCR = true;
            break;
          default:
            buffer[i] = 0x20;
            break;
          }
        } /* if control */
        if (skipCR) {
          // Do not write anything
        } else {
          if (toPosition == i) {
            /* nothing to move */
          } else {
            buffer[toPosition] = buffer[i];
          }
          toPosition++;
        }
      }
      return toPosition;
    } else {
      /* Binary */
      return length;
    }
  }

  static void transferFiles(AS400 as400, String testDirectory, Vector<String> fileList)
			throws IOException, AS400SecurityException, SQLException {
    String prefix = as400.getSystemName() + ":" + as400.getUserId() + ":";
    int totalCount = fileList.size();
    int count = 0;
    int transferCount = 0;
    long totalTransferTime = 0; 
    long startMillis = System.currentTimeMillis();
		Hashtable<String,Long> sizeHash = new Hashtable<String,Long>(); 
    Hashtable<String,Timestamp> timeHash = new Hashtable<String,Timestamp>(); 
		
    AS400JDBCDriver driver = new AS400JDBCDriver(); 
    
    Properties jdbcProperties = new Properties();
    jdbcProperties.put("block size", "512"); 
    Connection connection = driver.connect(as400,jdbcProperties,"QGPL"); 
    Statement s = connection.createStatement(); 
    int changeSize = fileList.size();
		if (changeSize < 500) { 
	    System.out.println(prefix + "Gathering some file statistics for changed size of "+changeSize); 
      String sql = "select PATH_NAME, DATA_SIZE, MAX(CREATE_TIMESTAMP, DATA_CHANGE_TIMESTAMP) AS TS from table(IFS_OBJECT_STATISTICS( ? )) ";
		  PreparedStatement ps = connection.prepareStatement(sql); 
		  
	    Enumeration<String> enumeration = fileList.elements();
	    int statCount = 0; 
	    while (enumeration.hasMoreElements()) {
	      statCount++;
	      String localFilename = enumeration.nextElement();
	      String remoteFilename = getRemoteFilename(testDirectory, localFilename);
	      ps.setString(1, remoteFilename); 
	      ResultSet rs = ps.executeQuery(); 
	      while (rs.next()) {
	        String filename = rs.getString(1);
	        sizeHash.put(filename, Long.valueOf(rs.getLong(2)));
	        timeHash.put(filename, rs.getTimestamp(3));
	        if (statCount % 1000 == 0)
	          System.out.println(prefix+" statCount="+statCount );
	      }
	      rs.close(); 
	    }
		  
    } else {
      System.out.println("Gathering all statistics for changed size of " + changeSize);
      String sql = "select PATH_NAME, DATA_SIZE, MAX(CREATE_TIMESTAMP, DATA_CHANGE_TIMESTAMP) AS TS from table(IFS_OBJECT_STATISTICS('/home/jdbctest', OMIT_LIST=>'/home/jdbctest/ct /home/jdbctest/gen /home/jdbctest/build')) ";
      ResultSet rs = s.executeQuery(sql);
      System.out.println("Gathering query results");
      int statCount = 0; 
      while (rs.next()) {
        String filename = rs.getString(1);
        statCount++; 
        sizeHash.put(filename, Long.valueOf(rs.getLong(2)));
        timeHash.put(filename, rs.getTimestamp(3));
        if (statCount % 1000 == 0)
          System.out.println(prefix+" statCount="+statCount );
      }
      rs.close(); 
    }
    s.close(); 
    connection.close(); 
		
		
    Enumeration<String> enumeration = fileList.elements();
    int skipCount = 0; 
    while (enumeration.hasMoreElements()) {
      String localFilename = enumeration.nextElement();
      boolean binary = isBinaryFile(localFilename);
      String remoteFilename = getRemoteFilename(testDirectory, localFilename);
      long currentMillis = System.currentTimeMillis();
      long elapsedMillis = currentMillis - startMillis;
      double millisPerFile;
      if (transferCount > 0)
        millisPerFile = (double) totalTransferTime / (double) transferCount; 
      else
        millisPerFile = (double) elapsedMillis / (double) count;

      int left = totalCount - count;
      double leftSeconds = left * millisPerFile / 1000;
      count++;
      File localFile = new File(testDirectory + File.separatorChar + localFilename);
			long ifsLastModified = 0;
			Timestamp ifsTime = timeHash.get(remoteFilename);
			if (ifsTime != null) ifsLastModified = ifsTime.getTime(); 
			if (localFile.lastModified() <= ifsLastModified && !refreshAll) {
        skipCount++; 
        if(skipCount % 100 == 1) 
					System.out.println(
							count + "("+skipCount+")/" + totalCount + " (" + leftSeconds + " s) " + prefix + "  " + remoteFilename);

      } else {
			  
				boolean doTransfer = true;
        Long longLen = sizeHash.get(remoteFilename); 
				if ((longLen != null)  && skipSameLen ) {
					long localLen = calculateUnixLen(localFile);
					long remoteLen = 0;
					remoteLen = longLen.longValue(); 
					if (localLen == remoteLen ) {
						doTransfer = false; 
						skipCount++;
						if (skipCount % 100 == 1)
							System.out.println(prefix+" "+(count-skipCount)+"+"+skipCount+"="+count+"/" + totalCount + " (" + leftSeconds + " s) "  + remoteFilename);
						if (skipCount % 1000 == 1) {
							long endTime = System.currentTimeMillis() + (long)(leftSeconds*1000);
						    Timestamp endTs = new Timestamp(endTime); 
						    System.out.println(prefix+" Predict completion at "+endTs); 
						}
					}
				}
				if (doTransfer) {
        transferCount++;
          System.out.println(prefix+" "+(count-skipCount)+"+"+skipCount+"="+count+"/" + totalCount + " (" + leftSeconds + " s) Transferring to "  + remoteFilename);

      long startTransferTime = System.currentTimeMillis(); 
			    IFSFile ifsFile = new IFSFile(as400, remoteFilename);
        ifsFile.delete();
        verifyParent(ifsFile);
        ifsFile.createNewFile();
					if (binary) { 
            ifsFile.setCCSID(65535);
					} else { 
        ifsFile.setCCSID(1208);
					}
        @SuppressWarnings("resource")
        IFSFileOutputStream ifsFileOutputStream = new IFSFileOutputStream(ifsFile);
        @SuppressWarnings("resource")
					FileInputStream fileInputStream = new FileInputStream(
							testDirectory + File.separatorChar + localFilename);
        byte[] buffer = new byte[65536];
        int bytesRead = fileInputStream.read(buffer);
        while (bytesRead >= 0) {
						int bytesToWrite;
						bytesToWrite = bufferCleanup(buffer, bytesRead, binary);
          ifsFileOutputStream.write(buffer, 0, bytesToWrite);
          bytesRead = fileInputStream.read(buffer);
        }
        fileInputStream.close();
        ifsFileOutputStream.close();
      long endTransferTime = System.currentTimeMillis(); 
      totalTransferTime += endTransferTime - startTransferTime; 
      }
    }
		}

	}

  private static long calculateUnixLen(File localFile) throws IOException {

	  if (isWindows) { 
		  FileReader reader = new FileReader(localFile); 
		  char[] buffer = new char[16384]; 
		  long count = 0; 
		int readChars = reader.read(buffer);  
		while (readChars > 0) { 
			for (int i = 0; i < readChars; i++) {
				if (buffer[i] != 0x0d) {
					count++;
				}
			}
			readChars = reader.read(buffer);  
		}
		reader.close(); 
		return count; 
  }
	  return localFile.length();
	}

  static boolean isBinaryFile(String localFilename) {
    if (localFilename.endsWith(".zip")) return true; 
    if (localFilename.endsWith(".jar")) return true; 
    if (localFilename.endsWith(".savf")) return true; 
    return false;
  }

  private static void verifyParent(IFSFile ifsFile) throws IOException {

    IFSFile parent = ifsFile.getParentFile();
    if (!parent.exists()) {
      parent.mkdirs();
    }
  }

  private static String getRemoteFilename(String testDirectory, String localFilename) {
    return "/home/jdbctest/" + localFilename.replace(File.separatorChar, '/');
  }

  private static Vector<String> buildFileList(File testDirectoryFile, String prefix, long lastModifiedTime) {
    Vector<String> returnList = new Vector<String>();
    File[] files = testDirectoryFile.listFiles();
    for (int i = 0; i < files.length; i++) {
      File f = files[i];
      String fName = f.getName();
      if ((fName.indexOf(".git") < 0) &&
    		  (fName.indexOf(".class") < 0) &&
          !(fName.equals("gen")) &&
          !(fName.equals("bin")) &&
          (fName.indexOf("deleteMe") < 0) &&
          (fName.indexOf("runit") < 0) &&
          (fName.indexOf(".out") < 0)){
        if (f.isFile()) {
          if ((f.lastModified() > lastModifiedTime) || refreshAll) {
            if (prefix.length() > 0) {
              returnList.add(prefix + File.separator + fName);
            } else {
              returnList.add(fName);
            }
          }
        } else if (f.isDirectory()) {
          Vector<String> newList;
          if (prefix.length() > 0) {
            newList = buildFileList(f, prefix + File.separator + fName, lastModifiedTime);
          } else {
            newList = buildFileList(f, fName, lastModifiedTime);
          }
          returnList.addAll(newList);
        }
      }
    }

    return returnList;
  }

}
