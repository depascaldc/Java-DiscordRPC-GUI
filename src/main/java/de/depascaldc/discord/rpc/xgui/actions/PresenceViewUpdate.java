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
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import de.depascaldc.discord.rpc.util.SwingUtil;
import de.depascaldc.discord.rpc.xgui.MainView;
import de.depascaldc.discord.rpc.xgui.components.WindowFiredSucceed;

public class PresenceViewUpdate implements MouseListener {
	
	private MainView view;
	
	public PresenceViewUpdate(MainView view) {
		this.view = view;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(!SwingUtil.canClickAgain()) return;
		if(WindowFiredSucceed.active) return;
		SwingUtil.invokeLater(() -> {
			new Thread(() -> {
				view.updatePreview();
			}).start();
		});
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton btnUpdateView = (JButton) e.getSource();
		btnUpdateView.setBorder(new LineBorder(Color.WHITE, 2));
		btnUpdateView.setForeground(Color.BLACK);
		btnUpdateView.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUpdateView.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnUpdateView.setBackground(Color.DARK_GRAY);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton btnUpdateView = (JButton) e.getSource();
		btnUpdateView.setBorder(new LineBorder(Color.BLACK, 2));
		btnUpdateView.setForeground(Color.WHITE);
		btnUpdateView.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUpdateView.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnUpdateView.setBackground(Color.GRAY);
	}
	
	public MainView getView() {
		return view;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}

