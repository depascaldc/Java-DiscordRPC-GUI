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

package de.depascaldc.discord.rpc.dc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import de.depascaldc.discord.rpc.main.GUIManager;
import de.depascaldc.discord.rpc.xgui.content.GuiContent;

public class RichPresenceManager {

	private File propertiesFile;
	private Properties properties;

	private long timeStampStarted = 0;
	private String rpcid = "";
	private String details = "";
	private String state = "";
	private String largeimagekey = "";
	private String largeimagetext = "";
	private String smallimagekey = "";
	private String smallimagetext = "";
	private boolean showStartTime = false;

	private DiscordRPC discordRPC_Instance;
	private DiscordEventHandlers discordEventHandlers;
	private DiscordRichPresence richPresence;

	private Thread rpcCallbackThread;

	public RichPresenceManager(long timeStarted) {
		this.timeStampStarted = timeStarted;
		this.discordRPC_Instance = DiscordRPC.INSTANCE;
		this.discordEventHandlers = new DiscordEventHandlers();
		this.discordEventHandlers.ready = (user) -> {
		};
		this.discordEventHandlers.errored = (id, err) -> {
			System.err.println(err);
		};
		initProperties();
		if (properties != null) {
			this.rpcid = (properties.getProperty("id", ""));
			this.details = (properties.getProperty("details", ""));
			this.state = (properties.getProperty("state", ""));
			this.largeimagekey = (properties.getProperty("largeimagekey", ""));
			this.largeimagetext = (properties.getProperty("largeimagetext", ""));
			this.smallimagekey = (properties.getProperty("smallimagekey", ""));
			this.smallimagetext = (properties.getProperty("smallimagetext", ""));
			this.showStartTime = propertyIsTrue(properties.getProperty("showtime", ""));
		}
		initClient(this.rpcid);
		reInitPresenceAndStart();
	}

	private void initClient(String id) {
		this.rpcid = id;
		this.discordRPC_Instance.Discord_Initialize(id, discordEventHandlers, true, "");
	}

	public void newPresenceID(String id) {
		this.rpcid = id;
		this.discordRPC_Instance.Discord_Initialize(this.rpcid, discordEventHandlers, true, "");
	}

	public void setAttr(String details, String state, String largeimagekey, String largeimagetext, String smallimagekey,
			String smallimagetext, boolean start) {
		this.details = details;
		this.state = state;
		this.largeimagekey = largeimagekey;
		this.largeimagetext = largeimagetext;
		this.smallimagekey = smallimagekey;
		this.smallimagetext = smallimagetext;
		this.showStartTime = start;
		reInitPresenceAndStart();
	}

	public void setAttributesFromContent(GuiContent content) {
		if (content.getRpcid() != this.rpcid) {
			newPresenceID(content.getRpcid());
		}
		setAttr(content.getDetails(), content.getState(), content.getLargeimagekey(), content.getLargeimagetext(), 
				content.getSmallimagekey(), content.getSmallimagetext(), content.isShowStartTime());
	}

	public void initPresence() {
		this.richPresence = new DiscordRichPresence();
		if (showStartTime)
			richPresence.startTimestamp = System.currentTimeMillis() / 1000;
		this.richPresence.details = this.details;
		this.richPresence.state = this.state;
		this.richPresence.largeImageKey = this.largeimagekey;
		this.richPresence.largeImageText = this.largeimagetext;
		this.richPresence.smallImageKey = this.smallimagekey;
		this.richPresence.smallImageText = this.smallimagetext;
		discordRPC_Instance.Discord_UpdatePresence(this.richPresence);
	}

	public void reInitPresenceAndStart() {
		initPresence();
		startRPC(discordRPC_Instance);
	}

	private void startRPC(DiscordRPC discordRPC_Instance) {
		if (this.rpcCallbackThread != null && !this.rpcCallbackThread.isInterrupted())
			this.rpcCallbackThread.interrupt();
		this.rpcCallbackThread = new Thread(() -> {
			while (!rpcCallbackThread.isInterrupted()) {
				discordRPC_Instance.Discord_RunCallbacks();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ignored) {
				}
			}
		}, "DiscordRPC-Callback-Handler");
		this.rpcCallbackThread.start();
	}

	public void onShutdown() {
		System.out.println("Byeee I Hope i will see you again :P");
		try {
			interruptRPC();
		} catch (Exception e) {
		}
		try {
			saveProperties(true);
		} catch(Exception e2) {
		}
	}

	private void interruptRPC() {
		if (this.rpcCallbackThread != null && !this.rpcCallbackThread.isInterrupted())
			this.rpcCallbackThread.interrupt();
	}

	private void initProperties() {
		File configDir = new File(GUIManager.userConfigDir);
		if (!configDir.exists())
			configDir.mkdirs();

		propertiesFile = new File(GUIManager.userConfigDir + File.separator + "presence.properties");
		properties = new Properties();

		if (!propertiesFile.exists()) {
			properties.setProperty("id", "775930524291039243");
			properties.setProperty("details", "Customize the Rich Presence");
			properties.setProperty("state", "github.com/depascaldc/Java-DiscordRPC-GUI");
			properties.setProperty("largeimagekey", "logo");
			properties.setProperty("largeimagetext", "github.com/depascaldc/Java-DiscordRPC-GUI");
			properties.setProperty("smallimagekey", "jdcof");
			properties.setProperty("smallimagetext", "https://depascaldc.xyz/");
			properties.setProperty("showtime", "yes");
			saveProperties(false);
		} else {
			try {
				InputStream fi = new FileInputStream(propertiesFile);
				properties.load(fi);
				fi.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void saveProperties(boolean useRPCAttributes) {
		if(useRPCAttributes) {
			properties = new Properties();
			properties.setProperty("id", this.rpcid);
			properties.setProperty("details", this.details);
			properties.setProperty("state", this.state);
			properties.setProperty("largeimagekey", this.largeimagekey);
			properties.setProperty("largeimagetext", this.largeimagetext);
			properties.setProperty("smallimagekey", this.smallimagekey);
			properties.setProperty("smallimagetext", this.smallimagetext);
			properties.setProperty("showtime", this.showStartTime ? "yes" : "no");
		}
		try {
			properties.store(new FileOutputStream(propertiesFile), null);
		} catch (Exception e) {
		}
	}
	
	public boolean propertyIsTrue(String propertyStr) {
		return propertyStr.equalsIgnoreCase("yes");
	}

	public String getRpcid() {
		return rpcid;
	}

	public String getDetails() {
		return details;
	}

	public String getState() {
		return state;
	}

	public String getLargeimagekey() {
		return largeimagekey;
	}

	public String getLargeimagetext() {
		return largeimagetext;
	}

	public String getSmallimagekey() {
		return smallimagekey;
	}

	public String getSmallimagetext() {
		return smallimagetext;
	}

	public long getTimeStampStarted() {
		return timeStampStarted;
	}

	public boolean isShowStartTime() {
		return showStartTime;
	}
	
}
