///////////////////////////////////////////////////////////////////////////////
//
// JTOpen (IBM Toolbox for Java - OSS version)
//
// Filename:  JDStatementStressTest206.java
//
// The source code contained herein is licensed under the IBM Public License
// Version 1.0, which has been approved by the Open Source Initiative.
// Copyright (C) 1997-2023 International Business Machines Corporation and
// others.  All rights reserved.
//
///////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////
//
// File Name:    JDStatementStressTest206.java
//
// Classes:      JDStatementStressTest206
//
////////////////////////////////////////////////////////////////////////

package test.JD.Statement;

import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Vector;

import com.ibm.as400.access.AS400;



/**
Testcase JDStatementStressTest201.  This tests multithreaded
use of parts of the JDBC driver.

This also stresses the use of different CCSIDs.. 

**/
public class JDStatementStressTest206
extends JDStatementStressTest
{
  public static void main(String args[]) throws Exception {
    String[] newArgs = new String[args.length+2];
     newArgs[0] = "-tc";
     newArgs[1] = "JDStatementStressTest206";
     for (int i = 0; i < args.length; i++) {
       newArgs[2+i]=args[i];
     }
     test.JDStatementTest.main(newArgs); 
   }

  
/**
Constructor.
**/
    public JDStatementStressTest206 (AS400 systemObject,
                        Hashtable<String,Vector<String>> namesAndVars,
                        int runMode,
                        FileOutputStream fileOutputStream,
                        
                        String password,
                        String miscParm)
    {
        super (systemObject, "JDStatementStressTest206",
            namesAndVars, runMode, fileOutputStream,
            password, miscParm);
    }


    public void Var001() { notApplicable(); }
    public void Var002() { notApplicable(); }
    public void Var003() { notApplicable(); }
    public void Var004() { notApplicable(); }
    public void Var005() { notApplicable(); }
    public void Var006() { notApplicable(); }
    public void Var007() { notApplicable(); }
    public void Var008() { notApplicable(); }
    public void Var009() { notApplicable(); }
    public void Var010() { notApplicable(); }
    public void Var011() { notApplicable(); }
    public void Var012() { notApplicable(); }
    public void Var013() { notApplicable(); }
    public void Var014() { notApplicable(); }
    public void Var015() { notApplicable(); }
    public void Var016() { notApplicable(); }
    public void Var017() { notApplicable(); }
    public void Var018() { notApplicable(); }
    public void Var019() { notApplicable(); }
    public void Var020() { notApplicable(); }
    public void Var021() { notApplicable(); }
    public void Var022() { notApplicable(); }
    public void Var023() { notApplicable(); }
    public void Var024() { notApplicable(); }
    public void Var025() { notApplicable(); }
    public void Var026() { notApplicable(); }
    public void Var027() { notApplicable(); }
    public void Var028() { notApplicable(); }
    public void Var029() { notApplicable(); }
    public void Var030() { notApplicable(); }
    public void Var031() { notApplicable(); }
    public void Var032() { notApplicable(); }
    public void Var033() { notApplicable(); }
    public void Var034() { notApplicable(); }
    public void Var035() { notApplicable(); }
    public void Var036() { notApplicable(); }
    public void Var037() { notApplicable(); }
    public void Var038() { notApplicable(); }
    public void Var039() { notApplicable(); }
    public void Var040() { notApplicable(); }
    public void Var041() { notApplicable(); }
    public void Var042() { notApplicable(); }
    public void Var043() { notApplicable(); }
    public void Var044() { notApplicable(); }
    public void Var045() { notApplicable(); }
    public void Var046() { notApplicable(); }
    public void Var047() { notApplicable(); }
    public void Var048() { notApplicable(); }
    public void Var049() { notApplicable(); }
    public void Var050() { notApplicable(); }
    public void Var051() { notApplicable(); }
    public void Var052() { notApplicable(); }
    public void Var053() { notApplicable(); }
    public void Var054() { notApplicable(); }
    public void Var055() { notApplicable(); }
    public void Var056() { notApplicable(); }
    public void Var057() { notApplicable(); }
    public void Var058() { notApplicable(); }
    public void Var059() { notApplicable(); }
    public void Var060() { notApplicable(); }
    public void Var061() { notApplicable(); }
    public void Var062() { notApplicable(); }
    public void Var063() { notApplicable(); }
    public void Var064() { notApplicable(); }
    public void Var065() { notApplicable(); }
    public void Var066() { notApplicable(); }
    public void Var067() { notApplicable(); }
    public void Var068() { notApplicable(); }
    public void Var069() { notApplicable(); }
    public void Var070() { notApplicable(); }
    public void Var071() { notApplicable(); }
    public void Var072() { notApplicable(); }
    public void Var073() { notApplicable(); }
    public void Var074() { notApplicable(); }
    public void Var075() { notApplicable(); }
    public void Var076() { notApplicable(); }
    public void Var077() { notApplicable(); }
    public void Var078() { notApplicable(); }
    public void Var079() { notApplicable(); }
    public void Var080() { notApplicable(); }
    public void Var081() { notApplicable(); }
    public void Var082() { notApplicable(); }
    public void Var083() { notApplicable(); }
    public void Var084() { notApplicable(); }
    public void Var085() { notApplicable(); }
    public void Var086() { notApplicable(); }
    public void Var087() { notApplicable(); }
    public void Var088() { notApplicable(); }
    public void Var089() { notApplicable(); }
    public void Var090() { notApplicable(); }
    public void Var091() { notApplicable(); }
    public void Var092() { notApplicable(); }
    public void Var093() { notApplicable(); }
    public void Var094() { notApplicable(); }
    public void Var095() { notApplicable(); }
    public void Var096() { notApplicable(); }
    public void Var097() { notApplicable(); }
    public void Var098() { notApplicable(); }
    public void Var099() { notApplicable(); }
    public void Var100() { notApplicable(); }
    public void Var101() { notApplicable(); }
    public void Var102() { notApplicable(); }
    public void Var103() { notApplicable(); }
    public void Var104() { notApplicable(); }
    public void Var105() { notApplicable(); }
    public void Var106() { notApplicable(); }
    public void Var107() { notApplicable(); }
    public void Var108() { notApplicable(); }
    public void Var109() { notApplicable(); }
    public void Var110() { notApplicable(); }
    public void Var111() { notApplicable(); }
    public void Var112() { notApplicable(); }
    public void Var113() { notApplicable(); }
    public void Var114() { notApplicable(); }
    public void Var115() { notApplicable(); }
    public void Var116() { notApplicable(); }
    public void Var117() { notApplicable(); }
    public void Var118() { notApplicable(); }
    public void Var119() { notApplicable(); }
    public void Var120() { notApplicable(); }
    public void Var121() { notApplicable(); }
    public void Var122() { notApplicable(); }
    public void Var123() { notApplicable(); }
    public void Var124() { notApplicable(); }
    public void Var125() { notApplicable(); }
    public void Var126() { notApplicable(); }
    public void Var127() { notApplicable(); }
    public void Var128() { notApplicable(); }
    public void Var129() { notApplicable(); }
    public void Var130() { notApplicable(); }
    public void Var131() { notApplicable(); }
    public void Var132() { notApplicable(); }
    public void Var133() { notApplicable(); }
    public void Var134() { notApplicable(); }
    public void Var135() { notApplicable(); }
    public void Var136() { notApplicable(); }
    public void Var137() { notApplicable(); }
    public void Var138() { notApplicable(); }
    public void Var139() { notApplicable(); }
    public void Var140() { notApplicable(); }
    public void Var141() { notApplicable(); }
    public void Var142() { notApplicable(); }
    public void Var143() { notApplicable(); }
    public void Var144() { notApplicable(); }
    public void Var145() { notApplicable(); }
    public void Var146() { notApplicable(); }
    public void Var147() { notApplicable(); }
    public void Var148() { notApplicable(); }
    public void Var149() { notApplicable(); }
    public void Var150() { notApplicable(); }
    public void Var151() { notApplicable(); }
    public void Var152() { notApplicable(); }
    public void Var153() { notApplicable(); }
    public void Var154() { notApplicable(); }
    public void Var155() { notApplicable(); }
    public void Var156() { notApplicable(); }
    public void Var157() { notApplicable(); }
    public void Var158() { notApplicable(); }
    public void Var159() { notApplicable(); }
    public void Var160() { notApplicable(); }
    public void Var161() { notApplicable(); }
    public void Var162() { notApplicable(); }
    public void Var163() { notApplicable(); }
    public void Var164() { notApplicable(); }
    public void Var165() { notApplicable(); }
    public void Var166() { notApplicable(); }
    public void Var167() { notApplicable(); }
    public void Var168() { notApplicable(); }
    public void Var169() { notApplicable(); }
    public void Var170() { notApplicable(); }
    public void Var171() { notApplicable(); }
    public void Var172() { notApplicable(); }
    public void Var173() { notApplicable(); }
    public void Var174() { notApplicable(); }
    public void Var175() { notApplicable(); }
    public void Var176() { notApplicable(); }
    public void Var177() { notApplicable(); }
    public void Var178() { notApplicable(); }
    public void Var179() { notApplicable(); }
    public void Var180() { notApplicable(); }
    public void Var181() { notApplicable(); }
    public void Var182() { notApplicable(); }
    public void Var183() { notApplicable(); }
    public void Var184() { notApplicable(); }
    public void Var185() { notApplicable(); }
    public void Var186() { notApplicable(); }
    public void Var187() { notApplicable(); }
    public void Var188() { notApplicable(); }
    public void Var189() { notApplicable(); }
    public void Var190() { notApplicable(); }
    public void Var191() { notApplicable(); }
    public void Var192() { notApplicable(); }
    public void Var193() { notApplicable(); }
    public void Var194() { notApplicable(); }
    public void Var195() { notApplicable(); }
    public void Var196() { notApplicable(); }
    public void Var197() { notApplicable(); }
    public void Var198() { notApplicable(); }
    public void Var199() { notApplicable(); }
    public void Var200() { notApplicable(); }

    public void Var201() { notApplicable(); }
    public void Var202() { notApplicable(); }
    public void Var203() { notApplicable(); }
    public void Var204() { notApplicable(); }
    public void Var205() { notApplicable(); }
  public void Var206() {
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[0]); /* 5473 */ 
    skipValidCheck = false; 
  }

  public void Var207() {
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[1]); /* 13676 */ 
    skipValidCheck = false; 
  }

  public void Var208() {
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[2]); /* 1175 */ 
    skipValidCheck = false; 
  }

  public void Var209() {
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[3]); /* 1379 */ 
    skipValidCheck = false; 
  }
  
  public void Var210() { 
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[4]); /* 1371 */ 
    skipValidCheck = false; 
    
  }

  
  public void Var211() { 
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[5]); /* 935 */ 
    skipValidCheck = false; 
    
  }

  public void Var212() { 
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[6]); /* 930 */ 
    skipValidCheck = false; 
    
  }

  public void Var213() { 
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[7]); /* 1399 */ 
    skipValidCheck = false; 
    
  }
  
  public void Var214() { 
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[8]); /* 5026*/ 
    skipValidCheck = false; 
    
  }

  public void Var215() { 
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[9]); /* 5035*/ 
    skipValidCheck = false; 
    
  }


  public void Var216() {
    notApplicable("CCSID 61175 not supported -- see issue 66267 "); 
    
  }

  public void Var217() { 
    skipValidCheck = true; 
    testOneCcsid(oneCcsid[11]); /* 939*/ 
    skipValidCheck = false; 
    
  }
  
    public void Var218() { notApplicable(); }
    public void Var219() { notApplicable(); }
    public void Var220() { notApplicable(); }
    public void Var221() { notApplicable(); }
    public void Var222() { notApplicable(); }
    public void Var223() { notApplicable(); }
    public void Var224() { notApplicable(); }
    public void Var225() { notApplicable(); }
    public void Var226() { notApplicable(); }
    public void Var227() { notApplicable(); }
    public void Var228() { notApplicable(); }
    public void Var229() { notApplicable(); }
}



