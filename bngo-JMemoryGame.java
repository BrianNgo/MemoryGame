//
// Name:    Ngo, Brian
// Extra Credit: #1
// Due:     12/11/2014
// Course:  CS-245-01-f14
//
// Description:
//       A simple Memory Game
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class JMemoryGame implements ActionListener{
    private String[] mFileName = {"card0.gif", "card1.gif", "card2.gif", 
				  "card3.gif", "card4.gif","card5.gif", 
				  "card6.gif", "card7.gif"};

    ArrayList<ImageIcon> mIcons;
    JButton[] mButtonArray;
    boolean[] mIsFlipped;
    ImageIcon mDefaultIcon;
    JFrame mFrame;
    JMenuBar mMenuBar;
    boolean isFirstCardOn = false, 
	    isSecondCardOn = false;
    int mFirstCardIndex = -1, 
	mSecondCardIndex = -1;
    Timer mTimer;
    long mCounter;

    JMemoryGame() {
	mFrame = new JFrame("JMemoryGame");
	mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mFrame.setSize(300, 450);
	mFrame.setLayout(new GridLayout(4, 4));
	mDefaultIcon = new ImageIcon("card8.gif");

	createMenuBar();
	createIconArray();
	createButtonArray();
	createTimer();
	
	mFrame.setLocationRelativeTo(null);
	mFrame.setVisible(true);
    }

    public void createMenuBar() {
	mMenuBar = new JMenuBar();
	JMenu mMainMenu = new JMenu("Menu");
	JMenuItem mShow = new JMenuItem("Show all tiles");
	JMenuItem mReset = new JMenuItem("Reset the game");
	JMenuItem mAbout = new JMenuItem("About...");
	JMenuItem mExit = new JMenuItem("Exit");

	mShow.addActionListener(this);
	mReset.addActionListener(this);
	mAbout.addActionListener(this);
	mExit.addActionListener(this);
	
	mMainMenu.add(mShow);
	mMainMenu.add(mReset);
	mMainMenu.addSeparator();
	mMainMenu.add(mAbout);
	mMainMenu.addSeparator();
	mMainMenu.add(mExit);
	mMenuBar.add(mMainMenu);

	mFrame.setJMenuBar(mMenuBar);
    }

    public void createIconArray() {
	ImageIcon[] mIconArray = new ImageIcon[16];
        mIcons = new ArrayList<ImageIcon>();
	int index = 0;
	for (int ii = 0; ii < 16; ++ii) {
	    if (index >= mFileName.length)
		index = 0;
	    mIconArray[ii] = new ImageIcon(mFileName[index]);
	    mIconArray[ii].setDescription(mFileName[index]);
	    mIcons.add(mIconArray[ii]);
	    ++index;
	}
	Collections.shuffle(mIcons);

    }

    public void createButtonArray() {
	mButtonArray = new JButton[16];
	for (int ii = 0; ii < 16; ++ii) 
	    createButton(ii);
	mIsFlipped = new boolean[16];
	for (int ii = 0; ii < 16; ++ii) 
	    mIsFlipped[ii] = false;
    }

    public void createTimer() {
	ActionListener mTimerAction = new ActionListener() {
		public void actionPerformed(ActionEvent xEvent) {
		    if (mCounter == 0) {
			mTimer.stop();
			validateSelectedCards();
		    } else 
			mCounter--;
		}
	    };
	mTimer = new Timer(1000, mTimerAction);
    }
    
    private void createButton(final int xIndex) {
	mButtonArray[xIndex] = new JButton(mDefaultIcon);
	mButtonArray[xIndex].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent xEvent) {
		    mIsFlipped[xIndex] = true;
		    if (!isFirstCardOn) {
			mButtonArray[xIndex].setIcon(mIcons.get(xIndex));
			mFirstCardIndex = xIndex;
			isFirstCardOn = true;
		    } else if (!isSecondCardOn) {
			mButtonArray[xIndex].setIcon(mIcons.get(xIndex));
			mSecondCardIndex = xIndex;
			isSecondCardOn = true;
			mCounter = 1;
			mTimer.setInitialDelay(0);
			mTimer.start();
		    }

		    if (isFinishPlaying()) 
			JOptionPane.showMessageDialog(mFrame, 
						      "Congratulation, you won!!");
		}
	    });
	mFrame.add(mButtonArray[xIndex]);
    }
    
    private JButton getButton(int xIndex) {
	return mButtonArray[xIndex];
    }

    private void validateSelectedCards() {
	String mFirstDescription = ((ImageIcon) mButtonArray[mFirstCardIndex].getIcon()).getDescription();
	String mSecondDescription = ((ImageIcon) mButtonArray[mSecondCardIndex].getIcon()).getDescription();
	if (!mFirstDescription.equals(mSecondDescription)) {
	    mButtonArray[mFirstCardIndex].setIcon(mDefaultIcon);
	    mButtonArray[mSecondCardIndex].setIcon(mDefaultIcon);
	    mIsFlipped[mFirstCardIndex] = false;
	    mIsFlipped[mSecondCardIndex] = false;
	}  
	mFirstCardIndex = -1;
	mSecondCardIndex = -1;
	isFirstCardOn = false;
	isSecondCardOn = false;
    }

    private boolean isFinishPlaying() {
	for (int ii = 0; ii < 16; ++ii) {
	    if (mIsFlipped[ii] == false)
		return false;
	}
	return true;
    }

    private void showAllTiles() {
	for (int ii = 0; ii < 16; ++ii) {
	    mButtonArray[ii].setIcon(mIcons.get(ii));
	    mIsFlipped[ii] = true;
	}
    }

    private void resetTheGame() {
	for (int ii = 0; ii < 16; ++ii) {
	    mButtonArray[ii].setIcon(mDefaultIcon);
	    mIsFlipped[ii] = false;
	}
	Collections.shuffle(mIcons);
    }
    
    public void actionPerformed(ActionEvent xEvent) {
	switch(xEvent.getActionCommand()) {
	case "Show all tiles":
	    showAllTiles();
	    break;
	case "Reset the game":
	    resetTheGame();
	    break;
	case "About...":
	    JOptionPane.showMessageDialog(mFrame, 
					  "(c) Brian Ngo");
	    break;
	case "Exit":
	    System.exit(0);
	    break;
	default:
	    break;
	}
    }
    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    new JMemoryGame();		    
		}
	    });
    }
}
