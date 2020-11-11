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

import com.sun.jna.platform.win32.Guid.GUID;
import com.sun.jna.platform.win32.KnownFolders;
import com.sun.jna.platform.win32.Shell32Util;
import com.sun.jna.platform.win32.ShlObj;
import com.sun.jna.platform.win32.Win32Exception;

import de.depascaldc.discord.rpc.appdirs.AppDirsException;
import de.depascaldc.discord.rpc.appdirs.impl.WindowsAppDirs.FolderId;

public class ShellFolderResolver implements WindowsFolderResolver {
	public String resolveFolder(FolderId folderId) {
		try {
			return Shell32Util.getKnownFolderPath(convertFolderIdToGuid(folderId));
		} catch (Win32Exception e) {
			throw new AppDirsException("SHGetKnownFolderPath returns an error: " + e.getErrorCode());
		} catch (UnsatisfiedLinkError e) {
			// Fallback for pre-vista OSes. #5
			try {
				int folder = convertFolderIdToCsidl(folderId);
				return Shell32Util.getFolderPath(folder);
			} catch (Win32Exception e2) {
				throw new AppDirsException("SHGetFolderPath returns an error: " + e2.getErrorCode());
			}
		}
	}

	private GUID convertFolderIdToGuid(FolderId folderId) {
		switch (folderId) {
		case APPDATA:
			return KnownFolders.FOLDERID_RoamingAppData;
		case LOCAL_APPDATA:
			return KnownFolders.FOLDERID_LocalAppData;
		case COMMON_APPDATA:
			return KnownFolders.FOLDERID_ProgramData;
		default:
			throw new AppDirsException("Unknown folder ID " + folderId + " was specified.");
		}
	}

	protected int convertFolderIdToCsidl(FolderId folderId) {
		switch (folderId) {
		case APPDATA:
			return ShlObj.CSIDL_APPDATA;
		case LOCAL_APPDATA:
			return ShlObj.CSIDL_LOCAL_APPDATA;
		case COMMON_APPDATA:
			return ShlObj.CSIDL_COMMON_APPDATA;
		default:
			throw new AppDirsException("Unknown folder ID " + folderId + " was specified.");
		}
	}
}
