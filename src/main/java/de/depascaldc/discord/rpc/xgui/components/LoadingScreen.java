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

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import de.depascaldc.discord.rpc.util.SwingUtil;

public class LoadingScreen {
	private JWindow window;
	private long startTime;
	private int minimumMilliseconds;

	private LoadingScreen() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		window = new JWindow();
		SwingUtil.installGtkPopupBugWorkaround();
		window.setLayout(new BorderLayout());
		window.setAlwaysOnTop(true);
		ImageIcon imageIcon = SwingUtil.newImageIcon("/progress.gif");
		JLabel label = new JLabel(imageIcon, SwingConstants.CENTER);
		label.setBackground(Color.darkGray.darker().darker());
		window.getContentPane().setBackground(Color.darkGray.darker().darker());
		window.getContentPane().add(BorderLayout.NORTH, label);
		window.getContentPane().add(BorderLayout.SOUTH, new ProgressPane());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setBounds((int) ((screenSize.getWidth() - imageIcon.getIconWidth()) / 2),
				(int) ((screenSize.getHeight() - imageIcon.getIconHeight()) / 2), (int) imageIcon.getIconWidth() + 50,
				(int) imageIcon.getIconHeight() + 50);
		window.pack();
		window.setBackground(Color.darkGray.darker().darker());
	}

	public static LoadingScreen get() {
		return new LoadingScreen();
	}

	public LoadingScreen show(int minimumMilliseconds) {
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

	public class ProgressPane extends JPanel {
		private static final long serialVersionUID = 8705960182045529776L;
		private MediaProgressBar pb;
		private int value = 0;
		private int delta = 1;

		public ProgressPane() {
			setBackground(Color.darkGray.darker().darker());
			setLayout(new GridBagLayout());
			pb = new MediaProgressBar();
			add(pb);
			Timer timer = new Timer(minimumMilliseconds / 100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					value += delta;
					pb.setValue(value);
				}
			});
			timer.start();
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(400, 20);
		}
	}

	public class MediaProgressBar extends JProgressBar {
		private static final long serialVersionUID = 6218957008678343385L;

		public MediaProgressBar() {
			setUI(new MediaProgressBarUI());
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(400, 15);
		}
	}

	public class MediaProgressBarUI extends BasicProgressBarUI {
		private Handler handler;
		private double renderProgress = 0;
		private double targetProgress = 0;
		private double progressDelta = 0.01;
		private Timer repaintTimer;
		private Timer paintTimer;

		public MediaProgressBarUI() {
			repaintTimer = new Timer(25, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					progressBar.repaint();
				}
			});
			repaintTimer.setRepeats(false);
			repaintTimer.setCoalesce(true);
			paintTimer = new Timer(40, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (progressDelta < 0) {
						if (renderProgress + progressDelta < targetProgress) {
							((Timer) e.getSource()).stop();
							renderProgress = targetProgress + progressDelta;
						}
					} else {
						if (renderProgress + progressDelta > targetProgress) {
							((Timer) e.getSource()).stop();
							renderProgress = targetProgress - progressDelta;
						}
					}
					renderProgress += progressDelta;
					requestRepaint();
				}
			});
		}

		protected void requestRepaint() {
			repaintTimer.restart();
		}

		@Override
		protected void installDefaults() {
			super.installDefaults();
			progressBar.setOpaque(false);
			progressBar.setBorder(null);
		}

		public void setRenderProgress(double value) {
			if (value != targetProgress) {
				paintTimer.stop();
				targetProgress = value;
				if (targetProgress < renderProgress && progressDelta > 0) {
					progressDelta *= -1;
				} else if (targetProgress > renderProgress && progressDelta < 0) {
					progressDelta *= -1;
				}
				paintTimer.start();
			}
		}

		public double getRenderProgress() {
			return renderProgress;
		}

		@Override
		public void paint(Graphics g, JComponent c) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int iStrokWidth = 3;
			g2d.setStroke(new BasicStroke(iStrokWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.setColor(c.getBackground());
			g2d.setBackground(c.getBackground());
			int width = c.getWidth();
			int height = c.getHeight();
			RoundRectangle2D outline = new RoundRectangle2D.Double((iStrokWidth / 2), (iStrokWidth / 2),
					width - iStrokWidth, height - iStrokWidth, height, height);
			g2d.draw(outline);
			int iInnerHeight = height - (iStrokWidth * 4);
			int iInnerWidth = width - (iStrokWidth * 4);
			iInnerWidth = (int) Math.round(iInnerWidth * renderProgress);
			int x = iStrokWidth * 2;
			int y = iStrokWidth * 2;
			Point2D start = new Point2D.Double(x, y);
			Point2D end = new Point2D.Double(x, y + iInnerHeight);
			float[] dist = { 0.0f, 0.25f, 1.0f };
			Color[] colors = { c.getBackground(), c.getBackground().brighter(), c.getBackground().darker() };
			LinearGradientPaint p = new LinearGradientPaint(start, end, dist, colors);
			g2d.setPaint(p);
			RoundRectangle2D fill = new RoundRectangle2D.Double(iStrokWidth * 2, iStrokWidth * 2, iInnerWidth,
					iInnerHeight, iInnerHeight, iInnerHeight);
			g2d.fill(fill);
			g2d.dispose();
		}

		@Override
		protected void installListeners() {
			super.installListeners();
			progressBar.addChangeListener(getChangeHandler());
		}

		protected ChangeListener getChangeHandler() {
			return getHandler();
		}

		protected Handler getHandler() {
			if (handler == null) {
				handler = new Handler();
			}
			return handler;
		}

		protected class Handler implements ChangeListener {
			@Override
			public void stateChanged(ChangeEvent e) {
				BoundedRangeModel model = progressBar.getModel();
				int newRange = model.getMaximum() - model.getMinimum();
				double dProgress = (double) (model.getValue() / (double) newRange);
				if (dProgress < 0) {
					dProgress = 0;
				} else if (dProgress > 1) {
					dProgress = 1;
				}
				setRenderProgress(dProgress);
			}
		}
	}

}
