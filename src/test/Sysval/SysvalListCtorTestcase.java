///////////////////////////////////////////////////////////////////////////////
//
// JTOpen (IBM Toolbox for Java - OSS version)
//
// Filename:  SysvalListCtorTestcase.java
//
// The source code contained herein is licensed under the IBM Public License
// Version 1.0, which has been approved by the Open Source Initiative.
// Copyright (C) 1997-2023 International Business Machines Corporation and
// others.  All rights reserved.
//
///////////////////////////////////////////////////////////////////////////////

package test.Sysval;


import com.ibm.as400.access.SystemValueList;

import test.Testcase;

/**
 * Testcase SysvalListCtorTestcase.
 *
 * Test variations for the methods:
 * <ul>
 * <li>SystemValueList()
 * <li>SystemValue(AS400)
 * </ul>
 **/
public class SysvalListCtorTestcase extends Testcase
{
  public static void main(String args[]) throws Exception {
    String[] newArgs = new String[args.length+2];
     newArgs[0] = "-tc";
     newArgs[1] = "SysvalListCtorTestcase";
     for (int i = 0; i < args.length; i++) {
       newArgs[2+i]=args[i];
     }
     test.SysvalTestDriver.main(newArgs); 
   }

    /**
     * Runs the variations requested.
     **/
    public void run()
    {
	boolean allVariations = (variationsToRun_.size() == 0);

	if ((allVariations || variationsToRun_.contains("1")) && runMode_ != ATTENDED)
	{
	    setVariation(1);
	    Var001();
	}

	if ((allVariations || variationsToRun_.contains("2")) && runMode_ != ATTENDED)
	{
	    setVariation(2);
	    Var002();
	}

	if ((allVariations || variationsToRun_.contains("3")) && runMode_ != ATTENDED)
	{
	    setVariation(3);
	    Var003();
	}
    }


    /**
     * Construct a system value list passing a null for the system.
     * An NullPointerException should be thrown.
     **/
    public void Var001()
    {
      try
      {
        SystemValueList sv = new SystemValueList(null);
        failed("No exception."+sv);
      }
      catch(Exception e)
      {
        if (exceptionIs(e, "NullPointerException", "system"))
          succeeded();
        else
          failed(e, "Wrong exception info.");
      }
    }


    /**
     * Successful construction of a system value list using ctor with parms.
     **/
    public void Var002()
    {
      try
      {
        SystemValueList sv = new SystemValueList(systemObject_);
        succeeded("sv="+sv);
      }
      catch(Exception e)
      {
        failed(e, "Unexpected exception.");
      }
    }


    /**
     * Successful construction of a system value list using default ctor.
     **/
    public void Var003()
    {
      try
      {
        SystemValueList sv = new SystemValueList();
        succeeded("sv="+sv);
      }
      catch(Exception e)
      {
        failed(e, "Unexpected exception.");
      }
    }
}
