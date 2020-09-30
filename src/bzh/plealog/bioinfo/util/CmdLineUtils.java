/* Copyright (C) 2006-2020 Patrick G. Durand
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.txt
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 */
package bzh.plealog.bioinfo.util;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.plealog.genericapp.api.EZEnvironment;

public class CmdLineUtils {
  private static final String HELP_KEY = "help";
  private static final String H_KEY    = "h";
  
  /**
   * Prepare an option to deal with help.
   */
  public static void setHelpOption(Options opts){
    String msg = "print this message";
    opts.addOption(new Option( HELP_KEY, msg ));
    opts.addOption(new Option( H_KEY, msg ));
  }

  /**
   * Handle the help message.
   */
  public static void printUsage(String toolName, Options opt, String footer) {
    
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(
        toolName,
        "tool [options]", 
        opt, 
        footer);
  }

  /**
   * Convert command-line options to a Apache Commons CLI object.
   * 
   * @param args string array from main program method
   * 
   * @return a command-line object or null. Null is returned in two case:
   * -h or -help is requested, or args parsing failed.
   */
  public static CommandLine handleArguments(String[] args, Options options, String toolName, String footer) {
    GnuParser parser;
    CommandLine line = null;

    try {
      parser = new GnuParser();
      line = parser.parse(options, args, true);
    } catch (Exception exp) {
      System.err.println(exp.getMessage());
      printUsage(toolName, options, footer);
      line = null;
    }
    
    if(line!=null){ 
      if ( line.hasOption( HELP_KEY ) ||  line.hasOption( H_KEY ) || 
          ( line.getArgList().isEmpty() && line.getOptions().length==0 ) ){
        // Initialize the member variable
        printUsage(toolName, options, footer);
        line = null;
      }
    }
    return line;
  }
  /**
   * Replace environment variable names by their values.
   * 
   * @param text a file path that may contain env var, e.g. $HOME/my-file.txt
   * 
   * @return an update file path, e.g. /Users/pgdurand/my-file.txt
   */
  public static String expandEnvVars(String text) {
    Map<String, String> envMap = System.getenv();
    for (Entry<String, String> entry : envMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if (EZEnvironment.getOSType()==EZEnvironment.WINDOWS_OS) {
        text = text.replaceAll("\\%" + key + "\\%", value);
      }
      else {
        text = text.replaceAll("\\$\\{" + key + "\\}", value);
        text = text.replaceAll("\\$" + key + "", value);
      }
    }
    return text;
  }

}
