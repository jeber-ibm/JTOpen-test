///////////////////////////////////////////////////////////////////////////////
//
// JTOpen (IBM Toolbox for Java - OSS version)
//
// Filename:  SetupDriver.java
//
// The source code contained herein is licensed under the IBM Public License
// Version 1.0, which has been approved by the Open Source Initiative.
// Copyright (C) 1997-2023 International Business Machines Corporation and
// others.  All rights reserved.
//
///////////////////////////////////////////////////////////////////////////////

package test;

import java.util.Vector;

import test.Setup.SetupJobLog;
import test.Setup.SetupNLS;
import test.Setup.SetupNetworkPrint;
import test.Setup.SetupPgmCall;
import test.Setup.SetupRFML;
import test.Setup.SetupRLA;

import java.util.Enumeration;

/**
Test driver for the NLS component.
See TestDriver for calling syntax.
**/
public class SetupDriver extends TestDriver
{

  // 10/27/2010 -- Removed rs6000_ stuff.  We expect save files to be loaded onto the
  //               client system with the rest of the tests. 
  // 
  // String rs6000_ = null; // rs6000 with cmvc save files
  // String rsUserId_ = null; // userid with access to cmvc
  // String rsPassword_ = null; // password associated with AFSid_

  static final int MAXTOKENS = 3; // maximum tokens interpreted in "-misc" parameter

/**
Main for running standalone application tests.
**/
  public static void main(String args[])
  {
    try
    {
      SetupDriver setdrv = new SetupDriver(args);
      setdrv.init();
      setdrv.start();
      setdrv.stop();
      setdrv.destroy();
    }
    catch (Exception e)
    {
      System.out.println("Program terminated abnormally.");
      e.printStackTrace();
    }
    System.exit(0);
  }

/**
This ctor used for applets.
@exception Exception Initialization errors may cause an exception.
**/
  public SetupDriver()
       throws Exception
  {
    super();
  }

/**
This ctor used for applications.
@param args the array of command line arguments
@exception Exception Incorrect arguments will cause an exception
**/
  public SetupDriver(String[] args)
       throws Exception
  {
    super(args);
  }

/**
Creates Testcase objects for all the testcases in this component.
**/
  public void createTestcases()
  {
    // Instantiate all testcases to be run.
    boolean allTestcases = (namesAndVars_.size() == 0);

    // Interpret the -misc parameter.
    // No longer needed 
    // parseMiscParm();

//    if (allTestcases || namesAndVars_.containsKey("SetupJDBC"))
//    {
//      SetupJDBC tc =
//        new SetupJDBC(systemObject_,
//                    (Vector) namesAndVars_.get("SetupJDBC"), runMode_,
//                     fileOutputStream_, password_,
//                     pwrSys_, pwrPwd_, rs6000_, rsUserId_, rsPassword_);
//      testcases_.addElement(tc);
//      namesAndVars_.remove("SetupJDBC");
//    }

    if (allTestcases || namesAndVars_.containsKey("SetupNLS"))
    {
      SetupNLS tc =
        new SetupNLS(systemObject_,
                     (Vector) namesAndVars_.get("SetupNLS"), runMode_,
                     fileOutputStream_, password_,
                     pwrSys_, pwrSysPassword_);
      testcases_.addElement(tc);
      namesAndVars_.remove("SetupNLS");
    }

    if (allTestcases || namesAndVars_.containsKey("SetupJobLog"))
    {
      SetupJobLog tc =
        new SetupJobLog(systemObject_,
                     (Vector) namesAndVars_.get("SetupJobLog"), runMode_,
                     fileOutputStream_, password_,
                     pwrSys_, pwrSysPassword_);
      testcases_.addElement(tc);
      namesAndVars_.remove("SetupJobLog");
    }

    if (allTestcases || namesAndVars_.containsKey("SetupRLA"))
    {
      SetupRLA tc =
        new SetupRLA(systemObject_,
                     (Vector) namesAndVars_.get("SetupRLA"), runMode_,
                     fileOutputStream_, password_,
                     pwrSys_, pwrSysPassword_);
      testcases_.addElement(tc);
      namesAndVars_.remove("SetupRLA");
    }

    if (allTestcases || namesAndVars_.containsKey("SetupNetworkPrint"))
    {
      SetupNetworkPrint tc =
        new SetupNetworkPrint(systemObject_,
                     (Vector) namesAndVars_.get("SetupNetworkPrint"), runMode_,
                     fileOutputStream_, password_,
                     pwrSys_, pwrSysPassword_);
      testcases_.addElement(tc);
      namesAndVars_.remove("SetupNetworkPrint");
    }

    if (allTestcases || namesAndVars_.containsKey("SetupPgmCall"))
    {
      SetupPgmCall tc =
        new SetupPgmCall(systemObject_,
                     (Vector) namesAndVars_.get("SetupPgmCall"), runMode_,
                     fileOutputStream_, password_,
                     pwrSys_, pwrSysPassword_);
      testcases_.addElement(tc);
      namesAndVars_.remove("SetupPgmCall");
    }

    if (allTestcases || namesAndVars_.containsKey("SetupRFML"))
    {
      SetupRFML tc =
        new SetupRFML(systemObject_,
                     (Vector) namesAndVars_.get("SetupRFML"), runMode_,
                     fileOutputStream_, password_,
                     pwrSys_, pwrSysPassword_);
      testcases_.addElement(tc);
      namesAndVars_.remove("SetupRFML");
    }

    // Put out error message for each invalid testcase name.
    for (Enumeration e = namesAndVars_.keys(); e.hasMoreElements();)
    {
      System.out.println("Testcase " + e.nextElement() + " not found.");
    }
  }


}



