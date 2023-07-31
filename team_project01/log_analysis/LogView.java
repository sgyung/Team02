package log_analysis;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class LogView extends JFrame{

	private JTextArea jta=new JTextArea();
	private JButton jbtnLoad=new JButton("Load");
	private JButton jbtnSet=new JButton("Set");
	private JButton jbtnView=new JButton("View");
	private JButton jbtnReport=new JButton("Report");
	private JTextField jtfStart = new JTextField(15);
	private JTextField jtfEnd = new JTextField(15);
	private JLabel jlblPath = new JLabel("");
	private JButton jbtnReset = new JButton("Reset");
	private LoginView loginView;
	
	public LogView(LoginView loginView) {
        this.loginView = loginView;

        // 이미지 파일 경로 설정
        ImageIcon imageIcon;
		try {
			String mainImgPath = "/img/mainHud_back.png";
			imageIcon = new ImageIcon(ImageIO.read(LogView.class.getResourceAsStream(mainImgPath)));
			Image image = imageIcon.getImage();
			// JLabel을 이용하여 이미지를 배경으로 설정
			setContentPane(new JLabel(new ImageIcon(image)));
		    
		} catch (IOException e) {
			e.printStackTrace();
		}

		//생성
		//JTextArea에 스크롤 추가
		JScrollPane jsp=new JScrollPane(jta);
		
		// selectFile 라벨 생성
		JLabel jlblPathName = new JLabel("Seleted File : ");

		
		JPanel rangePanel=new JPanel();
		JLabel jlblStart = new JLabel("Start Line : ");
		JLabel jlblEnd = new JLabel("End Line : ");
		
		// PrintInfoActionListener 설정
		LogEvt pial = new LogEvt(this);
		jbtnLoad.addActionListener(pial);
		jbtnView.addActionListener(pial);
		jbtnReport.addActionListener(pial);
		jbtnSet.addActionListener(pial);
		jbtnReset.addActionListener(pial);
		jtfStart.addActionListener(pial);
		addWindowListener(pial);
		
		//배치
		add(jsp);
		add(jbtnLoad);
		add(jbtnSet);
		add(jbtnView);
		add(jbtnReport);
		add(rangePanel);
		add(jlblStart);
		add(jlblEnd);
		add(jtfStart);
		add(jtfEnd);
		add(jlblPath);
		add(jlblPathName);
		add(jbtnReset);
		
		
		// viewBtn 설정
		jbtnView.setFont(new Font("Dialog", Font.ITALIC, 60));
		jbtnView.setForeground(new Color(0x7BE9FC));
		jbtnView.setFocusPainted(false);
	    jbtnView.setContentAreaFilled(false);
	    
	    // reportBtn 설정
	    jbtnReport.setFont(new Font("Dialog", Font.ITALIC, 60));
	    jbtnReport.setForeground(new Color(0x7BE9FC));
	    jbtnReport.setFocusPainted(false);
	    jbtnReport.setContentAreaFilled(false);
	    
	    // setBtn 설정
	    jbtnSet.setFont(new Font("Dialog", Font.ITALIC, 40));
	    jbtnSet.setForeground(new Color(0x7BE9FC));
	    jbtnSet.setFocusPainted(false);
	    jbtnSet.setContentAreaFilled(false);
	    
	    // loadBtn 설정
	    jbtnLoad.setFont(new Font("Dialog", Font.ITALIC, 60));
	    jbtnLoad.setForeground(new Color(0x7BE9FC));
	    jbtnLoad.setFocusPainted(false);
	    jbtnLoad.setContentAreaFilled(false);
	    
	    // resetBtn 설절
	    jbtnReset.setFont(new Font("Dialog", Font.ITALIC, 40));
	    jbtnReset.setForeground(new Color(0x7BE9FC));
	    jbtnReset.setFocusPainted(false);
	    jbtnReset.setContentAreaFilled(false);
	    
	    
	    // jlblStart, jlblEnd 설정
	    jlblStart.setFont(new Font("Dialog", Font.ITALIC, 25));
	    jlblStart.setForeground(new Color(0x7BE9FC));

	    jlblEnd.setFont(new Font("Dialog", Font.ITALIC, 25));
	    jlblEnd.setForeground(new Color(0x7BE9FC));
	    
	    // jtfStart, jtfEnd 설정
	    jtfStart.setOpaque(false);
	    jtfStart.setForeground(new Color(0x7BE9FC));
	    jtfStart.setFont(new Font("Dialog", Font.ITALIC, 20));
	    Color borderColor = new Color(0x7BE9FC);
        int borderThickness = 2; // 테두리 두께 (픽셀 단위)
        jtfStart.setBorder(new LineBorder(borderColor, borderThickness));
	
	    jtfEnd.setOpaque(false);
	    jtfEnd.setForeground(new Color(0x7BE9FC));
	    jtfEnd.setFont(new Font("Dialog", Font.ITALIC, 20));
        jtfEnd.setBorder(new LineBorder(borderColor, borderThickness));
        
        // jlblPathName 설정
        jlblPathName.setFont(new Font("Dialog", Font.BOLD, 23));
        jlblPathName.setForeground(new Color(0xF3EC79));
        
        // jlblPath 설정
        jlblPath.setFont(new Font("Dialog", Font.ITALIC, 20));
        jlblPath.setForeground(new Color(0x00B602));
        
        
		jsp.setBorder(new TitledBorder("Print"));
		// Range Title 설정
		rangePanel.setBorder(new TitledBorder(new LineBorder(new Color(0x02959F),3),"Range"));
		TitledBorder RangeTitledBorder = (TitledBorder) rangePanel.getBorder();
		RangeTitledBorder.setTitleFont(new Font("Dialog", Font.ITALIC, 20)); // 원하는 폰트 크기로 변경 (30으로 설정)
		RangeTitledBorder.setTitleColor(new Color(0xF3EC79));
		
		//크기설정
		setLayout(null);
		
		jta.setBackground(new Color(0x020303));
		jta.setFont(new Font("Dialog", Font.ITALIC, 20));
		jta.setForeground(Color.green);
		
		jsp.setBounds(120,80,1380,350);
		jbtnLoad.setBounds(1200,450,300,100);
		jbtnSet.setBounds(480,510,120,250);
		jbtnView.setBounds(850,580,300,200);
		jbtnReport.setBounds(1200,580,300,200);
		jlblStart.setBounds(140,550,150,60);
		jlblEnd.setBounds(140,650,150,60);
		jtfStart.setBounds(270,550,180,60);
		jtfEnd.setBounds(270,650,180,60);
		jlblPathName.setBounds(640,470,150,60);
		jlblPath.setBounds(800,470,250,60);
		jbtnReset.setBounds(650,580,150,200);
		
		rangePanel.setBounds(120,480,500,300);
		
		jtfStart.setOpaque(false);
		jtfEnd.setOpaque(false);
		rangePanel.setOpaque(false);
		
		// Report버튼 비활성화
		jbtnReport.setEnabled(!loginView.getUndefinedId().contains(loginView.getJtfId().getText()));
		
		LineBorder lineBorder = new LineBorder(new Color(0x02959F), 3);

        // TitledBorder 객체 생성, 경계선 스타일과 색상을 설정한 LineBorder 전달
        TitledBorder titledBorder = new TitledBorder(lineBorder, "Log");

        // 제목의 색상 설정 (색상: 0xF3EC79)
        titledBorder.setTitleColor(new Color(0xF3EC79));
        Font titleFont = new Font("Dialog", Font.BOLD, 20);
        titledBorder.setTitleFont(titleFont);

        // JTextArea에 TitledBorder 적용
        jsp.setBorder(titledBorder);
		jsp.setBackground(new Color(0x02959F));
		
		jbtnSet.addActionListener(null);
		
		// 화면 중앙 설정
		setBounds(loginView.getX(),loginView.getY(),1600,900);
		
		//원도우 크기설정
		setTitle("로그 정보");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}//PrintInfo

	public JTextArea getJta() {
		return jta;
	}

	public JButton getJbtnLoad() {
		return jbtnLoad;
	}

	public JButton getJbtnSet() {
		return jbtnSet;
	}

	public JButton getJbtnView() {
		return jbtnView;
	}

	public JButton getJbtnReport() {
		return jbtnReport;
	}

	public JTextField getJtfStart() {
		return jtfStart;
	}

	public JTextField getJtfEnd() {
		return jtfEnd;
	}

	public JLabel getJlblPath() {
		return jlblPath;
	}

	public JButton getJbtnReset() {
		return jbtnReset;
	}

	public LoginView getLoginView() {
		return loginView;
	}
}//class
