///////////////////////////////////////////////////////////////////////////////
//
// JTOpen (IBM Toolbox for Java - OSS version)
//
// Filename:  CmdTest.java
//
// The source code contained herein is licensed under the IBM Public License
// Version 1.0, which has been approved by the Open Source Initiative.
// Copyright (C) 1997-2023 International Business Machines Corporation and
// others.  All rights reserved.
//
///////////////////////////////////////////////////////////////////////////////

package test;

import java.io.IOException;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.Job;
import com.ibm.as400.access.SystemProperties;

import test.Cmd.CmdBeans;
import test.Cmd.CmdConstructor;
import test.Cmd.CmdOnThreadTestcase;
import test.Cmd.CmdRunTestcase;

/**
 The CmdTest class tests the CommandCall class.
 **/
public class CmdTest extends TestDriver
{
    /**
   * 
   */
public static final long serialVersionUID = 1L;
    public static boolean assumeCommandsThreadSafe_ = false;

    // Determine if testcase is running on an iSeries server.
    static {
      try {

        onAS400_ = JTOpenTestEnvironment.isOS400;
        if (onAS400_) {
          String s = SystemProperties.getProperty(SystemProperties.COMMANDCALL_THREADSAFE);
          if (s != null && s.equals("true")) {
            assumeCommandsThreadSafe_ = true;
          }
        }

      } catch (SecurityException e) {
      }
    }

    /**
     Run the test as an application.
     @param  args  The command line arguments.
     **/
    public static void main(String args[])
    {
	try
	{
            TestDriver.runApplication(new CmdTest(args));
	}
	catch (Exception e)
	{
	    System.out.println("Program terminated abnormally.");
	    e.printStackTrace();
	}

            System.exit(0);
    }

    /**
     Constructs an object for applets.
     @exception  Exception  Initialization errors may cause an exception.
     **/
    public CmdTest() throws Exception
    {
        super();
    }

    /**
     Constructs an object for testing applications.
     @param  args  The command line arguments.
     @exception  Exception  Incorrect arguments will cause an exception
     **/
    public CmdTest(String[] args) throws Exception
    {
        super(args);
    }

    /**
     Creates the testcases.
     **/
    public void createTestcases()
    {
    	
    	if(TestDriverStatic.pause_)
    	{ 
       	   try 
       	    {						
       		    systemObject_.connectService(AS400.COMMAND);
			}
       	    catch (AS400SecurityException e) 
       	    {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
       	    catch (IOException e) 
       	    {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 	 	   
    		try
    		{
    			Job[] jobs = systemObject_.getJobs(AS400.COMMAND);
    			System.out.println("Host Server job(s): ");

    			for(int i = 0 ; i< jobs.length; i++)
    			{   	    	
    				System.out.println(jobs[i]);
    			}
       	    
    		}
    		catch(Exception exc){}
       	    
       	    try 
       	    {
       	    	System.out.println ("Toolbox is paused. Press ENTER to continue.");
       	    	System.in.read ();
       	    } 
       	    catch (Exception exc) {};   	   
    	} 
       	
        Testcase[] testcases =
        {
            new CmdConstructor(),
            new CmdRunTestcase(),
            new CmdOnThreadTestcase(),
            new CmdBeans()
        };

        for (int i = 0; i < testcases.length; ++i)
        {
            testcases[i].setTestcaseParameters(systemObject_, pwrSys_, systemName_, userId_, password_, proxy_, mustUseSockets_, isNative_, isLocal_, onAS400_, namesAndVars_, runMode_, fileOutputStream_ );
            addTestcase(testcases[i]);
        }
    }
}
