package log_analysis;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

public class LoginEvt extends WindowAdapter implements ActionListener {
	
	private LoginView loginView;
	
	public LoginEvt(LoginView loginView) {
		this.loginView = loginView;
		loginView.getUndefinedId().add("root");
	}

	public void addUndefinedId(String id) {
		loginView.getUndefinedId().add(id);		
	}
	
	public boolean isUndefinedId(String id) {
		boolean flag = loginView.getUndefinedId().contains(id)? false : true;
		return flag;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String id = loginView.getJtfId().getText();
		
		if(ae.getSource() == loginView.getJtfId()) {
			if(!loginView.getJtfId().getText().isEmpty()) {
				loginView.getJtfPw().requestFocus();
			}
		}
		
		if (loginView.getJbtnLogin().equals(ae.getSource()) || ae.getSource() == loginView.getJtfPw()) {
			String strPw = String.valueOf(loginView.getJtfPw().getPassword());
			
			UserInfo ui = new UserInfo();
			
			String storedPw = ui.getUser().get(id);
			
			  if (!ui.getUser().containsKey(id)) {
	                JOptionPane.showMessageDialog(null, "존재하지 않는 아이디입니다.");
	                loginView.getJtfId().setText("");
	                loginView.getJtfPw().setText("");
	                loginView.getJtfId().requestFocus();
	            } else if (storedPw.equals(strPw)) {
	            	
	                if (!isUndefinedId(id)) {
	                    JOptionPane.showMessageDialog(null, "문서를 생성할 수 있는 권한이 없음");
	                }
	            	
	            	loginView.dispose();
	                
	                new LogView(loginView);
	                
	            } else {
	                JOptionPane.showMessageDialog(null, "아이디 및 비밀번호를 확인하세요!!");
	                loginView.getJtfId().setText("");
	                loginView.getJtfPw().setText("");
	                loginView.getJtfId().requestFocus();
	            }
		}
	}
	
	

	@Override
	public void windowClosing(WindowEvent e) {
		loginView.dispose();
	}

	public LoginView getLoginView() {
		return loginView;
	}

//	public Map<String, String> getLoginInfo() {
//		return loginInfo;
//	}

//	public LogView getLogView() {
//		return logView;
//	}
//	
}
