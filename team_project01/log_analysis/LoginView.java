package log_analysis;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 
@SuppressWarnings("serial")
public class LoginView extends JFrame {
 
	private JButton jbtnLogin;
	private JTextField jtfId;
	private JPasswordField jtfPw;
	private JTextField jtf;
	private Set<String> undefinedId = new HashSet<String>();
 
	private BufferedImage img = null;
 
    // 생성자
    public LoginView() {
        setTitle("로그인");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        // 레이아웃 설정
        setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1600, 900);
        layeredPane.setLayout(null);
        
        // Image path
        
 
        // 패널1
        // 이미지 받아오기
        try {
        	//로그인 이미지 상대경로를 통해 이미지를 가져와 inputStream으로 반환 받는다.
        	String loginImgPath = "/img/login.png";
            img = ImageIO.read(LoginView.class.getResourceAsStream(loginImgPath));
        } catch (IOException e) {
            System.exit(0);
        }
         
        MyPanel panel = new MyPanel();
        panel.setBounds(0, 0, 1600, 900);
         
 
        // 로그인 필드
        jtfId = new JTextField(15);
        jtfId.setBounds(731, 399, 280, 30);
        layeredPane.add(jtfId);
        jtfId.setOpaque(false);
        jtfId.setForeground(new Color(0x7BE9FC));
        jtfId.setFont(new Font("Dialog", Font.ITALIC, 20));
        jtfId.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        // 패스워드
        jtfPw = new JPasswordField(15);
        jtfPw.setBounds(731, 529, 280, 30);
        jtfPw.setOpaque(false);
        jtfPw.setForeground(new Color(0x7BE9FC));
        jtfPw.setFont(new Font("Dialog", Font.ITALIC, 20));
        jtfPw.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        layeredPane.add(jtfPw);
 
        // 로그인버튼 추가
        // 로그인 아이콘 이미지의 상대경로를 통해 절대경로를 반환받는다.
        String loginIconImgPath = "/img/btLogin_hud.png";
        jbtnLogin = new JButton(new ImageIcon(LoginView.class.getResource(loginIconImgPath)));
        jbtnLogin.setBounds(755, 689, 104, 48);
 
        // 버튼 투명처리
        jbtnLogin.setBorderPainted(false);
        jbtnLogin.setFocusPainted(false);
        jbtnLogin.setContentAreaFilled(false);
 
        layeredPane.add(jbtnLogin);
        
        LoginEvt le =new LoginEvt(this);
		jtfId.addActionListener(le);
		jtfPw.addActionListener(le);
		jbtnLogin.addActionListener(le);
		addWindowListener(le);
		
		// 화면 중앙 출력
		Dimension frameSize = this.getSize(); // 프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // 화면 중앙
 
        // 마지막 추가들
        layeredPane.add(panel);
        add(layeredPane);
        setVisible(true);
    }
 
    class MyPanel extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }
    }

	public JButton getJbtnLogin() {
		return jbtnLogin;
	}

	public JTextField getJtfId() {
		return jtfId;
	}

	public JPasswordField getJtfPw() {
		return jtfPw;
	}

	public JTextField getJtf() {
		return jtf;
	}

	public Set<String> getUndefinedId() {
		return undefinedId;
	}

	public BufferedImage getImg() {
		return img;
	}
 
}
