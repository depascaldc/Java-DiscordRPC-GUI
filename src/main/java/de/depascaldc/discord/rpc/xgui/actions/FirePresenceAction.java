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

package de.depascaldc.discord.rpc.xgui.actions;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import de.depascaldc.discord.rpc.util.SwingUtil;
import de.depascaldc.discord.rpc.xgui.MainView;
import de.depascaldc.discord.rpc.xgui.WindowFiredSucceed;

public class FirePresenceAction implements MouseListener {
	
	private MainView view;
	
	public FirePresenceAction(MainView view) {
		this.view = view;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(!SwingUtil.canClickAgain()) return;
		if(WindowFiredSucceed.active) return;
		SwingUtil.invokeLater(() -> {
			new Thread(() -> {
				view.getGuiContent().update();
			}).start();
		});
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton btnFirePresence = (JButton) e.getSource();
		btnFirePresence.setBackground(new Color(11, 11, 11));
		btnFirePresence.setForeground(Color.WHITE);
		btnFirePresence.setBorder(null);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton btnFirePresence = (JButton) e.getSource();
		btnFirePresence.setBackground(new Color(128, 128, 128));
		btnFirePresence.setForeground(new Color(128, 0, 0));
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	public MainView getView() {
		return view;
	}

}
