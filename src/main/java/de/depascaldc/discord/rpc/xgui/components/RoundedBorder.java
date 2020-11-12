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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Line2D;
import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {
	private static final long serialVersionUID = -7734219106698603413L;

	private final Color color;
	private final int gap;

	public RoundedBorder(Color c, int gap) {
		this.color = c;
		this.gap = gap;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		super.paintBorder(c, g, x, y, width, height);
		Graphics2D g2d;
		if (g instanceof Graphics2D) {
			g2d = (Graphics2D) g;
			g2d.setColor(color);
			g2d.draw(new Line2D.Double((double) x, (double) y + 10, (double) x + 3, (double) y + 3));
			g2d.draw(new Line2D.Double((double) x + 3, (double) y + 3, (double) x + 10, (double) y));
			g2d.draw(new Line2D.Double((double) x + 10, (double) y, (double) x + 30, (double) y));
			g2d.draw(new Line2D.Double((double) x + 30, (double) y, (double) x + 33, (double) y + 2));
			g2d.draw(new Line2D.Double((double) x + 33, (double) y + 2, (double) x + 36, (double) y + 8));
			g2d.draw(new Line2D.Double((double) x + 36, (double) y + 8, (double) x + 36, (double) y + 28));
			g2d.draw(new Line2D.Double((double) x + 36, (double) y + 28, (double) x + 34, (double) y + 31));
			g2d.draw(new Line2D.Double((double) x + 34, (double) y + 31, (double) x + 32, (double) y + 33));
			g2d.draw(new Line2D.Double((double) x + 32, (double) y + 33, (double) x + 6, (double) y + 33));
			g2d.draw(new Line2D.Double((double) x + 6, (double) y + 33, (double) x + 3, (double) y + 31));
			g2d.draw(new Line2D.Double((double) x + 3, (double) y + 31, (double) x, (double) y + 27));
			g2d.draw(new Line2D.Double((double) x, (double) y + 27, (double) x, (double) y + 10));
		}
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return (getBorderInsets(c, new Insets(gap, gap, gap, gap)));
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = insets.top = insets.right = insets.bottom = gap;
		return insets;
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}

}
