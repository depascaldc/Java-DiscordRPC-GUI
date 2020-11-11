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

package de.depascaldc.discord.rpc.xgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import de.depascaldc.discord.rpc.util.SwingUtil;

public class WoooW_Screen {
	
	private JWindow window;
	private long startTime;
	private int minimumMilliseconds;
	
	private WoooW_Screen() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		window = new JWindow();
		window.setLayout(new BorderLayout());
		window.setAlwaysOnTop(true);
		ImageIcon imageIcon = SwingUtil.newImageIcon("/wow.gif");
		JLabel label = new JLabel(imageIcon, SwingConstants.CENTER);
		label.setBackground(Color.darkGray.darker().darker());
		window.getContentPane().setBackground(Color.darkGray.darker().darker());
		window.getContentPane().add(BorderLayout.NORTH, label);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setBounds((int) ((screenSize.getWidth() - imageIcon.getIconWidth()) / 2),
				(int) ((screenSize.getHeight() - imageIcon.getIconHeight()) / 2), (int) imageIcon.getIconWidth() + 50,
				(int) imageIcon.getIconHeight() + 50);
		window.pack();
		window.setBackground(Color.darkGray.darker().darker());
	}

	public static WoooW_Screen get() {
		return new WoooW_Screen();
	}

	public WoooW_Screen show(int minimumMilliseconds) {
		this.minimumMilliseconds = minimumMilliseconds;
		startTime = System.currentTimeMillis();
		window.setVisible(true);
		return this;
	}

	public boolean hide() {
		long elapsedTime = System.currentTimeMillis() - startTime;
		try {
			Thread.sleep(Math.max(minimumMilliseconds - elapsedTime, 0));
		} catch (InterruptedException e) {
		}
		window.setVisible(false);
		window.dispose();
		return true;
	}

}
