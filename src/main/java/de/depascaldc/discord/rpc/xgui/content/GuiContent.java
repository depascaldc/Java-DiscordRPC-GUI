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

package de.depascaldc.discord.rpc.xgui.content;

import de.depascaldc.discord.rpc.dc.RichPresenceManager;
import de.depascaldc.discord.rpc.xgui.MainView;
import de.depascaldc.discord.rpc.xgui.components.WindowFiredSucceed;

public class GuiContent {
	
	private RichPresenceManager rpc;
	private MainView view;
	
	private long timeStampStarted;
	private String rpcid = "";
	private String details = "";
	private String state = "";
	private String largeimagekey = "";
	private String largeimagetext = "";
	private String smallimagekey = "";
	private String smallimagetext = "";
	private boolean showStartTime = true;
	
	public GuiContent(MainView view) {
		this.view = view;
		rpc = view.getRpcManager();
		timeStampStarted = 0;
		rpcid = rpc.getRpcid();
		details = rpc.getDetails();
		state = rpc.getState();
		largeimagekey = rpc.getLargeimagekey();
		largeimagetext = rpc.getLargeimagetext();
		smallimagekey = rpc.getSmallimagekey();
		smallimagetext = rpc.getSmallimagetext();
		showStartTime = rpc.isShowStartTime();
	}
	
	public void update() {
		if(WindowFiredSucceed.active) return;
		
		setRpcid(view.getTextField().getText());
		
		setDetails(view.getTextField_1().getText());
		setState(view.getTextField_2().getText());
		
		setLargeimagekey(view.getTextField_3().getText());
		setLargeimagetext(view.getTextField_4().getText());
		
		setSmallimagekey(view.getTextField_5().getText());
		setSmallimagetext(view.getTextField_6().getText());
		
		setShowStartTime(view.getSlider().getValue());
		
	}
	
	public long getTimeStampStarted() {
		return timeStampStarted;
	}
	public void setTimeStampStarted(long timeStampStarted) {
		this.timeStampStarted = timeStampStarted;
	}
	public String getRpcid() {
		return rpcid;
	}
	public void setRpcid(String rpcid) {
		this.rpcid = rpcid;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLargeimagekey() {
		return largeimagekey;
	}
	public void setLargeimagekey(String largeimagekey) {
		this.largeimagekey = largeimagekey;
	}
	public String getLargeimagetext() {
		return largeimagetext;
	}
	public void setLargeimagetext(String largeimagetext) {
		this.largeimagetext = largeimagetext;
	}
	public String getSmallimagekey() {
		return smallimagekey;
	}
	public void setSmallimagekey(String smallimagekey) {
		this.smallimagekey = smallimagekey;
	}
	public String getSmallimagetext() {
		return smallimagetext;
	}
	public void setSmallimagetext(String smallimagetext) {
		this.smallimagetext = smallimagetext;
	}
	public boolean isShowStartTime() {
		return showStartTime;
	}
	public void setShowStartTime(boolean showStartTime) {
		this.showStartTime = showStartTime;
	}
	
	public RichPresenceManager getRpc() {
		return rpc;
	}
	
	public MainView getView() {
		return view;
	}
}
