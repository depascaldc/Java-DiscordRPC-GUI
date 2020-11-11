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

package de.depascaldc.discord.rpc.xgui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JLabel;
import javax.swing.Timer;

import de.depascaldc.discord.rpc.util.SwingUtil;
import de.depascaldc.discord.rpc.util.exceptions.ExceptionUtil;

public class AnimatedLabel extends JLabel {
	private static final long serialVersionUID = 2835454541362414487L;
	private static int charIndex = 0;
	private static int recursiveIndex = 0;
	private static JLabel label;
	private static int charRange = 220;
	
	private static String text;

	public AnimatedLabel() {
		SwingUtil.invokeLater(() -> {
			label = this;
			recursiveIndex = recursiveIndex - charRange;
			try {
				text = readFromInputStream(getClass().getResourceAsStream("/the_longest_text_ever.XD"));
			} catch (Exception e) {
				ExceptionUtil.printStackTrace(e);
			}
			new Thread(() -> {
				Timer timer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int i = charIndex + 1;
						int i2 = charIndex + charRange;
						if (i2 >= text.length()) {
							// TODO add begin when text ends
						} else {
							recursiveIndex = recursiveIndex - charRange;
						}
						String insert = text.substring(i, i2);
						if (i2 >= text.length()) {
							// TODO add begin when text ends
						}
						label.setText(insert);
						charIndex++;
						if (charIndex >= text.length()) {
							charIndex = 0;
							recursiveIndex = recursiveIndex - charRange;
							((Timer) e.getSource()).restart();
						}
					}
				});
				timer.start();
			}).start();
		});
	}

	private static String readFromInputStream(InputStream inputStream) throws Exception {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

}