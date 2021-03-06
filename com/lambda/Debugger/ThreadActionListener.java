/*                        ThreadActionListener.java

  Copyright 2003, Bil Lewis

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  
  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA   
*/

package com.lambda.Debugger;


import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;


public class ThreadActionListener implements ActionListener {

  JButton b0, b1, b2, b3;

  public ThreadActionListener(){}

  public ThreadActionListener(JButton b0,JButton b1,JButton b2,JButton b3) {
    this.b0=b0;
    this.b1=b1;
    this.b2=b2;
    this.b3=b3;
  }

  public void actionPerformed(ActionEvent event) {
    if (TimeStamp.empty()) {
      Debugger.message("No Time Stamps recorded. Is the target program debugified?", true);
      return;
    }

    if (event.getSource() == b0) {
      DebuggerCommand dc = new DebuggerCommand(this.getClass(), "first");
      dc.execute();
      return;
    }	    
    if (event.getSource() == b1) {
      DebuggerCommand dc = new DebuggerCommand(this.getClass(), "previous");
      dc.execute();
      return;
    }
    if (event.getSource() == b2) {
      DebuggerCommand dc = new DebuggerCommand(this.getClass(), "next");
      dc.execute();
      return;
    }
    if (event.getSource() == b3) {
      DebuggerCommand dc = new DebuggerCommand(this.getClass(), "last");
      dc.execute();
      return;
    }
  }


  public static void first() {
    int i = Debugger.ObjectsPList.getSelectedIndex();
    TimeStamp ts = TimeStamp.currentTime().getFirstThisThread();
    if ((ts != null) && (ts.earlierThan(TimeStamp.currentTime())))
      Debugger.revert(ts);
    else
      Debugger.message("First time stamp this thread.", true);
  }

  public static void previous() {
    int i = Debugger.ObjectsPList.getSelectedIndex();
    boolean nextThread = true; //Debugger.threadNextCommand.getState();
    TimeStamp ts = TimeStamp.currentTime().getPreviousSwitchThisThread();
    if ((nextThread) && (ts != null)) ts = ts.getPrevious();
    if (ts != null)
      Debugger.revert(ts);
    else
      Debugger.message("No previous context switch", true);
  }

  public static void next() {
    int i = Debugger.ObjectsPList.getSelectedIndex();
    boolean nextThread = true; //Debugger.threadNextCommand.getState();
    TimeStamp ts = TimeStamp.currentTime().getNextSwitchThisThread();
    if ((nextThread) && (ts != null)) ts = ts.getNext();
    if (ts != null)
      Debugger.revert(ts);
    else
      Debugger.message("No next context switch", true);
  }


  public static void last() {
    int i = Debugger.ObjectsPList.getSelectedIndex();
    TimeStamp ts = TimeStamp.currentTime().getLastThisThread();
    if ((ts != null) && (ts.laterThan(TimeStamp.currentTime())))
      Debugger.revert(ts);
    else
      Debugger.message("Last time stamp this thread.", true);
  }

}
	    
