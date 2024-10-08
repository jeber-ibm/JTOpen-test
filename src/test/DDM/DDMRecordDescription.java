///////////////////////////////////////////////////////////////////////////////
//
// JTOpen (IBM Toolbox for Java - OSS version)
//
// Filename:  DDMRecordDescription.java
//
// The source code contained herein is licensed under the IBM Public License
// Version 1.0, which has been approved by the Open Source Initiative.
// Copyright (C) 1997-2023 International Business Machines Corporation and
// others.  All rights reserved.
//
///////////////////////////////////////////////////////////////////////////////

package test.DDM;

import java.io.*;

import java.util.Vector;
import java.math.BigDecimal;
import com.ibm.as400.access.*;

import test.Testcase;

/**
 *Testcase DDMRecordDescription.  This test class verifies valid and
 *invalid usage of the public interface of the AS400FileRecordDescription
 *class.
**/
public class DDMRecordDescription extends Testcase
{
  public static void main(String args[]) throws Exception {
    String[] newArgs = new String[args.length+2];
     newArgs[0] = "-tc";
     newArgs[1] = "DDMRecordDescription";
     for (int i = 0; i < args.length; i++) {
       newArgs[2+i]=args[i];
     }
     test.DDMTest.main(newArgs); 
   }
  long start;
  long time;
  String testLib_ = null;
  AS400 pwrSys_;

  /**
  Constructor.  This is called from the DDMTest constructor.
  **/
  public DDMRecordDescription(AS400            systemObject,
                              Vector           variationsToRun,
                              int              runMode,
                              FileOutputStream fileOutputStream,
                              
                              String testLib,
                              AS400 pwrSys)
  {
    // The third parameter is the total number of variations in this class.
    super(systemObject, "DDMRecordDescription", 6,
          variationsToRun, runMode, fileOutputStream);
    testLib_ = testLib;
    pwrSys_ = pwrSys;
  }

  /**
    Runs the variations requested.
  **/
  public void run()
  {
    boolean allVariations = (variationsToRun_.size() == 0);

    // Connect to the AS/400 for record the record level access service
    try
    {
      systemObject_.connectService(AS400.RECORDACCESS);
    }
    catch(Exception e)
    {
      System.out.println("Unable to connect to the AS/400");
      return;
    }

    // Do any necessary setup work for the variations
    try
    {
      setup();
    }
    catch (Exception e)
    {
      // Testcase setup did not complete successfully
      System.out.println("Unable to complete setup; variations not run");
      return;
    }

    if (allVariations || variationsToRun_.contains("1")) Var001();
    if (allVariations || variationsToRun_.contains("2")) Var002();
    if (allVariations || variationsToRun_.contains("3")) Var003();
    if (allVariations || variationsToRun_.contains("4")) Var004();
    if (allVariations || variationsToRun_.contains("5")) Var005();
    if (allVariations || variationsToRun_.contains("6")) Var006();

    // Do any necessary cleanup
    try
    {
      cleanup();
    }
    catch (Exception e)
    {
      System.out.println("Cleanup failed");
    }

    // Disconnect from the AS/400 for record the record level access service
    systemObject_.disconnectService(AS400.RECORDACCESS);
  }

  /**
   @exception  Exception  If an exception occurs.
   **/
  protected void setup()
    throws Exception
  {
    try
    {
      // Verify the existence of library DDMTESTSAV on the system
      CommandCall c = new CommandCall(systemObject_, "CHKOBJ OBJ(DDMTESTSAV) OBJTYPE(*LIB)");
      boolean ran = c.run();
      AS400Message[] msgs = c.getMessageList();
      if (msgs.length != 0)
      {
        for (int i = 0; i < msgs.length; ++i)
        {
          System.out.println(msgs[i]);
        }
        System.out.println("Either library DDMTESTSAV does not exist or you");
        System.out.println("do not have authority to it.");
        System.out.println("ftp DDMTESTSAV.SAVF in binary from");
        System.out.println("the test/ directory in git");
        System.out.println("to the AS/400 system to which you are running.");
        System.out.println("Use RSTLIB to restore library DDMTESTSAV to the system.");
        throw new Exception("");
      }

      CommandCall cc = new CommandCall(pwrSys_);
      deleteLibrary(cc, testLib_);

      if (!cc.run("CRTLIB "+testLib_))
      {
        output_.println("Unable to create library "+testLib_+": "+cc.getMessageList()[0].toString());
      }
      if (! cc.run("GRTOBJAUT OBJ("+testLib_+") OBJTYPE(*LIB) USER(JAVA) AUT(*ALL)")) {
	  output_.println("Unable to grant permission to library "+testLib_+": "+cc.getMessageList()[0].toString());

      } 
       
    }
    catch(Exception e)
    {
      e.printStackTrace(output_);
      throw e;
    }
  }

  /**
   @exception  Exception  If an exception occurs.
   **/
  protected void cleanup()
    throws Exception
  {
    try
    {
      // Verify the existence of library DDMTESTSAV on the system
      CommandCall c = new CommandCall(systemObject_, "CLRPFM DDMTESTSAV/SIMPLESEQ");
      c.run();
      c.run("CLRPFM DDMTESTSAV/SIMPLEKEY");
      c.run("CLRPFM DDMTESTSAV/ALLFLDS");
      c.run("CLRPFM DDMTESTSAV/ALLFLDSKEY");
    }
    catch(Exception e)
    {
      e.printStackTrace(output_);
      throw e;
    }
  }

  /**
   *Verify valid usage of AS400FileRecordDescription().
   *<br>
   *Expected results:
   *<ul compact>
   *<li>getPath() returns an empty string
   *<li>getMemberName() returns an empty string
   *<li>getFileName() returns an empty string
   *<li>getSystem() returns null.
   *</ul>
  **/
  public void Var001()
  {
    setVariation(1);
    try
    {
      AS400FileRecordDescription rd = new AS400FileRecordDescription();
      if (!rd.getPath().equals(""))
      {
        failed("Empty string not returned for getPath()");
        return;
      }
      if (!rd.getMemberName().equals(""))
      {
        failed("Empty string not returned for getMemberName()");
        return;
      }
      if (!rd.getFileName().equals(""))
      {
        failed("Empty string not returned for getFileName()");
        return;
      }
      if (rd.getSystem() != null)
      {
        failed("Null not returned for getSystem()");
        return;
      }
      succeeded();
    }
    catch(Exception e)
    {
      failed(e, "Unexpected exception");
    }
  }

  /**
   *Verify valid usage of AS400FileRecordDescription(system, name).
   *<ul compact>
   *<li>Pass in a name with no member
   *<li>Pass in a name containing special value %FILE% 
   *<li>Pass in a name with a member
   *</ul>
   *Expected results:
   *<ul compact>
   *<li>getSystem() will return the AS400 object passed in.
   *<li>getPath() will return the path name passed in.
   *<li>getMember() will return *FIRST when no member is specified
   *<li>getMember() will return the file name when %FILE% is specified
   *for the member name.
   *<li>getMember() will return the member name specified when a member
   *name is specified.
   *<li>getFileName() will return the file name specified.
   *</ul>
  **/
  public void Var002()
  {
    setVariation(2);
    try
    {
      AS400FileRecordDescription rd1 = new AS400FileRecordDescription(systemObject_, "/QSYS.LIB/MYFILE.FILE");
      AS400FileRecordDescription rd2 = new AS400FileRecordDescription(systemObject_, "/QSYS.LIB/" + testLib_ + ".LIB/MYFILE.FILE/%FILE%.MBR");
      AS400FileRecordDescription rd3 = new AS400FileRecordDescription(systemObject_, "/QSYS.LIB/MYFILE.FILE/MYMBR.MBR");

      if (systemObject_ != rd1.getSystem())
      {
        failed("Incorrect system object returned from getSystem()");
        return;
      }
      if (!rd2.getPath().equals("/QSYS.LIB/" + testLib_ + ".LIB/MYFILE.FILE/%FILE%.MBR"))
      {
        failed("Incorrect path returned from getPath()");
        return;
      }
      if (!rd1.getMemberName().equals("*FIRST"))
      {
        failed("Incorrect member name (rd1) returned from getMemberName()");
        return;
      }
      if (!rd2.getMemberName().equals("MYFILE"))
      {
        failed("Incorrect member name (rd2) returned from getMemberName()");
        return;
      }
      if (!rd3.getMemberName().equals("MYMBR"))
      {
        failed("Incorrect member name (rd3) returned from getMemberName()");
        return;
      }
      if (!rd1.getFileName().equals("MYFILE"))
      {
        failed("Incorrect file name (rd1) returned from getFileName()");
        return;
      }
      succeeded();
    }
    catch(Exception e)
    {
      failed(e, "Unexpected exception");
    }
  }

  /**
   *Verify invalid usage of AS400FileRecordDescription(system, name).
   *<ul compact>
   *<li>Specify null for <i>system</i>
   *<li>Specify null for <i>name</i>
   *<li>Specify invalid object type in <i>name</i>
   *<li>Specify invalid IFS name (member without file) for <i>name</i>
   *</ul>
   *Expected results:
   *<ul compact>
   *<li>NullPointerException indicating "system"
   *<li>NullPointerException indicating "name"
   *<li>IllegalPathNameException indicating the name value and
   *OBJECT_TYPE_NOT_VALID
   *<li>IllegalPathNameException indicating the name value and
   *MEMBER_WITHOUT_FILE
   *</ul>
  **/
  public void Var003()
  {
    setVariation(3);
    try
    {
      AS400FileRecordDescription rd = null;
      String name = "/QSYS.LIB/FILE.FILE/MBR.MBR";
      String badObject = "/QSYS.LIB/FILE.DTAQ";
      String noFile = "/QSYS.LIB/MBR.MBR";
      try
      {
        rd = new AS400FileRecordDescription(null, name);
        failed("No exception specifying null for system");
        return;
      }
      catch(Exception e)
      {
        if (!exceptionIs(e, "NullPointerException", "system"))
        {
          failed(e, "Incorrect exception specifying null for system");
          return;
        }
      }
      try
      {
        rd = new AS400FileRecordDescription(systemObject_, null);
        failed("No exception specifying null for name");
        return;
      }
      catch(Exception e)
      {
        if (!exceptionIs(e, "NullPointerException", "name"))
        {
          failed(e, "Incorrect exception specifying null for name");
          return;
        }
      }
      try
      {
        rd = new AS400FileRecordDescription(systemObject_, badObject);
        failed("No exception specifying incorrect object type for name");
        return;
      }
      catch(Exception e)
      {
        if (!exceptionStartsWith(e, "IllegalPathNameException", badObject, IllegalPathNameException.OBJECT_TYPE_NOT_VALID))
        {
          failed(e, "Incorrect exception specifying incorrect object type");
          return;
        }
      }
      try
      {
        rd = new AS400FileRecordDescription(systemObject_, noFile);
        failed("No exception specifying incorrect IFS name for name");
        return;
      }
      catch(Exception e)
      {
        if (!exceptionStartsWith(e, "IllegalPathNameException", noFile, IllegalPathNameException.MEMBER_WITHOUT_FILE))
        {
          failed(e, "Incorrect exception specifying incorrect IFS name");
          return;
        }
      }

      succeeded();
    }
    catch(Exception e)
    {
      failed(e, "Unexpected exception");
    }
  }

  /**
   *Verify valid usage of the createRecordFormatSource() method.
   *<br><b>Note: </b>This is an attended variation.
   *<ul compact>
   *<li>Specify null for the filePath.
   *<li>Specify a valid filePath.
   *<li>Specify null for the packageName.
   *<li>Specify a packageName.
   *</ul>
   *Expected results:
   *<ul compact>
   *<li>The Java source files will be created with the expected names and
   *paths.
   *<li>The class names will be correct.
   *<li>The Java source will compile without errors or warnings.
   *<li>The .class output from the compiled source will be usable in
   *Java code using the record level access classes.
   *</ul>
  **/
  public void Var004()
  {
    setVariation(4);
    if (runMode_ == UNATTENDED)
    {
      notApplicable("Attended variation.");
      return;
    }
    AS400FileRecordDescription rd = null;
    String simple = "/QSYS.LIB/DDMTESTSAV.LIB/SIMPLESEQ.FILE";
    try
    {
      rd = new AS400FileRecordDescription(systemObject_, simple);
      rd.createRecordFormatSource(null, null);
      System.out.println("Verify the following:");
      System.out.println("  1) File SIMPLEFormat.java exists in the current (client) directory.");
      System.out.println("  2) Verify the contents of the file:");
      System.out.println("     a) No package statement");
      System.out.println("     b) Class name = SIMPLEFormat and extends RecordFormat");
      System.out.println("     c) Three fields: FIELD1 (CHAR10), TENCHARFLD (BIN7), ZONED (27,4)");
      System.out.println("     d) CCSID for character fields = the ccsid displayed for the field");
      System.out.println("        in the output from the DSPFFD DDMTESTSAV/SIMPLESEQ command on");
      System.out.println("        the AS/400.");
      System.out.println("     e) No key fields");
      System.out.println("  3) The file compiles.\n");
      System.out.println("Is everything ok (Y/N)?");
      InputStreamReader r = new InputStreamReader(System.in);
      BufferedReader inBuf = new BufferedReader(r);
      String resp = inBuf.readLine();
      if (!resp.equals("y") && !resp.equals("Y"))
      {
        failed("Verification of createRecordFormatSource(null, null)");
        return;
      }
      rd = new AS400FileRecordDescription(systemObject_, simple);
      System.out.println("Enter a directory path in which to create the file");
      String path = null;
      String pkg = null;
      try
      {
        path = inBuf.readLine();
      }
      catch(IOException e)
      {
        failed(e, "Unable to read path specified");
        return;
      }
      System.out.println("Enter a package name in which to create the file");
      try
      {
        pkg = inBuf.readLine();
      }
      catch(IOException e)
      {
        failed(e, "Unable to read package specified");
        return;
      }
      rd.createRecordFormatSource(path, pkg);
      System.out.println("Verify the following:");
      System.out.println("  1) File SIMPLEFormat.java exists in client directory " + path);
      System.out.println("  2) Verify the contents of the file:");
      System.out.println("     a) package " + pkg);
      System.out.println("     b) Class name = SIMPLEFormat and extends RecordFormat");
      System.out.println("     c) Three fields: FIELD1 (CHAR10), TENCHARFLD (BIN7), ZONED (27,4)");
      System.out.println("     d) CCSID for character fields = the ccsid displayed for the field");
      System.out.println("        in the output from the DSPFFD DDMTESTSAV/SIMPLESEQ command on");
      System.out.println("        the AS/400.");
      System.out.println("     e) No key fields");
      System.out.println("  3) The file compiles.\n");
      System.out.println("Is everything ok (Y/N)?");
      resp = inBuf.readLine();
      if (!resp.equals("y") && !resp.equals("Y"))
      {
        failed("Verification of createRecordFormatSource(" + path + ", " + pkg + ")");
        return;
      }
      succeeded();
    }
    catch(Exception e)
    {
      failed(e, "Unexpected exception");
    }
  }

  /**
   *Verify valid usage of the createRecordFormatSource() method on
   *physical files.
   *<br><b>Note: </b>This is an attended variation.
   *<ul compact>
   *<li>Specify a physical file with a simple record format
   *<li>Specify a physical file with a simple keyed record format
   *<li>Specify a physical file with a record format which contains many
   *fields and keywords such as ALWNULL, VARLEN and DFT.
   *<li>Specify a physical file with a record format which contains many
   *fields and keywords such as ALWNULL, VARLEN and DFT, and key fields.
   *</ul>
   *Expected results:
   *<ul compact>
   *<li>The Java source will compile without errors or warnings.
   *<li>The .class output from the compiled source will be usable in
   *Java code using the record level access classes.
   *</ul>
  **/
  public void Var005()
  {
    setVariation(5);
    if (runMode_ == UNATTENDED)
    {
      notApplicable("Attended variation.");
      return;
    }
    AS400FileRecordDescription rd1 = null;
    AS400FileRecordDescription rd2 = null;
    AS400FileRecordDescription rd3 = null;
    AS400FileRecordDescription rd4 = null;
    String simple = "/QSYS.LIB/DDMTESTSAV.LIB/SIMPLESEQ.FILE";
    String simpleKey = "/QSYS.LIB/DDMTESTSAV.LIB/SIMPLEKEY.FILE";
    String complex = "/QSYS.LIB/DDMTESTSAV.LIB/ALLFLDS.FILE";
    String complexKey = "/QSYS.LIB/DDMTESTSAV.LIB/ALLFLDSKEY.FILE";
    try
    {
      rd1 = new AS400FileRecordDescription(systemObject_, simple);
      rd1.createRecordFormatSource(null, null);
      rd2 = new AS400FileRecordDescription(systemObject_, simpleKey);
      rd2.createRecordFormatSource(null, null);
      RecordFormat[] rfs = rd2.retrieveRecordFormat();
      rd3 = new AS400FileRecordDescription(systemObject_, complex);
      rd3.createRecordFormatSource(null, null);
      rd4 = new AS400FileRecordDescription(systemObject_, complexKey);
      rd4.createRecordFormatSource(null, null);
      InputStreamReader r = new InputStreamReader(System.in);
      BufferedReader inBuf = new BufferedReader(r);
      System.out.println("Please ensure that the following files exist in the current working directory and compile them:");
      System.out.println("  SIMPLEFormat.java");
      System.out.println("  SIMPLEKEYFormat.java");
      System.out.println("  ALLFLDSFormat.java");
      System.out.println("  ALLFLDSKEYFormat.java\n");
      System.out.println("Do the files exist and compile (Y/N)?");
      String resp = inBuf.readLine();
      if (!resp.equals("y") && !resp.equals("Y"))
      {
        failed("Verification of created files");
        return;
      }

      Class simpleClass = Class.forName("SIMPLEFormat");
      Class simpleKeyClass = Class.forName("SIMPLEKEYFormat");
      Class complexClass = Class.forName("ALLFLDSFormat");
      Class complexKeyClass = Class.forName("ALLFLDSKEYFormat");
      RecordFormat rf1 = (RecordFormat)simpleClass.newInstance();
      RecordFormat rf2 = (RecordFormat)simpleKeyClass.newInstance();
      RecordFormat rf3 = (RecordFormat)complexClass.newInstance();
      RecordFormat rf4 = (RecordFormat)complexKeyClass.newInstance();

      SequentialFile s1 = new SequentialFile(systemObject_, simple);
      s1.setRecordFormat(rf1);
      KeyedFile k1 = new KeyedFile(systemObject_, simpleKey);
      k1.setRecordFormat(rf2);
      SequentialFile s2 = new SequentialFile(systemObject_, complex);
      s2.setRecordFormat(rf3);
      KeyedFile k2 = new KeyedFile(systemObject_, complexKey);
      k2.setRecordFormat(rf4);
      try
      {
        s1.open(AS400File.READ_WRITE, 0, AS400File.COMMIT_LOCK_LEVEL_NONE);
        s2.open(AS400File.READ_WRITE, 0, AS400File.COMMIT_LOCK_LEVEL_NONE);
        k1.open(AS400File.READ_WRITE, 0, AS400File.COMMIT_LOCK_LEVEL_NONE);
        k2.open(AS400File.READ_WRITE, 0, AS400File.COMMIT_LOCK_LEVEL_NONE);

        Record r1 = rf1.getNewRecord(); // Simple
        Record r2 = rf2.getNewRecord(); // Simple key
        Record r3 = rf3.getNewRecord(); // complex
        Record r4 = rf4.getNewRecord(); // complex key

        String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int i = 1; i < 9; ++i)
        {
          r1.setField(0, "RECORD" + String.valueOf(i) + "   ");
          r1.setField(1, new Integer(i));
          r1.setField(2, new BigDecimal(String.valueOf(i) + "." + String.valueOf(i)));
          s1.write(r1);

          r2.setField(0, "RECORD" + String.valueOf(i) + "   ");
          r2.setField(1, new Integer(i));
          r2.setField(2, new BigDecimal(String.valueOf(i) + "." + String.valueOf(i)));
          k1.write(r2);

          r3.setField("VARCHAR", "RECORD" + String.valueOf(i));
          r3.setField("BINARY2", new Short((short)i));
          r3.setField("BINARY4", new Integer(i+10000));
          r3.setField("DATE", "01/01/97");
          r3.setField("TIME", "12.59.33");
          r3.setField("TIMESTAMP", "1997-01-01-12.59.33");
          r3.setField("CHARACTER", "A");
          r3.setField("PACKEDDEC", new BigDecimal("1"));
          s2.write(r3);

          r4.setField("CHARACTER", chars[i]);
          r4.setField("BINARY2", new Short((short)(-(i))));
          r4.setField("BINARY4", new Integer(i+10000));
          r4.setField("DATE", "01/0" + String.valueOf(i) + "/97");
          r4.setField("TIME", "12.59.33");
          r4.setField("TIMESTAMP", "1997-01-01-12.59.33.000000");
          r4.setField("ZONEDDEC", new BigDecimal(String.valueOf(i) + ".999"));
          k2.write(r4);
        }
        s1.close();
        s2.close();
        k1.close();
        k2.close();

        Record[] recs = s1.readAll();
        System.out.println("Records in " + s1.getFileName());
        for (int i = 0; i < recs.length; ++i)
        {
          System.out.println(recs[i]);
        }
        recs = s2.readAll();
        System.out.println("Records in " + s2.getFileName());
        for (int i = 0; i < recs.length; ++i)
        {
          System.out.println(recs[i]);
        }
        recs = k1.readAll();
        System.out.println("Records in " + k1.getFileName());
        for (int i = 0; i < recs.length; ++i)
        {
          System.out.println(recs[i]);
        }
        recs = k2.readAll();
        System.out.println("Records in " + k2.getFileName());
        for (int i = 0; i < recs.length; ++i)
        {
          System.out.println(recs[i]);
        }
      }
      catch(Exception e)
      {
        failed(e, "Checking that formats work with existing files");
        s1.close();
        s2.close();
        k1.close();
        k2.close();
        return;
      }
      succeeded();
    }
    catch(Exception e)
    {
      failed(e, "Unexpected exception");
    }
  }

  /**
   * Tests AS400Bin8 compatibility in a RecordFormat.
  **/
  public void Var006()
  {
    setVariation(6);
    try
    {
      CommandCall cc = new CommandCall(pwrSys_);
      cc.run("DLTF "+testLib_+"/TESTBIN8");

      RecordFormat rf = new RecordFormat("MYRF");
      rf.addFieldDescription(new BinaryFieldDescription(new AS400Bin8(), "fld1"));
      rf.addFieldDescription(new CharacterFieldDescription(new AS400Text(10), "fld2"));

      SequentialFile sf = new SequentialFile(systemObject_, "/QSYS.LIB/"+testLib_+".LIB/TESTBIN8.FILE/TESTBIN8.MBR");
      sf.create(rf, "Testing AS400Bin8");

      Record rec = rf.getNewRecord();
      rec.setField(0, new Long(1234567890));
      rec.setField(1, "THISISTEXT");

      sf.open(AS400File.READ_WRITE, 0, AS400File.COMMIT_LOCK_LEVEL_NONE);

      sf.write(rec);

      sf.close();

      sf = new SequentialFile(systemObject_, "/QSYS.LIB/"+testLib_+".LIB/TESTBIN8.FILE");
      sf.setRecordFormat();
      RecordFormat rf2 = sf.getRecordFormat();
      if (!(rf2.getFieldDescription(0).getDataType() instanceof AS400Bin8))
      {
        failed("Wrong data type: "+rf2.getFieldDescription(0).getDataType().getClass());
        return;
      }
      
      Record[] recs = sf.readAll();
      Long val0 = (Long)recs[0].getField(0);
      String val1 = (String)recs[0].getField(1);

      if (val0.longValue() == 1234567890)
      {
        if (!val1.equals("THISISTEXT"))
        {
          failed("Strings not equal: "+val1+" != THISISTEXT");
          return;
        }
      }
      else
      {
        failed("Longs not equal: "+val0+" != 1234567890");
        return;
      }

      // Now test the record format source.
      try
      {
        File f = new File("MYRFFormat.java");
        if (f.exists()) f.delete();
      }
      catch(Exception e) {}

      AS400FileRecordDescription frd = new AS400FileRecordDescription(systemObject_, "/QSYS.LIB/"+testLib_+".LIB/TESTBIN8.FILE");
      frd.createRecordFormatSource(null, null);

      BufferedReader br = new BufferedReader(new FileReader("MYRFFormat.java"));

      String line = br.readLine();
      int occurrences = 0;
      while(line != null)
      {
        if (line.trim().indexOf("AS400Bin8") > 0) ++occurrences;
        line = br.readLine();
      }
      br.close();

      if (occurrences != 2)
      {
        failed("Generated source contains "+occurrences+" occurrences of AS400Bin8.");
      }
      else
      {
        succeeded();
      }

    }
    catch(Exception e)
    {
      failed(e, "Unexpected exception.");
    }
    finally
    {
      try
      {
        CommandCall cc = new CommandCall(pwrSys_);
        cc.run("DLTF "+testLib_+"/TESTBIN8");
      }
      catch(Exception e) {}
      try
      {
        File f = new File("MYRFFormat.java");
        f.delete();
      }
      catch(Exception e) {}
    }
  }

}
