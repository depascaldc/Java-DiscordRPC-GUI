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

package de.depascaldc.discord.rpc.appdirs;

public abstract class AppDirs {
	public String getUserDataDir(String appName, String appVersion, String appAuthor) {
		return getUserDataDir(appName, appVersion, appAuthor, false);
	}

	public abstract String getUserDataDir(String appName, String appVersion, String appAuthor, boolean roaming);

	public String getUserConfigDir(String appName, String appVersion, String appAuthor) {
		return getUserConfigDir(appName, appVersion, appAuthor, false);
	}

	public abstract String getUserConfigDir(String appName, String appVersion, String appAuthor, boolean roaming);

	public abstract String getUserCacheDir(String appName, String appVersion, String appAuthor);

	public String getSiteDataDir(String appName, String appVersion, String appAuthor) {
		return getSiteDataDir(appName, appVersion, appAuthor, false);
	}

	public abstract String getSiteDataDir(String appName, String appVersion, String appAuthor, boolean multiPath);

	public String getSiteConfigDir(String appName, String appVersion, String appAuthor) {
		return getSiteConfigDir(appName, appVersion, appAuthor, false);
	}

	public abstract String getSiteConfigDir(String appName, String appVersion, String appAuthor, boolean multiPath);

	public abstract String getUserLogDir(String appName, String appVersion, String appAuthor);

	public abstract String getSharedDir(String appName, String appVersion, String appAuthor);

	protected String home() {
		return System.getProperty("user.home");
	}

	protected String buildPath(String... elems) {
		String separator = System.getProperty("file.separator");
		StringBuilder buffer = new StringBuilder();
		String lastElem = null;
		for (String elem : elems) {
			if (elem == null) {
				continue;
			}

			if (lastElem == null) {
				buffer.append(elem);
			} else if (lastElem.endsWith(separator)) {
				buffer.append(elem.startsWith(separator) ? elem.substring(1) : elem);
			} else {
				if (!elem.startsWith(separator)) {
					buffer.append(separator);
				}
				buffer.append(elem);
			}
			lastElem = elem;
		}
		return buffer.toString();
	}

	protected String joinPaths(String... paths) {
		String separator = System.getProperty("path.separator");
		StringBuilder buffer = new StringBuilder();
		for (String path : paths) {
			if (path == null) {
				continue;
			}

			if (buffer.length() > 0) {
				buffer.append(separator);
			}
			buffer.append(path);
		}
		return buffer.toString();
	}

	protected String[] splitPaths(String paths) {
		String separator = System.getProperty("path.separator");
		return paths.split(separator);
	}
}
