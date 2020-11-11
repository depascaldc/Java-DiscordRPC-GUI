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

import java.awt.event.*;

import de.depascaldc.discord.rpc.util.InternetBrowser;
import de.depascaldc.discord.rpc.util.SwingUtil;
import de.depascaldc.discord.rpc.util.exceptions.ExceptionUtil;

public class IssueActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(SwingUtil.canClickAgain()) {
			SwingUtil.invokeLater(() -> {
				try {
					InternetBrowser.open("https://github.com/depascaldc/Java-DiscordRPC-GUI/issues/new");
				} catch (Exception e) {
					ExceptionUtil.printStackTrace(e);
				}
			});
		}
	}

}
