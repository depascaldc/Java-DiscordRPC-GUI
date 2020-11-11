/**
 *   Copyright © 2020 | depascaldc <https://depascaldc.xyz/> | Discord: [depascaldc]#4093
 *   
 *    ____  _                       _       ____  ____   ____ 
 *   |  _ \(_)___  ___ ___  _ __ __| |     |  _ \|  _ \ / ___|
 *   | | | | / __|/ __/ _ \| '__/ _` |_____| |_) | |_) | |    
 *   | |_| | \__ \ (_| (_) | | | (_| |_____|  _ <|  __/| |___ 
 *   |____/|_|___/\___\___/|_|  \__,_|     |_| \_\_|    \____|
 *                                                         
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *   
 */

package de.depascaldc.discord.rpc.appdirs.impl;

import java.util.Map;

import de.depascaldc.discord.rpc.appdirs.AppDirs;

public class UnixAppDirs extends AppDirs {
  public static final String XDG_CONFIG_DIRS = "XDG_CONFIG_DIRS";

  public static final String XDG_DATA_DIRS = "XDG_DATA_DIRS";

  public static final String XDG_CACHE_HOME = "XDG_CACHE_HOME";

  public static final String XDG_CONFIG_HOME = "XDG_CONFIG_HOME";

  public static final String XDG_DATA_HOME = "XDG_DATA_HOME";

  protected final Map<String, String> sysEnv;

  public String getUserDataDir(String appName, String appVersion,
      String appAuthor, boolean roaming) {
    String dir = getOrDefault(XDG_DATA_HOME,
        buildPath(home(), "/.local/share"));
    return buildPath(dir, appName, appVersion);
  }

  public String getUserConfigDir(String appName, String appVersion,
      String appAuthor, boolean roaming) {
    String dir = getOrDefault(XDG_CONFIG_HOME, buildPath(home(), "/.config"));
    return buildPath(dir, appName, appVersion);
  }

  public String getUserCacheDir(String appName, String appVersion,
      String appAuthor) {
    String dir = getOrDefault(XDG_CACHE_HOME, buildPath(home(), "/.cache"));
    return buildPath(dir, appName, appVersion);
  }

  public String getSiteDataDir(String appName, String appVersion,
      String appAuthor, boolean multiPath) {
    String xdgDirs = sysEnv.get(XDG_DATA_DIRS);
    if (xdgDirs == null) {
      String primary = buildPath("/usr/local/share", appName, appVersion);
      String secondary = buildPath("/usr/share", appName, appVersion);
      return multiPath ? joinPaths(primary, secondary) : primary;
    }

    String[] xdgDirArr = splitPaths(xdgDirs);
    if (multiPath) {
      return buildMultiPaths(appName, appVersion, xdgDirArr);
    } else {
      return buildPath(xdgDirArr[0], appName, appVersion);
    }
  }

  public String getSiteConfigDir(String appName, String appVersion,
      String appAuthor, boolean multiPath) {
    String xdgDirs = sysEnv.get(XDG_CONFIG_DIRS);
    if (xdgDirs == null) {
      return buildPath("/etc/xdg", appName, appVersion);
    }

    String[] xdgDirArr = splitPaths(xdgDirs);
    if (multiPath) {
      return buildMultiPaths(appName, appVersion, xdgDirArr);
    } else {
      return buildPath(xdgDirArr[0], appName, appVersion);
    }
  }

  protected String buildMultiPaths(String appName, String appVersion,
      String[] xdgDirArr) {
    int dirNum = xdgDirArr.length;
    String[] newDirs = new String[dirNum];
    for (int i = 0; i < dirNum; i++) {
      newDirs[i] = buildPath(xdgDirArr[i], appName, appVersion);
    }
    return joinPaths(newDirs);
  }

  public String getUserLogDir(String appName, String appVersion,
      String appAuthor) {
    String dir = getOrDefault(XDG_CACHE_HOME, buildPath(home(), "/.cache"));
    return buildPath(dir, appName, "/logs", appVersion);
  }

  @Override
  public String getSharedDir(String appName, String appVersion,
      String appAuthor) {
    return buildPath("/srv", appName, appVersion);
  }

  public String getOrDefault(String key, String def) {
    String val = sysEnv.get(key);
    return val == null ? def : val;
  }

  public UnixAppDirs(Map<String, String> sysEnv) {
    super();
    this.sysEnv = sysEnv;
  }

  public UnixAppDirs() {
    super();
    this.sysEnv = System.getenv();
  }
}
