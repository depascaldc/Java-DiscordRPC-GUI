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

package de.depascaldc.discord.rpc.main;

import java.io.File;

import de.depascaldc.discord.rpc.appdirs.AppDirs;
import de.depascaldc.discord.rpc.appdirs.AppDirsFactory;
import de.depascaldc.discord.rpc.dc.RichPresenceManager;
import de.depascaldc.discord.rpc.xgui.MainView;
import de.depascaldc.discord.rpc.xgui.components.LoadingScreen;

public enum GUIManager {

	INSTANCE;

	public static final String CREDENTIALS_APP_NAME = "JavaDRPCTool", CREDENTIALS_AUTHOR = "de.depascaldc",
			CREDENTIALS_CONFIG_VERSION = "v1.0.1";

	private static long timeStarted = System.currentTimeMillis() / 1000;
	public static long ts = System.currentTimeMillis();

	public static MainView rpcGUI;
	private static RichPresenceManager rpcManager;

	private static AppDirs appDirs;

	public static String userConfigDir;
	
	public static LoadingScreen loadingScreen;
	public static Thread loadingThread;
	public static boolean isLoading = true;

	public static void initialize(String mainPath) {
		loadingThread = new Thread(() -> {
			loadingScreen = LoadingScreen.get().show(6000);
			new Thread(() -> {
				while(isLoading) {
					try {
						Thread.sleep(200);
					} catch (Exception e) {
					}
				}
				loadingScreen.hide();
			}).start();
		});
		loadingThread.start();
		// DEFINE DATA STORAGE
		appDirs = AppDirsFactory.getInstance();
		userConfigDir = appDirs.getUserConfigDir(CREDENTIALS_APP_NAME, CREDENTIALS_CONFIG_VERSION, CREDENTIALS_AUTHOR);
		File configDirectory = new File(userConfigDir);
		if(!configDirectory.exists())
			configDirectory.mkdirs();
		
		// INIT AND DEFINE MANAGERS
		rpcManager = new RichPresenceManager(timeStarted);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> rpcManager.onShutdown()));
		
		// DEFINE AND OPEN GUI
		new Thread(() -> {
			try {
				Thread.sleep(4000);
			} catch(Exception e) {}
			rpcGUI = MainView.init(rpcManager);
			rpcGUI.open();
		}).start();
		isLoading = false;
	}
	
	public static void quitApplication() {
		System.exit(0);
	}

}
