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

import de.depascaldc.discord.rpc.appdirs.impl.MacOSXAppDirs;
import de.depascaldc.discord.rpc.appdirs.impl.ShellFolderResolver;
import de.depascaldc.discord.rpc.appdirs.impl.UnixAppDirs;
import de.depascaldc.discord.rpc.appdirs.impl.WindowsAppDirs;
import de.depascaldc.discord.rpc.appdirs.impl.WindowsFolderResolver;

public class AppDirsFactory {
	private AppDirsFactory() {
		super();
	}

	public static AppDirs getInstance() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.startsWith("mac os x")) {
			return new MacOSXAppDirs();
		} else if (os.startsWith("windows")) {
			WindowsFolderResolver folderResolver = new ShellFolderResolver();
			return new WindowsAppDirs(folderResolver);
		} else {
			// Assume other *nix.
			return new UnixAppDirs();
		}
	}
}
