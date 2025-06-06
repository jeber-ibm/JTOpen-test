///////////////////////////////////////////////////////////////////////////////
//
// JTOpen (IBM Toolbox for Java - OSS version)
//
// Filename:  JDSDSProperties.java
//
// The source code contained herein is licensed under the IBM Public License
// Version 1.0, which has been approved by the Open Source Initiative.
// Copyright (C) 1997-2023 International Business Machines Corporation and
// others.  All rights reserved.
//
///////////////////////////////////////////////////////////////////////////////


package test.JD.SDS;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400JDBCDataSource;
import com.ibm.as400.access.ExtendedIllegalArgumentException;

import test.JDReflectionUtil;
import test.JDTestDriver;
import test.JDTestcase;
import test.PasswordVault;

import java.io.FileOutputStream;
import javax.naming.*;
import java.util.Hashtable; import java.util.Vector;
import java.sql.*;
import javax.sql.DataSource;
import java.io.PrintWriter;



/**
Testcase JDSDSProperties.  This tests the following methods
of the JDBC Driver class:

<ul>
<li>setDataSourceName()
<li>getDataSourceName()
<li>setDatabaseName()
<li>getDatabaseName()
<li>setDescription()
<li>getDescription()
<li>getLogWriter()
<li>setLogWriter()
<li>getLoginTimeout()
<li>setLoginTimeout()
<li>getAccess()
<li>setAccess()
<li>getDateFormat()
<li>setDateFormat()
<li>getDateSeparator()
<li>setDateSeparator()
<li>getDecimalSeparator()
<li>setDecimalSeparator()
<li>getLibraries()
<li>setLibraries()
<li>getNamingOption()
<li>setNamingOption()
<li>getPassword()
<li>setPassword()
<li>getTimeFormat()
<li>setTimeFormat()
<li>getTimeSeparator()
<li>setTimeSeparator()
<li>getTraceActive()
<li>setTraceActive()
<li>getTransactionIsolationLevel()
<li>setTransactionIsolationLevel()
<li>getTranslateBinary()
<li>setTranslateBinary()
<li>getUser()
<li>setUser()
<li>getUseBlocking()
<li>setUseBlocking()
<li>getBlockSize()
<li>setBlockSize()
<li>getAutoCommit()
<li>setAutoCommit()
<li>getMaximumBlockedInputRows(); 
<li>setMaximumBlockedInputRows(); 
<li>getMaximumPrecision()
<li>setMaximumPrecision()
<li>getMaximumScale()
<li>setMaximumScale()
<li>getMinimumDivideScale()
<li>setMinimumDivideScale()
<li>getTranslateHex()
<li>setTranslateHex()
<li>getCursorSensitivity()
<li>setCursorSensitivity()
<li>getReturnExtendedMetaData()
<li>setReturnExtendedMetaData()

</ul>
**/
public class JDSDSProperties
extends JDTestcase {
  public static void main(String args[]) throws Exception {
    String[] newArgs = new String[args.length+2];
     newArgs[0] = "-tc";
     newArgs[1] = "JDSDSProperties";
     for (int i = 0; i < args.length; i++) {
       newArgs[2+i]=args[i];
     }
     test.JDSDSTest.main(newArgs); 
   }


   // Private data.
   private DataSource dataSource_;
   private AS400JDBCDataSource dataSourceTB_;
   private Context ctx_;
   private Hashtable<String, String> env;

   protected static final String bindName_ = "JDSDSPropertiesTest";

/**
Constructor.
**/
   public JDSDSProperties (AS400 systemObject,
                          Hashtable<String,Vector<String>> namesAndVars,
                          int runMode,
                          FileOutputStream fileOutputStream,
                          
                          String password)
   {
      super (systemObject, "JDSDSProperties",
             namesAndVars, runMode, fileOutputStream,
             password);
   }



/**
Performs setup needed before running variations.

@exception Exception If an exception occurs.
**/
   protected void setup ()
   throws Exception
   {
      if (isJdbc20StdExt ()) {
         dataSource_ = (DataSource) JDReflectionUtil.createObject("com.ibm.db2.jdbc.app.DataSource");
         dataSourceTB_ = new AS400JDBCDataSource();
         env = new Hashtable<String, String>();
         env.put(Context.INITIAL_CONTEXT_FACTORY, 
                 "com.sun.jndi.fscontext.RefFSContextFactory");
         env.put(Context.PROVIDER_URL, "file:/tmp/");

         JDReflectionUtil.callMethod_V(dataSource_,"setDataSourceName","local connection");
         JDReflectionUtil.callMethod_V(dataSource_,"setDescription","This is something cool");
         dataSourceTB_.setDataSourceName("local connection toolbox");
         dataSourceTB_.setDescription("This is something cool toolbox");
         ctx_ = new InitialContext(env);

      }
   }

   // TO Test:
   // All the items we can get and set.
   // test them with defaults, with valid values, and with invalid values.
   // This will be the largest group of tests we have for data sources.

/* The proto for variations
   - set the property 
   - bind the context to the data source
   - get the property
   - unbind the context from the data source
*/


/**
getDataSourceName() - call getDataSourceName to check that the value set before is returned.
**/
   public void Var001 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDataSourceName").equals("local connection"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setDataSourceName(),getDataSourceName() - set the dataSourceName property
and then verify it was set.
**/
   public void Var002 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            String dsn = "MyDataSource";
            JDReflectionUtil.callMethod_V(dataSource_,"setDataSourceName",dsn);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDataSourceName").equals(dsn));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
getDatabaseName() - call getDatabaseName to check that the value set before is returned.
**/
   public void Var003 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDatabaseName").equals("*LOCAL"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setDatabaseName(),getDatabaseName() - set the databaseName property
and then verify it was set.
**/
   public void Var004 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            String dbn = system_;
            JDReflectionUtil.callMethod_V(dataSource_,"setDatabaseName",dbn);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDatabaseName").equals(dbn));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
getDescription() - call getDescription to check that the value set before is returned.
**/
   public void Var005 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDescription").equals("This is something cool"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setDescription(),getDescription() - set the Description property
and then verify it was set.
**/
   public void Var006 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            String desc = "Test data source for JDSDSProperties test";
            JDReflectionUtil.callMethod_V(dataSource_,"setDescription",desc);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDescription").equals(desc));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
getLogWriter() - call getLogWriter to check that the default value is returned.
**/
   public void Var007 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(null == JDReflectionUtil.callMethod_S(ds,"getLogWriter"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setLogWriter(),getLogWriter() - set the LogWriter property
and then verify it was set.
**/
   public void Var008 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            PrintWriter pw = new PrintWriter(System.out);
            JDReflectionUtil.callMethod_V(dataSource_,"setLogWriter",pw);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            //assertCondition(JDReflectionUtil.callMethod_S(ds,"getLogWriter().equals(pw));
            assertCondition(null == JDReflectionUtil.callMethod_S(ds,"getLogWriter"));  // may be a bug
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
getLoginTimeout() - call getLoginTimeout to check that the default value is returned.
**/
   public void Var009 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(0 == JDReflectionUtil.callMethod_I(ds,"getLoginTimeout"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setLoginTimeout(),getLoginTimeout() - set the LoginTimeout property
and then verify it was set.
**/
   public void Var010 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setLoginTimeout",10);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(10 == JDReflectionUtil.callMethod_I(ds,"getLoginTimeout"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
getAccess() - call getAccess to check that the default value is returned.
**/
   public void Var011 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getAccess").equals("all"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setAccess(),getAccess() - set the Access property
and then verify it was set.
**/
   public void Var012 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setAccess","read only");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getAccess").equals("read only"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
setAccess(),getAccess() - set an invalid Access property. Should throw exception.
**/
   public void Var013 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            String invacc = "invalidaccessproperty";
            JDReflectionUtil.callMethod_V(dataSource_,"setAccess",invacc);
            failed("Did not throw exception");
         } catch (Exception e) {
            assertExceptionIsInstanceOf (e, "java.sql.SQLException");
         }
      }
   }


/**
getDateFormat() - call getDateFormat to check that the default value is returned.
**/
   public void Var014 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDateFormat").equals(""));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setDateFormat(),getDateFormat() - set the DateFormat property
and then verify it was set.
**/
   public void Var015 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setDateFormat","usa");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDateFormat").equals("usa"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
setDateFormat(),getDateFormat() - set an invalid DateFormat property. Should throw exception.
**/
   public void Var016 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setDateFormat","invaliddateproperty");
            failed("Did not throw exception");
         } catch (Exception e) {
            assertExceptionIsInstanceOf (e, "java.sql.SQLException");
         }
      }
   }

/**
getDateSeparator() - call getDateSeparator to check that the default value is returned.
**/
   public void Var017 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDateSeparator").equals(""));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setDateSeparator(),getDateSeparator() - set the DateSeparator property
and then verify it was set.
**/
   public void Var018 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setDateSeparator","/");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDateSeparator").equals("/"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
setDateSeparator(),getDateSeparator() - set an invalid DateSeparator property. Should throw exception.
**/
   public void Var019 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setDateSeparator","invaliddateproperty");
            failed("Did not throw exception");
         } catch (Exception e) {
            assertExceptionIsInstanceOf (e, "java.sql.SQLException");
         }
      }
   }


/**
getDecimalSeparator() - call getDecimalSeparator to check that the default value is returned.
**/
   public void Var020 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDecimalSeparator").equals(""));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setDecimalSeparator(),getDecimalSeparator() - set the DecimalSeparator property
and then verify it was set.
**/
   public void Var021 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setDecimalSeparator",".");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getDecimalSeparator").equals("."));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
setDecimalSeparator(),getDecimalSeparator() - set an invalid DecimalSeparator property. Should throw exception.
**/
   public void Var022 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setDecimalSeparator","invalidDecimalproperty");
            failed("Did not throw exception");
         } catch (Exception e) {
            assertExceptionIsInstanceOf (e, "java.sql.SQLException");
         }
      }
   }


/**
getUser() - call getUSer to check that it is unset.
**/
   public void Var023 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getUser").equals(""));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setUser(),getUser() - set the User property
and then verify it was set.
**/
   public void Var024 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setUser","JoeUser");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getUser").equals("JoeUser"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
getPassword() - call getPassword to check that it is unset.
**/
   public void Var025 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getPassword").equals(""));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setPassword(),getPassword() - set the Password property
and then verify it was set.
**/
   public void Var026 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setPassword","JoePassword");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getPassword").equals("JoePassword"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
getTranslateBinary() - call getTranslateBinary to check the default.
**/
   public void Var027 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(false == JDReflectionUtil.callMethod_B(ds,"getTranslateBinary"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setTranslateBinary(),getTranslateBinary() - set the TranslateBinary property
and then verify it was set.
**/
   public void Var028 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setTranslateBinary",true);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_B(ds,"getTranslateBinary"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
getTimeFormat() - call getTimeFormat to check that the default value is returned.
**/
   public void Var029 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getTimeFormat").equals(""));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setTimeFormat(),getTimeFormat() - set the TimeFormat property
and then verify it was set.
**/
   public void Var030 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setTimeFormat","usa");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getTimeFormat").equals("usa"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
setTimeFormat(),getTimeFormat() - set an invalid TimeFormat property. Should throw exception.
**/
   public void Var031 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setTimeFormat","invalidTimeproperty");
            failed("Did not throw exception");
         } catch (Exception e) {
            assertExceptionIsInstanceOf (e, "java.sql.SQLException");
         }
      }
   }

/**
getTimeSeparator() - call getTimeSeparator to check that the default value is returned.
**/
   public void Var032 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getTimeSeparator").equals(""));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setTimeSeparator(),getTimeSeparator() - set the TimeSeparator property
and then verify it was set.
**/
   public void Var033 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setTimeSeparator",":");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getTimeSeparator").equals(":"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
setTimeSeparator(),getTimeSeparator() - set an invalid TimeSeparator property. Should throw exception.
**/
   public void Var034 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setTimeSeparator","invalidTimeproperty");
            failed("Did not throw exception");
         } catch (Exception e) {
            assertExceptionIsInstanceOf (e, "java.sql.SQLException");
         }
      }
   }


/**
getNamingOption() - call getNamingOption to check that it is default.
**/
   public void Var035 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getNamingOption").equals("sql"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setNamingOption(),getNamingOption() - set the naming property
and then verify it was set.
**/
   public void Var036 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setNamingOption","system");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getNamingOption").equals("system"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
setNamingOption() - set the Naming property to an invalid value.
Exception should be thrown
**/
   public void Var037 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setNamingOption","invalidNamingOption");
            failed("Did not throw exception");
         } catch (Exception e) {
            assertExceptionIsInstanceOf (e, "java.sql.SQLException");
         }
      }
   }

/* for the Libraries property (which actually specifies the default lib) 
variations, these depend on the value of the "naming" property.
In the default case where "naming" is "sql", the default library is
the same name as the user profile. If "naming" is set to "system",
the default lib will be the top lib in the users library list.
*/

/**
getLibraries() - call getLibraries to check that the default value is returned.
**/
   public void Var038 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setUser",userId_);
            JDReflectionUtil.callMethod_V(dataSource_,"setPassword",PasswordVault.decryptPasswordLeak(encryptedPassword_));
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getLibraries").equals(""));
            //assertCondition(JDReflectionUtil.callMethod_S(ds,"getLibraries().equals(userId_));   may be a bug
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
getLibraries() - set the "naming" to "system", call getLibraries
to check that the correct (default) value is returned.  The default
library when using system naming is that there isn't one.  This was 
changed for V4R4 from QGPL.
**/
   public void Var039 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setUser",userId_);
            JDReflectionUtil.callMethod_V(dataSource_,"setPassword",PasswordVault.decryptPasswordLeak(encryptedPassword_));
            JDReflectionUtil.callMethod_V(dataSource_,"setNamingOption","system");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getLibraries").equals(""));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
setLibraries(),getLibraries() - set the Libraries property
and then verify it was set.
**/
   public void Var040 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setLibraries","QUSRSYS");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getLibraries").equals("QUSRSYS"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
getTraceActive() - call getTraceActive to check the default.
**/
   // 02/15/2001 - v2kea447 - getTraceActive was changed to getTrace
   public void Var041 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(false == JDReflectionUtil.callMethod_B(ds,"getTrace"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setTraceActive(),getTraceActive() - set the TraceActive property
and then verify it was set.
**/
   // 02/15/2001 - v2kea447 - setTraceActive and getTraceActive have changed
   // to setTrace(), getTrace()
   public void Var042 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setTrace",true);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_B(ds,"getTrace"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
getTransactionIsolationLevel() - call getTransactionIsolationLevel
to check that the default value is returned.
**/
   public void Var043 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getTransactionIsolationLevel").equals("none"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setTransactionIsolationLevel(),getTransactionIsolationLevel() - set the
TransactionIsolationLevel property and then verify it was set.
**/
   public void Var044 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setTransactionIsolationLevel","read committed");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_S(ds,"getTransactionIsolationLevel").equals("read committed"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
setTransactionIsolationLevel(),getTransactionIsolationLevel() - set an invalid TransactionIsolationLevel property. Should throw exception.
**/
   public void Var045 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            String invtr = "invalidTransactionIsolationLevelproperty";
            JDReflectionUtil.callMethod_V(dataSource_,"setTransactionIsolationLevel",invtr);
            failed("Did not throw exception");
         } catch (Exception e) {
            assertExceptionIsInstanceOf (e, "java.sql.SQLException");
         }
      }
   }


/**
getUseBlocking() - call getUseBlocking to check the default.
**/
   public void Var046 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_B(ds,"getUseBlocking"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setUseBlocking(),getUseBlocking() - set the UseBlocking property
and then verify it was set.
**/
   public void Var047 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setUseBlocking",false);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_B(ds,"getUseBlocking") == false);
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
getBlockSize() - call getBlockSize to check the default.
**/
   public void Var048 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_I(ds,"getBlockSize") == 32);
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setBlockSize(),getBlockSize() - set the BlockSize property
and then verify it was set.
**/
   public void Var049 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setBlockSize",16);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_I(ds,"getBlockSize") == 16);
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }


/**
setBlockSize(),getBlockSize() - set the BlockSize property to
an invalid value. Exception should be thrown.
**/
   public void Var050 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setBlockSize",10);
            failed("Did not throw exception");
         } catch (Exception e) {
            assertExceptionIsInstanceOf (e, "java.sql.SQLException");
         }
      }
   }
//NERYKAL
/**
* getAutoCommit() - call getAutoCommit to check the default.
* it is important that TransactionIsolation level is set to its default value
* because AutoCommit is dependent of that value
**/
   public void Var051 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
	    JDReflectionUtil.callMethod_V(dataSource_,"setTransactionIsolationLevel","none");
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_B(ds,"getAutoCommit") == true, "New native property (toolbox will need) Auto Commit = "+
            JDReflectionUtil.callMethod_S(ds,"getAutoCommit"));
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception New native property (toolbox will need)");
         }
      }
   }

/**
setAutoCommit(),getAutoCommit() - set AutoCommit property
and then verify it was set.
**/
   public void Var052 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setAutoCommit",false);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_B(ds,"getAutoCommit") == false, "New native property (toolbox will need)");
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception New native property (toolbox will need)");
         }
      }
   }
/**
setAutoCommit(),getAutoCommit() - set AutoCommit property to an
invalid value and verify that it was set
**/
   public void Var053 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            JDReflectionUtil.callMethod_V(dataSource_,"setAutoCommit",false);
            ctx_.rebind(bindName_, dataSource_);
            DataSource ds = (DataSource)ctx_.lookup(bindName_);
            assertCondition(JDReflectionUtil.callMethod_B(ds,"getAutoCommit") == false, "New native property (toolbox will need)");
            ctx_.unbind(bindName_);
         } catch (Exception e) {
            failed (e, "Unexpected Exception New native property (toolbox will need)");
         }
      }
   }
/**
check the default value of maximum precision , should be 31
**/
   public void Var054 ()
   {
       if (getRelease() >=  JDTestDriver.RELEASE_V7R1M0) {	
	   if (checkJdbc20StdExt ()) {
	       try {
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   int value = JDReflectionUtil.callMethod_I(ds, "getMaximumPrecision"); 
		   assertCondition(value == 31, "New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }
/**
set the precision to the other valid number and see that it was changed
**/
   public void Var055 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setMaximumPrecision", 63);
	    //  JDReflectionUtil.callMethod_V(dataSource_,"setMaximumPrecision",63);
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   int value = JDReflectionUtil.callMethod_I(ds, "getMaximumPrecision"); 
		   assertCondition(value == 63, "New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }

/**
set the maximum precision to an invalid value
**/
   public void Var056 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setMaximumPrecision", 64);
	    // JDReflectionUtil.callMethod_V(dataSource_,"setMaximumPrecision",64);
		   failed("Did not throw exception New native property (toolbox will need)");
	       } catch (Exception e) {
		   assertExceptionIsInstanceOf (e, "java.sql.SQLException");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }
/**
check the default value of maximum scale , should be 31
**/
   public void Var057 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   int value = JDReflectionUtil.callMethod_I(ds, "getMaximumScale"); 
		   assertCondition(value == 31, "New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }
/**
set the maximum scale to lowest valid number and see that it was changed
**/
   public void Var058 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setMaximumScale", 0);
	    // JDReflectionUtil.callMethod_V(dataSource_,"setMaximumScale",0);
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   int value = JDReflectionUtil.callMethod_I(ds, "getMaximumScale"); 
		   assertCondition(value == 0,"New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }

/**
set the maximum scale to the highest valid number and see that it was changed
**/
   public void Var059 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setMaximumScale", 63);
	    // JDReflectionUtil.callMethod_V(dataSource_,"setMaximumScale",63);
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   int value = JDReflectionUtil.callMethod_I(ds, "getMaximumScale"); 
		   assertCondition(value == 63, "New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }

/**
set the maximum scale to an invalid high value
**/
   public void Var060 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setMaximumScale", 64);
	    // JDReflectionUtil.callMethod_V(dataSource_,"setMaximumScale",64);
		   failed("Did not throw exception New native property (toolbox will need)");
	       } catch (Exception e) {
		   System.out.println("new native property");
		   assertExceptionIsInstanceOf (e, "java.sql.SQLException");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }
/**
set the maximum scale to an invalid low value
**/
   public void Var061 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setMaximumScale", -1);
	    // JDReflectionUtil.callMethod_V(dataSource_,"setMaximumScale",-1);
		   failed("Did not throw exception New native property (toolbox will need)");
	       } catch (Exception e) {
		   System.out.println("NEW native property");
		   assertExceptionIsInstanceOf (e, "java.sql.SQLException");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }
/**
check the default value of minimum divide scale , should be 0
**/
   public void Var062 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   int value = JDReflectionUtil.callMethod_I(ds, "getMinimumDivideScale"); 
		   assertCondition(value == 0, "New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }
/**
check the lowest valid value for minimum divide scale
**/
   public void Var063 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setMinimumDivideScale", 0);
	    // JDReflectionUtil.callMethod_V(dataSource_,"setMinimumDivideScale",0);
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   int value = JDReflectionUtil.callMethod_I(ds, "getMinimumDivideScale"); 
		   assertCondition(value  == 0, "New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }
/**
check the highest valid value for minimum divide scale
**/
   public void Var064 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setMinimumDivideScale", 9);
	    // JDReflectionUtil.callMethod_V(dataSource_,"setMinimumDivideScale",9);
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   int value = JDReflectionUtil.callMethod_I(ds, "getMinimumDivideScale"); 
		   assertCondition(value == 9, "New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }
/**
error should be thrown when invalid number is entered for minimum divide scale
**/
   public void Var065 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setMinimumDivideScale", 10);
	    // JDReflectionUtil.callMethod_V(dataSource_,"setMinimumDivideScale",10);
		   failed("Did not throw exception New native property (toolbox will need)");
	       } catch (Exception e) {
		   System.out.println("NEW NATIVE PROPERTY");
		   assertExceptionIsInstanceOf (e, "java.sql.SQLException");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }
/** 
check the default value of translate hex , should be "character"
**/
   public void Var066 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   String property = JDReflectionUtil.callMethod_S(ds, "getTranslateHex"); 
		   assertCondition(property.equalsIgnoreCase("character"), "Expecting: \"character\" Received: \""+property+"\" -- New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }

/**
set translate hex to "character" and check that it was set correctly
**/
   public void Var067 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setTranslateHex", "character"); 
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   String property = JDReflectionUtil.callMethod_S(ds, "getTranslateHex"); 
		   assertCondition(property.equalsIgnoreCase("character"), "Expecting: \"character\" Received: \""+property+"\" -- New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }

 
/**
set translate hex to its only other valid value "binary" and check that it was changed
**/
   public void Var068 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   JDReflectionUtil.callMethod_V(dataSource_, "setTranslateHex", "binary"); 
		   ctx_.rebind(bindName_, dataSource_);
		   DataSource ds = (DataSource)ctx_.lookup(bindName_);
		   String property = JDReflectionUtil.callMethod_S(ds, "getTranslateHex"); 
		   assertCondition(property.equalsIgnoreCase("binary"), "Expecting: \"binary\" Received: \""+property+"\" -- New native property (toolbox will need)");
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception New native property (toolbox will need)");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }

/**
set translate hex to an invalid value and check that error is thrown 
**/
   public void Var069 ()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   String badHex = "invalidHexValue";
		   JDReflectionUtil.callMethod_V(dataSource_, "setTranslateHex", badHex); 
		   failed("Did not throw exception New native property (toolbox will need)");
	       } catch (SQLException e) {
		   // HY024 is received by the native driver when trying to set the
		   // translate hex property to something other than
		   // "character" or "binary"
		   if(e.getSQLState().equals("HY024") || e.getSQLState().equals("TOOLBOXSTATE"))
		       succeeded();
		   else
		       failed(e, "Unexpected Exception -- New native property (toolbox will need) added by native 01/16/04");
	       }
	       catch (Exception e){
		   failed(e, "Unexpected Exception -- New native property (toolbox will need) added by native 01/16/04");
	       }
	    }
       } else {
	   notApplicable("V5R3 variation"); 
       } 
   }

/**
getCursorSensitivity() -- call getCursorSensitivity() to check the default value, should be "asensitive"
**/
   public void Var070()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt()) {
	       try {

           if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
               ctx_.rebind(bindName_, dataSourceTB_);
               AS400JDBCDataSource dsTB = (AS400JDBCDataSource)ctx_.lookup(bindName_); //new AS400JDBCDataSource();   
               String property =  JDReflectionUtil.callMethod_S(dsTB, "getCursorSensitivity");
               assertCondition(property.equalsIgnoreCase("asensitive"), "Expecting: \"asensitive\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 01/16/04");
           }else{
               ctx_.rebind(bindName_, dataSource_);
               DataSource ds = (DataSource)ctx_.lookup(bindName_); 
               String property = JDReflectionUtil.callMethod_S(ds, "getCursorSensitivity");
               assertCondition(property.equalsIgnoreCase("asensitive"), "Expecting: \"asensitive\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 01/16/04");
           }

		   
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception -- New native property (toolbox will need) added by native 01/16/04");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       }
   }

/**
setCursorSensitivity(), getCursorSensitivity() -- call setCursorSensitivity() to set it to
"asensitive" and check that it was changed correctly
**/
   public void Var071()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
               if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                   JDReflectionUtil.callMethod_V(dataSourceTB_, "setCursorSensitivity", "asensitive");
                   ctx_.rebind(bindName_, dataSourceTB_);
                   AS400JDBCDataSource dsTB = (AS400JDBCDataSource)ctx_.lookup(bindName_); //new AS400JDBCDataSource();   
                   String property = JDReflectionUtil.callMethod_S(dsTB, "getCursorSensitivity");
                   assertCondition(property.equalsIgnoreCase("asensitive"), "Expecting: \"asensitive\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 01/16/04");
               }else{
                   JDReflectionUtil.callMethod_V(dataSource_, "setCursorSensitivity", "asensitive"); 
                   ctx_.rebind(bindName_, dataSource_);
                   DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                   String property = JDReflectionUtil.callMethod_S(ds, "getCursorSensitivity");
                   assertCondition(property.equalsIgnoreCase("asensitive"), "Expecting: \"asensitive\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 01/16/04");
               }
               
		       ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception -- New native property (toolbox will need) added by native 01/16/04");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }

/**
setCursorSensitivity(), getCursorSensitivity() -- call setCursorSensitivity() to set it to
"insensitive" and check that it was changed correctly
**/
   public void Var072()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
               if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                   JDReflectionUtil.callMethod_V(dataSourceTB_, "setCursorSensitivity", "insensitive");
                   ctx_.rebind(bindName_, dataSourceTB_);
                   AS400JDBCDataSource dsTB = (AS400JDBCDataSource)ctx_.lookup(bindName_);  
                   String property = JDReflectionUtil.callMethod_S(dsTB, "getCursorSensitivity");
                   assertCondition(property.equalsIgnoreCase("insensitive"), "Expecting: \"insensitive\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 01/16/04");
               }else{
                   JDReflectionUtil.callMethod_V(dataSource_, "setCursorSensitivity", "insensitive"); 
                   ctx_.rebind(bindName_, dataSource_);
                   DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                   String property = JDReflectionUtil.callMethod_S(ds, "getCursorSensitivity");
                   assertCondition(property.equalsIgnoreCase("insensitive"), "Expecting: \"insensitive\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 01/16/04");
               }
               
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception -- New native property (toolbox will need) added by native 01/16/04");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }

/**
setCursorSensitivity(), getCursorSensitivity() -- call setCursorSensitivity() to set it to
"sensitive" and check that it was changed correctly
**/
   public void Var073()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
               if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                   JDReflectionUtil.callMethod_V(dataSourceTB_, "setCursorSensitivity", "sensitive");
                   ctx_.rebind(bindName_, dataSourceTB_);
                   AS400JDBCDataSource dsTB = (AS400JDBCDataSource)ctx_.lookup(bindName_); //new AS400JDBCDataSource();   
                   String property = JDReflectionUtil.callMethod_S(dsTB, "getCursorSensitivity");
                   assertCondition(property.equalsIgnoreCase("sensitive"), "Expecting: \"sensitive\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 01/16/04");
               }else{
                   JDReflectionUtil.callMethod_V(dataSource_, "setCursorSensitivity", "sensitive"); 
                   ctx_.rebind(bindName_, dataSource_);
                   DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                   String property = JDReflectionUtil.callMethod_S(ds, "getCursorSensitivity");
                   assertCondition(property.equalsIgnoreCase("sensitive"), "Expecting: \"sensitive\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 01/16/04");
               }
               
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception -- New native property (toolbox will need) added by native 01/16/04");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }
		   
/**
setCursorSensitivity() -- call setCursorSensitivity() with an invalid value and check that an exception is thrown
**/
   public void Var074()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		   String badSensitivity = "invalidSensitivityValue";
           if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) 
               JDReflectionUtil.callMethod_V(dataSourceTB_, "setCursorSensitivity", badSensitivity);
           else
		       JDReflectionUtil.callMethod_V(dataSource_, "setCursorSensitivity", badSensitivity); 
           
		   failed("Did not throw expected exception -- New native property (toolbox will need) added by native 01/16/04");
	       } catch (SQLException e) {
		   // HY024 is received by the native driver when trying to set the
		   // cursor sensitivity property to something other than
		   // "asensitive", "insensitive" or "sensitive"
		   if(e.getSQLState().equals("HY024") || e.getSQLState().equals("TOOLBOXSTATE"))
		       succeeded();
		   else
		       failed(e, "Unexpected Exception -- New native property (toolbox will need) added by native 01/16/04");
	       }
           catch (ExtendedIllegalArgumentException e){
               //toolbox throws ExtendedIllegalArgumentException
               succeeded();
           }
	       catch (Exception e){
		   failed(e, "Unexpected Exception -- New native property (toolbox will need) added by native 01/16/04");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 
   }

/**
getReturnExtendedMetaData() -- call getReturnExtendedMetaData() to check the default value, should be "false"
**/
   public void Var075()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt()) {
	       try {
               if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                   ctx_.rebind(bindName_, dataSourceTB_);
                   AS400JDBCDataSource dsTB = (AS400JDBCDataSource)ctx_.lookup(bindName_); //new AS400JDBCDataSource();   
                   boolean property = JDReflectionUtil.callMethod_B(dsTB, "isExtendedMetaData");
                   assertCondition(!property, "Expecting: \"false\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 02/03/04");
               }else{
                   ctx_.rebind(bindName_, dataSource_);
                   DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                   boolean property = JDReflectionUtil.callMethod_B(ds, "getReturnExtendedMetaData");
                   assertCondition(!property, "Expecting: \"false\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 02/03/04");
               }
               
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception -- New native property (toolbox will need) added by native 02/03/04");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       }
   }

/**
setReturnExtendedMetaData(), getReturnExtendedMetaData() -- call setReturnExtendedMetaData() to set it to
"false" and check that it was changed correctly
**/
   public void Var076()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
		    

           if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
               JDReflectionUtil.callMethod_V(dataSourceTB_, "setExtendedMetaData", false);
               ctx_.rebind(bindName_, dataSourceTB_);
               AS400JDBCDataSource dsTB = (AS400JDBCDataSource)ctx_.lookup(bindName_); //new AS400JDBCDataSource();   
               boolean property = JDReflectionUtil.callMethod_B(dsTB, "isExtendedMetaData");
               assertCondition(!property, "Expecting: \"false\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 02/03/04");
           }else{
               JDReflectionUtil.callMethod_V(dataSource_, "setReturnExtendedMetaData", false);
               ctx_.rebind(bindName_, dataSource_);
               DataSource ds = (DataSource)ctx_.lookup(bindName_); 
               boolean property = JDReflectionUtil.callMethod_B(ds, "getReturnExtendedMetaData");
               assertCondition(!property, "Expecting: \"false\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 02/03/04");
           }
  		   
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception -- New native property (toolbox will need) added by native 02/03/04");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }

/**
setReturnExtendedMetaData(), getReturnExtendedMetaData() -- call setExtendedMetaData() to set it to
"true" and check that it was changed correctly
**/
   public void Var077()
   {
       if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
	   if (checkJdbc20StdExt ()) {
	       try {
               if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                   JDReflectionUtil.callMethod_V(dataSourceTB_, "setExtendedMetaData", true);
                   ctx_.rebind(bindName_, dataSourceTB_);
                   AS400JDBCDataSource dsTB = (AS400JDBCDataSource)ctx_.lookup(bindName_); //new AS400JDBCDataSource();   
                   boolean property = JDReflectionUtil.callMethod_B(dsTB, "isExtendedMetaData");
                   assertCondition(property, "Expecting: \"true\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 02/03/04");
               }else{
                   JDReflectionUtil.callMethod_V(dataSource_, "setReturnExtendedMetaData", true);
                   ctx_.rebind(bindName_, dataSource_);
                   DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                   boolean property = JDReflectionUtil.callMethod_B(ds, "getReturnExtendedMetaData");
                   assertCondition(property, "Expecting: \"true\" Received: \""+property+"\" -- New native property (toolbox will need) added by native 02/03/04");
               }
 
		   ctx_.unbind(bindName_);
	       } catch (Exception e) {
		   failed (e, "Unexpected Exception -- New native property (toolbox will need) added by native 02/03/04");
	       }
	   }
       } else {
	   notApplicable("V5R3 variation"); 
       } 

   }

   /**
   getDirectMap() -- call getDirectMap() to check the default value, should be "false"
   **/
      public void Var078()
      {
          if (getRelease() >= JDTestDriver.RELEASE_V7R1M0 && 
              (getDriver()  == JDTestDriver.DRIVER_NATIVE)) {
           if (checkJdbc20StdExt()) {
               try {
                   ctx_.rebind(bindName_, dataSource_);
                   DataSource ds = (DataSource)ctx_.lookup(bindName_);
                   boolean property = JDReflectionUtil.callMethod_B(ds, "getDirectMap");
                   assertCondition(!property, "Expecting: \"false\" Received: \""+property+"\" -- New native property added by native 12/30/05");
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception -- New native property (toolbox will need) added by native 12/30/05");
               }
           }
          } else {
           notApplicable("V5R4 native variation"); 
          }
      }


   /**
   setDirectMap(), getDirectMap() -- call setDirectMap() to set it to
   "false" and check that it was changed correctly
   **/
      public void Var079()
      {
          if (getRelease() >= JDTestDriver.RELEASE_V7R1M0 && 
              (getDriver()  == JDTestDriver.DRIVER_NATIVE)) {
           if (checkJdbc20StdExt ()) {
               try {
                   JDReflectionUtil.callMethod_V(dataSource_, "setDirectMap", false); 
                   ctx_.rebind(bindName_, dataSource_);
                   DataSource ds = (DataSource)ctx_.lookup(bindName_);
                   boolean property = JDReflectionUtil.callMethod_B(ds, "getDirectMap");
                   assertCondition(!property, "Expecting: \"false\" Received: \""+property+"\" -- New native property added by native 12/30/05");
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception -- New native property added by native 12/30/05");
               }
           }
          } else {
           notApplicable("V5R4 native variation"); 
          } 

      }

   /**
   setDirectMap(), getDirectMap() -- call setDirectMap() to set it to
   "true" and check that it was changed correctly
   **/
      public void Var080()
      {
          if (getRelease() >= JDTestDriver.RELEASE_V7R1M0 && 
              (getDriver()  == JDTestDriver.DRIVER_NATIVE)) {
           if (checkJdbc20StdExt ()) {
               try {
                   JDReflectionUtil.callMethod_V(dataSource_, "setDirectMap", true); 
                   ctx_.rebind(bindName_, dataSource_);
                   DataSource ds = (DataSource)ctx_.lookup(bindName_);
                   boolean property = JDReflectionUtil.callMethod_B(ds, "getDirectMap");
                   assertCondition(property, "Expecting: \"true\" Received: \""+property+"\" -- New native property added by native 12/30/05");
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception -- New native property  added by native 12/30/05");
               }
           }
          } else {
           notApplicable("V5R4 native variation"); 
          } 

      }

   /**
   getQueryOptimizeGoal() -- call getQueryOptimizeGoal() to check the default value, should be "2" for native 0 for toolbox
   **/
      public void Var081()
      {
	  String added = " -- new v5r4 property added by native 01/19/2006"; 
          if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
           if (checkJdbc20StdExt()) {
               try {
		   String expected="2";
		   if (getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
		       expected="0"; 
		   }
                   
                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);
                       AS400JDBCDataSource dsTB = (AS400JDBCDataSource)ctx_.lookup(bindName_); //new AS400JDBCDataSource();   
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getQueryOptimizeGoal"));
                       assertCondition(expected.equals(property), "Expecting: \""+expected+"\" Received: \""+property+"\" "+added); 
                   }else{
                       ctx_.rebind(bindName_, dataSource_);
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getQueryOptimizeGoal");
                       assertCondition(expected.equals(property), "Expecting: \""+expected+"\" Received: \""+property+"\" "+added); 
                   }
                   
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception "+added); 
               }
           }
          } else {
           notApplicable("V5R4 variation"); 
          }
      }


   /**
   setQueryOptimizeGoal(), getQueryOptimizeGoal() -- call setQueryOptimizeGoal() to set it to
   "2" and check that it was changed correctly
   **/
      public void Var082()
      {
	  String added = " -- new v5r4 property added by native 01/19/2006"; 
          if (getRelease() >= JDTestDriver.RELEASE_V7R1M0 ) {
           if (checkJdbc20StdExt ()) {
               try {
                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);       
                       AS400JDBCDataSource dsTB =  (AS400JDBCDataSource)ctx_.lookup(bindName_);
                       JDReflectionUtil.callMethod_V(dsTB, "setQueryOptimizeGoal", 1);  
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getQueryOptimizeGoal"));
                       assertCondition("1".equals(property), "Expecting: \"1\" Received: \""+property+"\" "+added); 
                   }else{
                       JDReflectionUtil.callMethod_V(dataSource_, "setQueryOptimizeGoal", "1"); 
                       ctx_.rebind(bindName_, dataSource_);       
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getQueryOptimizeGoal");
                       assertCondition("1".equals(property), "Expecting: \"1\" Received: \""+property+"\" "+added); 
                   }
                   
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception "+added); 
               }
           }
          } else {
           notApplicable("V5R4 variation"); 
          } 

      }

   /**
   setQueryOptimizeGoal(), getQueryOptimizeGoal() -- call setQueryOptimizeGoal() to set it to
   "2" and check that it was changed correctly
   **/
      public void Var083()
      {
	  String added = " -- new v5r4 property added by native 01/19/2006"; 
          if (getRelease() >= JDTestDriver.RELEASE_V7R1M0 ) {
           if (checkJdbc20StdExt ()) {
               try {

                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);  
                       AS400JDBCDataSource dsTB =  (AS400JDBCDataSource)ctx_.lookup(bindName_);
                       JDReflectionUtil.callMethod_V(dsTB, "setQueryOptimizeGoal", 2); 
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getQueryOptimizeGoal"));
                       assertCondition("2".equals(property), "Expecting: \"2\" Received: \""+property+"\" "+added); 
                   }else{
                       JDReflectionUtil.callMethod_V(dataSource_, "setQueryOptimizeGoal", "2"); 
                       ctx_.rebind(bindName_, dataSource_);
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getQueryOptimizeGoal");
                       assertCondition("2".equals(property), "Expecting: \"2\" Received: \""+property+"\" "+added); 
                   }

                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception -- "+added); 
               }
           }
          } else {
           notApplicable("V5R4 variation"); 
          } 

      }
      
      //@PDA
      /**
      * Toolbox DS.setProperties() method testcase
      **/
         public void Var084 ()
         {
             if( getDriver() != JDTestDriver.DRIVER_TOOLBOX) {
                 notApplicable("Toolbox driver variation"); 
                 return;
             }
            if (checkJdbc20StdExt ()) {
               try {  
                   //set props to something other than defaults
                   //array of triplets:  property name, value, and getter method
                   String[] propsNVM = { "access", "read call", "getAccess", 
                           "behavior override",    "1",         "getBehaviorOverride",
                           "bidi string type",     "6",         "getBidiStringType",
                           "big decimal",          "false",     "isBigDecimal",     
                           "block size",           "128",       "getBlockSize",
                           "block criteria",       "1",         "getBlockCriteria",
                           "cursor hold",          "false",     "isCursorHold",               
                           "cursor sensitivity",   "sensitive", "getCursorSensitivity",
                           "data compression",     "false",     "isDataCompression",   
                           "data truncation",      "false",     "isDataTruncation",    
                           "database name",        "testdb",    "getDatabaseName",       
                           "date format",          "julian",    "getDateFormat",
                           "date separator",       ".",         "getDateSeparator",
                           "decimal separator",    ",",         "getDecimalSeparator",
                           "driver",               "native",    "getDriver",              
                           "errors",               "full",      "getErrors",
                           "extended dynamic",     "true",      "isExtendedDynamic",
                           "extended metadata",    "true",      "isExtendedMetaData",
                           "full open",            "true",      "isFullOpen",       
                           "hold input locators",  "false",     "isHoldInputLocators",       
                           "key ring name",        "keyname",   "",        
                           "key ring password",    "keypass",   "",    
                           "lazy close",           "true",      "isLazyClose",       
                           "libraries",            "testlib",   "getLibraries",
                           "lob threshold",        "1",         "getLobThreshold",
                           "maximum precision",    "63",        "getMaximumPrecision",
                           "maximum scale",        "9",         "getMaximumScale",   
                           "minimum divide scale", "4",         "getMinimumDivideScale",
                           "naming",               "system",    "getNaming",
                           "package",              "testpack",  "getPackage",
                           "package add",          "false",     "isPackageAdd",
                           "package cache",        "true",      "isPackageCache",
                           "package ccsid",        "1200",      "getPackageCCSID",  
                           "package clear",        "false",      "isPackageClear",
                           "package criteria",     "select",    "getPackageCriteria",
                           "package error",        "warning",   "getPackageError",
                           "package library",      "packlib",   "getPackageLibrary",
                           "password",             "testpw",    "",
                           "prefetch",             "false",     "isPrefetch",
                           "prompt",               "false",     "isPrompt",
                           "proxy server",         "pxy",       "getProxyServer",
                           "remarks",              "sql",     "getRemarks",
                           "secondary URL",        "testurl",   "getSecondaryUrl",
                           "secure",               "true",      "isSecure",
                           "sort",                 "table",     "getSort",
                           "sort language",        "lang",      "getSortLanguage",
                           "sort table",           "sorttab",   "getSortTable",
                           "sort weight",          "unique",    "getSortWeight",
                           "thread used",          "false",     "isThreadUsed",
                           "time format",          "iso",       "getTimeFormat",
                           "time separator",       ".",         "getTimeSeparator",
                           "trace",                "false",      "isTrace",
                           "server trace",         "1",         "getServerTraceCategories", 
                           "toolbox trace",        "none",      "getToolboxTraceCategory",   
                           "transaction isolation", "serializable", "getTransactionIsolation",
                           "translate binary",     "true",      "isTranslateBinary",
                           "translate hex",        "binary",    "getTranslateHex",  
                           "user",                 "TESTUSR",   "getUser",
                           "qaqqinilib",           "whatlib",   "getQaqqiniLibrary",         
                           "login timeout",        "100",       "getLoginTimeout",   
                           "true autocommit",      "true",      "isTrueAutoCommit",       
                           "bidi implicit reordering", "false", "isBidiImplicitReordering",
                           "bidi numeric ordering", "true",     "isBidiNumericOrdering",   
                           "hold statements",      "true",      "isHoldStatements",
                           "rollback cursor hold", "true",      "isRollbackCursorHold",
                           "variable field compression", "false", "isVariableFieldCompression",   
                           "query optimize goal",  "2",         "getQueryOptimizeGoal",
                           "keep alive",           "true",      "getKeepAlive",
                           "receive buffer size",  "111",       "getReceiveBufferSize",
                           "send buffer size",     "222",       "getSendBufferSize",
                           "XA loosely coupled support", "1",   "getXALooselyCoupledSupport",  
                           "translate boolean",    "false",     "isTranslateBoolean" };
                           //!note to toolbox, use JDDSProperties.java instead of here.
                   
                   int count = propsNVM.length / 3;
                   String propertyValueList = ""; 
                   for(int x = 0 ; x < count; x++){
                       int ind = x * 3;
                       propertyValueList = propertyValueList + propsNVM[ind] + "=" + propsNVM[ind+1] + ";";
                   }
                    
                   ctx_.rebind(bindName_, dataSourceTB_);  
                   AS400JDBCDataSource dsTB =  (AS400JDBCDataSource)ctx_.lookup(bindName_);
                   // This is a new method, not in older jar file.  Use reflection to all 
                   // dsTB.setProperties(propertyValueList);  //updates all propeties
                   JDReflectionUtil.callMethod_V(dsTB, "setProperties", propertyValueList);
                   
                   boolean passed = true;
                   for(int x = 0; x < count; x++){
                       int ind = x * 3;
                       String value = null;
                       //rather than adding return types from getters to array, just try each method for now
                       try{
                           value = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, propsNVM[ind + 2]));
                       }catch(Exception e){
                           try{
                               value = String.valueOf( JDReflectionUtil.callMethod_B(dsTB, propsNVM[ind + 2]));
                           }catch(Exception e2){
                               try{
                                   value =  JDReflectionUtil.callMethod_S(dsTB, propsNVM[ind + 2]);
                               }catch(Exception e3){
                                   //if here, then there is a problem unless method text is "", then there is not
                                   //a method in datasource for the corresponding property
                                   if(propsNVM[ind+2] != ""){
                                       System.out.println("Error getting back Property: " + propsNVM[ind] + " by calling method DS." + propsNVM[ind+2] );
                                       passed = false;
                                   } else
                                       value = "";
                                       
                               }
                           }
                       }
                       if( (value != "") && (value.equals(propsNVM[ind+1]) == false)){
                           System.out.println("Property: " + propsNVM[ind] + ", set to " + propsNVM[ind+1] + ", but get back " + value );
                           passed = false;
                       }
                           
                           
                               
                   }
                      
                   assertCondition(passed);  //pass fail
                
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                  failed (e, "Unexpected Exception toolbox testcase 03/09/06");
               }
            }
         }

         

   /**
   getDecfloatRoundingMode() -- call getDecfloatRoundingMode() to check the default value, should be "round half even"
   **/
      public void Var085()
      {
	  String added = " -- new v5r5 property added by native 11/21/2006"; 
	  if (checkDecFloatSupport()) { 
	      try {
		  String expected="round half even"; 
                   
                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);
                       AS400JDBCDataSource dsTB = (AS400JDBCDataSource)ctx_.lookup(bindName_); //new AS400JDBCDataSource();   
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getDecfloatRoundingMode"));
                       assertCondition(expected.equals(property), "Expecting: \""+expected+"\" Received: \""+property+"\" "+added); 
                   }else{
                       ctx_.rebind(bindName_, dataSource_);
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getDecfloatRoundingMode");
                       assertCondition(expected.equals(property), "Expecting: \""+expected+"\" Received: \""+property+"\" "+added); 
                   }
                   
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception "+added); 
               }
	  }
      }


   /**
   setDecfloatRoundingMode(), getDecfloatRoundingMode() -- call setDecfloatRoundingMode() to set it to
   "round half even" and check that it was changed correctly
   **/
      public void Var086()
      {
	  String testValue = "round half even"; 
	  String added = " -- new v5r5 property added by native 11/21/2006"; 

           if (checkDecFloatSupport ()) {
               try {
                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);       
                       AS400JDBCDataSource dsTB =  (AS400JDBCDataSource)ctx_.lookup(bindName_);
                       JDReflectionUtil.callMethod_V(dsTB, "setDecfloatRoundingMode", testValue);  
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getDecfloatRoundingMode"));
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }else{
                       JDReflectionUtil.callMethod_V(dataSource_, "setDecfloatRoundingMode", testValue); 
                       ctx_.rebind(bindName_, dataSource_);       
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getDecfloatRoundingMode");
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }
                   
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception "+added); 
               }
           }

      }

   /**
   setDecfloatRoundingMode(), getDecfloatRoundingMode() -- call setDecfloatRoundingMode() to set it to
   "round half even" and check that it was changed correctly
   **/
      public void Var087()
      {
	  String testValue = "round half up"; 
	  String added = " -- new v5r5 property added by native 11/21/2006"; 

           if (checkDecFloatSupport ()) {
               try {
                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);       
                       AS400JDBCDataSource dsTB =  (AS400JDBCDataSource)ctx_.lookup(bindName_);
                       JDReflectionUtil.callMethod_V(dsTB, "setDecfloatRoundingMode", testValue);  
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getDecfloatRoundingMode"));
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }else{
                       JDReflectionUtil.callMethod_V(dataSource_, "setDecfloatRoundingMode", testValue); 
                       ctx_.rebind(bindName_, dataSource_);       
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getDecfloatRoundingMode");
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }
                   
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception "+added); 
               }
           }

      }

   /**
   setDecfloatRoundingMode(), getDecfloatRoundingMode() -- call setDecfloatRoundingMode() to set it to
   "round half even" and check that it was changed correctly
   **/
      public void Var088()
      {
	  String testValue = "round down"; 
	  String added = " -- new v5r5 property added by native 11/21/2006"; 

           if (checkDecFloatSupport ()) {
               try {
                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);       
                       AS400JDBCDataSource dsTB =  (AS400JDBCDataSource)ctx_.lookup(bindName_);
                       JDReflectionUtil.callMethod_V(dsTB, "setDecfloatRoundingMode", testValue);  
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getDecfloatRoundingMode"));
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }else{
                       JDReflectionUtil.callMethod_V(dataSource_, "setDecfloatRoundingMode", testValue); 
                       ctx_.rebind(bindName_, dataSource_);       
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getDecfloatRoundingMode");
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }
                   
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception "+added); 
               }
           }

      }

   /**
   setDecfloatRoundingMode(), getDecfloatRoundingMode() -- call setDecfloatRoundingMode() to set it to
   "round half even" and check that it was changed correctly
   **/
      public void Var089()
      {
	  String testValue = "round ceiling"; 
	  String added = " -- new v5r5 property added by native 11/21/2006"; 

           if (checkDecFloatSupport ()) {
               try {
                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);       
                       AS400JDBCDataSource dsTB =  (AS400JDBCDataSource)ctx_.lookup(bindName_);
                       JDReflectionUtil.callMethod_V(dsTB, "setDecfloatRoundingMode", testValue);  
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getDecfloatRoundingMode"));
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }else{
                       JDReflectionUtil.callMethod_V(dataSource_, "setDecfloatRoundingMode", testValue); 
                       ctx_.rebind(bindName_, dataSource_);       
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getDecfloatRoundingMode");
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }
                   
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception "+added); 
               }
           }

      }

   /**
   setDecfloatRoundingMode(), getDecfloatRoundingMode() -- call setDecfloatRoundingMode() to set it to
   "round half even" and check that it was changed correctly
   **/
      public void Var090()
      {
	  String testValue = "round floor"; 
	  String added = " -- new v5r5 property added by native 11/21/2006"; 

           if (checkDecFloatSupport ()) {
               try {
                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);       
                       AS400JDBCDataSource dsTB =  (AS400JDBCDataSource)ctx_.lookup(bindName_);
                       JDReflectionUtil.callMethod_V(dsTB, "setDecfloatRoundingMode", testValue);  
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getDecfloatRoundingMode"));
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }else{
                       JDReflectionUtil.callMethod_V(dataSource_, "setDecfloatRoundingMode", testValue); 
                       ctx_.rebind(bindName_, dataSource_);       
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getDecfloatRoundingMode");
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }
                   
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception "+added); 
               }
           }

      }

   /**
   setDecfloatRoundingMode(), getDecfloatRoundingMode() -- call setDecfloatRoundingMode() to set it to
   "round half even" and check that it was changed correctly
   **/
      public void Var091()
      {
	  String testValue = "round half down"; 
	  String added = " -- new v5r5 property added by native 11/21/2006"; 

           if (checkDecFloatSupport ()) {
               try {
                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);       
                       AS400JDBCDataSource dsTB =  (AS400JDBCDataSource)ctx_.lookup(bindName_);
                       JDReflectionUtil.callMethod_V(dsTB, "setDecfloatRoundingMode", testValue);  
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getDecfloatRoundingMode"));
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }else{
                       JDReflectionUtil.callMethod_V(dataSource_, "setDecfloatRoundingMode", testValue); 
                       ctx_.rebind(bindName_, dataSource_);       
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getDecfloatRoundingMode");
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }
                   
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception "+added); 
               }
           }

      }

   /**
   setDecfloatRoundingMode(), getDecfloatRoundingMode() -- call setDecfloatRoundingMode() to set it to
   "round half even" and check that it was changed correctly
   **/
      public void Var092()
      {
	  String testValue = "round up"; 
	  String added = " -- new v5r5 property added by native 11/21/2006"; 

           if (checkDecFloatSupport ()) {
               try {
                   if( getDriver() == JDTestDriver.DRIVER_TOOLBOX) {
                       ctx_.rebind(bindName_, dataSourceTB_);       
                       AS400JDBCDataSource dsTB =  (AS400JDBCDataSource)ctx_.lookup(bindName_);
                       JDReflectionUtil.callMethod_V(dsTB, "setDecfloatRoundingMode", testValue);  
                       String property = String.valueOf( JDReflectionUtil.callMethod_I(dsTB, "getDecfloatRoundingMode"));
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }else{
                       JDReflectionUtil.callMethod_V(dataSource_, "setDecfloatRoundingMode", testValue); 
                       ctx_.rebind(bindName_, dataSource_);       
                       DataSource ds = (DataSource)ctx_.lookup(bindName_); 
                       String property = JDReflectionUtil.callMethod_S(ds, "getDecfloatRoundingMode");
                       assertCondition(testValue.equals(property), "Expecting: \""+testValue+"\" Received: \""+property+"\" "+added); 
                   }
                   
                   ctx_.unbind(bindName_);
               } catch (Exception e) {
                   failed (e, "Unexpected Exception "+added); 
               }
           }

      }


      /**
         * setSort() to test toolbox ptf to not throw exception if sort=job, but
         * to use default
         */
      public void Var093()
      {

          String added = " toolbox ptf test for setSort() added 02/12/2007";

          try
          {
              if (getDriver() == JDTestDriver.DRIVER_TOOLBOX)
              {
                  ctx_.rebind(bindName_, dataSourceTB_);
                  AS400JDBCDataSource dsTB = (AS400JDBCDataSource) ctx_.lookup(bindName_);
               
                  dsTB.setSort("job");

                  ctx_.unbind(bindName_);
                  succeeded();
              }
              else 
                  notApplicable("Toolbox variation"); 
          } catch (Exception e)
          {
              failed(e, "Unexpected Exception " + added);
          }
      }

      /**
      getQaqqinilib() -- call getQaqqinilib() to check the default value, should be "QUSRSYS"
      **/
         public void Var094()
         {
             String added = " -- new V5R5 property added by native driver 05/22/07"; 
             if (getRelease() >= JDTestDriver.RELEASE_V7R1M0 && 
                 (getDriver()  == JDTestDriver.DRIVER_NATIVE)) {
              if (checkJdbc20StdExt()) {
                  try {
                      ctx_.rebind(bindName_, dataSource_);
                      DataSource ds = (DataSource)ctx_.lookup(bindName_);
                      String property = JDReflectionUtil.callMethod_S(ds, "getQaqqinilib");
                      assertCondition("QUSRSYS".equals(property), "Expecting: \"QUSRSYS\" Received: \""+property+"\""+added); 
                      ctx_.unbind(bindName_);
                  } catch (Exception e) {
                      failed (e, "Unexpected Exception "+added); 
                  }
              }
             } else {
              notApplicable("V5R5 native variation"); 
             }
         }


      /**
      setQaqqinilib(), getQaqqinilib() -- call setQaqqinilib() to set it to
      "QUSRSYS" and check that it was changed correctly
      **/
         public void Var095()
         {
           String added = " -- new V5R5 property added by native driver 05/22/07"; 
             if (getRelease() >= JDTestDriver.RELEASE_V7R1M0 && 
                 (getDriver()  == JDTestDriver.DRIVER_NATIVE)) {
                  try {
                      JDReflectionUtil.callMethod_V(dataSource_, "setQaqqinilib", "EBERHARD"); 
                      ctx_.rebind(bindName_, dataSource_);
                      DataSource ds = (DataSource)ctx_.lookup(bindName_);
                      String property = JDReflectionUtil.callMethod_S(ds, "getQaqqinilib");
                      assertCondition("EBERHARD".equals(property), "Expecting: \"EBERHARD\" Received: \""+property+"\""+added); 
                      ctx_.unbind(bindName_);
                  } catch (Exception e) {
                      failed (e, "Unexpected Exception"+added); 
                  }
             } else {
              notApplicable("V5R5 native variation"); 
             } 

         }


         /**
         getIgnoreWarning() -- call getIgnoreWarnings() to check the default value, should be ""
         **/
            public void Var096()
            {
                String added = " -- new V5R5 property added by native driver 05/22/07"; 
                if (getRelease() >= JDTestDriver.RELEASE_V7R1M0 && 
                    (getDriver()  == JDTestDriver.DRIVER_NATIVE)) {
                 if (checkJdbc20StdExt()) {
                     try {
                         ctx_.rebind(bindName_, dataSource_);
                         DataSource ds = (DataSource)ctx_.lookup(bindName_);
                         String property = JDReflectionUtil.callMethod_S(ds, "getIgnoreWarnings");
                         assertCondition("".equals(property), "Expecting: \"\" Received: \""+property+"\""+added); 
                         ctx_.unbind(bindName_);
                     } catch (Exception e) {
                         failed (e, "Unexpected Exception "+added); 
                     }
                 }
                } else {
                 notApplicable("V5R5 native variation"); 
                }
            }


         /**
         setIgnoreWarnings(), getIgnoreWarnings() -- call setIgnoreWarnings() to set it to
         "0100C" and check that it was changed correctly
         **/
            public void Var097()
            {
              String added = " -- new V5R5 property added by native driver 05/22/07"; 
                if (getRelease() >= JDTestDriver.RELEASE_V7R1M0 && 
                    (getDriver()  == JDTestDriver.DRIVER_NATIVE)) {
                     try {
                         JDReflectionUtil.callMethod_V(dataSource_, "setIgnoreWarnings", "0100C"); 
                         ctx_.rebind(bindName_, dataSource_);
                         DataSource ds = (DataSource)ctx_.lookup(bindName_);
                         String property = JDReflectionUtil.callMethod_S(ds, "getIgnoreWarnings");
                         assertCondition("0100C".equals(property), "Expecting: \"0100C\" Received: \""+property+"\""+added); 
                         ctx_.unbind(bindName_);
                     } catch (Exception e) {
                         failed (e, "Unexpected Exception"+added); 
                     }
                } else {
                 notApplicable("V5R5 native variation"); 
                } 

            }

            /**
            check the default value of maximum blocked input rows , should be 32000
            **/
               public void Var098 ()
               {
                   if (getRelease() >=  JDTestDriver.RELEASE_V7R1M0) {      
                       if (checkJdbc20StdExt ()) {
                           try {
                               ctx_.rebind(bindName_, dataSource_);
                               DataSource ds = (DataSource)ctx_.lookup(bindName_);
                               int value = JDReflectionUtil.callMethod_I(ds, "getMaximumBlockedInputRows"); 
                               assertCondition(value == 32000, "New native property (toolbox will need)");
                               ctx_.unbind(bindName_);
                           } catch (Exception e) {
                               failed (e, "Unexpected Exception New native property (toolbox will need)");
                           }
                       }
                   } else {
                       notApplicable("V5R4 variation"); 
                   } 

               }

               /**
               set the maximum blocked input rows to a valid number and see that it was changed
               **/
                  public void Var099 ()
                  {
                      if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
                          if (checkJdbc20StdExt ()) {
                              try {
                                  JDReflectionUtil.callMethod_V(dataSource_, "setMaximumBlockedInputRows", 1000);  
                                  ctx_.rebind(bindName_, dataSource_);
                                  DataSource ds = (DataSource)ctx_.lookup(bindName_);
                                  int value = JDReflectionUtil.callMethod_I(ds, "getMaximumBlockedInputRows"); 
                                  assertCondition(value == 1000, "Got "+value+" Expected 1000 New native property (toolbox will need)");
                                  ctx_.unbind(bindName_);
                              } catch (Exception e) {
                                  failed (e, "Unexpected Exception New native property (toolbox will need)");
                              }
                          }
                      } else {
                          notApplicable("V5R4 variation"); 
                      } 

                  }

                  public void Var100 ()
                  {
                      if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
                          if (checkJdbc20StdExt ()) {
                              try {
                                  JDReflectionUtil.callMethod_V(dataSource_, "setMaximumBlockedInputRows", 0);  
                                  ctx_.rebind(bindName_, dataSource_);
                                  DataSource ds = (DataSource)ctx_.lookup(bindName_);
                                  int value = JDReflectionUtil.callMethod_I(ds, "getMaximumBlockedInputRows"); 
                                  assertCondition(value == 0, "Got "+value+" expected 0. New native property (toolbox will need)");
                                  ctx_.unbind(bindName_);
                              } catch (Exception e) {
                                  failed (e, "Unexpected Exception New native property (toolbox will need)");
                              }
                          }
                      } else {
                          notApplicable("V5R4 variation"); 
                      } 

                  }

                  public void Var101 ()
                  {
                      if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
                          if (checkJdbc20StdExt ()) {
                              try {
                                  JDReflectionUtil.callMethod_V(dataSource_, "setMaximumBlockedInputRows", 32000);  
                                  ctx_.rebind(bindName_, dataSource_);
                                  DataSource ds = (DataSource)ctx_.lookup(bindName_);
                                  int value = JDReflectionUtil.callMethod_I(ds, "getMaximumBlockedInputRows"); 
                                  assertCondition(value == 32000, "Got "+value+" expected 32000.  New native property (toolbox will need)");
                                  ctx_.unbind(bindName_);
                              } catch (Exception e) {
                                  failed (e, "Unexpected Exception New native property (toolbox will need)");
                              }
                          }
                      } else {
                          notApplicable("V5R4 variation"); 
                      } 

                  }


                  
               /**
               set the maximum blocked input rows to an invalid value
               **/
                  public void Var102 ()
                  {
                      if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
                          if (checkJdbc20StdExt ()) {
                              try {
                                  JDReflectionUtil.callMethod_V(dataSource_, "setMaximumBlockedInputRows", -1);
                                  failed("Did not throw exception New native property (toolbox will need)");
                              } catch (Exception e) {
                                  assertExceptionIsInstanceOf (e, "java.sql.SQLException");
                              }
                          }
                      } else {
                          notApplicable("V5R4 variation"); 
                      } 

                  }


                  /**
                  set the maximum blocked input rows to an invalid value
                  **/
                     public void Var103 ()
                     {
                         if (getRelease() >= JDTestDriver.RELEASE_V7R1M0) {
                             if (checkJdbc20StdExt ()) {
                                 try {
                                     JDReflectionUtil.callMethod_V(dataSource_, "setMaximumBlockedInputRows", 32001);
                                     failed("Did not throw exception New native property (toolbox will need)");
                                 } catch (Exception e) {
                                     assertExceptionIsInstanceOf (e, "java.sql.SQLException");
                                 }
                             }
                         } else {
                             notApplicable("V5R4 variation"); 
                         } 

                     }

   
}
