///////////////////////////////////////////////////////////////////////////////
//
// JTOpen (IBM Toolbox for Java - OSS version)
//
// Filename:  RIFSFileBasicTestcase.java
//
// The source code contained herein is licensed under the IBM Public License
// Version 1.0, which has been approved by the Open Source Initiative.
// Copyright (C) 1997-2023 International Business Machines Corporation and
// others.  All rights reserved.
//
///////////////////////////////////////////////////////////////////////////////
package test.RIFS;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.resource.RIFSFile;

import test.Testcase;
import test.UserTest;
import test.misc.VIFSSandbox;

import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Testcase RIFSFileBasicTestcase. This tests the following methods of the
 * RIFSFile class:
 * 
 * <ul>
 * <li>constructors
 * <li>serialization
 * <li>getPath()
 * <li>getSystem()
 * <li>setPath()
 * <li>setSystem()
 * </ul>
 **/
@SuppressWarnings("deprecation")
public class RIFSFileBasicTestcase extends Testcase {
  public static void main(String args[]) throws Exception {
    String[] newArgs = new String[args.length + 2];
    newArgs[0] = "-tc";
    newArgs[1] = "RIFSFileBasicTestcase";
    for (int i = 0; i < args.length; i++) {
      newArgs[2 + i] = args[i];
    }
    test.RIFSTest.main(newArgs);
  }

  // Constants.

  // Private data.
  private VIFSSandbox sandbox_;

  /**
   * Constructor.
   **/
  public RIFSFileBasicTestcase(AS400 systemObject, Hashtable<String, Vector<String>> namesAndVars, int runMode,
      FileOutputStream fileOutputStream,

      String password, AS400 pwrSys) {
    super(systemObject, "RIFSFileBasicTestcase", namesAndVars, runMode, fileOutputStream, password);
    pwrSys_ = pwrSys;

    if (pwrSys == null)
      throw new IllegalStateException("ERROR: Please specify a power system.");
  }

  /**
   * Performs setup needed before running variations.
   * 
   * @exception Exception If an exception occurs.
   **/
  protected void setup() throws Exception {
    sandbox_ = new VIFSSandbox(systemObject_, "RIFSFBT");
  }

  /**
   * Performs cleanup needed after running variations.
   * 
   * @exception Exception If an exception occurs.
   **/
  protected void cleanup() throws Exception {
    sandbox_.cleanup();
  }

  /**
   * constructor() with 0 parms - Should work.
   **/
  public void Var001() {
    try {
      RIFSFile u = new RIFSFile();
      assertCondition((u.getSystem() == null) && (u.getPath() == null));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * constructor() with 2 parms - Pass null for system.
   **/
  public void Var002() {
    try {
      RIFSFile u = new RIFSFile(null, "/QIBM");
      failed("Didn't throw exception for " + u);
    } catch (Exception e) {
      assertExceptionIsInstanceOf(e, "java.lang.NullPointerException");
    }
  }

  /**
   * constructor() with 2 parms - Pass null for name.
   **/
  public void Var003() {
    try {
      RIFSFile u = new RIFSFile(systemObject_, null);
      failed("Didn't throw exception for " + u);
    } catch (Exception e) {
      assertExceptionIsInstanceOf(e, "java.lang.NullPointerException");
    }
  }

  /**
   * constructor() with 2 parms - Pass invalid values. This should work, because
   * the constructor does not check the validity.
   **/
  public void Var004() {
    try {
      AS400 bogus = new AS400("bogus", "bogus", "bogus");
      RIFSFile u = new RIFSFile(bogus, "BadRIFSFile");
      assertCondition((u.getSystem() == bogus) && (u.getPath().equals("BadRIFSFile")));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * constructor() with 2 parms - Pass valid values. Verify that the system and
   * name are used.
   **/
  public void Var005() {
    try {
      IFSFile f = sandbox_.createFile("Clif");
      RIFSFile u = new RIFSFile(systemObject_, f.getPath());
      String name = (String) u.getAttributeValue(RIFSFile.NAME);
      assertCondition((u.getSystem() == systemObject_) && (u.getPath().equals(f.getPath())) && (name.equals("Clif")));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * serialization - Verify that the properties are set and verify that its
   * usable.
   **/
  public void Var006() {
    try {
      IFSFile f = sandbox_.createFile("Robb");
      RIFSFile u = new RIFSFile(systemObject_, f.getPath());
      String name = (String) u.getAttributeValue(RIFSFile.NAME);
      RIFSFile u2 = (RIFSFile) UserTest.serialize(u);
      String name2 = (String) u2.getAttributeValue(RIFSFile.NAME);
      assertCondition((u2.getSystem().getSystemName().equals(systemObject_.getSystemName()))
          && (u2.getSystem().getUserId().equals(systemObject_.getUserId())) && (u2.getPath().equals(f.getPath()))
          && (name.equals(name2)));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * getPath() - Should return "" when the name has not been set.
   **/
  public void Var007() {
    try {
      RIFSFile u = new RIFSFile();
      assertCondition(u.getPath() == null);
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * getPath() - Should return the name when the name has been set.
   **/
  public void Var008() {
    try {
      RIFSFile u = new RIFSFile(systemObject_, "/maine/newHampshire");
      assertCondition(u.getPath().equals("/maine/newHampshire"));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * getSystem() - Should return null when the system has not been set.
   **/
  public void Var009() {
    try {
      RIFSFile u = new RIFSFile();
      assertCondition(u.getSystem() == null);
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * getSystem() - Should return the system when the system has been set.
   **/
  public void Var010() {
    try {
      RIFSFile u = new RIFSFile(systemObject_, "newhampshire");
      assertCondition(u.getSystem().equals(systemObject_));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * setPath() - Should throw an exception if null is passed.
   **/
  public void Var011() {
    try {
      RIFSFile u = new RIFSFile(systemObject_, "massachusetts");
      u.setPath(null);
      failed("Didn't throw exception");
    } catch (Exception e) {
      assertExceptionIsInstanceOf(e, "java.lang.NullPointerException");
    }
  }

  /**
   * setPath() - Set to an invalid name. Should be reflected by getPath(), since
   * the validity is not checked here.
   **/
  public void Var012() {
    try {
      RIFSFile u = new RIFSFile(systemObject_, "/connecticut");
      u.setPath("/SDFFF$$($#($%($%");
      assertCondition(u.getPath().equals("/SDFFF$$($#($%($%"));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * setPath() - Set to a valid name. Should be reflected by getPath() and verify
   * that it is used.
   **/
  public void Var013() {
    try {
      IFSFile f = sandbox_.createFile("Robb");
      RIFSFile u = new RIFSFile(systemObject_, "/vermont");
      u.setPath(f.getPath());
      String name = (String) u.getAttributeValue(RIFSFile.NAME);
      assertCondition((u.getPath().equals(f.getPath())) && (name.equals("Robb")));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * setPath() - Set to a valid name after the RIFSFile has made a connection.
   **/
  public void Var014() {
    try {
      IFSFile f = sandbox_.createFile("Robb");
      RIFSFile u = new RIFSFile(systemObject_, f.getPath());
      String name = (String) u.getAttributeValue(RIFSFile.NAME);
      u.setPath("alabama");
      failed("Didn't throw exception name=" + name);
    } catch (Exception e) {
      assertExceptionIsInstanceOf(e, "java.lang.IllegalStateException");
    }
  }

  /**
   * setPath() - Should fire a property change event.
   **/
  public void Var015() {
    try {
      RIFSFile u = new RIFSFile(systemObject_, "/massachusetts");
      UserTest.PropertyChangeListener_ pcl = new UserTest.PropertyChangeListener_();
      u.addPropertyChangeListener(pcl);
      u.setPath("/newyork");
      assertCondition((pcl.eventCount_ == 1) && (pcl.event_.getPropertyName().equals("path"))
          && (pcl.event_.getOldValue().equals("/massachusetts")) && (pcl.event_.getNewValue().equals("/newyork")));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * setSystem() - Should throw an exception if null is passed.
   **/
  public void Var016() {
    try {
      RIFSFile u = new RIFSFile(systemObject_, "virginia");
      u.setSystem(null);
      failed("Didn't throw exception");
    } catch (Exception e) {
      assertExceptionIsInstanceOf(e, "java.lang.NullPointerException");
    }
  }

  /**
   * setSystem() - Set to an invalid system. Should be reflected by getSystem(),
   * since the validity is not checked here.
   **/
  public void Var017() {
    try {
      AS400 bogus = new AS400("bogus", "bogus", "bogus");
      RIFSFile u = new RIFSFile(systemObject_, "scarolina");
      u.setSystem(bogus);
      assertCondition(
          (u.getSystem().getSystemName().equals("bogus")) && (u.getSystem().getUserId().equalsIgnoreCase("bogus")));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * setSystem() - Set to a valid name. Should be reflected by getSystem() and
   * verify that it is used.
   **/
  public void Var018() {
    try {
      AS400 bogus = new AS400("bogus", "bogus", "bogus");
      IFSFile f = sandbox_.createFile("Robb");
      RIFSFile u = new RIFSFile(bogus, f.getPath());
      u.setSystem(systemObject_);
      String name = (String) u.getAttributeValue(RIFSFile.NAME);
      assertCondition((u.getSystem().getSystemName().equals(systemObject_.getSystemName()))
          && (u.getSystem().getUserId().equals(systemObject_.getUserId())) && (name.equals("Robb")));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * setSystem() - Set to a valid name after the RIFSFile object has made a
   * connection.
   **/
  public void Var019() {
    try {
      IFSFile f = sandbox_.createFile("Robb");
      RIFSFile u = new RIFSFile(systemObject_, f.getPath());
      String name = (String) u.getAttributeValue(RIFSFile.NAME);
      u.setSystem(systemObject_);
      failed("Didn't throw exception name=" + name);
    } catch (Exception e) {
      assertExceptionIsInstanceOf(e, "java.lang.IllegalStateException");
    }
  }

  /**
   * setSystem() - Should fire a property change event.
   **/
  public void Var020() {
    try {
      AS400 temp1 = new AS400("temp1", "temp1", "temp1".toCharArray());
      AS400 temp2 = new AS400("temp2", "temp2", "temp2".toCharArray());
      RIFSFile u = new RIFSFile(temp1, "ncarolina");
      UserTest.PropertyChangeListener_ pcl = new UserTest.PropertyChangeListener_();
      u.addPropertyChangeListener(pcl);
      u.setSystem(temp2);
      assertCondition((pcl.eventCount_ == 1) && (pcl.event_.getPropertyName().equals("system"))
          && (pcl.event_.getOldValue().equals(temp1)) && (pcl.event_.getNewValue().equals(temp2)));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * setSystem() - Should fire a vetoable change event.
   **/
  public void Var021() {
    try {
      AS400 temp1 = new AS400("temp1", "temp1", "temp1");
      AS400 temp2 = new AS400("temp2", "temp2", "temp2");
      RIFSFile u = new RIFSFile(temp1, "georgia");
      UserTest.VetoableChangeListener_ vcl = new UserTest.VetoableChangeListener_();
      u.addVetoableChangeListener(vcl);
      u.setSystem(temp2);
      assertCondition((vcl.eventCount_ == 1) && (vcl.event_.getPropertyName().equals("system"))
          && (vcl.event_.getOldValue().equals(temp1)) && (vcl.event_.getNewValue().equals(temp2)));
    } catch (Exception e) {
      failed(e, "Unexpected Exception");
    }
  }

  /**
   * setSystem() - Should throw a PropertyVetoException if the change is vetoed.
   **/
  public void Var022() {
    try {
      AS400 temp1 = new AS400("temp1", "temp1", "temp1");
      AS400 temp2 = new AS400("temp2", "temp2", "temp2");
      RIFSFile u = new RIFSFile(temp1, "florida");
      UserTest.VetoableChangeListener_ vcl = new UserTest.VetoableChangeListener_(true);
      u.addVetoableChangeListener(vcl);
      u.setSystem(temp2);
      failed("Didn't throw exception");
    } catch (Exception e) {
      assertExceptionIsInstanceOf(e, "java.beans.PropertyVetoException");
    }
  }

}
